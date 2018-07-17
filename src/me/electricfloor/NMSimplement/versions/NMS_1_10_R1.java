package me.electricfloor.NMSimplement.versions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.electricfloor.NMSimplement.NMSimplement;
import me.electricfloor.NMSimplement.TitleColor;
import me.electricfloor.NMSimplement.TitleStyle;
import me.electricfloor.NPCmanagement.ConfigReader;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_10_R1.PlayerConnection;

public class NMS_1_10_R1 implements NMSimplement {
	
	@SuppressWarnings("unused")
	private ConfigReader reader = new ConfigReader();
	
	@Override
	public void sendTitle(Player player, String title, TitleColor color, TitleStyle style,  String subtitle, TitleColor subColor, TitleStyle subStyle, int time) {
		String sty = "\"" + style + "\":true,";
		String subSty = "\"" + subStyle + "\":true,";
		String msgT = "{\"text\":\"" + title + "\"," + sty + "\"color\":\"" + color + "\"}";
		String msgST = "{\"text\":\"" + subtitle + "\"," + subSty + "\"color\":\"" + subColor + "\"}";
		//{"text":"asd","color":"green","style":true}
		
		PacketPlayOutTitle Title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a(msgT), 20, time, 30);
		PacketPlayOutTitle subTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a(msgST), 20, time, 30);
		PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
		
		connection.sendPacket(Title);
		connection.sendPacket(subTitle);
		
	}

	@Override
	@SuppressWarnings("deprecation")
	public void sendActionbar(Player player, String message) {
Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("ElectricFloor");
		
		IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" +
                ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
       
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(bar);
            
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(pl, new Runnable () {
        	public void run() {
        		IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" +
                        ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        		PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
                
                    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(bar);
        	}
        }, 40L);
	}
	
	@Override
	public void blockBreakEffect(Player player, int effectID, Location location, int data, boolean sound) {
		BlockPosition pos = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
		PacketPlayOutWorldEvent packet =  new PacketPlayOutWorldEvent(effectID, pos, data, sound);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	}
	
	/*
	 * moved to ElectricFloor NPC project
	 * the dawn of the NPC's
	 * 
	@SuppressWarnings({ "unused", "deprecation" })
	@Override
	public void summonNPC(String npcname) {
		String name = (String) reader.get(npcname, "name");
		String world = (String) reader.get(npcname, "world");
		double x = Double.parseDouble((String) reader.get(npcname, "x"));
		double y = Double.parseDouble((String) reader.get(npcname, "y"));
		double z = Double.parseDouble((String) reader.get(npcname, "z"));
		float pitch = Float.parseFloat((String) reader.get(npcname, "pitch"));
		float yaw = Float.parseFloat((String) reader.get(npcname, "yaw"));
		
		MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer nmsWorld = ((CraftWorld) Bukkit.getServer().getWorld(world)).getHandle();
		GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "§aElectricFloor");
		
		EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
		Player npcPlayer = npc.getBukkitEntity().getPlayer();
		npcPlayer.setPlayerListName(" ");
		ItemStack hand = new ItemStack(Material.STAINED_GLASS, 1, DyeColor.BLUE.getData());
		npcPlayer.setItemInHand(hand);
		
		npc.setLocation(x, y, z, yaw, pitch);
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (all.hasPermission("electricfloor.admin") || all.hasPermission("electricfloor.npc") || all.hasPermission("electricfloor.npcManage")) {
				PlayerConnection connection = ((CraftPlayer) all).getHandle().playerConnection;
				PacketPlayOutPlayerInfo addPlayer = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc);
				PacketPlayOutNamedEntitySpawn spawnEntity = new PacketPlayOutNamedEntitySpawn(npc);
				PacketPlayOutPlayerInfo removePlayer = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, npc);
				
				connection.sendPacket(addPlayer);
				connection.sendPacket(spawnEntity);
				 hmmmm
				connection.sendPacket(removePlayer);
				
				
			}
		}
		
	}
	
	*/

}
