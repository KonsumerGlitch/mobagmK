// File: src/main/java/com/zenmgmoba/simulation/DraftSimulator.java
package com.zenmgmoba.simulation;

import com.zenmgmoba.model.Champion;
import com.zenmgmoba.model.Match;
import com.zenmgmoba.model.Team;

import java.util.List;
import java.util.Random;

/**
 * DraftSimulator.java
 * Simulates ban/pick phases for a best‐of‐series draft.
 */
public class DraftSimulator {
    private static final Random rnd = new Random();

    public static void runDraft(Match match, List<Champion> pool) {
        Team a = match.getTeamA();
        Team b = match.getTeamB();
        // Simplified: each ban 3 champs, each picks 5
        for (int i = 0; i < 3; i++) {
            pool.remove(rnd.nextInt(pool.size())); // ban
        }
        for (int i = 0; i < 5; i++) {
            Champion pickA = pool.remove(rnd.nextInt(pool.size()));
            Champion pickB = pool.remove(rnd.nextInt(pool.size()));
            a.getChampionSynergy().put(pickA, rnd.nextDouble());
            b.getChampionSynergy().put(pickB, rnd.nextDouble());
        }
    }
}
