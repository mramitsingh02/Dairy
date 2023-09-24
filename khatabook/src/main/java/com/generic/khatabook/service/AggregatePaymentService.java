package com.generic.khatabook.service;

import com.generic.khatabook.common.model.AggregatePaymentDTO;
import com.generic.khatabook.common.model.CustomerDTO;
import com.generic.khatabook.common.model.KhatabookDTO;
public interface AggregatePaymentService {
    void paymentAggregate(KhatabookDTO khatabook, CustomerDTO customer, AggregatePaymentDTO payment);

    AggregatePaymentDTO getLastAggregation(KhatabookDTO khatabook, CustomerDTO customer);

    void allPaymentAggregate(KhatabookDTO khatabook, CustomerDTO customerDTO, AggregatePaymentDTO payment);
}
