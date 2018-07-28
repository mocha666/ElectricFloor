package me.electricfloor.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.electricfloor.Language.Language;
import me.electricfloor.NMSimplement.NMSimplement;
import me.electricfloor.NMSimplement.versions.NMS_1_10_R1;
import me.electricfloor.NMSimplement.versions.NMS_1_11_R1;
import me.electricfloor.NMSimplement.versions.NMS_1_12_R1;
import me.electricfloor.NMSimplement.versions.NMS_1_8_R2;
import me.electricfloor.NMSimplement.versions.NMS_1_8_R3;
import me.electricfloor.NMSimplement.versions.NMS_1_9_R1;
import me.electricfloor.NMSimplement.versions.NMS_1_9_R2;
import me.electricfloor.config.ConfigManager;
import me.electricfloor.event.Event;
import me.electricfloor.event.EventGroup;
import me.electricfloor.file.ELogger;
import me.electricfloor.file.LogLevel;

public class ElectricFloor extends JavaPlugin implements Listener {
	static Logger logger;
	private static ELogger eLogger;
	public static boolean useWorldEdit;
	public static String chatPrefix;
	public static String warnPrefix;
	private String serverVersion;
	public static PluginDescriptionFile pdfile;
	public static boolean isRestart;
	
	public static ConfigManager manager = new ConfigManager();
	
	private static ElectricFloor instance;
	private static Listeners listeners;
	private static NMSimplement implement;
	//TODO: logging
	private static Language l;
	
	public static final String MAIN_CONFIG = "mainConfig";
	
	Plugin plugin = this;
	
	public void onEnable() {
		initVariables();
		eLogger.createLogFile(isRestart);
		eLogger.info("Starting plugin...");
		
		if (manager.getConfig(MAIN_CONFIG) == null) {
			manager.createConfig(MAIN_CONFIG);
			manager.reloadConfig(MAIN_CONFIG);
		}
		
		setupNMS();
		setupEconomy();
		
		getServer().getPluginManager().registerEvents(listeners, this);
		
		getCommand("electricfloor").setExecutor(new EfCommand(this));
		getCommand("ef").setExecutor(new EfCommand(this));
		getCommand("event").setExecutor(new EventCommand(this));
		
		setupWorldEdit();
		
		l.setupLanguage();
		
		logger.info("[" + pdfile.getName() + "]" + " (V." + pdfile.getVersion() + ") Enabled!");
		
		logger.severe("[ElectricFloor] This version is not working. Just as you can see. Not buggy version, NOT WORKING. Changes are in progress.");
		
		eLogger.info("Plugin successfully started!");
		if (manager.getConfig(MAIN_CONFIG).getBoolean("motd")) {
			eLogger.info("If you want to disable motd, set it false in mainConfig.yml");
		}
		
		Event.teleportRadius = manager.getConfig(MAIN_CONFIG).getInt("teleportRadius", 5);
		
		chatPrefix = manager.getConfig(MAIN_CONFIG).getString("messages.prefix", "§b[§eElectricFloor§b]§r ");
		warnPrefix = manager.getConfig(MAIN_CONFIG).getString("messages.warnprefix","§a[§2ElectricFloor§a]§r ");
		
		if (Bukkit.getOnlinePlayers().size() != 0) {
			eLogger.log(LogLevel.CRITICAL, "It seems ElectricFloor plugin has been reloaded, with an other plugin, or with /reload command. Please try to avoid this");
			logger.log(Level.WARNING, "It seems ElectricFloor plugin has been reloaded, with en other plugin, or with /reload command. Please try to avoid this");
			
			for (Player all : Bukkit.getOnlinePlayers()) {
				if (!EventGroup.isAppearSomewhere(all)) {
					EventGroup.addToGroup(all, EventGroup.NOT_MARKED);
				}
			}
		}
		
	}
	
	public void onDisable() {
		eLogger.info("Disabling...");
		Event.eventReadyTo = false;
		Event.eventBroadcasted = false;
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			Event.leaveEvent(all, plugin, true);
		}
		
