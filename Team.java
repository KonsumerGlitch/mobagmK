// File: src/main/java/com/zenmgmoba/model/Team.java
package com.zenmgmoba.model;

import javafx.scene.Node;
import javafx.scene.control.Label;

import java.io.Serializable;
import java.util.*;

/**
 * Team.java
 * Represents an eSports team, holding roster, finances, prestige, and division.
 */
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private Division division;
    private List<Player> roster = new ArrayList<>();
    private double cash;
    private double prestige;
    private Map<Champion, Double> championSynergy = new HashMap<>();

    public Team(String name) {
        this.name = name;
        this.division = Division.INTERMEDIATE;
        this.cash = 1_000_000.0;
        this.prestige = 50.0;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Division getDivision() { return division; }
    public void setDivision(Division division) { this.division = division; }

    public List<Player> getRoster() { return roster; }

    public double getCash() { return cash; }
    public void setCash(double cash) { this.cash = cash; }

    public double getPrestige() { return prestige; }
    public void setPrestige(double prestige) { this.prestige = prestige; }

    public Map<Champion, Double> getChampionSynergy() { return championSynergy; }

    /**
     * Returns a list of JavaFX Nodes summarizing key team info and starters.
     */
    public List<Node> getSummaryNodes() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Label("Team: " + name));
        nodes.add(new Label("Division: " + division.getDisplayName()));
        nodes.add(new Label(String.format("Cash: $%,.0f", cash)));
        nodes.add(new Label(String.format("Prestige: %.1f", prestige)));
        nodes.add(new Label("--- Starters ---"));
        roster.stream().filter(Player::isStarter)
                .forEach(p -> nodes.add(new Label(p.toString())));
        nodes.add(new Label("--- Bench ---"));
        roster.stream().filter(p -> !p.isStarter())
                .forEach(p -> nodes.add(new Label(p.toString())));
        return nodes;
    }
}
