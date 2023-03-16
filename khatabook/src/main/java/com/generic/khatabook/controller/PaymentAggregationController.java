package com.generic.khatabook.controller;

import com.generic.khatabook.entity.AppEntity;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.model.AggregatePaymentDTO;
import com.generic.khatabook.service.AggregatePaymentService;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.IdGeneratorService;
import com.generic.khatabook.service.KhatabookService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class PaymentAggregationController {


    @Autowired
    private CustomerService myCustomerService;

    @Autowired
    private KhatabookService myKhatabookService;

    @Autowired
    private AggregatePaymentService myAggregatePaymentService;


    @Autowired
    private IdGeneratorService myIdGeneratorService;

    @PostMapping(path = "/khatabook/{khatabookId}/customer/{customerId}/aggregate")
    public ResponseEntity<?> aggregatedPayment(@PathVariable String khatabookId, @PathVariable String customerId, @RequestBody AggregatePaymentDTO payment) {

        final val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            throw new NotFoundException(AppEntity.KHATABOOK, khatabookId);
        }

        final var customer = myCustomerService.getByCustomerId(customerId);

        if (Objects.isNull(customer)) {
            throw new NotFoundException(AppEntity.CUSTOMER, customerId);
        }

        myAggregatePaymentService.paymentAggregate(khatabook, customer, payment);

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/khatabook/{khatabookId}/msisdn/{msisdn}/aggregate")
    public ResponseEntity<?> aggregatedPaymentByMsisdn(@Validated @PathVariable String khatabookId, @Validated @PathVariable String msisdn, @RequestBody AggregatePaymentDTO payment) {

        final val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            throw new NotFoundException(AppEntity.KHATABOOK, khatabookId);
        }

        final var customer = myCustomerService.getByMsisdn(msisdn);

        if (Objects.isNull(customer)) {
            return ResponseEntity.badRequest().body(new NotFoundException(AppEntity.MSISDN, msisdn));
        }

        myAggregatePaymentService.paymentAggregate(khatabook, customer, payment);

        return ResponseEntity.ok().build();
    }


    @GetMapping(path = "/khatabook/{khatabookId}/customer/{customerId}/aggregate")
    public ResponseEntity<?> getLastAggregatedPayment(@PathVariable String khatabookId, @PathVariable String customerId) {

        final val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            throw new NotFoundException(AppEntity.KHATABOOK, khatabookId);
        }

        final var customer = myCustomerService.getByCustomerId(customerId);
        if (Objects.isNull(customer)) {
            throw new NotFoundException(AppEntity.CUSTOMER, customerId);
        }
        return ResponseEntity.ok(myAggregatePaymentService.getLastAggregation(khatabook, customer));
    }

    @GetMapping(path = "/khatabook/{khatabookId}/msisdn/{msisdn}/aggregate")
    public ResponseEntity<?> getLastAggregatedPaymentByMsisdn(@PathVariable String khatabookId, @PathVariable String msisdn) {

        final val khatabook = myKhatabookService.getKhatabookByKhatabookId(khatabookId);
        if (Objects.isNull(khatabook)) {
            throw new NotFoundException(AppEntity.KHATABOOK, khatabookId);
        }

        final var customer = myCustomerService.getByMsisdn(msisdn);
        if (Objects.isNull(customer)) {
            return ResponseEntity.badRequest().body(new NotFoundException(AppEntity.MSISDN, msisdn));
        }

        myAggregatePaymentService.getLastAggregation(khatabook, customer);

        return ResponseEntity.ok().build();
    }

}
