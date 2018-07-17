package me.electricfloor.main;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.electricfloor.Language.Language;
import me.electricfloor.NPCmanagement.Manage;
import me.electricfloor.event.Event;
import me.electricfloor.event.EventGroup;
import me.electricfloor.file.logging.LogLevel;
import me.electricfloor.file.logging.ELogger;

public class Listeners implements Listener {
	
	private ElectricFloor main = ElectricFloor.getInstance();
	private Plugin plugin = ElectricFloor.getPlugin();
	private ELogger eLogger = ElectricFloor.getELogger();
	//TODO: language in Listeners class
	@SuppressWarnings("unused")
	private Language l = ElectricFloor.getLanguage();
	
	//Event handlers
		@EventHandler
		public void onBlockBreak(BlockBreakEvent event) {
			Player player = event.getPlayer();
			if (!EventGroup.isInGroup(player, EventGroup.NOT_MARKED)) {
				event.setCancelled(true); 	
				player.sendMessage(ElectricFloor.warnPrefix + "§cYou can't break blocks in the Event!");
			}
		}
		
		@EventHandler
		public void onBlockPlace(BlockPlaceEvent event) {
			Player player = event.getPlayer();
			if (!EventGroup.isInGroup(player, EventGroup.NOT_MARKED)) {
				event.setCancelled(true);
				player.sendMessage(ElectricFloor.warnPrefix + "§cYou can't place blocks in the Event!");
			}
		}
		
		@EventHandler
		public void onPlayerMove(PlayerMoveEvent event) {
			Player player = event.getPlayer();
			Location loc = player.getLocation();
			loc.setY(loc.getY() - 1);
			Block block = loc.getBlock();
			if (block.getType() == Material.STAINED_GLASS) {
				Event.eventBlocks(player, ElectricFloor.getPlugin());
			}
		}
		
		@EventHandler
		public void pvp(EntityDamageByEntityEvent event) {
			Entity damager = event.getDamager();
			Entity target = event.getEntity();
			
			if ((damager instanceof Player) && (target instanceof Player)) {
				if (!EventGroup.isInGroup((Player) damager, EventGroup.NOT_MARKED)) {
					damager.sendMessage(ElectricFloor.warnPrefix + ChatColor.RED + "You can't PVP in the Event!");
					event.setCancelled(true);
				}
			}
		}
		
		@EventHandler
		public void onPlayerInteract(PlayerInteractEvent event) {
			if (!ElectricFloor.useWorldEdit) {
				Action a = event.getAction();
				Player player = event.getPlayer();
				ItemStack is = player.getItemInHand();
				
				if (!(is == null)) {
					if (a == Action.LEFT_CLICK_BLOCK && is.getType() == Material.WOOD_AXE) {
						Block block = event.getClickedBlock();
						ElectricFloor.sel1 = block.getLocation();
						event.setCancelled(true);
						player.sendMessage(main.chatPrefix + "Pos1 selected");
					}
					
					if (a == Action.RIGHT_CLICK_BLOCK && is.getType() == Material.WOOD_AXE) {
						Block block = event.getClickedBlock();
						ElectricFloor.sel2 = block.getLocation();
						player.sendMessage(main.chatPrefix + "Pos2 selected");
					}
				}
			}
		}
		
		@EventHandler
		public void onPlayerLogout(PlayerQuitEvent event) {
			Event.leaveEvent(event.getPlayer(), plugin, true);
			EventGroup.removePlayer(event.getPlayer());
		}
		
		@SuppressWarnings("deprecation")
		@EventHandler
		public void onPlayerJoin(PlayerJoinEvent event) {
			Player player = event.getPlayer();
			if (EventGroup.isAppearSomewhere(player)) {
				eLogger.log(LogLevel.CRITICAL, "Somehow " + player.getName() + " already registered at " + EventGroup.getPlayerGroup(player).toString());
				player.kickPlayer("You are kicked due security reasons");
				//remove
				return;
			}
			boolean motd = plugin.getConfig().getBoolean("motd");
			
			Calendar var1 = Calendar.getInstance();
			var1.setTime(new Date());
			
			if (var1.get(2) + 1 == 8 && var1.get(5) == 25) {
				motd = false;
				Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable () {
					public void run() {
						player.sendMessage("§4==§6==§e==§a==§b==§9==§1==§5==§d====§b♦§a⬤§b♦§d====§5==§1==§9==§b==§a==§e==§6==§4==");
						player.sendMessage(" ");
						player.sendMessage("§6Happy Birthday §a§lSunStorm§a!");
						player.sendMessage(" ");
						player.sendMessage("§8The develooper of §2ElectricFloor");
						player.sendMessage(" ");
						player.sendMessage("§4==§6==§e==§a==§b==§9==§1==§5==§d====§b♦§a⬤§b♦§d====§5==§1==§9==§b==§a==§e==§6==§4==");
					}
				}, 150L);
			}
			
			if (motd) {
				Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
					public void run() {
						player.sendMessage("§8==================================================");
						player.sendMessage("§bThis server running §aElectricFloor V." + ElectricFloor.pdfile.getVersion() + " §bmade by §e§lSunStorm");
						player.sendMessage("§8==================================================");
					}
				}, 150L);
			}
			
			Location location = player.getWorld().getSpawnLocation();
			Location spawn = Utils.teleportWithConfig(player, plugin, "spawn", true);
			if (spawn == null) {
				double x = location.getX();
				double y = location.getY();
				double z = location.getZ();
				float pitchF = location.getPitch();
				float yawF = location.getYaw();
				
				double yaw = (double) yawF;
				double pitch = (double) pitchF;
				
				plugin.getConfig().set("positions.spawn.X", x);
				plugin.getConfig().set("positions.spawn.Y", y);
				plugin.getConfig().set("positions.spawn.Z", z);
				plugin.getConfig().set("positions.spawn.pitch", pitch);
				plugin.getConfig().set("positions.spawn.yaw", yaw);
				plugin.getConfig().set("positions.spawn.world", player.getLocation().getWorld().getName());
				plugin.saveConfig();
				
				ElectricFloor.logger.info("[ElectricFloor] Alapértelmezett spawn point beállítva!");
			}
		}
		
		
		//not really work :"(
		@EventHandler
		public void onPlayerInteractNpc(PlayerInteractEntityEvent event) {
			Player player = event.getPlayer();
			Entity clicked = event.getRightClicked();
			player.sendMessage(clicked.getCustomName());
			if (clicked.getCustomName() == "ElectricFloor") {
				Manage.interactNPC(player);
			}
		}

}
