// File: src/main/java/com/zenmgmoba/service/StatsService.java
package com.zenmgmoba.service;

import com.zenmgmoba.model.Player;
import com.zenmgmoba.model.StatsRecord;
import com.zenmgmoba.dao.StatsDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

/**
 * StatsService.java
 * Aggregates and persists per‐match statistics; computes season and career summaries.
 */
public class StatsService {

    private static final String DB_URL = "jdbc:sqlite:zenmgmoba.db";
    private final StatsDAO statsDAO;

    public StatsService() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            statsDAO = new StatsDAO(conn);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize StatsService", e);
        }
    }

    /**
     * Records a single match's StatsRecord for a player:
     * persists to DB and adds to PlayerProfile.
     */
    public void recordStats(Player player, StatsRecord record, int matchId) {
        try {
            statsDAO.save(player.getId(), matchId, record);
            player.getProfile().addMatchStats(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and applies all stored StatsRecords for a player
     * into their in‐memory PlayerProfile.
     */
    public void loadPlayerStats(Player player) {
        try {
            List<StatsRecord> records = statsDAO.loadByPlayer(player.getId());
            for (StatsRecord r : records) {
                player.getProfile().addMatchStats(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints a season summary (avg KDA, CS, DPM, objectives) to console.
     */
    public void printSeasonSummary(Player player) {
        System.out.println("=== Season Summary: " + player.getName() + " ===");
        System.out.printf("Matches: %d%n", player.getProfile().getMatchStats().size());
        System.out.printf("Avg KDA: %.2f%n", player.getProfile().getAverageKDA());
        System.out.printf("Avg CS: %.1f%n", player.getProfile().getAverageCS());
        System.out.printf("Avg DPM: %.1f%n", player.getProfile().getAverageDPM());
        System.out.printf("Barons: %.1f%n",
            player.getProfile().getMatchStats().stream().mapToInt(StatsRecord::getBaronsTaken).average().orElse(0));
        System.out.printf("Dragons: %.1f%n",
            player.getProfile().getMatchStats().stream().mapToInt(StatsRecord::getDragonsTaken).average().orElse(0));
        System.out.printf("Turrets: %.1f%n",
            player.getProfile().getMatchStats().stream().mapToInt(StatsRecord::getTurretsTaken).average().orElse(0));
    }
}
