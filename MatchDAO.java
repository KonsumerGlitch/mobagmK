// File: src/main/java/com/zenmgmoba/dao/MatchDAO.java
package com.zenmgmoba.dao;

import com.zenmgmoba.model.Match;
import com.zenmgmoba.model.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MatchDAO.java
 * Handles CRUD operations for Match objects in the database.
 */
public class MatchDAO {
    private final Connection conn;

    public MatchDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new Match or updates an existing one.
     */
    public void save(Match match, int leagueId, int splitId) throws SQLException {
        String sql = """
            INSERT INTO matches (
                id, league_id, split_id,
                team_a_id, team_b_id, winner_id, loser_id,
                best_of, match_type,
                games_won_a, games_won_b
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT(id) DO UPDATE SET
                split_id      = excluded.split_id,
                team_a_id     = excluded.team_a_id,
                team_b_id     = excluded.team_b_id,
                winner_id     = excluded.winner_id,
                loser_id      = excluded.loser_id,
                games_won_a   = excluded.games_won_a,
                games_won_b   = excluded.games_won_b
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            ps.setInt(idx++, match.getId());
            ps.setInt(idx++, leagueId);
            ps.setInt(idx++, splitId);
            ps.setInt(idx++, match.getTeamA().getId());
            ps.setInt(idx++, match.getTeamB() != null ? match.getTeamB().getId() : 0);
            Team w = match.getWinner(), l = match.getLoser();
            ps.setInt(idx++, w != null ? w.getId() : 0);
            ps.setInt(idx++, l != null ? l.getId() : 0);
            ps.setInt(idx++, match.getBestOf());
            ps.setString(idx++, match.getMatchType().name());
            ps.setInt(idx++, match.getGamesWonA());
            ps.setInt(idx,   match.getGamesWonB());

            ps.executeUpdate();
        }
    }

    /**
     * Loads a Match by its ID.
     */
    public Match load(int matchId, LeagueDAO leagueDAO, TeamDAO teamDAO) throws SQLException {
        String sql = "SELECT * FROM matches WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, matchId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Team a = teamDAO.load(rs.getInt("team_a_id"));
                    Team b = teamDAO.load(rs.getInt("team_b_id"));
                    int bo = rs.getInt("best_of");
                    Match match = new Match(a, b, bo);
                    match.setId(matchId);
                    match.setWinner(teamDAO.load(rs.getInt("winner_id")));
                    match.setLoser(teamDAO.load(rs.getInt("loser_id")));
                    match.setGamesWonA(rs.getInt("games_won_a"));
                    match.setGamesWonB(rs.getInt("games_won_b"));
                    match.setMatchType(Match.MatchType.valueOf(rs.getString("match_type")));
                    return match;
                }
            }
        }
        return null;
    }
}
