package me.electricfloor.helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import me.electricfloor.file.logging.ELogger;
import me.electricfloor.main.ElectricFloor;

/**
 * An implementation for managing multiple {@link YamlConfiguration}s, with added {@code setLocation} and {@code getLocation}
 * 
 * @author SunStorm
 *
 */
public class ConfigManager {
	private ELogger logger = ElectricFloor.getELogger();
	
	public HashMap<String, LinkedConfig> configurations = new HashMap<String, LinkedConfig>();
	
	public void createConfig(String name) {
		if (configurations.containsKey(name)) {
			logger.error(name + " config already exist!");
		} else {
			File cf;
			if (name.equalsIgnoreCase("mainconfig")) {
				cf = new File(ElectricFloor.getPlugin().getDataFolder(), name);
			} else {
				cf = new File(ElectricFloor.getPlugin().getDataFolder() + "/configurations", name);
			}
			YamlConfiguration config = YamlConfiguration.loadConfiguration(cf);
			LinkedConfig c = new LinkedConfig(config, cf);
			
			configurations.put(name, c);
		}
	}
	
	/**
	 * Reloads the specified configuration.
	 * 
	 * @param name
	 */
	public void reloadConfig(String name) {
		if (configurations.containsKey(name)) {
			YamlConfiguration c = configurations.get(name).configuration;
			File cf = configurations.get(name).configFile;
			
			InputStreamReader defStream = new InputStreamReader(ElectricFloor.getPlugin().getResource(name + ".yml"));
			
			if (!cf.exists() || cf.length() == 0L && defStream != null) {
				c = YamlConfiguration.loadConfiguration(defStream);
			} else {
				c = YamlConfiguration.loadConfiguration(cf);
			}
			
			configurations.get(name).configuration = c;
		}
	}
	
	/**
	 * Save the specified configuration
	 * 
	 * @param name
	 */
	public void saveConfig(String name) {
		if (configurations.containsKey(name)) {
			LinkedConfig c = configurations.get(name);
			try {
				c.configuration.save(c.configFile);
			} catch (IOException e) {
				logger.logException(e, "Failed to save configuration: " + name);
			}
		} else {
			logger.error(name + " config doesn't exist! cant be saved...");
		}
	}
	
	/**
	 * return the specified {@link YamlConfiguration} by {@code name}
	 * 
	 * @param name
	 * @return The specified configuration
	 */
	public YamlConfiguration getConfig(String name) {
		if (configurations.containsKey(name)) {
			return configurations.get(name).configuration;
		}
		return null;
	}
	
	public void setLocation(String configName, String path, Location loc) {
		if (configurations.containsKey(configName)) {
			YamlConfiguration configuration = configurations.get(configName).configuration;
			
			configuration.set(path + ".X", loc.getX());
			configuration.set(path + ".Y", loc.getY());
			configuration.set(path + ".Z", loc.getZ());
			configuration.set(path + ".pitch", loc.getPitch());
			configuration.set(path + ".yaw", loc.getYaw());
			configuration.set(path + ".world", loc.getWorld().getName());
			try {
				configuration.save(configurations.get(configName).configFile);
			} catch (IOException e) {
				logger.logException(e, "Failed to save configuration: " + configName);
			}
		}
	}
	
	public Location getLocation(String configName, String path) {
		if (configurations.containsKey(configName)) {
			YamlConfiguration configuration = configurations.get(configName).configuration;
			
			double x = configuration.getDouble(path + ".X");
			double y = configuration.getDouble(path + ".Y");
			double z = configuration.getDouble(path + ".Z");
			float pitch = (float) configuration.getDouble(path + ".pitch");
			float yaw = (float) configuration.getDouble(path + ".pitch");
			World world = Bukkit.getServer().getWorld(configuration.getString(path + ".world"));
			return new Location(world, x, y, z, yaw, pitch);
		}
		return null;
	}
}
