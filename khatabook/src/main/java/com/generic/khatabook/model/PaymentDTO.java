package com.generic.khatabook.model;

public record PaymentDTO(String to, String from, AmountDTO amount) {
    public static PaymentDTO nullOf() {
        return null;
    }
}
