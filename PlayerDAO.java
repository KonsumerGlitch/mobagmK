// File: src/main/java/com/zenmgmoba/dao/PlayerDAO.java
package com.zenmgmoba.dao;

import com.zenmgmoba.model.Player;
import com.zenmgmoba.model.PlayerAttributes;
import com.zenmgmoba.model.Role;
import com.zenmgmoba.model.PlayerProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PlayerDAO.java
 * Handles CRUD operations for Player objects in the database.
 */
public class PlayerDAO {
    private final Connection conn;

    public PlayerDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new Player or updates an existing one.
     */
    public void save(Player player, int teamId) throws SQLException {
        String sql = """
            INSERT INTO players (
                id, team_id, name, role, age,
                overall_rating, potential, contract_years, salary, is_starter,
                adaptability, fortitude, consistency, team_player, leadership,
                tactical_awareness, laning, team_fighting, risk_taking, positioning,
                skill_shots, last_hitting, summoner_spells, stamina, injury_resistant
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT(id) DO UPDATE SET
                team_id = excluded.team_id,
                name = excluded.name,
                role = excluded.role,
                age = excluded.age,
                overall_rating = excluded.overall_rating,
                potential = excluded.potential,
                contract_years = excluded.contract_years,
                salary = excluded.salary,
                is_starter = excluded.is_starter,
                adaptability = excluded.adaptability,
                fortitude = excluded.fortitude,
                consistency = excluded.consistency,
                team_player = excluded.team_player,
                leadership = excluded.leadership,
                tactical_awareness = excluded.tactical_awareness,
                laning = excluded.laning,
                team_fighting = excluded.team_fighting,
                risk_taking = excluded.risk_taking,
                positioning = excluded.positioning,
                skill_shots = excluded.skill_shots,
                last_hitting = excluded.last_hitting,
                summoner_spells = excluded.summoner_spells,
                stamina = excluded.stamina,
                injury_resistant = excluded.injury_resistant
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            ps.setInt(idx++, player.getId());
            ps.setInt(idx++, teamId);
            ps.setString(idx++, player.getName());
            ps.setString(idx++, player.getRole().name());
            ps.setInt(idx++, player.getAge());
            ps.setInt(idx++, player.getOverall());
            ps.setInt(idx++, player.getPotential());
            ps.setInt(idx++, player.getContractYears());
            ps.setDouble(idx++, player.getSalary());
            ps.setBoolean(idx++, player.isStarter());

            PlayerAttributes a = player.getAttributes();
            ps.setInt(idx++, a.getAdaptability());
            ps.setInt(idx++, a.getFortitude());
            ps.setInt(idx++, a.getConsistency());
            ps.setInt(idx++, a.getTeamPlayer());
            ps.setInt(idx++, a.getLeadership());
            ps.setInt(idx++, a.getTacticalAwareness());
            ps.setInt(idx++, a.getLaning());
            ps.setInt(idx++, a.getTeamFighting());
            ps.setInt(idx++, a.getRiskTaking());
            ps.setInt(idx++, a.getPositioning());
            ps.setInt(idx++, a.getSkillShots());
            ps.setInt(idx++, a.getLastHitting());
            ps.setInt(idx++, a.getSummonerSpells());
            ps.setInt(idx++, a.getStamina());
            ps.setInt(idx,   a.getInjuryResistant());

            ps.executeUpdate();
        }
    }

    /**
     * Loads a Player by its ID.
     */
    public Player load(int playerId) throws SQLException {
        String sql = "SELECT * FROM players WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PlayerAttributes attrs = new PlayerAttributes(
                            rs.getInt("adaptability"),
                            rs.getInt("fortitude"),
                            rs.getInt("consistency"),
                            rs.getInt("team_player"),
                            rs.getInt("leadership"),
                            rs.getInt("tactical_awareness"),
                            rs.getInt("laning"),
                            rs.getInt("team_fighting"),
                            rs.getInt("risk_taking"),
                            rs.getInt("positioning"),
                            rs.getInt("skill_shots"),
                            rs.getInt("last_hitting"),
                            rs.getInt("summoner_spells"),
                            rs.getInt("stamina"),
                            rs.getInt("injury_resistant")
                    );

                    Player player = new Player(
                            rs.getString("name"),
                            Role.valueOf(rs.getString("role")),
                            rs.getInt("age"),
                            rs.getInt("overall_rating"),
                            rs.getInt("potential"),
                            attrs
                    );
                    player.setId(playerId);
                    player.setContractYears(rs.getInt("contract_years"));
                    player.setSalary(rs.getDouble("salary"));
                    player.setStarter(rs.getBoolean("is_starter"));

                    // Load profile and stats as needed via StatsDAO
                    PlayerProfile profile = new PlayerProfile();
                    // Profile loading logic omitted for brevity
                    player.setProfile(profile);

                    return player;
                }
            }
        }
        return null;
    }
}
