package com.generic.khatabook.specification.model;

import java.math.BigDecimal;

public record CustomerSpecificationDTO(String id, String name, String description, int version, String customerId,
                                       String khatabookId, String specificationFor, BigDecimal price,
                                       UnitOfMeasurement unitOfMeasurement) {


    public CustomerSpecificationUpdatable updatable() {
        return new CustomerSpecificationUpdatable(this.id, this.name, this.description, this.version, this.khatabookId, this.customerId, this.specificationFor, this.price, this.unitOfMeasurement);
    }


}
