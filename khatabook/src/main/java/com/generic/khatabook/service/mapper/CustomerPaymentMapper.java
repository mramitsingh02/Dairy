package com.generic.khatabook.service.mapper;

import com.generic.khatabook.entity.CustomerPayment;
import com.generic.khatabook.model.AmountDTO;
import com.generic.khatabook.model.CustomerPaymentSummary;
import com.generic.khatabook.model.PaymentType;

import java.util.Currency;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerPaymentMapper {


    public static Set<CustomerPaymentSummary> mapToPojos(final Set<CustomerPayment> customersPayment) {
        return customersPayment.stream().map(CustomerPaymentMapper::mapToPojo).collect(Collectors.toSet());
    }

    private static CustomerPaymentSummary mapToPojo(final CustomerPayment customerPayment) {
        return new CustomerPaymentSummary(customerPayment.getKhatabookId(), customerPayment.getCustomerId(), PaymentType.valueOf(customerPayment.getPaymentType()),

                AmountDTO.of(customerPayment.getAmount().unitValue(), customerPayment.getAmount().unitOfMeasurement(), Currency.getInstance(customerPayment.getAmount().unitOfMeasurement())));
    }
}
