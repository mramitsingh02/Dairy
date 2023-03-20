package com.generic.khatabook.specification.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductUpdatable {
    String id;
    String name;
    private BigDecimal price;
    private UnitOfMeasurement unitOfMeasurement;
    private float rating;

    public ProductDTO build() {
        return new ProductDTO(this.id, this.name, this.price, this.unitOfMeasurement,rating);
    }
}
