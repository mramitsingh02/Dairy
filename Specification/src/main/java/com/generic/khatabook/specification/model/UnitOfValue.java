package com.generic.khatabook.specification.model;

import java.math.BigDecimal;

public record UnitOfValue(BigDecimal price, long start, long end) {

    public static  UnitOfValue non(){
        return new UnitOfValue(BigDecimal.ZERO, 0, 0);
    }

}


