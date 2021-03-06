package me.electricfloor.Language;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.annotations.Beta;

import me.electricfloor.error.ElectricError;
import me.electricfloor.file.ELogger;
import me.electricfloor.file.LogLevel;
import me.electricfloor.main.ElectricFloor;

@Beta
public class Language {
	
	private static HashMap<String, String> messages = new HashMap<String, String>();
	private static HashMap<String, String> defaultM = new HashMap<String, String>();
	
	private static Plugin plugin = ElectricFloor.getPlugin();
	
	private static String lang = "en";
	
	public static ELogger eLog = ElectricFloor.getELogger();
	
	private static Logger logger = Logger.getLogger("Minecraft");
	
	private static boolean enabled = false;
	
	public static boolean isEnabled() {
		return enabled;
	}

	public static void setupLanguage() {
		lang = ElectricFloor.manager.getConfig(ElectricFloor.MAIN_CONFIG).getString("language", "en");
		extract();
		loadCurrentAndDefault();
		enabled = true;
	}
	
	public static String t(String message) {
		String msg = null;
		if (messages.containsKey(message)) {
			msg = messages.get(message);
		} else {
			if (defaultM.containsKey(message)) {
				msg = defaultM.get(message);
				//TODO: logging
				eLog.log(LogLevel.CRITICAL, "err");
				logger.severe("err");
			} else {
				throw new ElectricError("Fuckin' error in ElectricFloor language system", new NullPointerException("No such message like this in default messages"));
			}
		}
		
		return msg;
	}
	
	/**
	 * Get a translated message with arguments in it.<br>
	 * Argument syntax in lang files: {0}, {1}, {2}... etc.
	 * 
	 * @param message
	 * @param args
	 */
	public static String t(String message, String... args) {
		String returns = null;
		if (messages.containsKey(message)) {
			returns = prepareArgLists(messages.get(message), args);
		} else if (defaultM.containsKey(message)) {
			returns = prepareArgLists(defaultM.get(message), args);
		} else {
			throw new NullPointerException("No such message: " + message);
		}
		return returns;
	}
	
	private static String prepareArgLists(String message, String... args) {
		int argIndex = message.split("{").length;
		for (int i = 0; i < argIndex; i++) {
			message.replace("{" + i + "}", args[i]);
		}
		return message;
	}
	
	private static void extract() {
		File langFolder;
		ArrayList<String> langFiles = new ArrayList<String>();
		langFiles.add("hu.txt");
		langFiles.add("en.txt");
		
		langFolder = new File(plugin.getDataFolder() + "/Language");
		if (!langFolder.exists()) {
			langFolder.mkdirs();
		}
		
		for (int i = 0; i < langFiles.size(); i++) {
			try {
				String name = langFiles.get(i);
		        File target = new File(langFolder + "/" + name);
		        if (target.exists())
		            return;
		        FileOutputStream out = new FileOutputStream(target);
		        ClassLoader cl = ElectricFloor.class.getClassLoader();
		        InputStream in = cl.getResourceAsStream(name);

		        byte[] buf = new byte[8*1024];
		        int len;
		        while((len = in.read(buf)) != -1)
		        {
		        	out.write(buf,0,len);
		        }
		        out.close();
		        in.close();
		    
			} catch (Exception e) {
				eLog.logException(e);
			}
		}
	}
	
	private static void loadCurrentAndDefault() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(plugin.getDataFolder() + "/Language/" + lang + ".txt"));
		    String line = null;

		    int errCount = 0;
		    
		    while ((line = br.readLine()) != null) {
		    	if (!line.startsWith("#")) {
		    		String[] content = line.split(":", 2);
			        
		    		if (content.length == 2) {
		    			messages.put(content[0], content[1]);
		    		} else {
		    			errCount++;
		    		}
		    	}  
		    }
		    
		    if (errCount != 0) {
		    	eLog.log(LogLevel.WARNING, "There are " + errCount + " wrong formatted lines in " + lang + ".txt file.");
		    }
		    
		    br.close();
		    
		}catch (Exception e) {
			eLog.logException(e);
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(plugin.getDataFolder() + "/Language/en.txt"));
			String line = null;

		    int errCount = 0;
		    
		    while ((line = br.readLine()) != null) {
		    	if (!line.startsWith("#")) {
		    		String[] content = line.split(":", 2);
			        
		    		if (content.length == 2) {
		    			messages.put(content[0], content[1]);
		    		} else {
		    			errCount++;
		    		}
		    	}  
		    }
		    
		    if (errCount != 0) {
		    	eLog.log(LogLevel.WARNING, "There are " + errCount + " wrong formatted lines in en.txt file.");
		    }
		    
		    br.close();
		    
		}catch (Exception e) {
			eLog.logException(e);
		}
	}

}
