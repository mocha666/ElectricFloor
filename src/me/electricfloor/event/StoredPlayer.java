package me.electricfloor.event;

import java.lang.reflect.Field;
import java.util.Collection;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/**
 * Player storing system, for storing a state of player, and then recover that.
 * 
 * @author SunStorm
 *
 */
public class StoredPlayer {
	
	public Location location;
	public Inventory inv;
	public ItemStack[] contents;
	public GameMode gamemode;
	public float exp;
	public Player player;
	public boolean isFlying;
	public boolean allowFlying;
	public Collection<PotionEffect> potionEffects;
	
	public StoredPlayer(Player player) {
		this.player = player;
		this.location = player.getLocation();
		this.inv = player.getInventory();
		this.contents = inv.getContents();
		this.gamemode = player.getGameMode();
		this.exp = player.getExp();
		this.isFlying = player.isFlying();
		this.allowFlying = player.getAllowFlight();
		this.potionEffects = player.getActivePotionEffects();
		
		player.setFlying(false);
		player.setAllowFlight(false);
		player.setHealth(20d);
		player.setFoodLevel(20);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}
	
	private StoredPlayer() {
		for (@SuppressWarnings("unused") Field field : this.getClass().getDeclaredFields()) {
			field = null;
		}
	}
	
	public void setPlayerSettings(Player player, boolean teleport) {
		if (teleport) {
			player.teleport(location);
		}
		player.getInventory().setContents(contents);
		player.setGameMode(gamemode);
		player.setExp(exp);
		player.setFlying(isFlying);
		player.setAllowFlight(allowFlying);
		player.addPotionEffects(potionEffects);
	}
	
	@Deprecated
	protected void setSettings(StoredPlayer sp) {
		String thisName = null;
		
		for (Field field : this.getClass().getDeclaredFields()) {
			thisName = field.getName();
			
			for (Field field1 : sp.getClass().getDeclaredFields()) {
				if (thisName == field1.getName()) {
					field = field1;
				}
			}
		}
	}
	
	public StoredPlayer clone() {
		StoredPlayer sp = new StoredPlayer();
		sp.setSettings(this);
		return sp;
	}
	
	public String toString() {
		return "{StoredPlayer: " +  player.getName() + " " + location.toString() + " with " + contents.length + " items in inventory, in gamemode" + gamemode.name() + " with " + exp + " exp}";
	}

}
