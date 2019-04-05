package me.electricfloor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ElectricCommand {
	public void addSubCommand(SubCommand cmd);
	public void addParameter(Parameter param);
	
	public String getName();
	public String[] getPermissions();
	public boolean isClientSide();
	public boolean hasParameter(String name);
	public boolean hasSubCommand(String name);
	
	public Parameter getParameter(String name);
	public SubCommand getSubCommant(String name);
	
	public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args);

}
