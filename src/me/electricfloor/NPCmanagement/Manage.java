package me.electricfloor.NPCmanagement;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.electricfloor.NMSimplement.NMSimplement;
import me.electricfloor.main.ElectricFloor;

public class Manage implements CommandExecutor {

	private static Plugin plugin;
	public static NpcConfig currentConfig = null;
	
	@SuppressWarnings("unused")
	private static NMSimplement implement = ElectricFloor.getImplementation();
	
	@SuppressWarnings("static-access")
	public Manage(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("electricfloor.npc")) {
				if (args.length == 0) {
					player.sendMessage("help menu coming soon");
				} else if (args.length == 1) {
					//command help
				} else if (args.length == 2) {
					//commands
					if (args[0].equalsIgnoreCase("create")) {
						String name = args[1];
						NpcConfig config = new NpcConfig(name, player.getLocation());
						
						createNPC(config);
						
					} else if (args[0].equalsIgnoreCase("delete")) {
						deleteNPC();
					}
				} else {
					player.sendMessage(ElectricFloor.warnPrefix + "�cHib�s defin�ci�!");
				}
			} else {
				player.sendMessage(ElectricFloor.warnPrefix + "�cNincs jogod a parancs haszn�lat�ra!");
			}
		} else {
			sender.sendMessage("[ElectricFloor] Ezt a parancsot csak player haszn�lhatja!");
		}
		
		return true;
	}
	
	public void setupNPCs() {
		
	}
	
	public void createNPC(NpcConfig config) {
		
	}
	
	public void deleteNPC() {
		
	}
	
	public void teleportNPC() {
		
	}
	
	public static void interactNPC(Player player) {
		player.sendMessage("Manage.interactNPC");
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 20F, 0F);
		openGUI(player, plugin);
	}
	
	
	@SuppressWarnings("deprecation")
	public static void openGUI(Player player, Plugin plugin) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLUE + "Csatlakoz�s az eventhez");
		
		ItemStack yes = new ItemStack(Material.STAINED_CLAY, 1, DyeColor.GREEN.getData());
		ItemMeta yesMeta = yes.getItemMeta();
		yesMeta.setDisplayName(ChatColor.GREEN + "Csatlakoz�s");
		ArrayList<String> yesLore = new ArrayList<String>();
		yesLore.add(" ");
		yesLore.add(ChatColor.GOLD + "Csatlakozol az eventhez");
		yesLore.add(" ");
		yesMeta.setLore(yesLore);
		yes.setItemMeta(yesMeta);
		
		ItemStack no = new ItemStack(Material.STAINED_CLAY, 1, DyeColor.GREEN.getData());
		ItemMeta noMeta = yes.getItemMeta();
		yesMeta.setDisplayName(ChatColor.GREEN + "Csatlakoz�s");
		ArrayList<String> noLore = new ArrayList<String>();
		noLore.add(" ");
		noLore.add(ChatColor.GOLD + "Csatlakozol az eventhez");
		noLore.add(" ");
		noMeta.setLore(noLore);
		no.setItemMeta(noMeta);
		
		ItemStack frame = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.CYAN.getData());
		ItemMeta frameMeta = frame.getItemMeta();
		frameMeta.setDisplayName(" ");
		frame.setItemMeta(frameMeta);
		
		ItemStack fill = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta fillMeta = fill.getItemMeta();
		fillMeta.setDisplayName(" ");
		fill.setItemMeta(fillMeta);
		
		for (int i = 0; i < 8; i++) {
			inv.setItem(i, frame);
		}
		
		inv.setItem(9, frame);
		inv.setItem(10, fill);
		inv.setItem(11, yes);
		inv.setItem(12, fill);
		inv.setItem(13, fill);
		inv.setItem(14, fill);
		inv.setItem(15, no);
		inv.setItem(16, fill);
		inv.setItem(17, frame);
		
		player.openInventory(inv);
	}

}
