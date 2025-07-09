// File: src/main/java/com/zenmgmoba/model/Player.java
package com.zenmgmoba.model;

import java.io.Serializable;
import java.util.Random;

/**
 * Player.java
 * Represents an individual eSports player with full attributes, profile, and progression.
 */
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private Role role;
    private int age;
    private int overall;
    private int potential;
    private int contractYears;
    private double salary;
    private boolean isStarter;
    private PlayerAttributes attributes;
    private PlayerProfile profile;

    public Player(String name,
                  Role role,
                  int age,
                  int overall,
                  int potential,
                  PlayerAttributes attributes) {
        this.name = name;
        this.role = role;
        this.age = age;
        this.overall = overall;
        this.potential = potential;
        this.contractYears = 0;
        this.salary = 0.0;
        this.isStarter = false;
        this.attributes = attributes;
        this.profile = new PlayerProfile();
    }

    // Development logic: age curves and potential growth/regression
    public void develop() {
        if (age < 25 && overall < potential) {
            overall = Math.min(potential, overall + new Random().nextInt(3) + 1);
        } else if (age > 28) {
            overall = Math.max(1, overall - new Random().nextInt(2));
        }
        age++;
    }

    // Getters and setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public int getAge() {
        return age;
    }

    public int getOverall() {
        return overall;
    }
    public void setOverall(int overall) {
        this.overall = overall;
    }

    public int getPotential() {
        return potential;
    }

    public int getContractYears() {
        return contractYears;
    }
    public void setContractYears(int contractYears) {
        this.contractYears = contractYears;
    }

    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public boolean isStarter() {
        return isStarter;
    }
    public void setStarter(boolean starter) {
        isStarter = starter;
    }

    public PlayerAttributes getAttributes() {
        return attributes;
    }
    public void setAttributes(PlayerAttributes attributes) {
        this.attributes = attributes;
    }

    public PlayerProfile getProfile() {
        return profile;
    }
    public void setProfile(PlayerProfile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] Age:%d OVR:%d POT:%d",
                name, role, age, overall, potential);
    }
}
