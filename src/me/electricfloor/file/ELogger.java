package me.electricfloor.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import me.electricfloor.debug.Debug;
import me.electricfloor.main.ElectricFloor;

public class ELogger extends YamlConfiguration {

	static Plugin plugin = ElectricFloor.getPlugin();
	
	public void createLogFile(boolean selector) {
		try {
            File file = new File(plugin.getDataFolder().getAbsolutePath() + "/log.txt");
            if (!file.exists()){
                if (plugin.getResource("log.txt") != null){
                    plugin.saveResource("log.txt", false);
                }else{
                    save(file);
                }
            }else{
            	if (!selector) {
            		PrintWriter writer = new PrintWriter(file);
            		writer.print("");
            		writer.close();
            	}
                load(file);
                save(file);
            }
        } catch (Exception ex) {
        	System.out.println("[ElectricFloor] Failed to create log file :(");
			System.out.println("[ElectricFloor] Error: " + ex.getMessage());
			System.out.println("[ElectricFloor] Caused by: " + ex.getCause());
        }
	}
	
	public void logException(Exception e) {
		logException(e, null);
	}
	
	public void logException(Exception e, String msg) {
		try {
			
				if (msg == null) {
					log(LogLevel.ERROR, "An Exception occured!");
				} else {
					error(msg);
				}
				if (ElectricFloor.manager.getConfig("mainConfig").getBoolean("printStToConsole")) {
					e.printStackTrace();
				} else {
					ElectricFloor.getNormalLogger().severe("[ElectricFloor] An Exception occured! If you want to know more, look at the log.txt");
				}
			
				PrintWriter writer = new PrintWriter(new FileWriter(plugin.getDataFolder().getAbsolutePath() + "/log.txt", true));
			
				e.printStackTrace(writer);
				writer.write(" ");
				writer.flush();
			
			
			} catch (IOException e1) {
				e1.printStackTrace();
			}	
	}
	
	public void log(LogLevel level, String log) {
		int hour = LocalDateTime.now().getHour();
		int min = LocalDateTime.now().getMinute();
		int sec = LocalDateTime.now().getSecond();
		
		String prefix = "[" + hour + ":" + min + ":" + sec + " " + level.toString() + "]:";
		
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(plugin.getDataFolder().getAbsolutePath() + "/log.txt", true));
			writer.println(prefix + " " + log);
			writer.close();
		} catch (Exception e) {
			System.out.println("[ElectricFloor] Failed to write into log file :(");
			System.out.println("[ElectricFloor] Error: " + e.getMessage());
			System.out.println("[ElectricFloor] Caused by: " + e.getCause());
		}
		
	}
	
	public void info(String log) {
		log(LogLevel.INFO, log);
	}
	
	public void warning(String log) {
		log(LogLevel.WARNING, log);
	}
	
	public void error(String log) {
		log(LogLevel.ERROR, log);
	}
	
}
