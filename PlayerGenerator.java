// File: src/main/java/com/zenmgmoba/service/PlayerGenerator.java
package com.zenmgmoba.service;

import com.zenmgmoba.model.*;
import com.zenmgmoba.util.RandomUtil;

import java.util.List;
import java.util.Random;

/**
 * PlayerGenerator.java
 * Procedurally generates players with random attributes and assigns them to teams.
 */
public class PlayerGenerator {

    private static final String[] FIRST_NAMES = {
            "Alex", "Blake", "Casey", "Drew", "Evan",
            "Jordan", "Taylor", "Morgan", "Riley", "Cameron"
    };
    private static final String[] LAST_NAMES = {
            "Smith", "Lee", "Garcia", "Kim", "Jones",
            "Patel", "Nguyen", "Hernandez", "Lopez", "Brown"
    };
    private static final Random rnd = RandomUtil.get();

    /**
     * Generates the specified number of players per team for the league.
     *
     * @param league      the League instance to populate
     * @param perTeam     number of players per team
     */
    public static void generatePlayers(League league, int perTeam) {
        List<Team> teams = league.getTeams();
        for (Team team : teams) {
            for (int i = 0; i < perTeam; i++) {
                String name = FIRST_NAMES[rnd.nextInt(FIRST_NAMES.length)] + " "
                        + LAST_NAMES[rnd.nextInt(LAST_NAMES.length)];
                Role role = Role.values()[rnd.nextInt(Role.values().length)];
                int age = rnd.nextInt(14) + 16;            // 16–29
                int potential = rnd.nextInt(50) + 50;      // 50–99
                int overall = rnd.nextInt(potential - 20) + 30; // 30–(potential)

                PlayerAttributes attrs = new PlayerAttributes(
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1,
                        rnd.nextInt(99) + 1
                );

                Player player = new Player(name, role, age, overall, potential, attrs);

                // First five players become starters by default
                if (team.getRoster().size() < 5) {
                    player.setStarter(true);
                    // Assign initial salary based on overall
                    player.setSalary(overall * 10000);
                    player.setContractYears(rnd.nextInt(3) + 1); // 1–3 years
                }

                team.getRoster().add(player);
            }
        }
    }
}
