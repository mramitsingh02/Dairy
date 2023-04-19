package com.generic.khatabook.model;

public record PaymentDTO(String to, String from, String productId, AmountDTO amount) {
    public static PaymentDTO nullOf() {
        return null;
    }
}
