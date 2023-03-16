package com.generic.khatabook.model;

public record CustomerPaymentSummary(String customerId, String khatabookId, PaymentType paymentType, AmountDTO amount) {
}
