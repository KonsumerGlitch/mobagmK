// File: src/main/java/com/zenmgmoba/simulation/ScheduleGenerator.java
package com.zenmgmoba.simulation;

import com.zenmgmoba.model.League;
import com.zenmgmoba.model.Match;
import com.zenmgmoba.model.Split;
import com.zenmgmoba.model.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ScheduleGenerator.java
 * Generates round‐robin and Swiss schedules for splits and tournaments.
 */
public class ScheduleGenerator {

    /**
     * Generates a round‐robin schedule for the given league split.
     */
    public static void generateRoundRobin(Split split, League league) {
        List<Team> teams = new ArrayList<>(league.getTeams());
        int n = teams.size();
        for (int round = 0; round < n - 1; round++) {
            for (int i = 0; i < n / 2; i++) {
                Team home = teams.get(i);
                Team away = teams.get(n - 1 - i);
                split.addRegularSeasonMatch(new Match(home, away, 1));
            }
            // Rotate teams, keep first fixed
            teams.add(1, teams.remove(teams.size() - 1));
        }
    }

    /**
     * Generates a Swiss‐style group for international tournaments.
     */
    public static List<List<Team>> generateSwissGroups(List<Team> teams, int groupSize) {
        Collections.shuffle(teams);
        List<List<Team>> groups = new ArrayList<>();
        for (int i = 0; i < teams.size(); i += groupSize) {
            groups.add(new ArrayList<>(teams.subList(i, Math.min(i + groupSize, teams.size()))));
        }
        return groups;
    }
}
