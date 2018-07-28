package me.electricfloor.autostart;

import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;

import me.electricfloor.arena.Arena;
import me.electricfloor.arena.ArenaManager;
import me.electricfloor.event.Event;

public class AutoStarter {
	
	private ArrayList<Integer> tasks = new ArrayList<Integer>();
	
	public void startListening() {
		ArenaManager manager = Event.arenaManager;
		for (Arena a : manager.arenas.values()) {
			if (a.autoStart == true) {
				//start
			}
		}
	}
	
	private BukkitRunnable runnable = new BukkitRunnable() {
		@Override
		public void run() {
			//aunnounce
			//wait some
			//start
		}
		
	};

}
