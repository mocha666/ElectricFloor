package me.electricfloor.arena;

/**
 * The types of the Arena object.<br><br>
 *<b>EVENT:</b>  The arena is started whenewer a staff decide to make an "event"<br>
 *<b>TIMED_EVENT:</b>  Almost same, but the AutoStarter start the arena in specified time.<br>
 *<b>MINIGAME:</b>  Just like a minigame: always opened, if reach the minimum amount of players, arena will start.
 * 
 * @author MrExplode
 *
 */
public enum ArenaType {
	
	EVENT, TIMED_EVENT, MINIGAME;
}
