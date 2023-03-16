package com.generic.khatabook.model;

import java.util.Set;

public record KhatabookPaymentSummary(PaymentStatistics paymentStatistics, Set<CustomerPaymentSummary> customers) {
    static KhatabookPaymentSummary empty() {
        return new KhatabookPaymentSummary(PaymentStatistics.of(), Set.of());
    }
}
