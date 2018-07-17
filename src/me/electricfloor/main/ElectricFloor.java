package me.electricfloor.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import me.electricfloor.debug.Debug;
import me.electricfloor.event.Event;
import me.electricfloor.event.EventGroup;
import me.electricfloor.file.logging.ELogger;
import me.electricfloor.file.logging.LogLevel;

public class ElectricFloor extends JavaPlugin implements Listener {
	static Logger logger;
	public static ELogger eLogger;
	public static boolean eventReadyTo;
	public static boolean eventBroadcasted;
	public static Location sel1;
	public static Location sel2;
	public static boolean useWorldEdit;
	public String chatPrefix;
	public static String warnPrefix;
	public static String serverVersion;
	public static int eventPlayerCounter;
	public static int teleportRadius;
	public static PluginDescriptionFile pdfile;
	public static boolean isRestart;
	public static Debug d;
	
	private static ElectricFloor instance;
	private static Listeners listeners;
	private static NMSimplement implement;
	//TODO: logging
	private static Language l;
	
	Plugin plugin = this;
	
	public void onEnable() {
		initVariables();
		eLogger.createLogFile(isRestart);
		eLogger.log(LogLevel.INFO, "Plugin indátása...");
		
		setupNMS();
		setupEconomy();
		
		getServer().getPluginManager().registerEvents(listeners, this);
		
		getCommand("electricfloor").setExecutor(new EfCommand(this));
		getCommand("ef").setExecutor(new EfCommand(this));
		getCommand("event").setExecutor(new EventCommand(this));
		
		setupWorldEdit();
		
		l.setupLanguage();
		
		d.init();
		
		logger.info("[" + pdfile.getName() + "]" + " (V." + pdfile.getVersion() + ") Enabled!");
		eLogger.log(LogLevel.INFO, "Plugin successfully started!");
		if (this.getConfig().getBoolean("motd")) {
			eLogger.log(LogLevel.INFO, "If you want to disable motd, set it false in mainConfig.yml");
		}
		
		teleportRadius = getConfig().getInt("teleportRadius", 5);
		
		chatPrefix = getConfig().getString("messages.prefix", "§b[§eElectricFloor§b]§r ");
		warnPrefix = getConfig().getString("messages.warnprefix","§a[§2ElectricFloor§a]§r ");
		
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
		eLogger.log(LogLevel.INFO, "Disabling...");
		eventReadyTo = false;
		eventBroadcasted = false;
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			Event.leaveEvent(all, plugin, true);
		}
		
		//reset arena
		Utils.arenaSet(null, null, null, true);
		
		logger.info("[" + pdfile.getName() + "]" + " (V." + pdfile.getVersion() + ") Kikapcsolva!");
		eLogger.log(LogLevel.INFO, "Plugin kikapcsolva!");
	}
	
	private void setupNMS() {
		 String ver;
		try {

	            serverVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
	            ver = Bukkit.getServer().getBukkitVersion().toString();

	        } catch (ArrayIndexOutOfBoundsException e) {
	            eLogger.logException(e, "Failed to resolve the NMS version!");
	            ver = "undefinied";
	        }

	        if (serverVersion.equals("v1_8_R2")) {
	        	implement = new NMS_1_8_R2();
	        	eLogger.log(LogLevel.INFO, "The plugin is compatible with wersion " + ver);
	        	
	        } else if (serverVersion.equals("v1_8_R3")) {
	        	implement = new NMS_1_8_R3();
	        	eLogger.log(LogLevel.INFO, "The plugin is compatible with wersion " + ver);
	        } else if (serverVersion.equals("v1_9_R1")) {
	        	implement = new NMS_1_9_R1();
	        	eLogger.log(LogLevel.INFO, "The plugin is compatible with wersion " + ver);
	        } else if (serverVersion.equals("v1_9_R2")) {
	        	implement = new NMS_1_9_R2();
	        	eLogger.log(LogLevel.INFO, "The plugin is compatible with wersion " + ver);
	        } else if (serverVersion.equals("v1_10_R1")) {
	        	implement = new NMS_1_10_R1();
	        	eLogger.log(LogLevel.INFO, "The plugin is compatible with wersion " + ver);
	        } else if (serverVersion.equals("v1_11_R1")) {
	        	implement = new NMS_1_11_R1();
	        	eLogger.log(LogLevel.INFO, "The plugin is compatible with wersion " + ver);
	        } else if (serverVersion.equals("v1_12_R1")) {
	        	implement = new NMS_1_12_R1();
	        	eLogger.log(LogLevel.INFO, "The plugin is compatible with wersion " + ver);
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
        	eLogger.log(LogLevel.INFO, "Essentials found!");
        }
	}
	
	public static Debug getDebug() {
		return d;
	}
	
	public static NMSimplement getImplementation() {
		return implement;
	}
	
	public void initVariables() {
		instance = this;
		d = new Debug();
		eLogger = new ELogger();
		logger = Logger.getLogger("Minecraft");
		isRestart = false;
		pdfile = getDescription();
		l = new Language();
		eventReadyTo = false;
		eventBroadcasted = false;
		sel1 = null;
		sel2 = null;
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
			eLogger.log(LogLevel.INFO, "Unable to find WorldEdit, now using own implement");
		} else {
			useWorldEdit = true;
			eLogger.log(LogLevel.INFO, "Successfully hooked into WorldEdit");
		}
	}
	
}
