package com.generic.khatabook.model;

import java.util.Collection;
import java.util.List;

public record KhatabookPaymentSummaryView(PaymentStatistics paymentStatistics,
                                          Collection<CustomerPaymentSummaryView> customers) {
    static KhatabookPaymentSummaryView empty() {
        return new KhatabookPaymentSummaryView(PaymentStatistics.of(), List.of());
    }
}
