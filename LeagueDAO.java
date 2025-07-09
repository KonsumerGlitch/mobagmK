// File: src/main/java/com/zenmgmoba/dao/LeagueDAO.java
package com.zenmgmoba.dao;

import com.zenmgmoba.model.*;
import com.zenmgmoba.util.RandomUtil;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * LeagueDAO.java
 * Persists and loads the League state via serialization in SQLite.
 */
public class LeagueDAO {
    private final Connection conn;

    public LeagueDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Saves the entire League object into the leagues table as a BLOB.
     */
    public void save(League league) throws Exception {
        String sql = "INSERT OR REPLACE INTO leagues(id, name, data, last_updated) VALUES(1, ?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "Global MOBA League");
            ps.setBytes(2, serializeLeague(league));
            ps.executeUpdate();
        }
    }

    /**
     * Loads the League object from the leagues table.
     * If none exists, returns a newly initialized League.
     */
    public League load() throws Exception {
        String sql = "SELECT data FROM leagues WHERE id = 1";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                byte[] blob = rs.getBytes("data");
                return deserializeLeague(blob);
            }
        }
        return new League(); // default if no saved state
    }

    private byte[] serializeLeague(League league) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(league);
            return bos.toByteArray();
        }
    }

    private League deserializeLeague(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            return (League) in.readObject();
        }
    }
}
