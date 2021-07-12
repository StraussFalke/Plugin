package de.straussfalke.translateplugin.Datenbank;

import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL {

    public static String host;
    public static String port;
    public static String database;
    public static String username;
    public static String password ;
    public static Connection con;

    public static void connect() {
        if(!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                Bukkit.getConsoleSender().sendMessage("§aDatabase connected!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if(isConnected()){
            try {
                con.close();
                Bukkit.getConsoleSender().sendMessage("§cDatabase disconnected!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        return (con == null ? false : true);
    }
    public static Connection getConnection() {
        return con;
    }

    public static void update(String qry) {
        if(isConnected()) {
            try {
                con.createStatement().executeUpdate(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet getResult(String qry) {
        if(isConnected()) {
            try {
                return con.createStatement().executeQuery(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
