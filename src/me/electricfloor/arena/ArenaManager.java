package me.electricfloor.arena;

import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

import me.electricfloor.config.ConfigManager;
import me.electricfloor.config.ConfigType;
import me.electricfloor.main.ElectricFloor;

public class ArenaManager {
	
	public HashMap<String, Arena> arenas = new HashMap<String, Arena>();
	
	ConfigManager configManager = ElectricFloor.manager;
	
	/**
	 * Configuration name
	 */
	public static final String ARENA_LIST = "arenaList";
	
	public void load() {
		
	}
	
	public void createArena(String name) {
		if (!arenas.containsKey(name)) {
			configManager.createConfig(name, ConfigType.ARENA);
			arenas.put(name, new Arena(name));
		}
	}
	
	public void saveArena(String name) {
		Arena a = null;
		if ((a = arenas.get(name)) != null) {
			YamlConfiguration c = configManager.getConfig(name);
			c.set("ArenaName", a.name);
			c.set("MinPlayers", a.minPlayers);
			c.set("MaxPlayers", a.maxPlayers);
			configManager.setLocation(name, "locations.lobby", null);
			configManager.setLocation(name, "locations.start", null);
			configManager.setLocation(name, "locations.lost", null);
			configManager.setLocation(name, "locations.1st", null);
			configManager.setLocation(name, "locations.2nd", null);
			configManager.setLocation(name, "locations.3rd", null);
			configManager.setLocation(name, "locations.arena.1", null);
			configManager.setLocation(name, "locations.arena.2", null);
		}
	}
	
	public void deleteArena(String arena) {
		Arena a = null;
		if ((a = arenas.get(arena)) != null) {
			a.stop();
			configManager.deleteConfig(arena);
			arenas.remove(arena);
		}
	}
	
	public void startArena(String arena) {
		Arena a = null;
		if ((a = arenas.get(arena)) != null) {
			a.start();
		}
	}
	
	public void stopArena(String arena) {
		Arena a = null;
		if ((a = arenas.get(arena)) != null) {
			a.stop();
		}
	}

}
