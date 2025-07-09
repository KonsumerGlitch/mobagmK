package com.zenmgmoba.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseSchema.java
 * Creates and manages the SQLite database schema for the ZenGM MOBA GM application.
 * Handles table creation, indexes, and database structure initialization.
 */
public class DatabaseSchema {

    /**
     * Creates all necessary tables for the application if they don't exist.
     * This includes tables for leagues, teams, players, matches, stats, and achievements.
     */
    public static void createTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {

            // Create leagues table
            statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS leagues (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    current_season INTEGER DEFAULT 1,
                    current_split TEXT DEFAULT 'WINTER',
                    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // Create teams table
            statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS teams (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    league_id INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    division TEXT NOT NULL CHECK (division IN ('CHAMPIONS', 'CONTENDERS', 'INTERMEDIATE')),
                    cash REAL DEFAULT 1000000.0,
                    prestige REAL DEFAULT 50.0,
                    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (league_id) REFERENCES leagues(id) ON DELETE CASCADE
                )
            """);

            // Create players table
            statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS players (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    team_id INTEGER,
                    name TEXT NOT NULL,
                    role TEXT NOT NULL CHECK (role IN ('TOP', 'JGL', 'MID', 'ADC', 'SUP')),
                    age INTEGER NOT NULL CHECK (age >= 16 AND age <= 40),
                    overall_rating INTEGER NOT NULL CHECK (overall_rating >= 1 AND overall_rating <= 99),
                    potential INTEGER NOT NULL CHECK (potential >= 1 AND potential <= 99),
                    contract_years INTEGER DEFAULT 0,
                    salary REAL DEFAULT 0.0,
                    is_starter BOOLEAN DEFAULT FALSE,
                    
                    -- 16 core attributes (1-99 scale)
                    adaptability INTEGER CHECK (adaptability >= 1 AND adaptability <= 99),
                    fortitude INTEGER CHECK (fortitude >= 1 AND fortitude <= 99),
                    consistency INTEGER CHECK (consistency >= 1 AND consistency <= 99),
                    team_player INTEGER CHECK (team_player >= 1 AND team_player <= 99),
                    leadership INTEGER CHECK (leadership >= 1 AND leadership <= 99),
                    tactical_awareness INTEGER CHECK (tactical_awareness >= 1 AND tactical_awareness <= 99),
                    laning INTEGER CHECK (laning >= 1 AND laning <= 99),
                    team_fighting INTEGER CHECK (team_fighting >= 1 AND team_fighting <= 99),
                    risk_taking INTEGER CHECK (risk_taking >= 1 AND risk_taking <= 99),
                    positioning INTEGER CHECK (positioning >= 1 AND positioning <= 99),
                    skill_shots INTEGER CHECK (skill_shots >= 1 AND skill_shots <= 99),
                    last_hitting INTEGER CHECK (last_hitting >= 1 AND last_hitting <= 99),
                    summoner_spells INTEGER CHECK (summoner_spells >= 1 AND summoner_spells <= 99),
                    stamina INTEGER CHECK (stamina >= 1 AND stamina <= 99),
                    injury_resistant INTEGER CHECK (injury_resistant >= 1 AND injury_resistant <= 99),
                    
                    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE SET NULL
                )
            """);

            // Create champions table
            statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS champions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    league_id INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    champion_type TEXT NOT NULL,
                    win_rate REAL DEFAULT 50.0 CHECK (win_rate >= 0.0 AND win_rate <= 100.0),
                    ban_rate REAL DEFAULT 0.0 CHECK (ban_rate >= 0.0 AND ban_rate <= 100.0),
                    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (league_id) REFERENCES leagues(id) ON DELETE CASCADE
                )
            """);

            // Create matches table
            statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS matches (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    league_id INTEGER NOT NULL,
                    split_id INTEGER,
                    team_a_id INTEGER NOT NULL,
                    team_b_id INTEGER NOT NULL,
                    winner_id INTEGER,
                    loser_id INTEGER,
                    best_of INTEGER NOT NULL CHECK (best_of IN (1, 3, 5)),
                    match_type TEXT DEFAULT 'REGULAR' CHECK (match_type IN ('REGULAR', 'PLAYOFF', 'INTERNATIONAL')),
                    games_won_a INTEGER DEFAULT 0,
                    games_won_b INTEGER DEFAULT 0,
                    match_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (league_id) REFERENCES leagues(id) ON DELETE CASCADE,
                    FOREIGN KEY (team_a_id) REFERENCES teams(id) ON DELETE CASCADE,
                    FOREIGN KEY (team_b_id) REFERENCES teams(id) ON DELETE CASCADE,
                    FOREIGN KEY (winner_id) REFERENCES teams(id) ON DELETE SET NULL,
                    FOREIGN KEY (loser_id) REFERENCES teams(id) ON DELETE SET NULL
                )
            """);

            // Create splits table
            statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS splits (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    league_id INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    season INTEGER NOT NULL,
                    split_type TEXT NOT NULL CHECK (split_type IN ('WINTER', 'SPRING', 'SUMMER')),
                    status TEXT DEFAULT 'UPCOMING' CHECK (status IN ('UPCOMING', 'REGULAR_SEASON', 'PLAYOFFS', 'COMPLETED')),
                    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    end_date TIMESTAMP,
                    FOREIGN KEY (league_id) REFERENCES leagues(id) ON DELETE CASCADE
                )
            """);

            // Create player statistics table
            statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS player_stats (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    player_id INTEGER NOT NULL,
                    match_id INTEGER NOT NULL,
                    kills INTEGER DEFAULT 0,
                    deaths INTEGER DEFAULT 0,
                    assists INTEGER DEFAULT 0,
                    creep_score INTEGER DEFAULT 0,
                    damage_per_minute REAL DEFAULT 0.0,
                    barons_taken INTEGER DEFAULT 0,
                    dragons_taken INTEGER DEFAULT 0,
                    turrets_taken INTEGER DEFAULT 0,
                    game_duration_minutes INTEGER DEFAULT 0,
                    recorded_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE,
                    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE
                )
            """);

            // Create achievements table
            statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS achievements (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    player_id INTEGER NOT NULL,
                    achievement_type TEXT NOT NULL,
                    achievement_title TEXT NOT NULL,
                    description TEXT,
                    season INTEGER,
                    split_type TEXT,
                    earned_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE
                )
            """);

            // Create team champion synergy table
            statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS team_champion_synergy (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    team_id INTEGER NOT NULL,
                    champion_id INTEGER NOT NULL,
                    synergy_value REAL DEFAULT 0.5 CHECK (synergy_value >= 0.0 AND synergy_value <= 1.0),
                    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE,
                    FOREIGN KEY (champion_id) REFERENCES champions(id) ON DELETE CASCADE,
                    UNIQUE(team_id, champion_id)
                )
            """);

            // Create indexes for better performance
            createIndexes(statement);

            System.out.println("Database schema created successfully.");
        }
    }

    /**
     * Creates database indexes for improved query performance.
     */
    private static void createIndexes(Statement statement) throws SQLException {
        // Indexes on foreign keys
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_teams_league_id ON teams(league_id)");
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_players_team_id ON players(team_id)");
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_matches_league_id ON matches(league_id)");
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_matches_teams ON matches(team_a_id, team_b_id)");
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_player_stats_player_id ON player_stats(player_id)");
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_player_stats_match_id ON player_stats(match_id)");
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_achievements_player_id ON achievements(player_id)");
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_synergy_team_id ON team_champion_synergy(team_id)");

        // Indexes on commonly queried fields
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_players_role ON players(role)");
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_players_overall ON players(overall_rating)");
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_teams_division ON teams(division)");
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_teams_prestige ON teams(prestige)");
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_matches_type ON matches(match_type)");
        statement.executeUpdate("CREATE INDEX IF NOT EXISTS idx_splits_status ON splits(status)");
    }

    /**
     * Drops all tables (useful for testing or resetting the database).
     */
    public static void dropAllTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS team_champion_synergy");
            statement.executeUpdate("DROP TABLE IF EXISTS achievements");
            statement.executeUpdate("DROP TABLE IF EXISTS player_stats");
            statement.executeUpdate("DROP TABLE IF EXISTS splits");
            statement.executeUpdate("DROP TABLE IF EXISTS matches");
            statement.executeUpdate("DROP TABLE IF EXISTS champions");
            statement.executeUpdate("DROP TABLE IF EXISTS players");
            statement.executeUpdate("DROP TABLE IF EXISTS teams");
            statement.executeUpdate("DROP TABLE IF EXISTS leagues");

            System.out.println("All tables dropped successfully.");
        }
    }

    /**
     * Checks if the database schema exists and is properly initialized.
     */
    public static boolean isSchemaInitialized(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery("SELECT 1 FROM leagues LIMIT 1");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
