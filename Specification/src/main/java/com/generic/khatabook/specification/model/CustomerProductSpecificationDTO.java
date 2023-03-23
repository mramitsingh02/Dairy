package com.generic.khatabook.specification.model;

public record CustomerProductSpecificationDTO(Long id, String productId, float quantity) {

    public CustomerProductSpecificationDTO copyOf(final String productId) {
        return new CustomerProductSpecificationDTO(this.id, productId, 1);
    }


    public CustomerProductSpecificationUpdatable updatable() {
        return new CustomerProductSpecificationUpdatable(this.id, this.productId, this.quantity);
    }

}
