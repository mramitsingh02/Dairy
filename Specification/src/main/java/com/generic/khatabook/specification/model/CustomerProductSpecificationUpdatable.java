package com.generic.khatabook.specification.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerProductSpecificationUpdatable {
    private Long id;
    private String productId;
    private float quantity;

    public CustomerProductSpecificationDTO build() {
        return new CustomerProductSpecificationDTO(this.id, this.productId, this.quantity);
    }

}
