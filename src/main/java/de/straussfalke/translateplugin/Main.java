package de.straussfalke.translateplugin;

import de.straussfalke.translateplugin.Datenbank.MySQL;
import de.straussfalke.translateplugin.Datenbank.MySQLFile;
import de.straussfalke.translateplugin.listeners.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        String s;
        Bukkit.getConsoleSender().sendMessage("§aTranslation activated");

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new JoinListener(), this);

        MySQLFile file = new MySQLFile();
        file.setStandard();
        file.readData();

        MySQL.connect();

        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playerdata(id INT(100) PRIMARY KEY AUTO_INCREMENT, username VARCHAR(16) NOT NULL, uuid CHAR(36) not null, coins INT(100) not null, language VARCHAR(3) not null);");
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
    }
}
