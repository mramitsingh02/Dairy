package com.generic.khatabook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CustomerUpdatable  {
    private String customerId;
    private String khatabookId;
    private String msisdn;
    private String firstName;
    private String lastName;
    private String productId;
    private String specificationId;

    public CustomerDTO build() {
        return new CustomerDTO(this.customerId, this.khatabookId, this.msisdn, this.firstName, this.lastName, this.productId, this.specificationId);
    }

}