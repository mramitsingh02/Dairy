package com.generic.khatabook.specification.model;

import java.math.BigDecimal;

public record UnitOfValue(BigDecimal price, Long start, Long end) {

    public static UnitOfValue non() {
        return new UnitOfValue(BigDecimal.ZERO, 0l, 0l);
    }

}


