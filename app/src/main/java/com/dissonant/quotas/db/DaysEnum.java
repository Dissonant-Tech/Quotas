package com.dissonant.quotas.db;

public enum DaysEnum {
    MONDAY      ("Monday"),
    TUESDAY     ("Tuesday"),
    WEDNESDAY   ("Wednesday"),
    THURSDAY    ("Thursday"),
    FRIDAY      ("Friday"),
    SATURDAY    ("Saturday"),
    SUNDAY      ("Sunday");

    private final String name;

    DaysEnum(String name){
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public DaysEnum fromValue(String value) {
        return valueOf(value);
    }
}
