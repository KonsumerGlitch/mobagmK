// File: src/main/java/com/zenmgmoba/simulation/PlayerProgressionModel.java
package com.zenmgmoba.simulation;

import com.zenmgmoba.model.Player;

/**
 * PlayerProgressionModel.java
 * Applies seasonal growth/regression to players based on age and attributes.
 */
public class PlayerProgressionModel {

    public static void applyEndOfSeason(Player player) {
        int age = player.getAge();
        int overall = player.getOverall();
        int potential = player.getPotential();

        if (age < 25 && overall < potential) {
            player.setOverall(Math.min(potential, overall + (int)(Math.random() * 3) + 1));
        } else if (age > 28) {
            player.setOverall(Math.max(1, overall - (int)(Math.random() * 2)));
        }
        player.setAge(age + 1);
    }
}
