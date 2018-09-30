package me.electricfloor.debug;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.annotations.Beta;

import me.electricfloor.file.ELogger;
import me.electricfloor.file.FileHelper;
import me.electricfloor.file.LogLevel;
import me.electricfloor.main.ElectricFloor;

/**
 * This class is obsolete.
 * Can produce errors, us at own risk.
 * 
 * @author MrExplode
 *
 */
public class Debug {
	
	public String debugPrefix = "[DEBUG] ";
	
	public boolean enabled = false;
	
	private ArrayList<StoredPlayer> enabledPlayers = new ArrayList<StoredPlayer>();
	
	private ELogger eLog = ElectricFloor.getELogger();
	private Plugin plugin = ElectricFloor.getPlugin();
	
	private String nl = System.getProperty("line.separator");
	
	/**
	 * Remove all player who added earlier than a month
	 */
	@SuppressWarnings("deprecation")
	public void init() {
		FileHelper.extractFile(plugin.getDataFolder().toString() + "/data", "storedplayers");
		try {
			BufferedReader rd = new BufferedReader(new FileReader(plugin.getDataFolder().toString() + "/data/storedplayers"));
			
			String line;
			
			while ((line = rd.readLine()) != null) {
				String[] curr = line.split(";");
				Date playerDate = new Date();
				playerDate.setTime(Long.valueOf(curr[1]));
				StoredPlayer sp = new StoredPlayer(curr[0], playerDate);
				
				enabledPlayers.add(sp);
			}
			
			rd.close();
			
		} catch (IOException e) {
			eLog.logException(e);
		}
		
		Date now = new Date();
		for (int i = 0; i < enabledPlayers.size(); i++) {
			if (enabledPlayers.get(i).date.getMonth() != now.getMonth()) {
				removeDebugPlayer(Bukkit.getServer().getPlayer(enabledPlayers.get(i).player));
			}
		}
	}
	
	public void enableDebug() {
		this.enabled = true;
	}
	
	public void disableDebug() {
		this.enabled = false;
	}
	
	public void setDebug(boolean state) {
		this.enabled = state;
	}
	
	public boolean getDebugState() {
		return enabled;
	}
	
	public void addDebugPlayer(Player player) {
		for (int i = 0; i < enabledPlayers.size(); i++) {
			if (enabledPlayers.get(i).player.equals(player.getName())) {
				return;
			} else {
				enabledPlayers.add(new StoredPlayer(player.getName(), new Date()));
			}
			
			actualize();
		}
	}
	
	public void removeDebugPlayer(Player player) {
		for (int i = 0; i < enabledPlayers.size(); i++) {
			if (enabledPlayers.get(i).player.equals(player.getName())) {
				enabledPlayers.remove(i);
				break;
			}
		}
		
		actualize();
	}
	
	private void actualize() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(plugin.getDataFolder().toString() + "/data/storedplayers", false));
			
			for (int i = 0; i < enabledPlayers.size(); i++) {
				writer.write(enabledPlayers.get(i).player + ";" + enabledPlayers.get(i).date.getTime());
				writer.flush();
			}
			
			writer.close();
			
		} catch (IOException e) {
			eLog.logException(e);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void debug(String log) {
		if (enabled) {
			System.out.println(ElectricFloor.chatPrefix + debugPrefix + log);
			eLog.log(LogLevel.DEBUG, log);
		}
		
		Player current = null;
		eLog.log(LogLevel.DEBUG, log);
		for (int i = 0; i < enabledPlayers.size(); i++) {
			current = Bukkit.getServer().getPlayer(enabledPlayers.get(i).player);
			current.sendMessage(ElectricFloor.chatPrefix + debugPrefix + log);
		}
	}
	
	@Deprecated
	public void debugConsole(String log) {
		if (enabled) {
			System.out.println(ElectricFloor.chatPrefix + debugPrefix + log);
			eLog.log(LogLevel.DEBUG, log);
		}
	}
	
	@Deprecated
	public void debugForPlayer(String log) {
		Player current = null;
		eLog.log(LogLevel.DEBUG, log);
		for (int i = 0; i < enabledPlayers.size(); i++) {
			current = Bukkit.getServer().getPlayer(enabledPlayers.get(i).player);
			current.sendMessage(ElectricFloor.chatPrefix + debugPrefix + log);
		}
	}
	
	
	/**
	 * Early test, not use it.<br><br>
	 * Actually, this will compare the two given arguments, and if they not equal,<br>
	 * make an advanced debug report, with stack trace, type of the args, and etc.
	 * 
	 * <br><b>But this is not available now!</b>
	 */
	@Beta
	@Deprecated
	public void analyze(Object origin, Object var) {
		if (!origin.equals(var)) {
			StringBuilder report = new StringBuilder();
			
			//making the report
			report.append("-= ElectricFloor Advanced Debug info =-" + nl);
			report.append("");
			StackTraceElement tr = Thread.currentThread().getStackTrace()[2];
			System.out.println(tr.toString());
		}
	}
	
	private class StoredPlayer {
		private String player;
		private Date date;
		
		public StoredPlayer(String player, Date date) {
			this.player = player;
			this.date = date;
		}
	}

}
