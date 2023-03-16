package com.generic.khatabook.model;

import java.time.LocalDate;

public record AggregatePaymentDTO(LocalDate from, LocalDate to) {
}
