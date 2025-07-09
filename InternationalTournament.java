// File: src/main/java/com/zenmgmoba/model/InternationalTournament.java
package com.zenmgmoba.model;

import com.zenmgmoba.simulation.ScheduleGenerator;
import com.zenmgmoba.simulation.MatchSimulator;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * InternationalTournament.java
 * Simulates MSI or Worlds with Swiss and knockout stages.
 */
public class InternationalTournament {
    private final String name;
    private final League league;
    private final List<Match> knockoutMatches = new ArrayList<>();

    public InternationalTournament(String name, League league) {
        this.name = name;
        this.league = league;
    }

    public void runTournament() {
        // Group Stage (Swiss)
        var groups = ScheduleGenerator.generateSwissGroups(league.getTeams(), 4);
        MatchSimulator sim = new MatchSimulator();
        for (List<Team> group : groups) {
            for (int i = 0; i < group.size(); i++) {
                for (int j = i + 1; j < group.size(); j++) {
                    Match m = new Match(group.get(i), group.get(j), 1);
                    m.simulate();
                }
            }
        }
        // Knockout: top 8 by prestige
        league.getTeams().sort((a,b)->Double.compare(b.getPrestige(),a.getPrestige()));
        List<Team> top8 = league.getTeams().subList(0,8);
        for (int i = 0; i < 4; i++) {
            knockoutMatches.add(new Match(top8.get(i), top8.get(7-i), 5));
        }
        // Run knockouts
        for (Match m : knockoutMatches) {
            m.simulate();
        }
    }

    public List<Node> getTournamentSummary() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Label("=== " + name + " Results ==="));
        for (Match m : knockoutMatches) {
            nodes.add(new Label(
                    m.getWinner().getName() + " def. " + m.getLoser().getName()
            ));
        }
        return nodes;
    }
}
