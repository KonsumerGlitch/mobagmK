
package com.zenmgmoba.simulation;

import com.zenmgmoba.model.Division;
import com.zenmgmoba.model.League;
import com.zenmgmoba.model.Team;

import java.util.Comparator;
import java.util.List;

/**
 * PromotionRelegation.java
 * Moves teams between divisions based on prestige.
 */
public class PromotionRelegation {

    public static void apply(League league) {
        List<Team> teams = league.getTeams();
        teams.sort(Comparator.comparingDouble(Team::getPrestige).reversed());
        int n = teams.size();

        // Top 3 -> Champions, mid 4–7 -> Contenders, bottom -> Intermediate
        for (int i = 0; i < n; i++) {
            Team t = teams.get(i);
            if (i < 3) t.setDivision(Division.CHAMPIONS);
            else if (i < 7) t.setDivision(Division.CONTENDERS);
            else t.setDivision(Division.INTERMEDIATE);
        }
    }
}
