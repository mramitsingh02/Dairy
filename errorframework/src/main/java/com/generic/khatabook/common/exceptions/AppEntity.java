package com.generic.khatabook.common.exceptions;

public enum AppEntity {

    ID("Id"),
    PRODUCT("Product"),
    RATING("Rating"),
    KHATABOOK("Khatabook"),
    SPECIFICATION("Specification"),
    CUSTOMER_SPECIFICATION("Customer Specification"),
    CUSTOMER("Customer"),
    MSISDN("Mobile"),
    OTHER("");
    private String myName;
    private SubEntity[] mySubEntity;

    AppEntity(final String name, SubEntity... subEntity) {
        myName = name;
        mySubEntity = subEntity;
    }

    public String getName() {
        return myName;
    }

    @Override
    public String toString() {
        return "Entity Name='" + myName + '\'';
    }

    public AppEntity or(final AppEntity customer) {
        return null;
    }

    public AppEntity and(final AppEntity other) {
        return AppEntity.valueOf(name() + "." + other.getName());
    }
}
