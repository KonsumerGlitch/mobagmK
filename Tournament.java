// File: src/main/java/com/zenmgmoba/model/Tournament.java
package com.zenmgmoba.model;

import javafx.scene.Node;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;

/**
 * Tournament.java
 * Abstract base for multi-stage tournaments (e.g., MSI, Worlds, All-Star).
 * Provides common structure for building, running, and summarizing tournaments.
 */
public abstract class Tournament {
    protected String name;
    protected League league;
    protected List<Match> matches = new ArrayList<>();

    /**
     * Constructs a new Tournament.
     *
     * @param name   The tournament name (e.g., "MSI", "Worlds", "All-Star").
     * @param league The league instance containing teams.
     */
    public Tournament(String name, League league) {
        this.name = name;
        this.league = league;
    }

    /**
     * Builds the tournament structure: group stages, brackets, etc.
     * Subclasses implement this to populate the `matches` list.
     */
    public abstract void build();

    /**
     * Simulates all matches in this tournament.
     * Uses each Match's simulate() method.
     */
    public void run() {
        for (Match m : matches) {
            m.setMatchType(Match.MatchType.INTERNATIONAL);
            m.simulate();
        }
    }

    /**
     * Returns a JavaFX Node list summarizing tournament results.
     *
     * @return List of Label nodes showing each match outcome.
     */
    public List<Node> getSummaryNodes() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Label("=== " + name + " Results ==="));
        for (Match m : matches) {
            nodes.add(new Label(m.toString()));
        }
        return nodes;
    }

    /** @return The tournament name. */
    public String getName() {
        return name;
    }

    /** @return Unmodifiable list of matches in this tournament. */
    public List<Match> getMatches() {
        return List.copyOf(matches);
    }
}
