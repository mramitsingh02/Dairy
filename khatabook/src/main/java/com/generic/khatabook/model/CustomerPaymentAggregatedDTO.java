package com.generic.khatabook.model;

import com.generic.khatabook.entity.CustomerPayment;

public record CustomerPaymentAggregatedDTO(AggregatePaymentDTO aggregatePaymentDTO, CustomerPayment customerPayment) {

}
