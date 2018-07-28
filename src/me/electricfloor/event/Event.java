package me.electricfloor.event;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.earth2me.essentials.api.Economy;

import me.electricfloor.NMSimplement.NMSimplement;
import me.electricfloor.NMSimplement.TitleColor;
import me.electricfloor.NMSimplement.TitleStyle;
import me.electricfloor.arena.ArenaManager;
import me.electricfloor.file.ELogger;
import me.electricfloor.file.LogLevel;
import me.electricfloor.main.ElectricFloor;
import me.electricfloor.main.Utils;

public class Event {
	public static boolean eventReadyTo = false;
	public static boolean eventBroadcasted = false;
	public static Location sel1 = null;
	public static Location sel2 = null;
	public static int eventPlayerCounter = 0;
	public static int teleportRadius = 5;
	
	private static NMSimplement implement = ElectricFloor.getImplementation();
	private static ELogger eLogger = ElectricFloor.getELogger();
	private static Logger logger = ElectricFloor.getNormalLogger();
	
	protected static HashMap<String, Player> nm = new HashMap<String, Player>();
	protected static HashMap<String, Player> lobby = new HashMap<String, Player>();
	protected static HashMap<String, Player> ingame = new HashMap<String, Player>();
	protected static HashMap<String, Player> win1 = new HashMap<String, Player>();
	protected static HashMap<String, Player> win2 = new HashMap<String, Player>();
	protected static HashMap<String, Player> win3 = new HashMap<String, Player>();
	protected static HashMap<String, Player> lost = new HashMap<String, Player>();
	protected static HashMap<String, Player> clearing = new HashMap<String, Player>();
	
	public static HashMap<String, StoredPlayer> storedPlayers = new HashMap<String, StoredPlayer>();
	
	private static BlockManager blockManager = new BlockManager();
	public static ArenaManager arenaManager = new ArenaManager();
	
	/**
	 * Called from PlayerMoveEvent
	 * 
	 * @param player
	 * @param plugin
	 */
	public static void eventBlocks(Player player, Plugin plugin) {
			Location loc = player.getLocation();
			loc.setY(loc.getY() - 1);
		
			Block block = loc.getBlock();
		
			if (EventGroup.isInGroup(player, EventGroup.INGAME)) {
				blockManager.updateBlockState(block);
				
				if (blockManager.isCharged(block)) {
					eventFallout(player, plugin);
					if (Event.eventPlayerCounter == 2) {
						Event.eventPlayerCounter = 1;
						for (Player all : EventGroup.INGAME.map.values()) {
							eventFallout(all, plugin);
						}
					}
				}
			}	
	}
	
	@SuppressWarnings("deprecation")
	public static void eventFallout(Player player, Plugin plugin) {//player kiesés
		ArrayList<Player> ingame = new ArrayList<Player>();
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (all.hasMetadata("ingame")) {
				ingame.add(all);
			}
		}
		
		Event.eventPlayerCounter = ingame.size();
		
		if (Event.eventPlayerCounter > 3) {
			player.sendMessage(ElectricFloor.chatPrefix + ChatColor.GOLD + "You died!");
			player.removeMetadata("ingame", plugin);
			player.setMetadata("loss", new FixedMetadataValue(plugin, "EF"));
			Utils.teleportWithConfig(player, plugin, "loss", false);
			
			for (int i = 0; i < Event.eventPlayerCounter; i++) {
				Player br = ingame.get(i);
				
				br.sendMessage(ElectricFloor.chatPrefix + player.getName() + " fell out! Players remaining: " + Event.eventPlayerCounter);
			}
			
			eLogger.log(LogLevel.INFO, player.getName() + " fell out, players remaining: " + Event.eventPlayerCounter);
		}
		
