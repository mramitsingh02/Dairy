package com.generic.khatabook.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerUpdatable {
    private String customerId;
    private String khatabookId;
    private String msisdn;
    private String firstName;
    private String lastName;
    private List<Product> products;
    private String specificationId;

    public CustomerDTO build() {
        return new CustomerDTO(this.customerId, this.khatabookId, this.msisdn, this.firstName, this.lastName, this.products, this.specificationId);
    }

    public CustomerUpdatable addProduct(String productId, String name) {
        products.add(new Product(productId, name));
        return this;
    }
}