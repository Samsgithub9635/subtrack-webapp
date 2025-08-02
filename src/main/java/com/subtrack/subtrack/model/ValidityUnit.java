package com.subtrack.subtrack.model;

public enum ValidityUnit {
    DAYS("Days"),
    MONTHS("Months"),
    YEARS("Years");

    private final String displayName;

    ValidityUnit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}