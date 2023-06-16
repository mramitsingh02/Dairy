package com.generic.khatabook.model;

public record Product(String id, String name) {
    public Product(String productId) {
        this(productId, null);
    }

    public static Product of(String productId) {
        return new Product(productId);
    }
}
