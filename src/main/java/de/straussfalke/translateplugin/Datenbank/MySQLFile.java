package de.straussfalke.translateplugin.Datenbank;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MySQLFile {
    public void setStandard() {
        FileConfiguration cfg = getFileConfiguration();

        cfg.options().copyDefaults(true);

        cfg.addDefault("host", "localhost");
        cfg.addDefault("port", "3306");
        cfg.addDefault("database", "straussfalkeplugins");
        cfg.addDefault("username", "root");
        cfg.addDefault("password", "");

        try {
            cfg.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile() {
        return new File("D:/Programmierung/Plugins/SfAdminTools", "mysql.yml");
    }

    private FileConfiguration getFileConfiguration() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void readData(){
        FileConfiguration cfg = getFileConfiguration();

        MySQL.host = cfg.getString("host");
        MySQL.port = cfg.getString("port");
        MySQL.database = cfg.getString("database");
        MySQL.username = cfg.getString("username");
        MySQL.password = cfg.getString("password");
    }
}
