package com.generic.khatabook.model;

import java.util.NoSuchElementException;
import java.util.Objects;

public class Container<T, U> {

    private static final Container<?, ?> EMPTY = new Container<>(null, null);

    private final T value;
    private final U updatable;

    private Container(T value, U updatable) {
        this.value = value;
        this.updatable = updatable;
    }

    public static <T, U> Container<T, U> empty() {
        @SuppressWarnings("unchecked") Container<T, U> t = (Container<T, U>) EMPTY;
        return t;
    }

    public static <T, U> Container<T, U> of(T value) {
        return new Container<>(Objects.requireNonNull(value), null);
    }


    public static <T, U> Container<T, U> ofNullable(T value) {
        return value == null ? (Container<T, U>) EMPTY : new Container<>(value, null);
    }

    public static <T, U> Container<T, U> of(T value, U updatable) {
        return new Container<>(Objects.requireNonNull(value), updatable);
    }


    public static <T, U> Container<T, U> ofNullable(T value, U updatable) {
        return value == null ? (Container<T, U>) EMPTY : new Container<>(value, updatable);
    }


    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public U updatable() {
        if (updatable == null) {
            throw new NoSuchElementException("No value present");
        }
        return updatable;
    }

}
