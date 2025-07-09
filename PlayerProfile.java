// File: src/main/java/com/zenmgmoba/model/PlayerProfile.java
package com.zenmgmoba.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PlayerProfile.java
 * Maintains per‐match StatsRecord history and player achievements.
 */
public class PlayerProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<StatsRecord> matchStats = new ArrayList<>();
    private final List<String> achievements = new ArrayList<>();

    /**
     * Adds a per‐match statistics record for this player.
     *
     * @param stats the StatsRecord to add
     */
    public void addMatchStats(StatsRecord stats) {
        if (stats != null) {
            matchStats.add(stats);
        }
    }

    /**
     * Returns an unmodifiable list of all StatsRecord entries.
     */
    public List<StatsRecord> getMatchStats() {
        return Collections.unmodifiableList(matchStats);
    }

    /**
     * Adds an achievement (e.g., "MVP Spring Split", "Regional Champion").
     *
     * @param achievement descriptive title of the achievement
     */
    public void addAchievement(String achievement) {
        if (achievement != null && !achievement.isBlank()) {
            achievements.add(achievement);
        }
    }

    /**
     * Returns an unmodifiable list of all achievements earned.
     */
    public List<String> getAchievements() {
        return Collections.unmodifiableList(achievements);
    }

    /**
     * Computes average KDA over all recorded matches.
     */
    public double getAverageKDA() {
        return matchStats.stream()
                .mapToDouble(StatsRecord::calculateKDA)
                .average()
                .orElse(0.0);
    }

    /**
     * Computes average creep score (CS) over all recorded matches.
     */
    public double getAverageCS() {
        return matchStats.stream()
                .mapToInt(StatsRecord::getCreepScore)
                .average()
                .orElse(0.0);
    }

    /**
     * Computes average damage per minute (DPM) over all recorded matches.
     */
    public double getAverageDPM() {
        return matchStats.stream()
                .mapToDouble(StatsRecord::getDamagePerMinute)
                .average()
                .orElse(0.0);
    }

    @Override
    public String toString() {
        return String.format("Matches: %d | Achievements: %d | Avg KDA: %.2f | Avg CS: %.1f | Avg DPM: %.1f",
                matchStats.size(),
                achievements.size(),
                getAverageKDA(),
                getAverageCS(),
                getAverageDPM()
        );
    }
}
