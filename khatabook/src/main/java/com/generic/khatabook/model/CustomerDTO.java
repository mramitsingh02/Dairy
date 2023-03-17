package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerDTO(String customerId, String khatabookId, String msisdn, String firstName, String lastName, String specificationId) {
    public static final String ANONYMOUS = "Anonymous";


    public CustomerDTO copyOf(final String generateId) {
        return new CustomerDTO(generateId, this.khatabookId, this.msisdn, this.firstName, this.lastName, this.specificationId);
    }


    @Override
    public String toString() {

        if (Objects.nonNull(firstName) && Objects.nonNull(lastName)) {
            return ANONYMOUS + "user with " + msisdn + "" + "and customer id " + customerId + " belong to " + khatabookId + ".";

        }

        return firstName + " " + lastName + " with " + msisdn + "and" + " customer id " + customerId + " belong to " + khatabookId + ".";


    }
}
