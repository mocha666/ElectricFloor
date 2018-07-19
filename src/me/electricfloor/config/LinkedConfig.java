package me.electricfloor.helpers;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class LinkedConfig {
	
	public YamlConfiguration configuration = null;
	public File configFile = null;
	
	
	public LinkedConfig(YamlConfiguration configuration, File configFile) {
		this.configuration = configuration;
		this.configFile = configFile;
	}

	public YamlConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(YamlConfiguration configuration) {
		this.configuration = configuration;
	}

	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}

}
