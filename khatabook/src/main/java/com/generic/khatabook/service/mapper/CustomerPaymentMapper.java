package com.generic.khatabook.service.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Mapper;
import com.generic.khatabook.entity.Amount;
import com.generic.khatabook.entity.CustomerPayment;
import com.generic.khatabook.model.*;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Currency;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CustomerPaymentMapper implements Mapper<CustomerPayment, CustomerPaymentSummary, Void> {


    public static Collection<CustomerPaymentSummary> mapToPojos(final Collection<CustomerPayment> customersPayment, SummaryProperties of, CustomerSpecificationDTO customerSpecification) {
        final Stream<CustomerPaymentSummary> customerPaymentSummaryStream = customersPayment.stream().map(
                CustomerPaymentMapper::mapToPojo);
        return customerPaymentSummaryStream.sorted(Comparator.comparing(CustomerPaymentSummary::paymentOnDate)).collect(
                Collectors.toList());
    }

    public static Collection<CustomerPaymentSummary> mapToPojos(final Collection<CustomerPayment> customersPayment,
                                                                final SummaryProperties summaryProperties) {
        final Stream<CustomerPaymentSummary> customerPaymentSummaryStream = customersPayment.stream().map(
                CustomerPaymentMapper::mapToPojo);

        final Comparator<CustomerPaymentSummary> comparing = Comparator.comparing(CustomerPaymentSummary::customerId);
        if (summaryProperties.sortingBy() != null) {
            if (Objects.equals(summaryProperties.sortingBy(), "customer")) {
                comparing.thenComparing(CustomerPaymentSummary::customerId);
            }
            if (Objects.equals(summaryProperties.sortingBy(), "product")) {
                comparing.thenComparing(CustomerPaymentSummary::productId);
            }
            if (Objects.equals(summaryProperties.sortingBy(), "date")) {
                comparing.thenComparing(CustomerPaymentSummary::paymentOnDate);
            }


        }
        if (summaryProperties.sorting() != null) {
            if (Objects.equals(summaryProperties.sorting(), "desc")) {
                comparing.reversed();
            }


        }
        return customerPaymentSummaryStream.sorted(comparing).collect(
                Collectors.toList());
    }

    private static CustomerPaymentSummary mapToPojo(final CustomerPayment customerPayment) {
        return new CustomerPaymentSummary(customerPayment.getKhatabookId(),
                customerPayment.getCustomerId(),
                PaymentType.valueOf(customerPayment.getPaymentType()),

                AmountDTO.of(customerPayment.getAmount().unitValue(),
                        customerPayment.getAmount().unitOfMeasurement()),
                customerPayment.getProductId(),
                customerPayment.getPaymentOnDate());
    }

    private static CustomerPaymentSummaryView mapToPojo(final CustomerDTO customer, final CustomerPayment customerPayment) {
        String productName = customer.products().stream().filter(x -> x.productId().equals(customerPayment.getProductId())).map(Product::name).findFirst().orElse("CASH");
        return new CustomerPaymentSummaryView(customerPayment.getCustomerId(),
                PaymentType.valueOf(customerPayment.getPaymentType()),
                AmountDTO.of(customerPayment.getAmount().unitValue(),
                        customerPayment.getAmount().unitOfMeasurement()),
                new Product(customerPayment.getProductId(), productName),
                customerPayment.getPaymentOnDate());
    }

    public static Collection<CustomerPaymentSummaryView> mapToPojos(CustomerDTO customerRequest, Collection<CustomerPayment> customersPayment, CustomerSpecificationDTO customerSpecification) {
        return customersPayment.stream().map(x -> CustomerPaymentMapper.mapToPojo(customerRequest, x)).collect(Collectors.toList());
    }


    @Override
    public CustomerPayment mapToEntity(final CustomerPaymentSummary customerPaymentSummary) {
        return CustomerPayment.builder().khatabookId(customerPaymentSummary.khatabookId())
                .customerId(customerPaymentSummary.customerId())
                .productId(customerPaymentSummary.productId())
                .amount(Amount.of(customerPaymentSummary.amount().value(),
                        customerPaymentSummary.amount().unitOfMeasurement())).paymentOnDate(
                        LocalDateTime.now(Clock.systemDefaultZone())).build();
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
                        Currency.getInstance(customerPayment.getAmount().unitOfMeasurement())),
                customerPayment.getProductId(),
                customerPayment.getPaymentOnDate());
    }
}
