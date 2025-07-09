package com.zenmgmoba.model;

import java.io.Serializable;

/**
 * Champion.java
 * Represents a playable champion with base stats and meta metrics.
 * Used for draft simulation and team synergy calculations.
 */
public class Champion implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String type;        // e.g., "Tank", "Assassin", "Mage", "Marksman", "Support"
    private double winRate;     // percentage 0–100
    private double banRate;     // percentage 0–100
    private double pickRate;    // percentage 0–100
    private String[] counters;  // champions this champion counters
    private String[] counteredBy; // champions that counter this champion

    public Champion(String name, String type, double winRate, double banRate) {
        this.name = name;
        this.type = type;
        this.winRate = winRate;
        this.banRate = banRate;
        this.pickRate = 0.0;
        this.counters = new String[0];
        this.counteredBy = new String[0];
    }

    public Champion(String name, String type, double winRate, double banRate, double pickRate) {
        this(name, type, winRate, banRate);
        this.pickRate = pickRate;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getWinRate() { return winRate; }
    public void setWinRate(double winRate) {
        this.winRate = Math.max(0.0, Math.min(100.0, winRate));
    }

    public double getBanRate() { return banRate; }
    public void setBanRate(double banRate) {
        this.banRate = Math.max(0.0, Math.min(100.0, banRate));
    }

    public double getPickRate() { return pickRate; }
    public void setPickRate(double pickRate) {
        this.pickRate = Math.max(0.0, Math.min(100.0, pickRate));
    }

    public String[] getCounters() { return counters; }
    public void setCounters(String[] counters) { this.counters = counters; }

    public String[] getCounteredBy() { return counteredBy; }
    public void setCounteredBy(String[] counteredBy) { this.counteredBy = counteredBy; }

    /**
     * Calculate champion priority for draft simulation
     */
    public double getDraftPriority() {
        return (winRate * 0.4) + (pickRate * 0.3) + (banRate * 0.3);
    }

    /**
     * Check if this champion is meta (high pick/ban rate)
     */
    public boolean isMeta() {
        return (pickRate + banRate) > 60.0;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) – Win: %.1f%%, Ban: %.1f%%, Pick: %.1f%%",
                name, type, winRate, banRate, pickRate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Champion champion = (Champion) obj;
        return name.equals(champion.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
