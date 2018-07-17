package me.electricfloor.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.electricfloor.Language.Language;
import me.electricfloor.debug.Debug;
import me.electricfloor.file.logging.LogLevel;
import me.electricfloor.file.logging.ELogger;

public class EfCommand implements CommandExecutor {
	
	private Plugin plugin;
	
	private ELogger eLog = ElectricFloor.getELogger();
	//TODO: language in efCommand class
	@SuppressWarnings("unused")
	private Language l = ElectricFloor.getLanguage();
	private ElectricFloor main = ElectricFloor.getInstance();
	private Debug debug = ElectricFloor.getDebug();
	
	public EfCommand(Plugin plugin) {
		
		this.plugin = plugin;
		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			//szerver
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GREEN + "==========[ Electric Floor ]==========");
				sender.sendMessage(ChatColor.AQUA + "/ef        - Ez az oldal");
				sender.sendMessage(ChatColor.AQUA + "/ef reload - Konfig �jrat�lt�se");
				sender.sendMessage(ChatColor.AQUA + "/ef debug  - Debug m�d");
				sender.sendMessage(ChatColor.AQUA + "/event     - Event ir�ny�t�sa");
				sender.sendMessage(ChatColor.GREEN + "======================================");
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					sender.sendMessage("[ElectricFloor] Reload...");
					plugin.saveConfig();
					plugin.reloadConfig();
					Bukkit.getServer().getPluginManager().disablePlugin(plugin);
					Bukkit.getServer().getPluginManager().enablePlugin(plugin);
					sender.sendMessage("[ElectricFloor] Sikeres reload!");
				} else if (args[0].equalsIgnoreCase("debug")) {
					debugCmd(sender, cmd, commandLabel, args);
				} else {
					sender.sendMessage(ChatColor.GREEN + "==========[ Electric Floor ]==========");
					sender.sendMessage(ChatColor.AQUA + "/ef        - Ez az oldal");
					sender.sendMessage(ChatColor.AQUA + "/ef reload - Konfig �jrat�lt�se");
					sender.sendMessage(ChatColor.AQUA + "/ef debug  - Debug m�d");
					sender.sendMessage(ChatColor.AQUA + "/event     - Event ir�ny�t�sa");
					sender.sendMessage(ChatColor.GREEN + "======================================");
				}
			} else {
				sender.sendMessage(ChatColor.GREEN + "==========[ Electric Floor ]==========");
				sender.sendMessage(ChatColor.AQUA + "/ef        - Ez az oldal");
				sender.sendMessage(ChatColor.AQUA + "/ef reload - Konfig �jrat�lt�se");
				sender.sendMessage(ChatColor.AQUA + "/ef debug  - Debug m�d");
				sender.sendMessage(ChatColor.AQUA + "/event     - Event ir�ny�t�sa");
				sender.sendMessage(ChatColor.GREEN + "======================================");
			}
		} else {
			//kliens
			Player player = (Player) sender;
			
			Logger logger = Logger.getLogger("Minecraft");
			
			if (args.length == 0) {
				//help page
				if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.settings") || player.hasPermission("electricfloor.event")) {
					player.sendMessage(" ");
					player.sendMessage(" ");
					player.sendMessage(" ");
					player.sendMessage(ChatColor.GREEN + "============" + ChatColor.AQUA +"[ Electric Floor ]" + ChatColor.GREEN + "============");
					player.sendMessage(ChatColor.AQUA + "/ef        - Ez az oldal");
					player.sendMessage(ChatColor.AQUA + "/ef reload - Konfig �jrat�lt�se");
					sender.sendMessage(ChatColor.AQUA + "/ef debug  - Debug m�d");
					player.sendMessage(ChatColor.AQUA + "/ef set    - Kofigur�l�si f�oldal");
					player.sendMessage(ChatColor.AQUA + "/event     - Event ir�ny�t�sa");
					player.sendMessage(ChatColor.GREEN + "======================================");
				} else {//player perm
					player.sendMessage(" ");
					player.sendMessage(" ");
					player.sendMessage(" ");
					player.sendMessage(ChatColor.GREEN + "============" + ChatColor.AQUA +"[ Electric Floor ]" + ChatColor.GREEN + "============");
					player.sendMessage(ChatColor.AQUA + "/ef        - Ez az oldal");
					player.sendMessage(ChatColor.AQUA + "/event     - Event lehet�s�gek");
					player.sendMessage(ChatColor.GREEN + "======================================");
				}
			} else {
				if (args.length == 1) { //alap szelektorok
					if (player.hasPermission("electricfloor.settings") || player.hasPermission("electricfloor.admin")) {
						if (args[0].equalsIgnoreCase("set")) {
							//set help
							player.sendMessage(" ");
							player.sendMessage(" ");
							player.sendMessage(" ");
							player.sendMessage(ChatColor.GREEN + "=============�b[ Electric Floor ]�a=============");
							player.sendMessage(ChatColor.AQUA + "/ef set spawn - Spawn be�ll�t�sa �cNem k�telez�!");
							player.sendMessage(ChatColor.AQUA + "/ef set start - Start hely�nek be�ll�t�sa");
							player.sendMessage(ChatColor.AQUA + "/ef set wait  - V�r� be�ll�t�sa");
							player.sendMessage(ChatColor.AQUA + "/ef set arena - Ar�na be�ll�t�sa");
							player.sendMessage(ChatColor.AQUA + "/ef set loss  - Kiesettek hely�nek be�ll�t�sa");
							player.sendMessage(ChatColor.AQUA + "/ef set win1  - Els� hely be�ll�t�sa");
							player.sendMessage(ChatColor.AQUA + "/ef set win2  - M�sodik hely be�ll�t�sa");
							player.sendMessage(ChatColor.AQUA + "/ef set win3  - Harmadik hely be�ll�t�sa");
							player.sendMessage(ChatColor.GREEN + "=======================================");
						}
						
						if (args[0].equalsIgnoreCase("reload")) {
							if (player.hasPermission("electricfloor.reload")) {
								eLog.log(LogLevel.INFO, player.getName() + " �jraind�tja a plugint!");
								eLog.log(LogLevel.INFO, "�jraind�t�s...");
								ElectricFloor.isRestart = true;
								player.sendMessage(main.chatPrefix + "Reload...");
								plugin.saveConfig();
								plugin.reloadConfig();
								Bukkit.getServer().getPluginManager().disablePlugin(plugin);
								Bukkit.getServer().getPluginManager().enablePlugin(plugin);
								player.sendMessage(main.chatPrefix + "Sikeres reload!");
								eLog.log(LogLevel.INFO, "Sikeres �jraind�t�s!");
								ElectricFloor.isRestart = false;
							}
						}
						
						if (args[0].equalsIgnoreCase("debug")) {
							debugCmd(sender, cmd, commandLabel, args);
						}
					} else {//player perm
						player.sendMessage(" ");
						player.sendMessage(" ");
						player.sendMessage(" ");
						player.sendMessage(ChatColor.GREEN + "============" + ChatColor.AQUA +"[ Electric Floor ]" + ChatColor.GREEN + "============");
						player.sendMessage(ChatColor.AQUA + "/ef        - Ez az oldal");
						player.sendMessage(ChatColor.AQUA + "/event     - Event lehet�s�gek");
						player.sendMessage(ChatColor.GREEN + "======================================");
					}
				}
				
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("set")) {
						if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.settings") || player.hasPermission("electricfloor.set.wait") || player.hasPermission("electricfloor.set.loss") || player.hasPermission("electricfloor.set.start") || player.hasPermission("electricfloor.set.spawn") || player.hasPermission("electricfloor.set.win1") || player.hasPermission("electricfloor.set.win2") || player.hasPermission("electricfloor.set.win3")) {
							if (args[1].equalsIgnoreCase("wait")) {
								if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.settings") || player.hasPermission("electricfloor.set.wait")) {
									Location loc = player.getLocation();
									
									double x = loc.getX();
									double y = loc.getY();
									double z = loc.getZ();
									float pitchF = loc.getPitch();
									float yawF = loc.getYaw();
									
									double yaw = (double) yawF;
									double pitch = (double) pitchF;
									
									plugin.getConfig().set("positions.wait.X", x);
									plugin.getConfig().set("positions.wait.Y", y);
									plugin.getConfig().set("positions.wait.Z", z);
									plugin.getConfig().set("positions.wait.pitch", pitch);
									plugin.getConfig().set("positions.wait.yaw", yaw);
									plugin.getConfig().set("positions.wait.world", player.getLocation().getWorld().getName());
									plugin.saveConfig();
									
									player.sendMessage(main.chatPrefix + "�6Sikeresen be�ll�tottad ezt a helyet: " + args[1]);
									
									logger.info("[ElectricFloor]" + player.getName() + " Sikeresen v�grehajtotta ezt: " + commandLabel + " " + args[0] + " " + args[1]);
								} else {
									player.sendMessage(ElectricFloor.warnPrefix + "�cNincs jogod a parancs haszn�lat�ra!");
								}
							} else if (args[1].equalsIgnoreCase("spawn")) {
								if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.settings") || player.hasPermission("electricfloor.set.spawn")) {
									Location loc = player.getLocation();
									
									double x = loc.getX();
									double y = loc.getY();
									double z = loc.getZ();
									float pitchF = loc.getPitch();
									float yawF = loc.getYaw();
									
									double yaw = (double) yawF;
									double pitch = (double) pitchF;
									
									plugin.getConfig().set("positions.spawn.X", x);
									plugin.getConfig().set("positions.spawn.Y", y);
									plugin.getConfig().set("positions.spawn.Z", z);
									plugin.getConfig().set("positions.spawn.pitch", pitch);
									plugin.getConfig().set("positions.spawn.yaw", yaw);
									plugin.getConfig().set("positions.spawn.world", player.getLocation().getWorld().getName());
									plugin.saveConfig();
									
									player.sendMessage(main.chatPrefix + "�6Sikeresen be�ll�tottad ezt a helyet: " + args[1]);
									
									logger.info("[ElectricFloor]" + player.getName() + " Sikeresen v�grehajtotta ezt: " + commandLabel + " " + args[0] + " " + args[1]);
								} else {
									player.sendMessage(ElectricFloor.warnPrefix + "�cNincs jogod a parancs haszn�lat�ra!");
								}
								
							} else if (args[1].equalsIgnoreCase("arena")) {
								if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.settings") || player.hasPermission("electricfloor.set.arena")) {
									Utils.arenaSet(ElectricFloor.sel1, ElectricFloor.sel2, player, false);									
									
									player.sendMessage(main.chatPrefix + "�6Sikeresen be�ll�tottad ezt a helyet: " + args[1]);
									
									logger.info("[ElectricFloor]" + player.getName() + " Sikeresen v�grehajtotta ezt: " + commandLabel + " " + args[0] + " " + args[1]);
								} else {
									player.sendMessage(ElectricFloor.warnPrefix + "�cNincs jogod a parancs haszn�lat�ra!");
								}
								
							} else if (args[1].equalsIgnoreCase("loss")) {
								if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.settings") || player.hasPermission("electricfloor.set.loss")) {
									Location loc = player.getLocation();
									
									double x = loc.getX();
									double y = loc.getY();
									double z = loc.getZ();
									float pitchF = loc.getPitch();
									float yawF = loc.getYaw();
									
									double yaw = (double) yawF;
									double pitch = (double) pitchF;
									
									plugin.getConfig().set("positions.loss.X", x);
									plugin.getConfig().set("positions.loss.Y", y);
									plugin.getConfig().set("positions.loss.Z", z);
									plugin.getConfig().set("positions.loss.pitch", pitch);
									plugin.getConfig().set("positions.loss.yaw", yaw);
									plugin.getConfig().set("positions.loss.world", player.getLocation().getWorld().getName());
									plugin.saveConfig();
									
									player.sendMessage(main.chatPrefix + "�6Sikeresen be�ll�tottad ezt a helyet: " + args[1]);
									
									logger.info("[ElectricFloor]" + player.getName() + " Sikeresen v�grehajtotta ezt: " + commandLabel + " " + args[0] + " " + args[1]);
								} else {
									player.sendMessage(ElectricFloor.warnPrefix + "�cNincs jogod a parancs haszn�lat�ra!");
								}
								
							} else if (args[1].equalsIgnoreCase("win1")) {
								if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.settings") || player.hasPermission("electricfloor.set.win1")) {
									Location loc = player.getLocation();
									
									double x = loc.getX();
									double y = loc.getY();
									double z = loc.getZ();
									float pitchF = loc.getPitch();
									float yawF = loc.getYaw();
									
									double yaw = (double) yawF;
									double pitch = (double) pitchF;
									
									plugin.getConfig().set("positions.win1.X", x);
									plugin.getConfig().set("positions.win1.Y", y);
									plugin.getConfig().set("positions.win1.Z", z);
									plugin.getConfig().set("positions.win1.pitch", pitch);
									plugin.getConfig().set("positions.win1.yaw", yaw);
									plugin.getConfig().set("positions.win1.world", player.getLocation().getWorld().getName());
									plugin.saveConfig();
									
									player.sendMessage(main.chatPrefix + "�6Sikeresen be�ll�tottad ezt a helyet: " + args[1]);
									
									logger.info("[ElectricFloor]" + player.getName() + " Sikeresen v�grehajtotta ezt: " + commandLabel + " " + args[0] + " " + args[1]);
								} else {
									player.sendMessage(ElectricFloor.warnPrefix + "�cNincs jogod a parancs haszn�lat�ra!");
								}
								
							} else if (args[1].equalsIgnoreCase("win2")) {
								if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.settings") || player.hasPermission("electricfloor.set.win2")) {
									Location loc = player.getLocation();
									
									double x = loc.getX();
									double y = loc.getY();
									double z = loc.getZ();
									float pitchF = loc.getPitch();
									float yawF = loc.getYaw();
									
									double yaw = (double) yawF;
									double pitch = (double) pitchF;
									
									plugin.getConfig().set("positions.win2.X", x);
									plugin.getConfig().set("positions.win2.Y", y);
									plugin.getConfig().set("positions.win2.Z", z);
									plugin.getConfig().set("positions.win2.pitch", pitch);
									plugin.getConfig().set("positions.win2.yaw", yaw);
									plugin.getConfig().set("positions.win2.world", player.getLocation().getWorld().getName());
									plugin.saveConfig();
									
									player.sendMessage(main.chatPrefix + "�6Sikeresen be�ll�tottad ezt a helyet: " + args[1]);
									
									logger.info("[ElectricFloor]" + player.getName() + " Sikeresen v�grehajtotta ezt: " + commandLabel + " " + args[0] + " " + args[1]);
								} else {
									player.sendMessage(ElectricFloor.warnPrefix + "�cNincs jogod a parancs haszn�lat�ra!");
								}
								
							} else if (args[1].equalsIgnoreCase("win3")) {
								if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.settings") || player.hasPermission("electricfloor.set.win3")) {
									Location loc = player.getLocation();
									
									double x = loc.getX();
									double y = loc.getY();
									double z = loc.getZ();
									float pitchF = loc.getPitch();
									float yawF = loc.getYaw();
									
									double yaw = (double) yawF;
									double pitch = (double) pitchF;
									
									plugin.getConfig().set("positions.win3.X", x);
									plugin.getConfig().set("positions.win3.Y", y);
									plugin.getConfig().set("positions.win3.Z", z);
									plugin.getConfig().set("positions.win3.pitch", pitch);
									plugin.getConfig().set("positions.win3.yaw", yaw);
									plugin.getConfig().set("positions.win3.world", player.getLocation().getWorld().getName());
									plugin.saveConfig();
									
									player.sendMessage(main.chatPrefix + "�6Sikeresen be�ll�tottad ezt a helyet: " + args[1]);
									
									logger.info("[ElectricFloor]" + player.getName() + " Sikeresen v�grehajtotta ezt: " + commandLabel + " " + args[0] + " " + args[1]);
								} else {
									player.sendMessage(ElectricFloor.warnPrefix + "�cNincs jogod a parancs haszn�lat�ra!");
								}
								
							} else if (args[1].equalsIgnoreCase("start")) {
								if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.settings") || player.hasPermission("electricfloor.set.start")) {
									Location loc = player.getLocation();
									
									double x = loc.getX();
									double y = loc.getY();
									double z = loc.getZ();
									float pitchF = loc.getPitch();
									float yawF = loc.getYaw();
									
									double yaw = (double) yawF;
									double pitch = (double) pitchF;
									
									plugin.getConfig().set("positions.start.X", x);
									plugin.getConfig().set("positions.start.Y", y);
									plugin.getConfig().set("positions.start.Z", z);
									plugin.getConfig().set("positions.start.pitch", pitch);
									plugin.getConfig().set("positions.start.yaw", yaw);
									plugin.getConfig().set("positions.start.world", player.getLocation().getWorld().getName());
									plugin.saveConfig();
									
									player.sendMessage(main.chatPrefix + "�6Sikeresen be�ll�tottad ezt a helyet: " + args[1]);
									
									logger.info("[ElectricFloor]" + player.getName() + " Sikeresen v�grehajtotta ezt: " + commandLabel + " " + args[0] + " " + args[1]);
								} else {
									player.sendMessage(ElectricFloor.warnPrefix + "�cNincs jogod a parancs haszn�lat�ra!");
								}
								
							} else {
								player.sendMessage(ElectricFloor.warnPrefix + "�cHelytelen haszn�lat!\n" + ElectricFloor.warnPrefix + "�cLehets�ges helyek: win1, win2, win3, arena, wait, loss, spawn, start");
							}
						} else {
							player.sendMessage(ElectricFloor.warnPrefix + "�cNincs jogod a parancs haszn�lat�ra!");
						}
					}
				}
			}
			
		}
		
		return true;
	}

	//TODO: improve logging (fancy colors!!) and maybe language
	@SuppressWarnings("deprecation")
	public void debugCmd(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 1) {//help state, at debug
			sender.sendMessage("debug menu");
			sender.sendMessage("/ef debug <on|off>   -  enable console debug");
			sender.sendMessage("/ef debug <player> <on|off>");
			sender.sendMessage("/ef debug <player>   - enable for the player");
		} else if (args.length == 2) {
			if (args[1].equalsIgnoreCase("on") || args[1].equalsIgnoreCase("off")) {
				if (sender instanceof Player) {
					if (args[1].equalsIgnoreCase("on")) {
						debug.addDebugPlayer((Player) sender);
					} else {
						debug.removeDebugPlayer((Player) sender);
					}
				} else {
					sender.sendMessage(ElectricFloor.warnPrefix + "Csak player �ll�thatja a debugot!");
				}
			} else {
				String pn = args[1];
				Player player = Bukkit.getPlayer(pn);
				if (player != null) {
					debug.addDebugPlayer(player);
				} else {
					sender.sendMessage(ElectricFloor.warnPrefix + pn + " jelenleg nem online!");
				}
			}
		} else if (args.length == 3) {
			if (args[2].equalsIgnoreCase("on")) {
				debug.addDebugPlayer(Bukkit.getPlayer(args[1]));
			} else if (args[2].equalsIgnoreCase("off")) {
				debug.removeDebugPlayer(Bukkit.getPlayer(args[1]));
			} else {
				sender.sendMessage(ElectricFloor.warnPrefix + "Csak on vagy off lehet ez a be�ll�t�s!");
			}
		} else {
			sender.sendMessage("error in args");
		}
	}
	
}
