package me.electricfloor.arena;

import java.util.HashSet;
import java.util.UUID;

public class ArenaGroup {
	
	public HashSet<UUID> ingame = new HashSet<UUID>();
	public HashSet<UUID> lobby = new HashSet<UUID>();
	public HashSet<UUID> lost = new HashSet<UUID>();
	
	public boolean contains(UUID player) {
		if (ingame.contains(player) || lobby.contains(player) || lost.contains(player)) {
			return true;
		}
		return false;
	}

}
