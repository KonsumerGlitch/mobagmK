// File: src/main/java/com/zenmgmoba/model/AllStarGame.java
package com.zenmgmoba.model;

import java.util.*;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * AllStarGame.java
 * End‐of‐year event: top players by role from each division.
 */
public class AllStarGame {
    private final List<Match> bracket = new ArrayList<>();

    public AllStarGame(League league) {
        Map<Role, List<Player>> topByRole = new EnumMap<>(Role.class);
        for (Role r : Role.values()) topByRole.put(r, new ArrayList<>());

        // Select best player per role with min 10 matches
        for (Team t : league.getTeams()) {
            for (Player p : t.getRoster()) {
                if (p.getProfile().getSeasonStats().size() >= 10) {
                    topByRole.get(p.getRole()).add(p);
                }
            }
        }
        // For each role, pick highest KDA
        for (Role r : Role.values()) {
            Player best = topByRole.get(r).stream()
                .max(Comparator.comparingDouble(
                    pl -> pl.getProfile().getSeasonStats().stream()
                        .mapToDouble(s -> (s.getKills()+s.getAssists())/(s.getDeaths()==0?1:s.getDeaths()))
                        .average().orElse(0)))
                .orElse(null);
            if (best != null) bracket.add(new Match(best.getTeam(), null, 1));
        }
    }

    public void runAllStar() {
        for (Match m : bracket) m.simulate();
    }

    public List<Node> getAllStarResults() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Label("=== All-Star Game Results ==="));
        for (Match m : bracket) {
            nodes.add(new Label(
                m.getWinner().getName() + " wins All-Star match"
            ));
        }
        return nodes;
    }
}
