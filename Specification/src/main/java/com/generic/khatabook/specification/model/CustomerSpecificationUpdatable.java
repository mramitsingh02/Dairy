package com.generic.khatabook.specification.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CustomerSpecificationUpdatable {
    private String id;
    private String name;
    private String description;
    private int version;
    private String customerId;
    private String khatabookId;
    private String specificationFor;
    private BigDecimal price;
    private UnitOfMeasurement unitOfMeasurement;

    public CustomerSpecificationDTO build() {
        return new CustomerSpecificationDTO(this.id, this.name, this.description, this.version, this.khatabookId, this.customerId, this.specificationFor, this.price, this.unitOfMeasurement);
    }

}
