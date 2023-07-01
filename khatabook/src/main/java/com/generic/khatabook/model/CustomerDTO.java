package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerDTO(String customerId,
                          String khatabookId,
                          String msisdn,
                          String firstName,
                          String lastName,
                          List<Product> products,
                          String specificationId) {
    public static final String ANONYMOUS = "Anonymous";


    public CustomerDTO(final String customerId, final String khatabookId, final String msisdn) {
        this(customerId, khatabookId, msisdn, null, null, null, null);
    }


    public static CustomerDTO of(String customerId,
                                 String khatabookId,
                                 String msisdn,
                                 String firstName,
                                 String lastName,
                                 String productId,
                                 String specificationId) {
        return new CustomerDTO(customerId, khatabookId, msisdn, firstName, lastName, Collections.singletonList(new Product(productId)), specificationId);
    }

    public static CustomerDTO of(final String customerId, final String khatabookId, final String msisdn) {
        return new CustomerDTO(customerId, khatabookId, msisdn);
    }

    public CustomerDTO copyOf(final String generateId) {
        return new CustomerDTO(generateId,
                this.khatabookId,
                this.msisdn,
                this.firstName,
                this.lastName,
                this.products,
                this.specificationId);
    }

    public CustomerUpdatable updatable() {
        return new CustomerUpdatable(this.customerId,
                this.khatabookId,
                this.msisdn,
                this.firstName,
                this.lastName,
                this.products,
                this.specificationId);
    }


    @Override
    public String toString() {

        String fullName;
        String msisdnDetail = "with %s".formatted(msisdn);
        String custDetail = null;


        if (Objects.isNull(firstName) || Objects.isNull(lastName)) {
            fullName = "%s %s".formatted(ANONYMOUS, "user");
        } else {
            fullName = "%s %s".formatted(firstName, lastName);
        }
        if (Objects.nonNull(customerId)) {
            custDetail = "and customer productId %s".formatted(customerId);
        }

        final String khatabookDetail = "belong to %s".formatted(khatabookId);
        if (Objects.isNull(custDetail)) {
            return "%s %s %s.".formatted(fullName, msisdnDetail, khatabookDetail);
        }
        return "%s %s %s %s.".formatted(fullName, msisdnDetail, custDetail, khatabookDetail);

    }
}



