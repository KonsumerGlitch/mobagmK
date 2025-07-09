package com.zenmgmoba.model;

/**
 * Five-role MOBA framework
 */
public enum Role {
    TOP("Top Lane"),
    JGL("Jungle"),
    MID("Mid Lane"),
    ADC("Bot Lane Carry"),
    SUP("Support");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
