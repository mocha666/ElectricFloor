package me.electricfloor.NMSimplement.versions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.electricfloor.NMSimplement.NMSimplement;
import me.electricfloor.NMSimplement.TitleColor;
import me.electricfloor.NMSimplement.TitleStyle;
import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R2.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_8_R2.PlayerConnection;

public class NMS_1_8_R2 implements NMSimplement {
	
	@Override
	public void sendTitle(Player player, String title, TitleColor color, TitleStyle style,  String subtitle, TitleColor subColor, TitleStyle subStyle, int time) {
		String sty = "\"" + style + "\":true,";
		String subSty = "\"" + subStyle + "\":true,";
		String msgT = "{\"text\":\"" + title + "\"," + sty + "\"color\":\"" + color + "\"}";
		String msgST = "{\"text\":\"" + subtitle + "\"," + subSty + "\"color\":\"" + subColor + "\"}";
		//{"text":"asd","color":"green","style":true}
		
		
		PacketPlayOutTitle Title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a(msgT), 20, time * 20, 30);
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

}
