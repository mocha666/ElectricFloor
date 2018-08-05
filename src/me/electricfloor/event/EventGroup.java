package me.electricfloor.event;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.electricfloor.error.ElectricError;
import me.electricfloor.file.ELogger;
import me.electricfloor.file.LogLevel;
import me.electricfloor.main.ElectricFloor;

/**
 * A clear way to manage players in groups, using HashMaps, instead of Bukkit's Metadata System.<br>
 * The players must be in one group, if not else in the NOT_MARKED group.
 * 
 * @author SunStorm
 *
 */
public enum EventGroup {
	
	NOT_MARKED(EventControl.nm),
	LOBBY(EventControl.lobby),
	INGAME(EventControl.ingame),
	WIN1(EventControl.win1),
	WIN2(EventControl.win2),
	WIN3(EventControl.win3),
	LOST(EventControl.lost);
	
	public HashMap<String, Player> map = new HashMap<String, Player>();
	
	private static ELogger eLogger = ElectricFloor.getELogger();
	
	EventGroup(HashMap<String, Player> map) {
		this.map = map;
		if (this.map == null) {
			this.map = new HashMap<String, Player>();
		}
	}
	
	public static EventGroup getPlayerGroup(Player player) {
		if (isAppearSomewhere(player)) {
			if (isInGroup(player, EventGroup.NOT_MARKED)) {
				return EventGroup.NOT_MARKED;
			} else if (isInGroup(player, EventGroup.LOBBY)) {
				return EventGroup.LOBBY;
			} else if (isInGroup(player, EventGroup.INGAME)) {
				return EventGroup.INGAME;
			} else if (isInGroup(player, EventGroup.LOST)) {
				return EventGroup.LOST;
			} else {
				throw new ElectricError("Could not find player group for " + player.getName(), new NullPointerException("Player appears in group, but could not find the group"));
			}
		} else {
			return null;
		}
	}
	
	/**
	 * test for: lobby, ingame, lost<br>
	 * <b>NOT TESTING THE WINNERS!</b>
	 * 
	 * @param player
	 * @return the status of the event
	 */
	public static boolean isInEvent(Player player) {
		if (isInGroup(player, EventGroup.LOBBY) || isInGroup(player, EventGroup.INGAME) || isInGroup(player, EventGroup.LOST)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isAppearSomewhere(Player player) {
		if (isInGroup(player, EventGroup.NOT_MARKED) || isInGroup(player, EventGroup.LOBBY) || isInGroup(player, EventGroup.INGAME) || isInGroup(player, EventGroup.LOST)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isInGroup(Player player, EventGroup group) {
		if (group.map.containsKey(player.getName())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * <b>Should only use at PlayerJoinEvent! In other situations you have to use {@code EventGroup.movePlayerGroup(Player player, EventGroup from, EventGroup to)}</b>
	 * @param player
	 * @param group
	 */
	public static void addToGroup(Player player, EventGroup group) {
		if (!(group.map.containsKey(player.getName()))) {
			group.map.put(player.getName(), player);
		} else {
			eLogger.log(LogLevel.WARNING, "This player already added to group ");
		}
	}
	
	/**
	 * Should only use at PlayerQuitEvent! In other situations you have to use {@code EventGroup.movePlayerGroup(Player player, EventGroup from, EventGroup to)}</b>
	 * 
	 * @param player
	 * @param group
	 */
	public static void removeFromGroup(Player player, EventGroup group) {
		if (group.map.containsKey(player.getName())) {
			group.map.remove(player.getName());
		} else {
			eLogger.log(LogLevel.WARNING, "There is no player named " + player.getName() + " in group "+ group.toString());
		}
	}
	
	/**
	 * Should only use at PlayerQuitEvent! In other situations you have to use {@code EventGroup.movePlayerGroup(Player player, EventGroup from, EventGroup to)}</b>
	 * 
	 * @param player
	 * @param group
	 */
	public static void removePlayer(Player player) {
		if (isAppearSomewhere(player)) {
			EventGroup current = getPlayerGroup(player);
			removeFromGroup(player, current);
		}
	}
	
	public static void movePlayerGroup(Player player, EventGroup from, EventGroup to) {
		if (!to.map.containsKey(player.getName())) {
			if (from.map.containsKey(player.getName())) {	
				from.map.remove(player.getName());
				to.map.put(player.getName(), player);
			} else {
				eLogger.log(LogLevel.WARNING, "This player (" + player.getName() + ") didn't join the event");
			}
		} else {
			eLogger.log(LogLevel.WARNING, "Player " + player.getName() + " already moved to group " + from);
		}
	}
	
	/*
	public String toString() {
		if (map.equals(Event.NOT_MARKED)) {
			return "NOT_MARKED";
		} else if (map.equals(Event.lost)) {
			return "lost";
		} else if (map.equals(Event.lobby)) {
			return "lobby";
		} else if (map.equals(Event.ingame)) {
			return "ingame";
		} else if (map.equals(Event.clearing)) {
			return "clearing";
		} else {
			return "undefined group";
		}
	}
	*/

}
