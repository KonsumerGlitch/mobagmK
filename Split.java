// File: src/main/java/com/zenmgmoba/model/Split.java
package com.zenmgmoba.model;

import com.zenmgmoba.simulation.ScheduleGenerator;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Split.java
 * Represents one regional split: round-robin regular season + playoffs.
 */
public class Split {
    private int id;
    private String name;                      // e.g., "Spring Split"
    private List<Match> regularSeason = new ArrayList<>();
    private List<Match> playoffs = new ArrayList<>();
    private League league;
    private boolean playedRegular = false;
    private boolean playedPlayoffs = false;

    public Split(String name, League league) {
        this.name = name;
        this.league = league;
        // Generate round-robin schedule
        ScheduleGenerator.generateRoundRobin(this, league);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    /** Called by ScheduleGenerator to add a BO1 match to regular season */
    public void addRegularSeasonMatch(Match match) {
        regularSeason.add(match);
    }

    /** Simulates all BO1s in regular season */
    public void playRegularSeason() {
        if (playedRegular) return;
        for (Match m : regularSeason) {
            m.simulate();
        }
        playedRegular = true;
    }

    /** Seeds and simulates playoffs: top 4 teams in BO3 bracket */
    public void playPlayoffs() {
        if (!playedRegular || playedPlayoffs) return;

        // Rank by wins
        var standings = calculateStandings();
        List<Team> top4 = standings.subList(0, Math.min(4, standings.size()));

        // Semifinals: 1v4, 2v3
        playoffs.add(new Match(top4.get(0), top4.get(3), 3));
        playoffs.add(new Match(top4.get(1), top4.get(2), 3));

        // Simulate semis
        for (Match m : playoffs) m.simulate();

        // Finals: winners of semis in BO5
        Team winner1 = playoffs.get(0).getWinner();
        Team winner2 = playoffs.get(1).getWinner();
        Match finalMatch = new Match(winner1, winner2, 5);
        finalMatch.simulate();
        playoffs.add(finalMatch);

        playedPlayoffs = true;
    }

    /** Computes ranking by counting regular-season wins */
    private List<Team> calculateStandings() {
        var wins = new java.util.HashMap<Team, Integer>();
        for (Match m : regularSeason) {
            wins.merge(m.getWinner(), 1, Integer::sum);
        }
        List<Team> teams = new ArrayList<>(wins.keySet());
        teams.sort(Comparator.comparingInt(wins::get).reversed());
        return teams;
    }

    /** Returns summary nodes for UI */
    public List<Node> getSummaryNodes() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Label("=== " + name + " ==="));
        if (!playedRegular) {
            nodes.add(new Label("Regular Season: Pending"));
        } else {
            nodes.add(new Label("Regular Season Results:"));
            calculateStandings().forEach(t ->
                    nodes.add(new Label(t.getName() + " – Wins: " +
                            java.util.Optional.ofNullable(
                                    calculateStandings().stream()
                                            .filter(x -> x.equals(t))
                                            .findFirst()
                                            .map(w -> wins(t))
                                            .orElse(0)
                            )
                    ))
            );
        }
        if (!playedPlayoffs) {
            nodes.add(new Label("Playoffs: Pending"));
        } else {
            nodes.add(new Label("Playoff Champion: " +
                    playoffs.get(playoffs.size() - 1).getWinner().getName()
            ));
        }
        return nodes;
    }

    /** Helper to count wins from regular season for a given team */
    private int wins(Team team) {
        return (int) regularSeason.stream()
                .filter(m -> m.getWinner().equals(team))
                .count();
    }
}
