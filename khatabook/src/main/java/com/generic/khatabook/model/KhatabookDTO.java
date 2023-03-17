package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record KhatabookDTO(String groupId, String bookId, String khatabookId, String msisdn, String partnerName,
                           String partnerDescription) {


     public KhatabookDTO copyOf(String bookId) {
        return new KhatabookDTO(null, bookId, this.khatabookId, this.msisdn, this.partnerName, this.partnerDescription);
    }
    public KhatabookDTO copyOf(String groupId, String bookId) {
        return new KhatabookDTO(groupId, bookId, this.khatabookId, this.msisdn, this.partnerName, this.partnerDescription);
    }

}
