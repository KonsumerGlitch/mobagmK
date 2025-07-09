// File: src/main/java/com/zenmgmoba/service/PersistenceService.java
package com.zenmgmoba.service;

import com.zenmgmoba.dao.DatabaseSchema;
import com.zenmgmoba.dao.LeagueDAO;
import com.zenmgmoba.model.League;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * PersistenceService.java
 * Initializes the SQLite database schema and provides methods to save/load the League state.
 */
public class PersistenceService {

    private static final String DB_URL = "jdbc:sqlite:zenmgmoba.db";
    private static LeagueDAO leagueDAO;

    /**
     * Initialize the database (create tables) and DAO.
     */
    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            DatabaseSchema.createTables(conn);
            leagueDAO = new LeagueDAO(conn);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize persistence layer", e);
        }
    }

    /**
     * Saves the current League to the database.
     *
     * @param league the League instance to save
     */
    public static void saveLeague(League league) {
        try {
            leagueDAO.save(league);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error saving league: " + e.getMessage());
        }
    }

    /**
     * Loads the saved League from the database. If none exists, returns a new League.
     *
     * @return the loaded or new League
     */
    public static League loadLeague() {
        try {
            return leagueDAO.load();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading league, starting new: " + e.getMessage());
            return new League();
        }
    }
}
