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
import me.electricfloor.event.Event;
import me.electricfloor.event.EventGroup;
import me.electricfloor.file.logging.LogLevel;
import me.electricfloor.file.logging.ELogger;

public class EventCommand implements CommandExecutor {
	
	private Plugin plugin;
	
	@SuppressWarnings("unused")
	private PluginDescriptionFile pdffile = Bukkit.getServer().getPluginManager().getPlugin("ElectricFloor").getDescription();
	private Logger logger = Logger.getLogger("Minecraft");
	
	private ELogger eLog = ElectricFloor.getELogger();
	private NMSimplement implement = ElectricFloor.getImplementation();
	private ElectricFloor main = ElectricFloor.getInstance();
	//TODO: language in EventCommand class
	@SuppressWarnings("unused")
	private Language l = ElectricFloor.getLanguage();
	
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
				if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.event") || player.hasPermission("electricfloor.event.bc") || player.hasPermission("electricfloor.event.start") || player.hasPermission("electricfloor.event.stop") || player.hasPermission("electricfloor.event.kick")) {
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
				
				if (player.hasPermission("electricfloor.player") || player.hasPermission("electricfloor.event.join") || player.hasPermission("electricfloor.event.leave") && !(player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.event") || player.hasPermission("electricfloor.event.bc") || player.hasPermission("electricfloor.event.start") || player.hasPermission("electricfloor.event.stop") || player.hasPermission("electricfloor.event.kick"))) {
					player.sendMessage(ChatColor.GREEN + "============" + ChatColor.AQUA +"[ Electric Floor ]" + ChatColor.GREEN + "============");
					player.sendMessage(ChatColor.AQUA + "/event join  - Csatlakozás az eventhez");
					player.sendMessage(ChatColor.AQUA + "/event leave - Event elhagyása");
					player.sendMessage(ChatColor.GREEN + "======================================");
				}
			}
			
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("stopclear")) {
					if (player.hasPermission("electricfloor.admin")) {
						player.removeMetadata("clearing", plugin);
						player.sendMessage(main.chatPrefix + "manual clearing disabled");//beta log
					}
				}
				
				if (args[0].equalsIgnoreCase("clear")) {
					if (player.hasPermission("electricfloor.admin")) {
						player.setMetadata("clearing", new FixedMetadataValue(plugin, "EF"));
						player.sendMessage(main.chatPrefix + "manual clearing enabled");//beta log
					}
				}
				
				if (args[0].equalsIgnoreCase("bc")) {
					if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.event") || player.hasPermission("electricfloor.event.bc")) {
						logger.info("[ElectricFloor] Ellenőrzés kezdődik");
						boolean state = Utils.eventReady(plugin);
						if (state == true) {
							logger.info("[ELectricFloor] Az event használatra kész!");
							eLog.log(LogLevel.INFO, player.getName() + " meghirdette az eventet!");
							ElectricFloor.eventReadyTo = true;
							ElectricFloor.eventBroadcasted = true;
							
							for (Player onlineP : Bukkit.getOnlinePlayers()) {
								implement.sendActionbar(onlineP, "&4&lElectricFloor event lesz! &c&lCsatlakozáshoz: &6&n&l/event join");
								plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable () {
									public void run() {
										implement.sendActionbar(onlineP, "&4&lElectricFloor event lesz! &c&lCsatlakozáshoz: &6&n&l/event join");
									}
								}, 40L);
							}
						} else {
							player.sendMessage(ElectricFloor.warnPrefix + "Â§cAz eventhez szükséges helyek közül egy vagy több nincs beállítva!");
							ElectricFloor.eventReadyTo = false;
						}
					} else {
						player.sendMessage(ElectricFloor.warnPrefix + "Â§cNincs jogod a parancs használatára!");
					}
					
				}
				
				if (args[0].equalsIgnoreCase("start")) {
					if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.event") || player.hasPermission("electricfloor.event.start")) {
						if (ElectricFloor.eventBroadcasted == true) {
							if (ElectricFloor.eventReadyTo = true) {
								eLog.log(LogLevel.INFO, player.getName() + " elindította az eventet!");

								for (Player all : Bukkit.getOnlinePlayers()) {	
									if (EventGroup.isInGroup(player, EventGroup.LOBBY)) {
										Location loc = Utils.teleportWithConfig(all, plugin, "start", true);
										int x = loc.getBlockX();
										int z = loc.getBlockZ();
										Random random = new Random();
										int select = random.nextInt(3);
										int tpX = 0;
										int tpZ = 0;
										if (select == 0) {
											tpX = x + random.nextInt(ElectricFloor.teleportRadius);
											tpZ = z + random.nextInt(ElectricFloor.teleportRadius);
										} else if (select == 1){
											tpX = x - random.nextInt(ElectricFloor.teleportRadius);
											tpZ = z - random.nextInt(ElectricFloor.teleportRadius);
										} else if (select == 2) {
											tpX = x - random.nextInt(ElectricFloor.teleportRadius);
											tpZ = z + random.nextInt(ElectricFloor.teleportRadius);
										} else if (select == 3) {
											tpX = x + random.nextInt(ElectricFloor.teleportRadius);
											tpZ = z - random.nextInt(ElectricFloor.teleportRadius);
										}
										
										loc.setX(tpX);
										loc.setZ(tpZ);
										
										all.setFoodLevel(20);
										all.teleport(loc);
										
										all.removeMetadata("waitE", plugin);
										all.setMetadata("ingame", new FixedMetadataValue(plugin, "EF"));
										implement.sendActionbar(player, "&c&lAz event elindult!");
									}
								}
								
								player.sendMessage(main.chatPrefix + "Sikeresen elindítottad az eventet!");//check bc!!!!!!!!!
								logger.info("[ElectricFloor] " + player.getName() + " elindította az eventet!");
							} else {
								player.sendMessage(main.chatPrefix + "Az eventhez szĂĽkséges helyek kĂ¶zĂĽl egy vagy tĂ¶bb nincs beállítva!");
							}
						} else {
							player.sendMessage(main.chatPrefix + "Az event indításához előbb hirdetned kell azt!");
						}
					} else {
						player.sendMessage(ElectricFloor.warnPrefix + "Â§cNincs jogod a parancs használatára!");
					}
				}
				
				if (args[0].equalsIgnoreCase("stop")) {
					if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.event") || player.hasPermission("electricfloor.event.stop")) {
						eLog.log(LogLevel.CRITICAL, player.getName() + " kényszerítetten leállította az eventet");
						
						for (Player eventPlayer : Bukkit.getOnlinePlayers()) {
							if (EventGroup.isInEvent(eventPlayer)) {
								Event.leaveEvent(eventPlayer, plugin, true);
								eventPlayer.sendMessage(main.chatPrefix + "Az eventet leállították, ki lettál léptetve!");
								ElectricFloor.eventPlayerCounter = 0;
								eventPlayer.setFoodLevel(20);
							}
						}
						
						ElectricFloor.eventBroadcasted = false;
						player.sendMessage(main.chatPrefix + "Leállítottad az eventet!");
						logger.info("[ElectricFloor] " + player.getName() + " leállította az eventet!");
					} else {
						player.sendMessage(ElectricFloor.warnPrefix + "Â§cNincs jogod a parancs használatára!");
					}
				}
				
				if (args[0].equalsIgnoreCase("exit")) {
					if (player.hasPermission("electricfloor.admin")) {
						eLog.log(LogLevel.INFO, player.getName() + " titokban kilépett az eventből (/event exit)");
						EventGroup.movePlayerGroup(player, EventGroup.getPlayerGroup(player), EventGroup.NOT_MARKED);
						player.sendMessage(main.chatPrefix + "exited from event");
						
					}
				}
				
				if (args[0].equalsIgnoreCase("kick")) { //kick paraméter
					if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.event") || player.hasPermission("electricfloor.event kick")) {
						if (args.length == 3) {
							Player target = Bukkit.getServer().getPlayer(args[2]);
							if (target == null) {
								player.sendMessage(ElectricFloor.warnPrefix + "Â§6" + args[2].toString() + " Â§cjelenleg nem online!");
							} else {
								if (EventGroup.isInGroup(target, EventGroup.LOBBY) || EventGroup.isInGroup(target, EventGroup.INGAME)) {
									eLog.log(LogLevel.INFO, target.getName() + " ki lett rúgva az eventről " + player.getName() + " által");
									EventGroup.movePlayerGroup(target, EventGroup.getPlayerGroup(target), EventGroup.NOT_MARKED);
									
									Utils.teleportWithConfig(target, plugin, "spawn", false);
									target.sendMessage(main.chatPrefix + "ki lettél rúgva az eventről " + player.getName() + " által");
									ElectricFloor.eventPlayerCounter--;
									logger.info("[ElectricFloor] " + player.getName() + " kirúgta az eventről őt: " + target.getName());
								} else {
									player.sendMessage(ElectricFloor.warnPrefix + "Â§c" + target.getName() + " nem csatlakozott az eventhez!");
								}
							}
						} else {
							player.sendMessage(ElectricFloor.warnPrefix + "Â§cnem megfelelő definíció!");
						}
					} else {
						player.sendMessage(ElectricFloor.warnPrefix + "Â§cNincs jogod a parancs használatára!");
					}
				}
				
				if (args[0].equalsIgnoreCase("join")) { //join paraméter
					if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.event") || player.hasPermission("electricfloor.player") || player.hasPermission("electricfloor.event.join")) {
						Event.joinEvent(player, plugin, false);
					} else {
						player.sendMessage(ElectricFloor.warnPrefix + "Â§cNincs jogod a parancs használatára!");
					}
				}
				
				if (args[0].equalsIgnoreCase("leave")) {
					if (player.hasPermission("electricfloor.admin") || player.hasPermission("electricfloor.event") || player.hasPermission("electricfloor.player") || player.hasPermission("electricfloor.event.leave")) {
						eLog.log(LogLevel.INFO, player.getName() + " elhagyta az eventet");
						Event.leaveEvent(player, plugin, false);
					} else {
						player.sendMessage(ElectricFloor.warnPrefix + "Â§cNincs jogod a parancs használatára!");
					}
				}
			}
			
		}
		return true;
	}
}