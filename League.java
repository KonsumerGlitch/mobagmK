// File: src/main/java/com/zenmgmoba/model/League.java
package com.zenmgmoba.model;

import java.util.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import com.zenmgmoba.service.PlayerGenerator;
import com.zenmgmoba.service.ChampionGenerator;

/**
 * League.java
 * Represents the global MOBA league, containing all teams and handling initialization.
 * Guarantees at least 50 unique champions in the pool.
 */
public class League implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Team> teams = new ArrayList<>();
    private final List<Split> splits = new ArrayList<>();

    /** Default constructor: 10 teams, 10 players each, 50 champions */
    public League() {
        this(10, 10, 50);
    }

    /**
     * Constructs a league with given parameters, ensuring at least 50 champions.
     *
     * @param numTeams         number of teams to create
     * @param playersPerTeam   players per team
     * @param numChampions     requested champion count (will be floored at 50)
     */
    public League(int numTeams, int playersPerTeam, int numChampions) {
        // Create teams
        for (int i = 1; i <= numTeams; i++) {
            teams.add(new Team("Team " + i));
        }

        // Generate players
        PlayerGenerator.generatePlayers(this, playersPerTeam);

        // Ensure at least 50 champions
        int championCount = Math.max(numChampions, 50);
        ChampionGenerator.generateChampions(this, championCount);
    }

    public List<Team> getTeams() {
        return Collections.unmodifiableList(teams);
    }

    public List<Split> getSplits() {
        return Collections.unmodifiableList(splits);
    }

    public void addSplit(Split split) {
        splits.add(split);
    }

    /**
     * Returns JavaFX Nodes showing current standings sorted by prestige.
     */
    public List<Node> getStandingsNodes() {
        List<Team> sorted = new ArrayList<>(teams);
        sorted.sort(Comparator.comparingDouble(Team::getPrestige).reversed());
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Label("=== League Standings ==="));
        int rank = 1;
        for (Team t : sorted) {
            nodes.add(new Label(String.format(
                    "%d. %s (Prestige: %.1f)", rank++, t.getName(), t.getPrestige()
            )));
        }
        return nodes;
    }
}
