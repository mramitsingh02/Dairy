package com.generic.khatabook.specification.controller;

import com.generic.khatabook.common.exceptions.AppEntity;
import com.generic.khatabook.common.exceptions.DuplicateFoundException;
import com.generic.khatabook.common.exceptions.InvalidArgumentException;
import com.generic.khatabook.common.exceptions.NotFoundException;
import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Containers;
import com.generic.khatabook.specification.exchanger.CustomerClient;
import com.generic.khatabook.specification.model.CustomerProductSpecificationUpdatable;
import com.generic.khatabook.specification.model.CustomerSpecificationDTO;
import com.generic.khatabook.specification.model.CustomerSpecificationUpdatable;
import com.generic.khatabook.specification.services.CustomerSpecificationService;
import com.generic.khatabook.specification.services.IdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class CustomerSpecificationController {

    private final CustomerSpecificationService myCustomerSpecificationService;
    private final CustomerClient myCustomerClient;
    private final IdGeneratorService myIdGeneratorService;

    @Autowired
    public CustomerSpecificationController(final CustomerSpecificationService thatCustomerSpecificationService,
                                           final CustomerClient customerClient,
                                           final IdGeneratorService myIdGeneratorService) {
        this.myCustomerSpecificationService = thatCustomerSpecificationService;
        myCustomerClient = customerClient;
        this.myIdGeneratorService = myIdGeneratorService;
    }


    @GetMapping(path = "/khatabook/{khatabookId}/customer/{customerId}/specifications")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/khatabook/{khatabookId}/customer/{customerId}/specification")
    public ResponseEntity<?> create(@PathVariable String khatabookId,
                                    @PathVariable String customerId,
                                    @RequestBody CustomerSpecificationDTO customerSpecificationDTO) {


        customerSpecificationDTO = customerSpecificationDTO.copyOf(myIdGeneratorService.generateId(), khatabookId,
                                                                   customerId);

        ResponseEntity<?> verifyCustomer = checkForKhataBookAndCustomer(khatabookId, customerId);
        if (verifyCustomer.getStatusCode() != HttpStatus.OK) return verifyCustomer;

        ResponseEntity<?> checkForDuplicateRequestCreation = checkForDuplicateRequestCreation(customerSpecificationDTO);
        if (checkForDuplicateRequestCreation.getStatusCode() != HttpStatus.OK) return checkForDuplicateRequestCreation;

        final CustomerSpecificationDTO saved = myCustomerSpecificationService.save(customerSpecificationDTO);


        final Map<String, Object> value = new HashMap<>();
        value.put("specificationId", saved.id());
        myCustomerClient.updatePartialCustomer(khatabookId, customerId, value);


        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/khatabook/{khatabookId" + "}/customer" + "/{customerId" + "}/specification" + "/{specificationId}").buildAndExpand(
                khatabookId,
                customerId,
                saved.id()).toUri()).body(saved);
    }

    private ResponseEntity<Object> checkForDuplicateRequestCreation(final CustomerSpecificationDTO customerSpecificationDTO) {

        if (Objects.isNull(customerSpecificationDTO.id())) {
            return ResponseEntity.ok().build();
        }

        final Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> customerSpecificationExists = myCustomerSpecificationService.get(
                customerSpecificationDTO.id());
        if (customerSpecificationExists.isPresent()) {
            return ResponseEntity.of(new DuplicateFoundException(AppEntity.CUSTOMER_SPECIFICATION,
                                                                 customerSpecificationDTO.id()).get()).build();
        }
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<?> checkForKhataBookAndCustomer(final String khatabookId, final String customerId) {

        try {
            return myCustomerClient.getCustomerByCustomerId(khatabookId, customerId);
        } catch (Exception e) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK,
                                                           AppEntity.CUSTOMER,
                                                           khatabookId,
                                                           customerId).get()).build();
        }
    }

    @GetMapping("/khatabook/{khatabookId}/customer/{customerId}/specification/{specificationId}")
    public ResponseEntity<CustomerSpecificationDTO> getById(@PathVariable String khatabookId,
                                     @PathVariable String customerId,
                                     @PathVariable String specificationId) {

        Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> specificationDTOS = myCustomerSpecificationService.getCustomerSpecification(
                specificationId);
        return ResponseEntity.ok(specificationDTOS.get());
    }

    @DeleteMapping("/khatabook/{khatabookId}/customer/{customerId}/specification")
    public ResponseEntity<?> deleteById(@PathVariable String khatabookId, @PathVariable String customerId) {
        Containers<CustomerSpecificationDTO, CustomerSpecificationUpdatable> specificationDTOS = myCustomerSpecificationService.getCustomerSpecification(
                khatabookId,
                customerId);

        specificationDTOS.forEach(x -> myCustomerSpecificationService.delete(x.get()));
        return ResponseEntity.ok().build();
    }


    @PutMapping("/khatabook/{khatabookId}/customer/{customerId}/specification/{specificationId}")
    public ResponseEntity<?> updateCustomer(@PathVariable String khatabookId,
                                            @PathVariable String customerId,
                                            @PathVariable String specificationId,
                                            @RequestBody CustomerSpecificationDTO customerSpecificationDTO) {

        return ResponseEntity.ok().build();
    }


    @PatchMapping("/khatabook/{khatabookId}/customer/{customerId}/specification/{specificationId}")
    public ResponseEntity<?> patchUpdate(@PathVariable String khatabookId,
                                         @PathVariable String customerId,
                                         @PathVariable String specificationId,
                                         @RequestBody Map<String, Object> requestMap) {


        final CustomerSpecificationUpdatable entityModel = myCustomerSpecificationService.getCustomerSpecification(
                specificationId).updatable();
        if (Objects.isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER_SPECIFICATION,
                                                           specificationId).get()).build();
        }
        for (final Map.Entry<String, Object> member : requestMap.entrySet()) {
            final Field field = ReflectionUtils.findField(CustomerSpecificationUpdatable.class, member.getKey());
            if (Objects.nonNull(field)) {
                ReflectionUtils.makeAccessible(field);
                final Object valueToSet = member.getValue();

                if (List.class.getName().equals(field.getType().getName())) {
                    updateSubEntityOfCustomerProductSpecification(entityModel, field, valueToSet);
                } else if (BigDecimal.class.getName().equals(field.getType().getName())) {
                    ReflectionUtils.setField(field, entityModel, BigDecimal.valueOf((Double) valueToSet));
                } else {
                    ReflectionUtils.setField(field, entityModel, valueToSet);
                }
            } else {
                throw new InvalidArgumentException(AppEntity.CUSTOMER_SPECIFICATION, member.getKey());
            }
        }
        entityModel.setVersion(entityModel.getVersion() + 1);
        entityModel.setUpdatedOn(LocalDateTime.now(Clock.systemDefaultZone()));
        final CustomerSpecificationDTO updateProduct = myCustomerSpecificationService.update(entityModel.build());
        return ResponseEntity.ok(updateProduct);
    }

    private void updateSubEntityOfCustomerProductSpecification(final CustomerSpecificationUpdatable entityModel,
                                                               final Field field,
                                                               final Object valueToSet) {

        switch (field.getGenericType().getTypeName()) {
            case "java.util.List<com.generic.khatabook.specification.model.CustomerProductSpecificationUpdatable>" -> updateCustomerProductSpecificationUpdatable(
                    entityModel,
                    (List<LinkedHashMap<String, Object>>) valueToSet);
            default -> throw new InvalidArgumentException(AppEntity.CUSTOMER_SPECIFICATION, field.getName());
        }
    }

    private CustomerSpecificationUpdatable updateCustomerProductSpecificationUpdatable(final CustomerSpecificationUpdatable entityModel,
                                                                                       final List<LinkedHashMap<String, Object>> valueToSet) {
        List<LinkedHashMap<String, Object>> mapValue = valueToSet;
        for (final LinkedHashMap<String, Object> eachProduct : mapValue) {

            final String productId = (String) eachProduct.get("productId");

            final CustomerProductSpecificationUpdatable oldProductSpecification = entityModel.getProducts(productId);
            for (final Map.Entry<String, Object> customerProductSpecification : eachProduct.entrySet()) {
                final Field eachField = ReflectionUtils.findField(CustomerProductSpecificationUpdatable.class,
                                                                  customerProductSpecification.getKey());
                if (Objects.isNull(eachField)) {
                    throw new InvalidArgumentException(AppEntity.CUSTOMER_SPECIFICATION, eachField.getName());
                }
                ReflectionUtils.makeAccessible(eachField);
                setValueInField(oldProductSpecification, customerProductSpecification.getValue(), eachField);
                if (Objects.isNull(oldProductSpecification.getId())) {
                    entityModel.addProduct(oldProductSpecification);
                }
            }
            return entityModel;

        }
        return null;
    }

    private void setValueInField(final CustomerProductSpecificationUpdatable oldProductSpecification,
                                 final Object valueToSet,
                                 final Field eachField) {
        if (eachField.getGenericType().getTypeName().equals("float")) {
            ReflectionUtils.setField(eachField,
                                     oldProductSpecification,
                                     BigDecimal.valueOf((Double) valueToSet).floatValue());
        }
        if (BigDecimal.class.getName().equals(eachField.getType().getName())) {
            ReflectionUtils.setField(eachField, oldProductSpecification, BigDecimal.valueOf((Double) valueToSet));
        }
    }

}
