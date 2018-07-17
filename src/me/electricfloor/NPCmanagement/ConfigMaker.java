package me.electricfloor.NPCmanagement;

import java.io.File;
import java.io.PrintWriter;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigMaker extends YamlConfiguration {
    private JavaPlugin plugin;
    private String fileName;
    private String dir;
    private NpcConfig config;
    
    public ConfigMaker(JavaPlugin plugin, NpcConfig config, String dir){
        this.plugin = plugin;
        this.config = config;
        this.fileName = config.getName() + ".yml";
        this.dir = dir;
        createFile();
    }
    
    private void createFile() {
        try {
            File file = new File(plugin.getDataFolder().getAbsolutePath() + "/"  + dir, fileName);
            if (!file.exists()){
                if (plugin.getResource(fileName) != null){
                    plugin.saveResource(fileName, false);
                }else{
                    save(file);
                }
            }else{
                load(file);
                save(file);
            }
            writeFile(this.config, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void save(){
        try {
            save(new File(plugin.getDataFolder() + dir, fileName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void writeFile(NpcConfig config, File file) {
    	try {
    		PrintWriter writer = new PrintWriter(file, "UTF-8");
    		writer.println("NPC settings for ElectricFloor plugin");
    		writer.println("specially for the NPC named " + config.getName());
    		writer.println("DO NOT MODIFY!!!");
    		writer.println("If you want to move your npc, use /eventnpc teleport " + config.getName());
    		writer.println("This will teleport the npc to your location");
    		writer.println();
    		writer.println("name: " + config.getName());
    		writer.println("world: " + config.getWorld());
    		writer.println("x: " + config.getX());
    		writer.println("y: " + config.getY());
    		writer.println("z: " + config.getZ());
    		writer.println("pitch: " + config.getPitch());
    		writer.println("yaw: " + config.getYaw());
    		writer.close();
    	} catch(Exception e) {
    		System.out.println("Some error ¯\\_(ツ)_/¯");
    		e.printStackTrace();
    	}
    }
}