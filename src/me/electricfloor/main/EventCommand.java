package me.electricfloor.main;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import me.electricfloor.Language.Language;
import me.electricfloor.NMSimplement.NMSimplement;
import me.electricfloor.event.EventControl;
import me.electricfloor.event.EventGroup;
import me.electricfloor.file.ELogger;
import me.electricfloor.file.LogLevel;

public class EventCommand implements CommandExecutor {
	
	private Plugin plugin;
	private Logger logger = Logger.getLogger("Minecraft");
	
	private ELogger eLog = ElectricFloor.getELogger();
	private NMSimplement implement = ElectricFloor.getImplementation();
	
	public EventCommand(Plugin plugin) {
		
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			//szerver oldal
			sender.sendMessage("[ElectricFloor] Server page coming soon...");
		} else {
			//kliens oldal
			Player player = (Player) sender;
			
			if (args.length == 0) {
				if (PermissionHelper.controlEvent(player)) {
					player.sendMessage(" ");
					player.sendMessage(" ");
					player.sendMessage(" ");
					player.sendMessage(" ");
					player.sendMessage(ChatColor.GREEN + "============" + ChatColor.AQUA +"[ Electric Floor ]" + ChatColor.GREEN + "============");
					player.sendMessage(ChatColor.AQUA + "/event bc    - Event hirdetése");
					player.sendMessage(ChatColor.AQUA + "/event start - Event indítása");
					player.sendMessage(ChatColor.AQUA + "/event stop  - Event leállítása");
					player.sendMessage(ChatColor.AQUA + "/event kick  - Játékos kirúgása");
					player.sendMessage(ChatColor.AQUA + "/event join  - Csatlakozás az eventhez");
					player.sendMessage(ChatColor.AQUA + "/event leave - Event elhagyása");
					player.sendMessage(ChatColor.GREEN + "======================================");
				}
				
				if (PermissionHelper.playerMenuHelp(player)) {
					player.sendMessage(ChatColor.GREEN + "============" + ChatColor.AQUA +"[ Electric Floor ]" + ChatColor.GREEN + "============");
					player.sendMessage(ChatColor.AQUA + "/event join  - Csatlakozás az eventhez");
					player.sendMessage(ChatColor.AQUA + "/event leave - Event elhagyása");
					player.sendMessage(ChatColor.GREEN + "======================================");
				}
			}
			
			if (args.length == 1) {
				//arena display
				String arenaName;
			}
			
		}
		return true;
	}
}