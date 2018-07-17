package me.electricfloor.NPCmanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.bukkit.plugin.Plugin;

import me.electricfloor.main.ElectricFloor;

public class ConfigReader {

	private Plugin plugin = ElectricFloor.getPlugin();
	
	/**
	 * 
	 * @param String {@code name} name of config
	 * @param String {@code value} returns this value from config
	 * @return Object
	 */
	@SuppressWarnings("unused")
	public Object get(String name, String value) {
		ArrayList<String> temp = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(plugin.getDataFolder() + "/NPC/" + name + ".yml"));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        temp.add(line);
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		    
		    int cSize = temp.size();
		    
		    br.close();
		    
		    for (int i = 0; i < cSize; i++) {
		    	String temp2 = temp.get(i);
		    	if (temp2.startsWith(value + ": ")) {
		    		String[] asd = temp2.split(" ", 2);
		    		if (asd.length == 3) {
		    			String spaces = asd[1] + " " + asd[2];
		    			return spaces;
		    		} else {
		    			return asd[1];
		    		}
		    	}
		    }
		    
		    
		} catch (Exception e) {
			System.out.println("[ElectricFloor] Some error :(");
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * All configuration
	 * Return values: [0] name, [1] world, [2] x, [3] y, [4] z, [5] pitch, [6] yaw.
	 * @param String {@code name} <- the name of the configuration
	 */
	public void get(String name) {
		
	}
	
}
