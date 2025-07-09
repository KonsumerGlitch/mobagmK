// File: src/main/java/com/zenmgmoba/dao/StatsDAO.java
package com.zenmgmoba.dao;

import com.zenmgmoba.model.StatsRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * StatsDAO.java
 * Handles CRUD operations for StatsRecord objects in the database.
 */
public class StatsDAO {
    private final Connection conn;

    public StatsDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new StatsRecord for a player-match.
     */
    public void save(int playerId, int matchId, StatsRecord stats) throws SQLException {
        String sql = """
            INSERT INTO player_stats (
                player_id, match_id, kills, deaths, assists,
                creep_score, damage_per_minute, barons_taken,
                dragons_taken, turrets_taken, game_duration_minutes
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playerId);
            ps.setInt(2, matchId);
            ps.setInt(3, stats.getKills());
            ps.setInt(4, stats.getDeaths());
            ps.setInt(5, stats.getAssists());
            ps.setInt(6, stats.getCreepScore());
            ps.setDouble(7, stats.getDamagePerMinute());
            ps.setInt(8, stats.getBaronsTaken());
            ps.setInt(9, stats.getDragonsTaken());
            ps.setInt(10, stats.getTurretsTaken());
            ps.setInt(11, stats.getGameDurationMinutes());
            ps.executeUpdate();
        }
    }

    /**
     * Retrieves all StatsRecord entries for a given player.
     */
    public List<StatsRecord> loadByPlayer(int playerId) throws SQLException {
        String sql = "SELECT * FROM player_stats WHERE player_id = ?";
        List<StatsRecord> records = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StatsRecord rec = new StatsRecord(
                            rs.getInt("kills"),
                            rs.getInt("deaths"),
                            rs.getInt("assists"),
                            rs.getInt("creep_score"),
                            rs.getDouble("damage_per_minute"),
                            rs.getInt("barons_taken"),
                            rs.getInt("dragons_taken"),
                            rs.getInt("turrets_taken")
                    );
                    rec.setGameDurationMinutes(rs.getInt("game_duration_minutes"));
                    records.add(rec);
                }
            }
        }

        return records;
    }
}
