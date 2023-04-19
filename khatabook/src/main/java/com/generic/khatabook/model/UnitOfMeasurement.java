package com.generic.khatabook.model;

public enum UnitOfMeasurement {
    KILOGRAM("KG"), LITTER("L"), READING("R"), NONE("");
    private String myUnitType;

    UnitOfMeasurement(final String unitType) {
        myUnitType = unitType;
    }

    public String getUnitType() {
        return myUnitType;
    }
}
