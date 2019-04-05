package me.electricfloor.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.electricfloor.Language.Language;
import me.electricfloor.file.ELogger;
import me.electricfloor.file.LogLevel;
import me.electricfloor.main.ElectricFloor;
import me.electricfloor.main.PermissionHelper;

public class EfCommand implements CommandExecutor {
	
	private Plugin plugin;
	
	private ELogger eLog = ElectricFloor.getELogger();
	
	public static final String[] preLines = new String[] {
			" ",
			" ",
			" ",
	};
	
	public static final String[] mainHelpPage = new String[] {
			"§a============§b[ Electric Floor ]§a============",
			"§b/ef        - This page",
			"§b/ef reload - Reload config",
			"§b/ef set    - Configuration page",
			"§b/event     - Event controls",
			"§a======================================"
	};
	
	public static final String[] playerHelpPage = new String[] {
			"§a============§b[ Electric Floor ]§a============",
			"§b/ef        - This page",
			"§b/event     - Event controls",
			"§b======================================"
	};
	
	public static final String[] setterHelpPage = new String[] {
		"§a=============§b[ Electric Floor ]§a=============",
		"§b/ef set spawn  - Spawn loc - sets automatically",
		"§b/ef set lobby  - Lobby location",
		"§b/ef set arena  - Arena location",
		"§b/ef set lost   - Lost players location",
		"§b/ef set win1   - 1st place",
		"§b/ef set win2   - 2nd place",
		"§b/ef set win3   - 3rd place",
		"§a======================================="
	};
	
	public EfCommand(Plugin plugin) {
		
		this.plugin = plugin;
		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length == 0) {
				sender.sendMessage(mainHelpPage);
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					sender.sendMessage("[ElectricFloor] Reload...");
					plugin.saveConfig();
					plugin.reloadConfig();
					Bukkit.getServer().getPluginManager().disablePlugin(plugin);
					Bukkit.getServer().getPluginManager().enablePlugin(plugin);
					sender.sendMessage("[ElectricFloor] Successful reload!");
				} else {
					sender.sendMessage(mainHelpPage);
				}
			} else {
				sender.sendMessage(mainHelpPage);
			}
		} else {
			//kliens
			Player player = (Player) sender;
			if (args.length == 0) {
				//help page
				if (PermissionHelper.fullMenuHelp(player)) {
					player.sendMessage(preLines);
					player.sendMessage(mainHelpPage);
				} else if (PermissionHelper.playerMenuHelp(player)) {
					player.sendMessage(preLines);
					player.sendMessage(playerHelpPage);
				}
			} else {
				if (args.length == 1) { //alap szelektorok
					if (PermissionHelper.canSet(player) || PermissionHelper.canReload(player)) {
						if (args[0].equalsIgnoreCase("set")) {
							//set help
							player.sendMessage(preLines);
							player.sendMessage(setterHelpPage);
						}
						
						if (args[0].equalsIgnoreCase("reload")) {
							if (PermissionHelper.canReload(player)) {
								eLog.log(LogLevel.INFO, player.getName() + " restared the plugin!");
								eLog.log(LogLevel.INFO, "Restarting...");
								ElectricFloor.isRestart = true;
								player.sendMessage(ElectricFloor.chatPrefix + "Reload...");
								plugin.saveConfig();
								plugin.reloadConfig();
								Bukkit.getServer().getPluginManager().disablePlugin(plugin);
								Bukkit.getServer().getPluginManager().enablePlugin(plugin);
								player.sendMessage(ElectricFloor.chatPrefix + "Successful reload!");
								eLog.log(LogLevel.INFO, "Successful reload!");
								ElectricFloor.isRestart = false;
							}
						}
						
						if (args[0].equalsIgnoreCase("debug")) {
							//debugCmd(sender, cmd, commandLabel, args);
						}
					} else {//player perm
						player.sendMessage(preLines);
						player.sendMessage(playerHelpPage);
					}
				}
				
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("set")) {
						
					}
				}
			}
			
		}
		
		return true;
	}

	/*
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
	*/
	
}
