package com.iseong.zombie.data;

import com.iseong.zombie.zombie;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class teamManager {
    private final zombie plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public teamManager(zombie plugin) {
        this.plugin = plugin;
        this.saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), "team.yml");
        }

        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
        InputStream defaultStream = this.plugin.getResource("team.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getDataConfig() {
        if (this.dataConfig == null) {
            this.reloadConfig();
        }

        return this.dataConfig;
    }

    public void saveConfig() {
        if (this.dataConfig != null && this.configFile != null) {
            try {
                this.getDataConfig().save(this.configFile);
            } catch (IOException e) {
                this.plugin.getLogger().log(Level.SEVERE, "Could not save config to" + this.configFile, e);
            }
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), "team.yml");
        }

        if (!this.configFile.exists()) {
            this.plugin.saveResource("team.yml", false);
        }
    }
}