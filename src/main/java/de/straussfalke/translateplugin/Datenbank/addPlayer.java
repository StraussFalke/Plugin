package de.straussfalke.translateplugin.Datenbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class addPlayer {
    public static boolean addPlayer(String username, UUID uuid, int coins) {
        try (PreparedStatement stmt = MySQL.getConnection().prepareStatement(
                "INSERT INTO playerdata(username, uuid, coins) VALUES(?, ?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2,  uuid.toString());
            stmt.setInt(3, coins);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
