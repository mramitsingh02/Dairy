package com.generic.khatabook.specification.controller;

import com.generic.khatabook.specification.model.CustomerSpecificationDTO;
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

    @Autowired
    public CustomerSpecificationController(final CustomerSpecificationService thatCustomerSpecificationService) {
        this.myCustomerSpecificationService = thatCustomerSpecificationService;
    }


    @GetMapping(path = "customer/specifications")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "customer/{customerId}/specification")
    public ResponseEntity<?> create(@PathVariable String customerId, @RequestBody CustomerSpecificationDTO customerSpecificationDTO) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{specificationId}").buildAndExpand(customerId).toUri()).body(customerSpecificationDTO);
    }

    @GetMapping("customer/{customerId}/specification")
    public ResponseEntity<?> getById(@PathVariable String customerId) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("customer/{customerId}/specification")
    public ResponseEntity<?> deleteById(@PathVariable String msisdn) {

        return ResponseEntity.ok().build();
    }


    @PutMapping("customer/{customerId}/specification")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerSpecificationDTO CustomerSpecificationDTO) {

        return ResponseEntity.ok().build();
    }

}
