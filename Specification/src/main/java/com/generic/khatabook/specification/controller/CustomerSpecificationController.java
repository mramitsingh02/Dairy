package com.generic.khatabook.specification.controller;

import com.generic.khatabook.common.exceptions.AppEntity;
import com.generic.khatabook.common.exceptions.DuplicateFoundException;
import com.generic.khatabook.common.exceptions.NotFoundException;
import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.specification.exchanger.CustomerClient;
import com.generic.khatabook.specification.model.CustomerSpecificationDTO;
import com.generic.khatabook.specification.model.CustomerSpecificationUpdatable;
import com.generic.khatabook.specification.services.CustomerSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class CustomerSpecificationController {

    private final CustomerSpecificationService myCustomerSpecificationService;
    private final CustomerClient myCustomerClient;

    @Autowired
    public CustomerSpecificationController(final CustomerSpecificationService thatCustomerSpecificationService, final CustomerClient customerClient) {
        this.myCustomerSpecificationService = thatCustomerSpecificationService;
        myCustomerClient = customerClient;
    }


    @GetMapping(path = "/khatabook/{khatabookId}/customer/{customerId}/specifications")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/khatabook/{khatabookId}/customer/{customerId}/specification")
    public ResponseEntity<?> create(@PathVariable String khatabookId, @PathVariable String customerId,
                                     @RequestBody CustomerSpecificationDTO customerSpecificationDTO) {


        try {
            final ResponseEntity<?> customer = myCustomerClient.getCustomerByCustomerId(khatabookId, customerId);
        } catch (Exception e) {
            return ResponseEntity.of(new NotFoundException(AppEntity.KHATABOOK, AppEntity.CUSTOMER, khatabookId, customerId).get()).build();
        }


        final Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> customerSpecificationExists = myCustomerSpecificationService.get(customerSpecificationDTO.id());

        if (customerSpecificationExists.isPresent()) {
        return ResponseEntity.of(new DuplicateFoundException(AppEntity.CUSTOMER_SPECIFICATION,
                                                             customerSpecificationDTO.id()).get()).build();

        }


        final CustomerSpecificationDTO saved = myCustomerSpecificationService.save(customerSpecificationDTO);


        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{specificationId}").buildAndExpand().toUri()).body(saved);
    }

    @GetMapping("/khatabook/{khatabookId}/customer/{customerId}/specification")
    public ResponseEntity<?> getById(@PathVariable String customerId) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/khatabook/{khatabookId}/customer/{customerId}/specification")
    public ResponseEntity<?> deleteById(@PathVariable String msisdn) {

        return ResponseEntity.ok().build();
    }


    @PutMapping("/khatabook/{khatabookId}/customer/{customerId}/specification")
    public ResponseEntity<?> updateCustomer(@PathVariable String khatabookId, @PathVariable String customerId,
                                            @RequestBody CustomerSpecificationDTO customerSpecificationDTO) {

        return ResponseEntity.ok().build();
    }

}
