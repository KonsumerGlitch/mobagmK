// File: src/main/java/com/zenmgmoba/model/StatsRecord.java
package com.zenmgmoba.model;

import java.io.Serializable;

/**
 * StatsRecord.java
 * Records per-match statistics for a player: KDA, CS, DPM, and objective controls.
 */
public class StatsRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private int kills;
    private int deaths;
    private int assists;
    private int creepScore;
    private double damagePerMinute;
    private int baronsTaken;
    private int dragonsTaken;
    private int turretsTaken;
    private int gameDurationMinutes;

    public StatsRecord(int kills, int deaths, int assists,
                       int creepScore, double damagePerMinute,
                       int baronsTaken, int dragonsTaken, int turretsTaken,
                       int gameDurationMinutes) {
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.creepScore = creepScore;
        this.damagePerMinute = damagePerMinute;
        this.baronsTaken = baronsTaken;
        this.dragonsTaken = dragonsTaken;
        this.turretsTaken = turretsTaken;
        this.gameDurationMinutes = gameDurationMinutes;
    }

    // Getters and setters
    public int getKills() { return kills; }
    public void setKills(int kills) { this.kills = kills; }

    public int getDeaths() { return deaths; }
    public void setDeaths(int deaths) { this.deaths = deaths; }

    public int getAssists() { return assists; }
    public void setAssists(int assists) { this.assists = assists; }

    public int getCreepScore() { return creepScore; }
    public void setCreepScore(int creepScore) { this.creepScore = creepScore; }

    public double getDamagePerMinute() { return damagePerMinute; }
    public void setDamagePerMinute(double damagePerMinute) { this.damagePerMinute = damagePerMinute; }

    public int getBaronsTaken() { return baronsTaken; }
    public void setBaronsTaken(int baronsTaken) { this.baronsTaken = baronsTaken; }

    public int getDragonsTaken() { return dragonsTaken; }
    public void setDragonsTaken(int dragonsTaken) { this.dragonsTaken = dragonsTaken; }

    public int getTurretsTaken() { return turretsTaken; }
    public void setTurretsTaken(int turretsTaken) { this.turretsTaken = turretsTaken; }

    public int getGameDurationMinutes() { return gameDurationMinutes; }
    public void setGameDurationMinutes(int gameDurationMinutes) {
        this.gameDurationMinutes = gameDurationMinutes;
    }

    /**
     * Calculates the Kill/Death/Assist ratio (KDA), with deaths treated as 1 if zero.
     */
    public double calculateKDA() {
        return (kills + assists) / (deaths == 0 ? 1.0 : deaths);
    }

    @Override
    public String toString() {
        return String.format("KDA: %.2f | CS: %d | DPM: %.1f | Baron: %d | Dragon: %d | Turret: %d",
                calculateKDA(), creepScore, damagePerMinute, baronsTaken, dragonsTaken, turretsTaken);
    }
}
