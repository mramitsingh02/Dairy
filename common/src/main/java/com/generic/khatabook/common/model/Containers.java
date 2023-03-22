package com.generic.khatabook.common.model;

import java.util.Iterator;
import java.util.List;

public class Containers<T, U> implements Iterable<Container<T, U>> {
    private final List<Container<T, U>> myList;

    public Containers(final List<Container<T, U>> list) {
        myList = list;
    }

    @Override
    public Iterator<Container<T, U>> iterator() {
        return myList.iterator();
    }
}
