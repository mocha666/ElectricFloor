package me.electricfloor.command;

public class SubCommand {
	
	public String name;
	public Parameter[] params;
	
	public SubCommand(String name, Parameter... params) {
		this.name = name;
		this.params = params;
	}

}
