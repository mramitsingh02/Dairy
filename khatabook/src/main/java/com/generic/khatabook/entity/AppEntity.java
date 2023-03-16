package com.generic.khatabook.entity;

public enum AppEntity {

    ID("Id"),
    KHATABOOK("Khatabook"),
    CUSTOMER("Customer"),
    MSISDN("Mobile");
    private String myName;

    AppEntity(final String name) {
        myName = name;
    }

    public String getName() {
        return myName;
    }

    @Override
    public String toString() {
        return "Entity Name='" + myName + '\'';
    }
}
