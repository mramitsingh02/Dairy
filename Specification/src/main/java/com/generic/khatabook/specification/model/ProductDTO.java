package com.generic.khatabook.specification.model;

import java.math.BigDecimal;

public record ProductDTO(String id, String name, BigDecimal price, UnitOfMeasurement unitOfMeasurement, float rating) {

    public ProductDTO copyOf(final String productId) {
        return new ProductDTO(productId, name, price, unitOfMeasurement, rating);
    }


    public ProductUpdatable updatable() {
        return new ProductUpdatable(this.id, this.name, this.price, this.unitOfMeasurement, this.rating);
    }

}
