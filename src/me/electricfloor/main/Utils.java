package me.electricfloor.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

import me.electricfloor.file.logging.LogLevel;

public class Utils {

	public static void arenaSet(Location loc, Location loc2, Player player, boolean callback) {
		int x1 = 0;
		int y1 = 0;
		int z1 = 0;
		int x2 = 0;
		int y2 = 0;
		int z2 = 0;
		World world = null;
	    Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("ElectricFloor");
	    
	    FileConfiguration config = pl.getConfig();
		
		if (!callback) { //player
			if (!ElectricFloor.useWorldEdit) {//no worldedit
				if (!(loc == null || loc2 == null)) {
					x1 = loc.getBlockX();
				    y1 = loc.getBlockY();
				    z1 = loc.getBlockZ();
				    
				    x2 = loc2.getBlockX(); 
				    y2 = loc2.getBlockY();
				    z2 = loc2.getBlockZ();
				    
				    if (loc.getWorld().getName() == loc2.getWorld().getName()) {
						world = loc.getWorld();
					} else {
						player.sendMessage(ElectricFloor.warnPrefix + "�cAz ar�na mindk�t pontj�nak ugyanazon vil�gban kell lennie");
						ElectricFloor.eLogger.log(LogLevel.WARNING, "ArenaSet: " + player.getName() + " k�l�nb�z� vil�gokba rakta az ar�na k�t pontj�t");
						return;
					}
				} else {
					player.sendMessage(ElectricFloor.warnPrefix + ChatColor.RED + "A kijel�l�s nem lehet null!");
					ElectricFloor.eLogger.log(LogLevel.WARNING, "ArenaSet: " + player.getName() + " kijel�l�s n�lk�l akart ar�n�t csin�lni");
					return;
				}
			} else {//with worldedit
				WorldEditPlugin we = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
				Selection selection = we.getSelection(player);
				if (selection == null) {
					player.sendMessage(ElectricFloor.warnPrefix + ChatColor.RED + "A kijel�l�s nem lehet null!");
					ElectricFloor.eLogger.log(LogLevel.WARNING, "ArenaSet: " + player.getName() + " kijel�l�s n�lk�l akart ar�n�t csin�lni");
					return;
				} else {
					ElectricFloor.sel1 = selection.getMinimumPoint();
					ElectricFloor.sel2 = selection.getMaximumPoint();
					
					int minX = ElectricFloor.sel1.getBlockX();
					int minY = ElectricFloor.sel1.getBlockY();
					int minZ = ElectricFloor.sel1.getBlockZ();
					
					int maxX = ElectricFloor.sel2.getBlockX();
					int maxY = ElectricFloor.sel2.getBlockY();
					int maxZ = ElectricFloor.sel2.getBlockZ();
					
					if (ElectricFloor.sel1.getWorld().getName() == ElectricFloor.sel2.getWorld().getName()) {
						world = ElectricFloor.sel1.getWorld();
					} else {
						ElectricFloor.logger.warning("[ElectricFloor] Az ar�na mindk�t pontj�nak ugyanazon vil�gban kell lennie");
						ElectricFloor.eLogger.log(LogLevel.WARNING, "ArenaSet: " + player.getName() + " k�l�nb�z� vil�gokba rakta az ar�na k�t pontj�t");
						return;
					}
					
					if (minY == maxY) {
						x1 = minX;
						y1 = minY;
						z1 = minZ;
						x2 = maxX;
						y2 = maxY;
						z2 = maxZ;
					} else {
						player.sendMessage(ElectricFloor.warnPrefix + ChatColor.RED + "A kijel�l�s csak egy szintes lehet!");
						ElectricFloor.eLogger.log(LogLevel.WARNING, "ArenaSet: " + player.getName() + " nem egyszint� kijel�l�ssel akart ar�n�t k�sz�teni");
						return;
					}
				}
			}
			
			config.set("positions.arena.min.X", x1);
			config.set("positions.arena.min.Y", y1);
			config.set("positions.arena.min.Z", z1);
			config.set("positions.arena.min.world", world.getName());
			Bukkit.getServer().getPluginManager().getPlugin("ElectricFloor").saveConfig();
			
			config.set("positions.arena.max.X", x2);
			config.set("positions.arena.max.Y", y2);
			config.set("positions.arena.max.Z", z2);
			config.set("positions.arena.max.world", world.getName());
			Bukkit.getServer().getPluginManager().getPlugin("ElectricFloor").saveConfig();
			
		} else { //server
			Location loc0 = teleportWithConfig(null, pl, "arena.min", true);
			Location loc1 = teleportWithConfig(null, pl, "arena.max", true);
			
			if (loc0 == null || loc1 == null) {
				ElectricFloor.logger.warning("[ElectricFloor] Nem l�tezik a configban megadott poz�ci� az ar�n�hoz!");
				ElectricFloor.eLogger.log(LogLevel.CRITICAL, "ArenaSet: a kor�bban t�rolt poz�ci� nem l�tezik a configban!");
				return;
			}
			
			x1 = loc0.getBlockX();
			y1 = loc0.getBlockY();
			z1 = loc0.getBlockZ();
			x2 = loc1.getBlockX();
			y2 = loc1.getBlockY();
			z2 = loc1.getBlockZ();
			if (loc0.getWorld().getName() == loc1.getWorld().getName()) {
				world = loc0.getWorld();
			} else {
				ElectricFloor.logger.warning("[ElectricFloor] Az ar�na mindk�t pontj�nak ugyanazon vil�gban kell lennie");
				return;
			}
		}
		
	   
	    if (x1 < x2 && z1 < z2) {
	    	for (int xPoint = x1; xPoint <= x2; xPoint++) { 
		        // Loop over the cube in the y dimension.
		        for (int yPoint = y1; yPoint <= y2; yPoint++) {
		        	Block currentBlock = world.getBlockAt(xPoint, yPoint, z1);
	                currentBlock.setType(Material.STAINED_GLASS);
		        }
		    }
	    } else if (x1 < x2 && z1 > z2) {
	    	for (int xPoint = x1; xPoint <= x2; xPoint++) { 
		        // Loop over the cube in the y dimension.
		        for (int yPoint = y1; yPoint <= y2; yPoint++) {
		        	Block currentBlock = world.getBlockAt(xPoint, yPoint, z1);
	                currentBlock.setType(Material.STAINED_GLASS);
		        }
		    }
	    	
	    } else if (x1 > x2 && z1 > z2) {
	    	for (int xPoint = x2; xPoint <= x1; xPoint++) { 
		        // Loop over the cube in the y dimension.
		        for (int yPoint = y1; yPoint <= y2; yPoint++) {
		        	Block currentBlock = world.getBlockAt(xPoint, yPoint, z1);
	                currentBlock.setType(Material.STAINED_GLASS);
		        }
		    }
	    	
	    } else if (x1 > x2 && z1 < z2) {
	    	for (int xPoint = x2; xPoint <= x1; xPoint++) { 
		        // Loop over the cube in the y dimension.
		        for (int yPoint = y1; yPoint <= y2; yPoint++) {
		        	Block currentBlock = world.getBlockAt(xPoint, yPoint, z1);
	                currentBlock.setType(Material.STAINED_GLASS);
		        }
		    }
	    	
	    }
	}

