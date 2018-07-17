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
import me.electricfloor.file.logging.LogLevel;
import me.electricfloor.file.logging.ELogger;
import me.electricfloor.main.ElectricFloor;

@Beta
public class Language {
	
	private HashMap<String, String> messages = new HashMap<String, String>();
	private HashMap<String, String> defaultM = new HashMap<String, String>();
	
	private Plugin plugin = ElectricFloor.getPlugin();
	
	private String lang = plugin.getConfig().getString("language");
	
	public ELogger eLog = ElectricFloor.getELogger();
	
	private Logger logger = Logger.getLogger("Minecraft");

	public void setupLanguage() {
		extract();
		loadCurrentAndDefault();
	}
	
	@SuppressWarnings("unused")
	public void get(String message) {
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
	}
	
	public void get(String message, Player name1) {
		String msg = null;
		if (messages.containsKey(message)) {
			msg = messages.get(message);
			if (msg.contains("{PLAYER}")) {
				msg.replace("{PLAYER}", name1.getName());
			} else {
				//TODO: logging
				logger.warning("err in get - replace");
				eLog.log(LogLevel.CRITICAL, "err in get - replace");
			}
		} else {
			if (defaultM.containsKey(message)) {
				msg = defaultM.get(message);
				if (msg.contains("{PLAYER}")) {
					msg.replace("{PLAYER}", name1.getName());
				} else {
					//TODO: logging
					logger.warning("err in get - replace");
					eLog.log(LogLevel.CRITICAL, "err in get - replace");
				}
				//TODO: logging
				eLog.log(LogLevel.CRITICAL, "err");
				logger.severe("err");
			} else {
				throw new ElectricError("Fuckin' error in ElectricFloor language system", new NullPointerException("No such message like this in default messages"));
			}
		}
	}
	
	public void get(String message, Player name1, Player name2) {
		String msg = null;
		if (messages.containsKey(message)) {
			msg = messages.get(message);
			if (msg.contains("{PLAYER}")) {
				msg.replace("{PLAYER}", name1.getName());
			} else {
				//TODO: logging
				logger.warning("err in get - replace");
				eLog.log(LogLevel.CRITICAL, "err in get - replace");
			}
			
			if (msg.contains("{PLAYER2}")) {
				msg.replace("{PLAYER2}", name2.getName());
			} else {
				//TODO: logging
				logger.warning("err in get - replace");
				eLog.log(LogLevel.CRITICAL, "err in get - replace");
			}
		} else {
			if (defaultM.containsKey(message)) {
				msg = defaultM.get(message);
				if (msg.contains("{PLAYER}")) {
					msg.replace("{PLAYER", name1.getName());
				} else {
					//TODO: logging
					logger.warning("err in get - replace");
					eLog.log(LogLevel.CRITICAL, "err in get - replace");
				}
				//TODO: logging
				eLog.log(LogLevel.CRITICAL, "err");
				logger.severe("err");
			} else {
				throw new ElectricError("Fuckin' error in ElectricFloor language system", new NullPointerException("No such message like this in default messages"));
			}
		}
	}
	
	private void extract() {
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
	
	private void loadCurrentAndDefault() {
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
