package com.generic.khatabook.specification.model;

public record ProductRatingDTO(String productId, float rating, String description) {
    public ProductRatingDTO copyOf(final String productId) {
        return new ProductRatingDTO(productId, rating, description);
    }
}
