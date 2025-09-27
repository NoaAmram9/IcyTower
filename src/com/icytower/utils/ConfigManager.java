// Configuration Manager - Singleton
package com.icytower.utils;
import java.util.Properties;


public class ConfigManager {
    private static ConfigManager instance;
    private Properties config;
    
    private ConfigManager() {
        config = new Properties();
        loadDefaultConfig();
    }
    
    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
    
    private void loadDefaultConfig() {
        config.setProperty("window.width", "800");
        config.setProperty("window.height", "600");
        config.setProperty("game.fps", "60");
        config.setProperty("player.speed", "5.0");
        config.setProperty("player.jumpPower", "12.0");
        config.setProperty("physics.gravity", "0.5");
        config.setProperty("sound.enabled", "true");
    }
    
    public String getProperty(String key) {
        return config.getProperty(key);
    }
    
    public float getFloatProperty(String key) {
        return Float.parseFloat(config.getProperty(key, "0"));
    }
    
    public int getIntProperty(String key) {
        return Integer.parseInt(config.getProperty(key, "0"));
    }
    
    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(config.getProperty(key, "false"));
    }
}