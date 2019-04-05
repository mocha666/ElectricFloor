package me.electricfloor.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class AbstractECommand extends BukkitCommand {

	public AbstractECommand(String name, String description, String usageMessage, List<String> aliases) {
		super(name, description, usageMessage, aliases);
	}

	@Override
	public boolean execute(CommandSender paramCommandSender, String paramString, String[] paramArrayOfString) {
		paramCommandSender.sendMessage("fuck off");
		return false;
	}

}
