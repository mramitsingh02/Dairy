package com.generic.khatabook.specification.model;

public enum UnitOfMeasurement {
    KILOGRAM("KG"), LITTER("L"), READING("R"), ITEM("I"), NONE("");
    private String myUnitType;

    UnitOfMeasurement(final String unitType) {
        myUnitType = unitType;
    }

    public String getUnitType() {
        return myUnitType;
    }
}
