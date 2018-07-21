package me.electricfloor.main;

import org.bukkit.entity.Player;

/**
 * <b>PermissionHelper<b><br>
 * simple checks for big permission groups, and wrap the base check.
 * 
 * @author SunStorm
 *
 */
public class PermissionHelper {
	
	public static boolean isAdmin(Player player) {
		return (player.hasPermission("electricfloor.admin"));
	}
	/*
	 * just to mention here: if the player has event.leave but dont have event.join that is pointless, because there is now way to use the leave if you cant join.
	 * btw, i dont really care about that, i created perm for all command, and then set them as the children of main groups like admin, or player. 
	 */
	public static boolean isPlayer(Player player) {
		return (player.hasPermission("electricfloor.player") || player.hasPermission("electricfloor.event.join") || player.hasPermission("electricfloor.event.leave"));
	}
	
	public static boolean canSet(Player player)  {
		return (player.hasPermission("electricfloor.settings") || player.hasPermission("electricfloor.admin"));
	}
	
	public static boolean controlEvent(Player player) {
		return (player.hasPermission("electricfloor.event"));
	}
	
	public static boolean canSetPlace(Player player, String place) {
		return player.hasPermission("electricfloor.set" + place);
	}
	
	public static boolean canReload(Player player) {
		return  (player.hasPermission("electricfloor.reload") || player.hasPermission("electricfloor.admin"));
	}
	
	/*
	 * Complicant menu visitor permission checks below, based on the previous sample checks.
	 */
	
	public static boolean fullMenuHelp(Player player) {
		return (isAdmin(player) || canSet(player) || controlEvent(player));
	}
	
	public static boolean playerMenuHelp(Player player) {
		return (isPlayer(player) && !fullMenuHelp(player));
	}
	
	/**
	 * Sample wrapper for simple permissions.
	 * 
	 * @param p the player
	 * @param perm the permission
	 * @return true when the player has the specified permission
	 */
	public boolean hasPerm(Player p, String perm) {
		return p.hasPermission(perm);
	}

}
