package com.itself.enums;

public enum Sex {
    MALE("male"),
    FEMALE("female");
    String value;
    private Sex(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
}
