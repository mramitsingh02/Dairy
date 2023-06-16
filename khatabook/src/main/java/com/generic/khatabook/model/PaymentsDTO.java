package com.generic.khatabook.model;

import com.generic.khatabook.service.mapper.AmountMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BinaryOperator;

public class PaymentsDTO extends ArrayList<PaymentDTO> {

    public boolean hasAmounts() {
        return this.stream().map(PaymentDTO::products).flatMap(Collection::stream).map(CustomerProductDTO::amount).anyMatch(Objects::nonNull);
    }

    public AmountDTO finalAmount() {
        BinaryOperator<AmountDTO> addAmount = AmountMapper::add;
        return this.stream().map(PaymentDTO::products).flatMap(Collection::stream).map(CustomerProductDTO::amount).reduce(AmountDTO.ZERO, addAmount);
    }

}
