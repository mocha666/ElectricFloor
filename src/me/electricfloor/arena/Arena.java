package me.electricfloor.arena;

import org.bukkit.configuration.file.YamlConfiguration;

import me.electricfloor.main.ElectricFloor;

public class Arena {
	
	public int maxPlayers = 15;
	public int minPlayers = 5;
	
	public boolean announced = false;
	
	public boolean autoStart = false;
	
	public ArenaState state = ArenaState.WAITING;
	public ArenaType type = ArenaType.EVENT;

	public ArenaGroup groups = new ArenaGroup();
	
	public String name = null;
	public String config = null;
	
	public Arena(String name) {
		this.name = name;
		this.config = name + "_config";
		ElectricFloor.manager.createConfig(config);
		
	}
	
	public void start() {
		
	}
	
	public void stop() {
		
	}
	
	public YamlConfiguration getConfig() {
		return ElectricFloor.manager.getConfig(config);
	}

}
