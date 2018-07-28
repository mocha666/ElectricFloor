package me.electricfloor.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class LinkedConfig {
	
	public YamlConfiguration configuration = null;
	public File configFile = null;
	public ConfigType type = ConfigType.GENERAL;
	
	public LinkedConfig(YamlConfiguration configuration, File configFile, ConfigType type) {
		this.configuration = configuration;
		this.configFile = configFile;
		this.type = type;
	}
	
	public LinkedConfig(YamlConfiguration configuration, File configFile) {
		this(configuration, configFile, ConfigType.GENERAL);
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
