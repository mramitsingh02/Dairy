package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.CustomerPayment;
import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.exchanger.CustomerSpecificationClient;
import com.generic.khatabook.exchanger.ProductClient;
import com.generic.khatabook.model.AggregatePaymentDTO;
import com.generic.khatabook.model.AmountDTO;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerProductSpecificationDTO;
import com.generic.khatabook.model.CustomerSpecificationDTO;
import com.generic.khatabook.model.PaymentDTO;
import com.generic.khatabook.model.ProductDTO;
import com.generic.khatabook.model.UnitOfMeasurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Service
public final class PaymentLogic {
    private final CustomerSpecificationClient customerSpecificationClient;
    private final ProductClient productClient;

    @Autowired
    public PaymentLogic(final CustomerSpecificationClient customerSpecificationClient,
                        final ProductClient productClient) {
        this.customerSpecificationClient = customerSpecificationClient;
        this.productClient = productClient;
    }

    public PaymentDTO calculateFinalPayment(final CustomerDTO customerDTO, final PaymentDTO paymentDTO) {
        if (Objects.isNull(paymentDTO.productId())) {
            return paymentDTO;
        }

        final BigDecimal finalTotalAmount;
        UnitOfMeasurement unitOfMeasurement = UnitOfMeasurement.NONE;
        if (nonNull(customerDTO.specificationId())) {
            final ProductDTO customerProduct = getCustomerProduct(paymentDTO.productId());
            final CustomerSpecificationDTO customerProductSpecification = getCustomerSpecification(customerDTO);
            if (nonNull(customerProduct) && nonNull(customerProductSpecification)) {
                final CustomerProductSpecificationDTO customerProductSpecificationDTO = getCustomerProductSpecificationDTO(
                        customerProduct,
                        customerProductSpecification);
                if (nonNull(customerProductSpecificationDTO)) {
                    unitOfMeasurement =
                            (UnitOfMeasurement.NONE == customerProductSpecificationDTO.unitOfMeasurement()) ?
                                    customerProduct.unitOfMeasurement() : customerProductSpecificationDTO.unitOfMeasurement();
                    if (UnitOfMeasurement.KILOGRAM == unitOfMeasurement || UnitOfMeasurement.LITTER == unitOfMeasurement) {
                        if (Objects.nonNull(customerProductSpecificationDTO.unitOfValue().price())) {
                            finalTotalAmount = customerProductSpecificationDTO.unitOfValue().price().multiply(BigDecimal.valueOf(
                                    customerProductSpecificationDTO.quantity()));
                        } else {
                            finalTotalAmount = customerProduct.price().multiply(BigDecimal.valueOf(
                                    customerProductSpecificationDTO.quantity()));
                        }
                    } else {
                        finalTotalAmount = customerProduct.price().multiply(BigDecimal.valueOf(
                                customerProductSpecificationDTO.quantity()));
                    }


                } else {
                    finalTotalAmount = customerProduct.price();
                }
            } else {
                finalTotalAmount = paymentDTO.amount().value();
            }

            return new PaymentDTO(paymentDTO.to(),
                                  paymentDTO.from(),
                                  paymentDTO.productId(),
                                  AmountDTO.of(finalTotalAmount,
                                               unitOfMeasurement.getUnitType())
            );
        }

        return paymentDTO;
    }


    public CustomerPayment aggregatePayment(CustomerDTO customerDTO, AggregatePaymentDTO aggregatePaymentDTO){

        return new CustomerPayment();
    }

    private CustomerProductSpecificationDTO getCustomerProductSpecificationDTO(final ProductDTO customerProduct,
                                                                               final CustomerSpecificationDTO customerProductSpecification) {
        return customerProductSpecification.products().stream().filter(product -> isSameProduct(customerProduct,
                                                                                                product)).findFirst().orElse(
                CustomerProductSpecificationDTO.nonProduct());
    }

    private boolean isSameProduct(final ProductDTO customerProduct, final CustomerProductSpecificationDTO x) {
        return x.productId().equals(customerProduct.id());
    }

    private ProductDTO getCustomerProduct(final String productId) {
        try {
            final ResponseEntity<ProductDTO> responseEntity = productClient.getProductById(productId);
            if (Objects.isNull(responseEntity)) {
                throw new NotFoundException(AppEntity.PRODUCT, productId);
            } else {
                return responseEntity.getBody();
            }
        } catch (WebClientResponseException e) {
            throw new NotFoundException(AppEntity.PRODUCT, productId);
        }

    }

    private CustomerSpecificationDTO getCustomerSpecification(final CustomerDTO customerDTO) {
        try {
            final ResponseEntity<CustomerSpecificationDTO> responseEntity = customerSpecificationClient.getById(
                    customerDTO.khatabookId(),
                    customerDTO.customerId(),
                    customerDTO.specificationId());
            if (Objects.isNull(responseEntity)) {
                throw new NotFoundException(AppEntity.SPECIFICATION, customerDTO.specificationId());
            } else if (responseEntity.getBody() != null) {
                return responseEntity.getBody();
            }

        } catch (WebClientResponseException e) {
            throw new NotFoundException(AppEntity.SPECIFICATION, customerDTO.specificationId());
        }
        return null;
    }
}
