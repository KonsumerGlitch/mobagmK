// File: src/main/java/com/zenmgmoba/service/ChampionGenerator.java
package com.zenmgmoba.service;

import com.zenmgmoba.model.Champion;
import com.zenmgmoba.model.League;
import com.zenmgmoba.model.Team;
import com.zenmgmoba.util.RandomUtil;

import java.util.List;
import java.util.Random;

/**
 * ChampionGenerator.java
 * Procedurally generates a pool of Champions and assigns synergy values to each team.
 * Ensures at least the specified number of unique champions.
 */
public class ChampionGenerator {

    private static final String[] CHAMPION_NAMES = {
            "Aether", "Brutus", "Cinder", "Draco", "Echo",
            "Frost", "Gale", "Hex", "Ion", "Jade"
    };
    private static final String[] CHAMPION_TYPES = {
            "Tank", "Assassin", "Mage", "Marksman", "Support"
    };
    private static final Random rnd = RandomUtil.get();

    /**
     * Generates the specified number of unique champions for the league
     * and assigns each team a random synergy value (0.0–1.0) for each champion.
     *
     * @param league the League instance to populate
     * @param count  number of champions to generate (min 50 recommended)
     */
    public static void generateChampions(League league, int count) {
        List<Team> teams = league.getTeams();

        for (int i = 0; i < count; i++) {
            String baseName = CHAMPION_NAMES[rnd.nextInt(CHAMPION_NAMES.length)];
            String name = baseName + (i + 1);
            String type = CHAMPION_TYPES[rnd.nextInt(CHAMPION_TYPES.length)];
            double winRate = Math.round(rnd.nextDouble() * 1000.0) / 10.0;    // 0.0–100.0
            double banRate = Math.round(rnd.nextDouble() * 500.0) / 10.0;     // 0.0–50.0
            Champion champ = new Champion(name, type, winRate, banRate);

            // Assign synergy value between 0.0 and 1.0 for each team
            for (Team team : teams) {
                double synergy = Math.round(rnd.nextDouble() * 100.0) / 100.0;
                team.getChampionSynergy().put(champ, synergy);
            }
        }
    }
}
