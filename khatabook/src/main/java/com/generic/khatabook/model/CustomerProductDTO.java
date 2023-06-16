package com.generic.khatabook.model;

public record CustomerProductDTO(String productId, AmountDTO amount) {
    public static CustomerProductDTO nullOf() {
        return null;
    }
}
