package me.electricfloor.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractElectricCommand implements ElectricCommand {
	
	public boolean isClientSide = true;
	public boolean allowSubcommands = true;
	public String name;
	public String description;
	public String[] permissions;
	
	public ArrayList<SubCommand> subCommands;
	public ArrayList<Parameter> parameters;
	
	/**
	 * Construct a new command with the specified properties.
	 * 
	 * @param isClientSide - when true, the command only works for players
	 * @param allowSubCommands - allow or disallow subcommands.
	 * @param name
	 * @param description
	 * @param permissions
	 */
	public AbstractElectricCommand(boolean isClientSide, boolean allowSubCommands, String name, String description, String[] permissions) {
		this.isClientSide = isClientSide;
		this.allowSubcommands = allowSubCommands;
		this.name = name;
		this.description = description;
		this.permissions = permissions;
	}

	/**
	 * Add a {@link SubCommand} to the command.
	 * 
	 *@param the specified {@link SubCommand}
	 *@throws IllegalAccessError if the subcommands are disabled.
	 */
	@Override
	public void addSubCommand(SubCommand cmd) {
		if (!allowSubcommands) {
			throw new IllegalAccessError("Subcommands not enabled at " + name + " command, howewer tried to add one");
		}
	}
	
	/**
	 * Add a {@link Parameter} to the command.
	 * 
	 * @param the Parameter
	 */
	@Override
	public void addParameter(Parameter param) {
		
	}

	/**
	 * Don't TucH ME! override the execute.
	 * 
	 * @param sender
	 * @param cmd
	 * @param commandLabel
	 * @param args
	 */
	public boolean execute0(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player) && isClientSide) {
			return false;
		}
		
		if (sender instanceof Player) {
			for (int i = 0; i < permissions.length; i++) {
				if (!sender.hasPermission(permissions[i])) {
					return false;
				}
			}
		}
		
		return execute(sender, cmd, commandLabel, args);
	}
	
	public abstract boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args);
	
	/**
	 * Return the command name
	 * 
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Return the permission list
	 * 
	 * @return the permission list
	 */
	@Override
	public String[] getPermissions() {
		return permissions;
	}
	
	/**
	 * Return true if the command is only enabled at client side.
	 * 
	 * @return the value
	 */
	@Override
	public boolean isClientSide() {
		return isClientSide;
	}
}
