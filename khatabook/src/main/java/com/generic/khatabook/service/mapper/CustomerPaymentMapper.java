package com.generic.khatabook.service.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Mapper;
import com.generic.khatabook.entity.Amount;
import com.generic.khatabook.entity.CustomerPayment;
import com.generic.khatabook.model.AmountDTO;
import com.generic.khatabook.model.CustomerPaymentSummary;
import com.generic.khatabook.model.PaymentType;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerPaymentMapper implements Mapper<CustomerPayment, CustomerPaymentSummary, Void> {


    public static Set<CustomerPaymentSummary> mapToPojos(final Set<CustomerPayment> customersPayment) {
        return customersPayment.stream().map(CustomerPaymentMapper::mapToPojo).collect(Collectors.toSet());
    }

    private static CustomerPaymentSummary mapToPojo(final CustomerPayment customerPayment) {
        return new CustomerPaymentSummary(customerPayment.getKhatabookId(),
                                          customerPayment.getCustomerId(),
                                          PaymentType.valueOf(customerPayment.getPaymentType()),

                                          AmountDTO.of(customerPayment.getAmount().unitValue(),
                                                       customerPayment.getAmount().unitOfMeasurement(),
                                                       Currency.getInstance(customerPayment.getAmount().unitOfMeasurement())));
    }

    @Override
    public CustomerPayment mapToEntity(final CustomerPaymentSummary customerPaymentSummary) {
        return CustomerPayment.builder().khatabookId(customerPaymentSummary.khatabookId()).customerId(
                customerPaymentSummary.customerId()).amount(Amount.of(customerPaymentSummary.amount().value(),
                                                                      customerPaymentSummary.amount().unitOfMeasurement())).paymentOnDate(
                LocalDate.now(Clock.systemDefaultZone())).build();
    }

    @Override
    public Container<CustomerPaymentSummary, Void> mapToContainer(final CustomerPayment customerPayment) {
        return null;
    }

    @Override
    public CustomerPaymentSummary mapToDTO(final CustomerPayment customerPayment) {
        return new CustomerPaymentSummary(customerPayment.getKhatabookId(),
                                          customerPayment.getCustomerId(),
                                          PaymentType.valueOf(customerPayment.getPaymentType()),

                                          AmountDTO.of(customerPayment.getAmount().unitValue(),
                                                       customerPayment.getAmount().unitOfMeasurement(),
                                                       Currency.getInstance(customerPayment.getAmount().unitOfMeasurement())));
    }
}