		if (Event.eventPlayerCounter == 3) {
			player.sendMessage(ElectricFloor.chatPrefix + ChatColor.GOLD + "You placed 3rd!");
			player.removeMetadata("ingame", plugin);
			player.setMetadata("win3", new FixedMetadataValue(plugin, "EF"));
			Utils.teleportWithConfig(player, plugin, "win3", false);
			implement.sendActionbar(player, "&b&lCongratulations, &6&l" + player.getName() + " &b&lyou placed 3rd!");
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable () {
				public void run() {
					implement.sendActionbar(player, "&b&lCongratulations, &6&l" + player.getName() + " &b&lyou placed 3rd!");
				}
			}, 40L);
			
			for (int i = 0; i < Event.eventPlayerCounter; i++) {
				Player br = ingame.get(i);
				
				br.sendMessage(ElectricFloor.chatPrefix + player.getName() + " fell out! Players remaining: " + Event.eventPlayerCounter);
				eLogger.log(LogLevel.INFO, player.getName() + " fell out, and placed 3rd");
			}
		}
		
		if (Event.eventPlayerCounter == 2) {
			player.sendMessage(ElectricFloor.chatPrefix + ChatColor.GOLD + "You placed 2nd!");
			player.removeMetadata("ingame", plugin);
			player.setMetadata("win2", new FixedMetadataValue(plugin, "EF"));
			Utils.teleportWithConfig(player, plugin, "win2", false);
			implement.sendActionbar(player, "&b&Congratulations, &6&l" + player.getName() + " &b&lyou placed 2nd!");
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable () {
				public void run() {
					implement.sendActionbar(player, "&b&Congratulations, &6&l" + player.getName() + " &b&llyou placed 2nd!");
				}
			}, 40L);
			
			for (int i = 0; i < Event.eventPlayerCounter; i++) {
				Player br = ingame.get(i);
				
				br.sendMessage(ElectricFloor.chatPrefix + player.getName() + " fell out! Players remaining: " + Event.eventPlayerCounter);
				eLogger.log(LogLevel.INFO, player.getName() + " fell out, and placed 2rd");
			}
			
		}
		
		if (Event.eventPlayerCounter == 1) {
			player.sendMessage(ElectricFloor.chatPrefix + ChatColor.GOLD + "You placed 1st!");
			player.removeMetadata("ingame", plugin);
			player.setMetadata("win1", new FixedMetadataValue(plugin, "EF"));
			Utils.teleportWithConfig(player, plugin, "win1", false);
			implement.sendActionbar(player, "&b&Congratulations, &6&l" + player.getName() + " &b&you placed 1st!");
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable () {
				public void run() {
					implement.sendActionbar(player, "&b&Congratulations, &6&l" + player.getName() + " &b&lyou placed 1st!");
					eLogger.log(LogLevel.INFO, "No one remaining, " + player.getName() + " is the winner");
				}
			}, 40L);
			
			eventEnding(player, plugin, ingame);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void eventEnding(Player player, Plugin plugin, ArrayList<Player> ingame) {
		BukkitScheduler scheudler = Bukkit.getServer().getScheduler();
		
		String p1 = null;
		String p2 = null;
		String p3 = null;
		
		for (int i = 0; i < ingame.size(); i++) {
			Player p = ingame.get(i);
			
			if (p.hasMetadata("win1")) {
				p1 = p.getName();
			}
			
			if (p.hasMetadata("win2")) {
				p2 = p.getName();
			}
			
			if (p.hasMetadata("win3")) {
				p3 = p.getName();
			}
		}
		
		final String win1 = p1;
		final String win2 = p2;
		final String win3 = p3;
		
		scheudler.scheduleSyncDelayedTask(plugin, new Runnable () {
			public void run() {
				Utils.arenaSet(null, null, null, true);
				String text = ElectricFloor.chatPrefix + "&cThe event has ended!";
				Bukkit.getServer().broadcastMessage(text.replace('&', '�'));
				
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (EventGroup.isInEvent(all)) {
						implement.sendTitle(all, "Event ended!", TitleColor.red, TitleStyle.bold, "Awarding", TitleColor.gold, TitleStyle.italic, 3);
					}
				}
				
				Location fwLoc = Utils.teleportWithConfig(player, plugin, "start", true);
				Utils.firework(fwLoc, player, true, true, Type.BALL_LARGE, Color.RED, Color.GREEN, Color.BLUE);
				
				scheudler.scheduleSyncDelayedTask(plugin, new Runnable () {
					public void run() {
						Bukkit.getServer().broadcastMessage("&b&ki&r &3&l3rd place: &r&6" + win3 + " &r&b&ki");
						Location loc = Utils.teleportWithConfig(player, plugin, "win3", true);
						Utils.firework(loc, player, true, true, Type.BALL, Color.PURPLE, Color.MAROON, Color.RED);
						//TODO: effects
						
						try {
							Player winner3 = Bukkit.getServer().getPlayer(win3);
							double reward = plugin.getConfig().getInt("rewards.win3");
							Economy.add(win3, BigDecimal.valueOf(reward));
							implement.sendActionbar(winner3, "&b&lYour award: &6" + reward);
						} catch (Exception e) {
							logger.warning("[ElectricFloor] There is no 3rd player");
							eLogger.log(LogLevel.WARNING, "Error in rewarding:");
							e.printStackTrace();
						}
						
					}
				}, 60L);
				
				scheudler.scheduleSyncDelayedTask(plugin, new Runnable () {
					public void run() {
						String text = "&b&ki&r &3&lA m�sodik helyezett: &r&6" + win2 + " &r&b&ki";
						Bukkit.getServer().broadcastMessage(text.replace('&', '�'));
						Location loc = Utils.teleportWithConfig(player, plugin, "win2", true);
						Utils.firework(loc, player, true, true, Type.BALL, Color.WHITE, Color.GRAY, Color.RED);
						//TODO: effects
						
						try {
							Player winner2 = Bukkit.getServer().getPlayer(win2);
							double reward = plugin.getConfig().getInt("rewards.win2");
							Economy.add(win3, BigDecimal.valueOf(reward));
							implement.sendActionbar(winner2, "&b&lJutalmad: &6" + reward);
						} catch (Exception e) {
							logger.warning("[ElectricFloor] Nincs 2. helyezett");
							eLogger.log(LogLevel.WARNING, "Hiba az eredm�nyhirdet�sben: \n" + "Hiba neve: " + e.getMessage() + "\n Hiba indoka: " + e.getCause().toString());
						}
						
					}
				}, 120L);
				
				scheudler.scheduleSyncDelayedTask(plugin, new Runnable () {
					public void run() {
						String text = "&b&ki&r &3&lAz els� helyezett: &r&6" + win1 + " &r&b&ki";
						Bukkit.getServer().broadcastMessage(text.replace('&', '�'));
						Location loc = Utils.teleportWithConfig(player, plugin, "win1", true);
						Utils.firework(loc, player, true, true, Type.BALL, Color.YELLOW, Color.ORANGE, Color.RED);
						//TODO: effects
						
						try {
							Player winner1 = Bukkit.getServer().getPlayer(win1);
							double reward = plugin.getConfig().getInt("rewards.win1");
							Economy.add(win3, BigDecimal.valueOf(reward));
							implement.sendActionbar(winner1, "&b&lJutalmad: &6" + reward);
						} catch (Exception e) {
							logger.warning("[ElectricFloor] Nincs 1. helyezett");
							eLogger.log(LogLevel.WARNING, "Hiba az eredm�nyhirdet�sben: \n" + "Hiba neve: " + e.getMessage() + "\n Hiba indoka: " + e.getCause().toString());
						}
					}
				}, 180L);
				
				scheudler.scheduleSyncDelayedTask(plugin, new Runnable () {
					public void run() {
						eLogger.log(LogLevel.INFO, "eventben r�szt vett j�t�kosok teleport�l�sa a spawnra");
						for (Player all : Bukkit.getOnlinePlayers()) {
							if (all.hasMetadata("WaitE") || all.hasMetadata("ingame") || all.hasMetadata("loss") || all.hasMetadata("win1") || all.hasMetadata("win2") || all.hasMetadata("win3")) {
								leaveEvent(all, plugin, true);
								all.sendMessage(ElectricFloor.chatPrefix + ChatColor.YELLOW + "Az event v�get �rt!");
							}
						}
						Event.eventBroadcasted = false;
						Location effectLoc = Utils.teleportWithConfig(player, plugin, "spawn", true);
					}
				}, 240L);
			}
		}, 200L);
	}
	
	/**
	 * 
	 * Selector true: server forces the player join, else: testing perm, and others
	 * 
	 * @param player
	 * @param plugin
	 * @param selector
	 */
	public static void joinEvent(Player player, Plugin plugin, boolean selector) {
		if (!EventGroup.isInGroup(player, EventGroup.NOT_MARKED)) {
			player.sendMessage(ElectricFloor.chatPrefix + "you already joined the event!");
		} else {
			if (Event.eventBroadcasted == true) {
				eLogger.log(LogLevel.INFO, player.getName() + " joined the event");
				EventGroup.movePlayerGroup(player, EventGroup.NOT_MARKED, EventGroup.LOBBY);
				
				StoredPlayer sp = new StoredPlayer(player);
				storedPlayers.put(player.getName(), sp);
				player.sendMessage(ElectricFloor.chatPrefix + ChatColor.AQUA + "You successfully joined the event!");
				player.sendMessage(ElectricFloor.chatPrefix + ChatColor.AQUA + "Leave event:" + ChatColor.GOLD + " /event leave");
				player.sendMessage(ElectricFloor.chatPrefix + ChatColor.AQUA + "You can only leave the event until it start");
				
				Utils.teleportWithConfig(player, plugin, "wait", false);
			} else {
				player.sendMessage(ElectricFloor.chatPrefix + "There is no announced event, you can't join!");
			}
		}
	}
	
	/**
	 *  Leave the event for a specified player.<br>
	 *  <br>
	 *  Selector: usually server side, when not a player invoke this void.
	 *  Not ask for permission, etc.
	 */
	public static void leaveEvent(Player player, Plugin plugin, boolean selector) {
		if (selector == true) {
			EventGroup.movePlayerGroup(player, EventGroup.getPlayerGroup(player), EventGroup.NOT_MARKED);
			
			//TODO: config key for teleport option
			//if true tp back to origin loc, false: to spawn
			boolean tpOption = false;
			storedPlayers.get(player.getName()).setPlayerSettings(player, tpOption);
			storedPlayers.remove(player.getName());
			Utils.teleportWithConfig(player, plugin, "spawn", false);
		} else {
			if (!EventGroup.isInGroup(player, EventGroup.INGAME)) {
				if (EventGroup.isInGroup(player, EventGroup.LOBBY) || EventGroup.isInGroup(player, EventGroup.LOST)) {
					EventGroup.movePlayerGroup(player, EventGroup.getPlayerGroup(player), EventGroup.NOT_MARKED);
					player.sendMessage(ElectricFloor.chatPrefix + "You left the event!");
					
					Utils.teleportWithConfig(player, plugin, "spawn", false);
					
				} else {
					if (EventGroup.isInGroup(player, EventGroup.WIN1) || EventGroup.isInGroup(player, EventGroup.WIN2) || EventGroup.isInGroup(player, EventGroup.WIN3)) {
						player.sendMessage(ElectricFloor.warnPrefix + "§aWinners can't leave!");
					} else {
						player.sendMessage(ElectricFloor.warnPrefix + "�6You not in event!");
					}
				}
			} else {
				player.sendMessage(ElectricFloor.warnPrefix + "§6Ingame can't exit! If you really want to go, then step on a red glass..");
			}
			
		}
	}

}
