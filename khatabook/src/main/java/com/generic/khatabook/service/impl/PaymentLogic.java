package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.Amount;
import com.generic.khatabook.entity.CustomerPayment;
import com.generic.khatabook.exchanger.ProductClient;
import com.generic.khatabook.model.*;
import com.generic.khatabook.service.mapper.AmountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;

import static java.util.Objects.nonNull;

@Service
public final class PaymentLogic {

    private final ProductClient productClient;

    @Autowired
    public PaymentLogic(final ProductClient productClient) {
        this.productClient = productClient;
    }

    public PaymentDTO calculateFinalPayment(final CustomerDTO customerDTO, final PaymentDTO paymentDTO, ProductDTO customerProduct, CustomerSpecificationDTO customerProductSpecification) {
        if (Objects.isNull(paymentDTO.productId())) {
            return paymentDTO;
        }

        final BigDecimal finalTotalAmount;
        UnitOfMeasurement unitOfMeasurement = UnitOfMeasurement.NONE;
        if (nonNull(customerDTO.specificationId())) {
            if (nonNull(customerProduct) && nonNull(customerProductSpecification)) {
                final CustomerProductSpecificationDTO customerProductSpecificationDTO = getCustomerProductSpecificationDTO(customerProduct, customerProductSpecification);
                if (nonNull(customerProductSpecificationDTO)) {
                    unitOfMeasurement = (UnitOfMeasurement.NONE == customerProductSpecificationDTO.unitOfMeasurement()) ? customerProduct.unitOfMeasurement() : customerProductSpecificationDTO.unitOfMeasurement();
                    if (UnitOfMeasurement.KILOGRAM == unitOfMeasurement || UnitOfMeasurement.LITTER == unitOfMeasurement) {
                        if (Objects.nonNull(customerProductSpecificationDTO.unitOfValue().price())) {
                            finalTotalAmount = customerProductSpecificationDTO.unitOfValue().price().multiply(BigDecimal.valueOf(customerProductSpecificationDTO.quantity()));
                        } else {
                            finalTotalAmount = customerProduct.price().multiply(BigDecimal.valueOf(customerProductSpecificationDTO.quantity()));
                        }
                    } else {
                        finalTotalAmount = customerProduct.price().multiply(BigDecimal.valueOf(customerProductSpecificationDTO.quantity()));
                    }


                } else {
                    finalTotalAmount = customerProduct.price();
                }
            } else {
                finalTotalAmount = paymentDTO.amount().value();
            }

            return new PaymentDTO(paymentDTO.to(), paymentDTO.from(), paymentDTO.productId(), AmountDTO.of(finalTotalAmount, unitOfMeasurement.getUnitType()));
        }

        return paymentDTO;
    }


    public CustomerPaymentAggregatedDTO aggregatePayment(CustomerDTO customerDTO, AggregatePaymentDTO aggregatePaymentDTO, List<CustomerPayment> customerAllPaymentBetweenRange) {

        List<CustomerPayment> customerPayments = customerAllPaymentBetweenRange.stream().sorted(Comparator.comparing(CustomerPayment::getPaymentOnDate)).toList();
        CustomerPayment customerFirstPayment = customerPayments.get(0);
        CustomerPayment customerLastPayment = customerPayments.get(customerPayments.size() - 1);

        PaymentStatistics paymentStatistics = getPaymentStatistics(customerAllPaymentBetweenRange);

        CustomerPayment customerPayment = new CustomerPayment(null, customerDTO.khatabookId(), customerDTO.customerId(), PaymentType.AGGRIGATED.name(), Amount.of(paymentStatistics.total().value(), paymentStatistics.total().unitOfMeasurement()), aggregatePaymentDTO.productId(), LocalDateTime.now(), "Aggregated");

        return new CustomerPaymentAggregatedDTO(new AggregatePaymentDTO(aggregatePaymentDTO.productId(), customerFirstPayment.getPaymentOnDate(), customerLastPayment.getPaymentOnDate()), customerPayment);
    }

    public PaymentStatistics getPaymentStatistics(final Collection<CustomerPayment> customersPayment) {
        BinaryOperator<AmountDTO> addAmount = AmountMapper::add;
        final AmountDTO totalCredited = customersPayment.stream().filter(this::isCreditRecord).map(CustomerPayment::getAmount).map(AmountMapper::dto).reduce(AmountDTO.ZERO, addAmount);

        final AmountDTO totalDebited = customersPayment.stream().filter(this::isDebitRecord).map(CustomerPayment::getAmount).map(AmountMapper::dto).reduce(AmountDTO.ZERO, addAmount);
        final AmountDTO aggregatedAmount = customersPayment.stream().filter(this::isAggregatedRecord).map(CustomerPayment::getAmount).map(AmountMapper::dto).reduce(AmountDTO.ZERO, addAmount);
        final AmountDTO total = aggregatedAmount.pluse(totalCredited).minus(totalDebited);

        if (customersPayment.isEmpty()) {
            return null;
        }

        return new PaymentStatistics(total, totalCredited, totalDebited);
    }


    private boolean isCreditRecord(final CustomerPayment x) {
        return x.getPaymentType().equals(PaymentType.CREDIT.name());
    }

    private boolean isDebitRecord(final CustomerPayment x) {
        return x.getPaymentType().equals(PaymentType.DEBIT.name());
    }

    private boolean isAggregatedRecord(final CustomerPayment x) {
        return x.getPaymentType().equals(PaymentType.AGGRIGATED.name());
    }

    private CustomerProductSpecificationDTO getCustomerProductSpecificationDTO(final ProductDTO customerProduct, final CustomerSpecificationDTO customerProductSpecification) {
        return customerProductSpecification.products().stream().filter(product -> isSameProduct(customerProduct, product)).findFirst().orElse(CustomerProductSpecificationDTO.nonProduct());
    }

    private boolean isSameProduct(final ProductDTO customerProduct, final CustomerProductSpecificationDTO x) {
        return x.productId().equals(customerProduct.id());
    }



}