		//reset arena
		Utils.arenaSet(null, null, null, true);
		
		logger.info("[" + pdfile.getName() + "]" + " (V." + pdfile.getVersion() + ") Disabled!");
		eLogger.info("Plugin disabled!");
		Runtime.getRuntime().gc();
	}
	
	private void setupNMS() {
		 String ver;
		try {

	            serverVersion = getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
	            ver = getServer().getBukkitVersion().toString();

	        } catch (ArrayIndexOutOfBoundsException e) {
	            eLogger.logException(e, "Failed to resolve the NMS version!");
	            ver = "undefinied";
	        }

	        if (serverVersion.equals("v1_8_R2")) {
	        	implement = new NMS_1_8_R2();
	        	eLogger.info("The plugin is compatible with wersion " + ver);
	        	
	        } else if (serverVersion.equals("v1_8_R3")) {
	        	implement = new NMS_1_8_R3();
	        	eLogger.info("The plugin is compatible with wersion " + ver);
	        } else if (serverVersion.equals("v1_9_R1")) {
	        	implement = new NMS_1_9_R1();
	        	eLogger.info("The plugin is compatible with wersion " + ver);
	        } else if (serverVersion.equals("v1_9_R2")) {
	        	implement = new NMS_1_9_R2();
	        	eLogger.info("The plugin is compatible with wersion " + ver);
	        } else if (serverVersion.equals("v1_10_R1")) {
	        	implement = new NMS_1_10_R1();
	        	eLogger.info("The plugin is compatible with wersion " + ver);
	        } else if (serverVersion.equals("v1_11_R1")) {
	        	implement = new NMS_1_11_R1();
	        	eLogger.info("The plugin is compatible with wersion " + ver);
	        } else if (serverVersion.equals("v1_12_R1")) {
	        	implement = new NMS_1_12_R1();
	        	eLogger.info("The plugin is compatible with wersion " + ver);
	        }
	        
	        if (implement == null || ver.equalsIgnoreCase("undefinied")) {
	        	logger.severe("[ElectricFloor] Incompatible version " + serverVersion + " Compativle versions: 1.8, 1.9, 1.10, 1.11, 1.12 (all subversion)");
				logger.severe("[ElectricFloor] Disabling");
				eLogger.log(LogLevel.ERROR, "Incompatible version \" + serverVersion + \" Compativle versions: 1.8, 1.9, 1.10, 1.11, 1.12 (all subversion");
				Bukkit.getServer().getPluginManager().disablePlugin(this);
	        }
	}
	
	private void setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Essentials") == null) {
			eLogger.log(LogLevel.ERROR, "Can't find Essentials");
			logger.severe("[ElectricFloor] Can't find Essentials, disabling");
			getServer().getPluginManager().disablePlugin(this);
        } else {
        	eLogger.info("Essentials found!");
        }
	}
	
	public static NMSimplement getImplementation() {
		return implement;
	}
	
	public void initVariables() {
		instance = this;
		eLogger = new ELogger();
		logger = Logger.getLogger("Minecraft");
		isRestart = false;
		pdfile = getDescription();
		l = new Language();
		listeners = new Listeners();
	}
	
	public static Plugin getPlugin() {
		return Bukkit.getServer().getPluginManager().getPlugin("ElectricFloor");
	}
	
	public static ElectricFloor getInstance() {
		return instance;
	}
	
	public static Logger getNormalLogger() {
		return logger;
	}
	
	public static ELogger getELogger() {
		return eLogger;
	}
	
	public static Language getLanguage() {
		return l;
	}
	
	private void setupWorldEdit() {
		if (Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") == null) {
			useWorldEdit = false;
			eLogger.info("Unable to find WorldEdit, now using own implementation");
		} else {
			useWorldEdit = true;
			eLogger.info("Successfully hooked into WorldEdit");
		}
	}
	
}
