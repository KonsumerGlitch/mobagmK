package com.zenmgmoba.model;

/**
 * Three-tier promotion/relegation system
 */
public enum Division {
    CHAMPIONS("Champions Series"),
    CONTENDERS("Contenders"),
    INTERMEDIATE("Intermediate");

    private final String displayName;

    Division(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
