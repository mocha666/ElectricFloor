package me.electricfloor.event;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import me.electricfloor.main.ElectricFloor;

public class BlockManager {
	
	/*
	 * Blue
	 */
	private ArrayList<Block> state1 = new ArrayList<Block>();
	
	/*
	 * Dark Blue 
	 */
	private ArrayList<Block> state2 = new ArrayList<Block>();
	
	/*
	 *  Red
	 */
	private ArrayList<Block> state3 = new ArrayList<Block>();
	
	/**
	 * Updates the specified block's state.
	 * 
	 * @param block
	 */
	public void updateBlockState(Block block) {
		if (!state1.contains(block) && !state2.contains(block) && !state3.contains(block)) {
			state1.add(block);
			updateTypeSync(block, 3);
		} else if (state1.contains(block)) {
			state1.remove(block);
			state2.add(block);
			updateTypeSync(block, 11);
		
		} else if (state2.contains(block)) {
			state2.remove(block);
			state3.add(block);
			updateTypeSync(block, 14);
		}
	}
	
	/**
	 * Returns true when the specified block is in state3, wich is the deadly state for the player.
	 * @param block 
	 * @return the condition
	 */
	public boolean isCharged(Block block) {
		if (state3.contains(block)) {
			return true;
		}
		return false;
	}
	
	public void clearAll() {
		state1.clear();
		state2.clear();
		state3.clear();
	}
	
	/*
	 * Do the update delayed.
	 * Required since if the player step on a block without timing, it loops through all state inmediatelly.
	 */
	private void updateTypeSync(Block block, int type) {
		BukkitRunnable runnable = new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				block.setData((byte) type);
			}
		};
		runnable.runTaskLater(ElectricFloor.getPlugin(), 10L);
	}

}