	public static Location teleportWithConfig(Player player, Plugin plugin, String name, boolean callback) {
		String worldS = (String) plugin.getConfig().get("positions." + name + ".world");
		World world = Bukkit.getServer().getWorld(worldS);
		
		if (world != null) {
			double X = plugin.getConfig().getDouble("positions." + name + ".X");
			double Y = plugin.getConfig().getDouble("positions." + name + ".Y") + 0.05;
			double Z = plugin.getConfig().getDouble("positions." + name + ".Z");
			
			float pitch = (float) plugin.getConfig().getDouble("positions." + name + ".pitch");
			float yaw = (float) plugin.getConfig().getDouble("positions." + name + ".yaw");
			
			Location loc = new Location(world, X, Y, Z);
			loc.setPitch(pitch);
			loc.setYaw(yaw);
			
			if (callback == false) {
				player.teleport(loc);
			} else {
				return loc;
			}
	
		}
		
		return null;
	}

	/**
	 * Play the specified firework animation for {@code player}<br>
	 * 
	 * @param loc - The firework will spawn here
	 * @param player - This player can see the firework
	 * @param flicker - Boolean
	 * @param trail - Boolean
	 * @param type - The firework's type, Enum  -  org.bukkit.FireworkEffect.Type
	 * @param color - Color - Main color
	 * @param color2 - Color -  Fade color
	 * @param fade - Color
	 */
	public static void firework(Location loc, Player player, boolean flicker, boolean trail, org.bukkit.FireworkEffect.Type type, Color color, Color color2, Color fade) {
		Firework f = (Firework) player.getWorld().spawn(loc, Firework.class);
		FireworkMeta fm = f.getFireworkMeta();
		
		fm.addEffect(FireworkEffect.builder().flicker(flicker).trail(trail).with(type).withColor(color).withColor(color).withFade(fade).build());
		
		fm.setPower(0);
		f.setFireworkMeta(fm);
	    
	}

	public static boolean eventReady(Plugin plugin) {
		ElectricFloor.eLogger.log(LogLevel.INFO, "Event ellen�rz�se kezd�dik");
		int checker = 0;
		ArrayList<String> names = new ArrayList<String>();
		names.add("wait");
		names.add("loss");
		names.add("spawn");
		names.add("win1");
		names.add("win2");
		names.add("win3");
		names.add("start");
		names.add("arena.min");
		names.add("arena.max");
		
		for (int i = 0; i < names.size(); i++) {
			String current = names.get(i);
			
			double X = plugin.getConfig().getDouble("positions." + current + ".X");
			double Y = plugin.getConfig().getDouble("positions." + current + ".Y");
			double Z = plugin.getConfig().getDouble("positions." + current + ".Z");
			
			float pitch = (float) plugin.getConfig().getDouble("positions." + current + ".pitch");
			float yaw = (float) plugin.getConfig().getDouble("positions." + current + ".yaw");
			String world = plugin.getConfig().getString("positions." + current + ".world");
			
			if (X != 0.0d) {
				checker++;
			}
			
			if (Y != 0.0d) {
				checker++;
			}
			
			if (Z != 0.0d) {
				checker++;
			}
			
			if (pitch != 0.0d) {
				checker++;
			}
			
			if (yaw != 0.0d) {
				checker++;
			}
			
			if (!(world == null)) {
				checker++;
			}
			
		}
		
		ElectricFloor.d.debug("checker:" + checker);
		
		if (checker == 50) {
			ElectricFloor.eLogger.log(LogLevel.INFO, "Az event haszn�latra k�sz!");
			return true;
		} else {
			ElectricFloor.eLogger.log(LogLevel.WARNING, "Az event nics be�ll�tva!");
			ElectricFloor.logger.warning("[ElectricFloor] Hiba van a configban, az eventet nem lehet elind�tani!");
			return false;
		}
	}

}
