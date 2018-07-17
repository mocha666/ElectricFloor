package me.electricfloor.NPCmanagement;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class NpcConfig {
	
	private String name;
	private Location location;
	private double x;
	private double y;
	private double z;
	private float pitch;
	private float yaw;
	private String world;
	
	public NpcConfig(String name, World world, double x, double y, double z, float pitch, float yaw) {
		this.name = name.replace('&', '§');
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.world = world.getName();
		
		Location loc = new Location(Bukkit.getServer().getWorld(this.world), this.x, this.y, this.z);
		loc.setPitch(this.pitch);
		loc.setYaw(this.yaw);
		this.location = loc;
		
	}
	
	public NpcConfig(String name, Location location) {
		this.name = name.replace('&', '§');
		this.world = location.getWorld().getName();
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.pitch = location.getPitch();
		this.yaw = location.getYaw();
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public String getName() {
		return name;
	}

}
