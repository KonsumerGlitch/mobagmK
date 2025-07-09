// File: src/main/java/com/zenmgmoba/model/Match.java
package com.zenmgmoba.model;

import com.zenmgmoba.simulation.MatchSimulator;

/**
 * Match.java
 * Represents a best-of-N series between two teams.
 */
public class Match {
    private int id;
    private final Team teamA;
    private final Team teamB;   // may be null for bye
    private final int bestOf;   // 1, 3, or 5
    private Team winner;
    private Team loser;
    private int gamesWonA;
    private int gamesWonB;
    private MatchType matchType = MatchType.REGULAR;

    public enum MatchType {
        REGULAR, PLAYOFF, INTERNATIONAL, ALL_STAR
    }

    public Match(Team teamA, Team teamB, int bestOf) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.bestOf = bestOf;
        this.gamesWonA = 0;
        this.gamesWonB = 0;
    }

    /** Simulate this match series using MatchSimulator. */
    public void simulate() {
        MatchSimulator simulator = new MatchSimulator();
        MatchSimulator.Result result = simulator.simulate(this);
        this.winner = result.getWinner();
        this.loser  = result.getLoser();
        this.gamesWonA = result.getGamesWonA();
        this.gamesWonB = result.getGamesWonB();
    }

    // Getters and setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Team getTeamA() {
        return teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public int getBestOf() {
        return bestOf;
    }

    public Team getWinner() {
        return winner;
    }

    public Team getLoser() {
        return loser;
    }

    public int getGamesWonA() {
        return gamesWonA;
    }

    public int getGamesWonB() {
        return gamesWonB;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    @Override
    public String toString() {
        return String.format("%s vs %s (Bo%s) - Winner: %s [%d-%d]",
                teamA.getName(),
                teamB != null ? teamB.getName() : "BYE",
                bestOf,
                winner != null ? winner.getName() : "TBD",
                gamesWonA,
                gamesWonB
        );
    }
}
