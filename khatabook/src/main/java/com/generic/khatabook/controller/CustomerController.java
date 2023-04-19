package com.generic.khatabook.controller;

import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.InvalidArgumentException;
import com.generic.khatabook.exceptions.InvalidArgumentValueException;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.exchanger.SpecificationClient;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerUpdatable;
import com.generic.khatabook.model.KhatabookDetails;
import com.generic.khatabook.model.KhatabookPaymentSummary;
import com.generic.khatabook.model.PaymentDTO;
import com.generic.khatabook.model.SpecificationDTO;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.IdGeneratorService;
import com.generic.khatabook.service.KhatabookService;
import com.generic.khatabook.service.PaymentService;
import com.generic.khatabook.validator.CustomerValidation;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerController {
    public static final String ASC_DESC = "asc,desc";
    public static final String DATE_CUSTOMER_PRODUCT = "date,customer,product";
    public static final String SORTING_MSG = "%s is invalid value for sorting, possible value will be (%s).";
    @Autowired
    private CustomerService myCustomerService;

    @Autowired
    private KhatabookService myKhatabookService;

    @Autowired
    private PaymentService myPaymentService;
    @Autowired
    private SpecificationClient mySpecificationClient;

    @Autowired
    private IdGeneratorService myIdGeneratorService;

    @Autowired
    private CustomerValidation myCustomerValidation;

    @GetMapping(path = "/khatabook/{khatabookId}/customers")
    public ResponseEntity<KhatabookDetails> getKhatabookDetails(@PathVariable String khatabookId) {

        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        KhatabookDetails khatabookDetails = new KhatabookDetails(khatabook,
                                                                 myCustomerService.getAll(khatabookId),
                                                                 myPaymentService.getPaymentDetailByKhatabookId(
                                                                         khatabookId));
        return ResponseEntity.ok(khatabookDetails);
    }

    @PostMapping(path = "/khatabook/{khatabookId}/customer")
    public ResponseEntity<?> createCustomer(@PathVariable String khatabookId, @RequestBody CustomerDTO customerDTO) {

        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        if (customerDTO.specificationId() != null) {
            try {
                final ResponseEntity<SpecificationDTO> responseEntity = mySpecificationClient.getSpecificationId(
                        customerDTO.specificationId());
                if (!responseEntity.hasBody()) {
                    return ResponseEntity.of(new NotFoundException(AppEntity.SPECIFICATION,
                                                                   customerDTO.specificationId()).get()).build();
                }
            } catch (Exception e) {
                return ResponseEntity.of(new NotFoundException(AppEntity.SPECIFICATION,
                                                               customerDTO.specificationId()).get()).build();
            }
        }


        val customer = customerDTO.copyOf(myIdGeneratorService.generateId());
        myCustomerService.saveAndUpdate(customer);
        EntityModel<CustomerDTO> entityModel = EntityModel.of(customer);
        entityModel.add(linkTo(methodOn(CustomerController.class).getCustomerByCustomerId(null, null, khatabookId,
                                                                                          customer.customerId())).withSelfRel());
        entityModel.add(linkTo(methodOn(CustomerController.class).getCustomerByMsisdn(khatabookId,
                                                                                      customer.msisdn())).withSelfRel());

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{specificationId}").buildAndExpand(
                customerDTO.customerId()).toUri()).body(entityModel);
    }

    @GetMapping(path = "/khatabook/{khatabookId}/customer/{customerId}", produces = {"application/hal+json"})
    public ResponseEntity<?> getCustomerByCustomerId(
            @RequestParam(required = false, defaultValue = "desc") String sorting,
            @RequestParam(required = false, defaultValue = "date") String sortingBy,
            @PathVariable String khatabookId,
            @PathVariable String customerId

                                                    ) {


        if (nonNull(sorting) && !isSortingPossibleValueValid(sorting)) {
            return ResponseEntity.of(new InvalidArgumentValueException(SORTING_MSG.formatted(sorting, ASC_DESC)).get()).build();
        }

        if (nonNull(sortingBy) && !isSortingByPossibleValueValid(sortingBy)) {
            return ResponseEntity.of(new InvalidArgumentValueException("%s is invalid value for sortingBy, " +
                                                                               "possible value will be (%s).".formatted( sorting, DATE_CUSTOMER_PRODUCT)).get()).build();
        }



        final CustomerDTO customerDetails = myCustomerService.getByCustomerId(customerId).get();
        if (Objects.isNull(customerDetails)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }
        val khatabook = myKhatabookService.getKhatabookByKhatabookId(customerDetails.khatabookId());
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final KhatabookPaymentSummary customerDairy = myPaymentService.getPaymentDetailForCustomer(customerDetails,
                                                                                                   sorting, sortingBy);

        KhatabookDetails khatabookDetails = new KhatabookDetails(khatabook, customerDetails, customerDairy);
        final String customerLink = khatabookDetails.getCustomers().stream().findFirst().map(CustomerDTO::customerId).orElse(
                null);

        Link linkForGivePayment = linkTo(methodOn(PaymentController.class).gavenToCustomer(khatabookId,
                                                                                           customerLink,
                                                                                           PaymentDTO.nullOf())).withRel(
                "PayTo");

        Link linkForReceivePayment = linkTo(methodOn(PaymentController.class).receiveFromCustomer(khatabookId,
                                                                                                  customerLink,
                                                                                                  PaymentDTO.nullOf())).withRel(
                "WithdrawFrom");
        Link linkForAggregate = linkTo(methodOn(PaymentAggregationController.class).aggregatedPayment(khatabookId,
                                                                                                      customerLink,
                                                                                                      null)).withRel(
                "Aggregate");

        EntityModel<KhatabookDetails> entityModel = EntityModel.of(khatabookDetails);
        entityModel.add(linkForGivePayment);
        entityModel.add(linkForReceivePayment);
        entityModel.add(linkForAggregate);
        return ResponseEntity.ok(entityModel);
    }

    private boolean isSortingByPossibleValueValid(final String sortingBy) {
        return DATE_CUSTOMER_PRODUCT.contains(sortingBy);
    }

    private boolean isSortingPossibleValueValid(final String sorting) {
        return ASC_DESC.contains(sorting);
    }

    @GetMapping(path = "/khatabook/{khatabookId}/msisdn/{msisdn}", produces = {"application/hal+json"})
    public ResponseEntity<?> getCustomerByMsisdn(@PathVariable String khatabookId, @PathVariable String msisdn) {

        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final CustomerDTO customerDetails = myCustomerService.getCustomerByMsisdn(khatabookId, msisdn);
        if (Objects.isNull(customerDetails)) {
            return ResponseEntity.badRequest().body(new NotFoundException(AppEntity.MSISDN, msisdn));
        }

        final KhatabookPaymentSummary customerDairy = myPaymentService.getPaymentDetailForCustomer(customerDetails);

        KhatabookDetails khatabookDetails = new KhatabookDetails(khatabook, customerDetails, customerDairy);

        Link linkForGivePayment = linkTo(methodOn(PaymentController.class).gavenToCustomerByMsisdn(khatabookId,
                                                                                                   msisdn,
                                                                                                   PaymentDTO.nullOf())).withRel(
                "PayTo");
        Link linkForReceivePayment = linkTo(methodOn(PaymentController.class).receiveFromCustomerByMsisdn(khatabookId,
                                                                                                          msisdn,
                                                                                                          PaymentDTO.nullOf())).withRel(
                "WithdrawFrom");
        Link linkForAggregate = linkTo(methodOn(PaymentAggregationController.class).aggregatedPaymentByMsisdn(
                khatabookId,
                msisdn,
                null)).withRel("Aggregate");

        EntityModel<KhatabookDetails> entityModel = EntityModel.of(khatabookDetails);
        entityModel.add(linkForGivePayment);
        entityModel.add(linkForReceivePayment);
        entityModel.add(linkForAggregate);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping(path = "/khatabook/{khatabookId}/msisdn/{msisdn}")
    public ResponseEntity<?> deleteByMsisdn(@PathVariable String msisdn) {

        final CustomerDTO customerDetails = myCustomerService.getByMsisdn(msisdn);
        if (Objects.isNull(customerDetails)) {
            return ResponseEntity.badRequest().body(new NotFoundException(AppEntity.MSISDN, msisdn));
        }


        myCustomerService.delete(null, customerDetails.msisdn());

        return ResponseEntity.ok(customerDetails);
    }

    @DeleteMapping(path = "/khatabook/{khatabookId}/customer/{customerId}")
    public ResponseEntity<CustomerDTO> deleteByCustomerId(@PathVariable String customerId) {
        final CustomerDTO customerDetails = myCustomerService.getByCustomerId(customerId).get();
        if (Objects.isNull(customerDetails)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }


        myCustomerService.delete(customerId, customerDetails.msisdn());

        return ResponseEntity.ok(customerDetails);
    }

    @PutMapping(path = "/khatabook/{khatabookId}/customer/{customerId}")
    public ResponseEntity<EntityModel<KhatabookDetails>> updateCustomer(@PathVariable String khatabookId,
                                                                        @PathVariable String customerId,
                                                                        @RequestBody CustomerDTO customerDTO) {

        final CustomerDTO customerDetails = myCustomerService.getByCustomerId(customerId).get();
        if (Objects.isNull(customerDetails)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }
        val khatabook = myKhatabookService.getKhatabookByKhatabookId(customerDetails.khatabookId());
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        final KhatabookPaymentSummary customerDairy = myPaymentService.getPaymentDetailForCustomer(customerDetails);

        KhatabookDetails khatabookDetails = new KhatabookDetails(khatabook, customerDetails, customerDairy);
        final String customerLink = khatabookDetails.getCustomers().stream().findFirst().map(CustomerDTO::customerId).orElse(
                null);

        Link linkForGivePayment = linkTo(methodOn(PaymentController.class).gavenToCustomer(khatabookId,
                                                                                           customerLink,
                                                                                           PaymentDTO.nullOf())).withRel(
                "PayTo");

        Link linkForReceivePayment = linkTo(methodOn(PaymentController.class).receiveFromCustomer(khatabookId,
                                                                                                  customerLink,
                                                                                                  PaymentDTO.nullOf())).withRel(
                "WithdrawFrom");
        Link linkForAggregate = linkTo(methodOn(PaymentAggregationController.class).aggregatedPayment(khatabookId,
                                                                                                      customerLink,
                                                                                                      null)).withRel(
                "Aggregate");

        EntityModel<KhatabookDetails> entityModel = EntityModel.of(khatabookDetails);
        entityModel.add(linkForGivePayment);
        entityModel.add(linkForReceivePayment);
        entityModel.add(linkForAggregate);
        return ResponseEntity.ok(entityModel);
    }

    @PatchMapping(path = "/khatabook/{khatabookId}/customer/{customerId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updatePartialCustomer(@PathVariable String khatabookId,
                                                   @PathVariable String customerId,
                                                   @RequestBody Map<String, Object> customerEntities) {
        final CustomerUpdatable customerDetails = myCustomerService.getByCustomerId(customerId).updatable();
        if (Objects.isNull(customerDetails)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.CUSTOMER, customerId).get()).build();
        }
        val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, khatabookId).get()).build();
        }

        for (final Map.Entry<String, Object> member : customerEntities.entrySet()) {
            final Field field = ReflectionUtils.findField(CustomerUpdatable.class, member.getKey());
            if (nonNull(field)) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, customerDetails, member.getValue());
            } else {
                throw new InvalidArgumentException(AppEntity.CUSTOMER, member.getKey());
            }
        }

        if (nonNull(customerDetails.getProductId())) {
            final ProblemDetail customerProductValidation = myCustomerValidation.doCustomerProductValidation(
                    customerDetails.getProductId());
            if (nonNull(customerProductValidation)) {
                return ResponseEntity.of(customerProductValidation).build();
            }
        }

        final CustomerDTO updateCustomer = myCustomerService.saveAndUpdate(customerDetails.build());

        return ResponseEntity.ok(updateCustomer);
    }
}
