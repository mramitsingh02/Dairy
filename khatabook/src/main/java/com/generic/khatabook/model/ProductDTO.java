package com.generic.khatabook.model;

import java.math.BigDecimal;

public record ProductDTO(String id, String name, int quantity, BigDecimal price, UnitOfMeasurement unitOfMeasurement, float rating) {

}
