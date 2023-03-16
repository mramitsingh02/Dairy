package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record KhatabookDTO(String bookId, String khatabookId, String msisdn, String partnerName,
                           String partnerDescription) {


    public KhatabookDTO copyOf(String bookId) {
        return new KhatabookDTO(bookId, this.khatabookId, this.msisdn, this.partnerName, this.partnerDescription);
    }

    public CustomerDTO customerData() {
        return new CustomerDTO(khatabookId, khatabookId, msisdn, null, null);
    }
}
