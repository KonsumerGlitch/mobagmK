// File: src/main/java/com/zenmgmoba/dao/TeamDAO.java
package com.zenmgmoba.dao;

import com.zenmgmoba.model.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TeamDAO.java
 * Handles CRUD operations for Team objects in the database.
 */
public class TeamDAO {
    private final Connection conn;

    public TeamDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new Team or updates an existing one.
     */
    public void save(Team team, int leagueId) throws SQLException {
        String sql = """
            INSERT INTO teams (id, league_id, name, division, cash, prestige)
            VALUES (?, ?, ?, ?, ?, ?)
            ON CONFLICT(id) DO UPDATE SET
              name = excluded.name,
              division = excluded.division,
              cash = excluded.cash,
              prestige = excluded.prestige
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, team.getId());
            ps.setInt(2, leagueId);
            ps.setString(3, team.getName());
            ps.setString(4, team.getDivision().name());
            ps.setDouble(5, team.getCash());
            ps.setDouble(6, team.getPrestige());
            ps.executeUpdate();
        }
    }

    /**
     * Loads a Team by its ID.
     */
    public Team load(int teamId) throws SQLException {
        String sql = "SELECT name, division, cash, prestige FROM teams WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Team team = new Team(rs.getString("name"));
                    team.setId(teamId);
                    team.setDivision(Division.valueOf(rs.getString("division")));
                    team.setCash(rs.getDouble("cash"));
                    team.setPrestige(rs.getDouble("prestige"));
                    return team;
                }
            }
        }
        return null;
    }
}
