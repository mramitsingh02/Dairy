package com.generic.khatabook.model;

public record PaymentStatistics(AmountDTO total, AmountDTO credited, AmountDTO debited) {
    public static PaymentStatistics of() {
        return new PaymentStatistics(AmountDTO.ZERO, AmountDTO.ZERO, AmountDTO.ZERO);
    }
}
