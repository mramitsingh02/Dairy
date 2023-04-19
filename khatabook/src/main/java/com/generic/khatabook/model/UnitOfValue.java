package com.generic.khatabook.model;

import java.math.BigDecimal;

public record UnitOfValue(BigDecimal price, int start, int end) {

    public static  UnitOfValue non(){
        return new UnitOfValue(BigDecimal.ZERO, 0, 0);
    }

}


