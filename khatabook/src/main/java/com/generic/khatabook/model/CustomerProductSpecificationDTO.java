package com.generic.khatabook.model;

public record CustomerProductSpecificationDTO(Long id, String productId, float quantity, UnitOfValue unitOfValue,
                                              UnitOfMeasurement unitOfMeasurement) {


    public static CustomerProductSpecificationDTO nonProduct() {
        return new CustomerProductSpecificationDTO(null, null, 1, UnitOfValue.non(), UnitOfMeasurement.NONE);
    }
}
