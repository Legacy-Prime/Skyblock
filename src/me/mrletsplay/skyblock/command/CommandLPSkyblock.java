package me.mrletsplay.skyblock.command;

import org.bukkit.command.PluginCommand;

import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommand;
import me.mrletsplay.mrcore.command.event.CommandInvokedEvent;

public class CommandLPSkyblock extends BukkitCommand {
	
	public CommandLPSkyblock(PluginCommand c) {
		super(c);
		setDescription("LP-Skyblock commands");
		setUsage(null);
		
		addSubCommand(new CommandGive());
		addSubCommand(new CommandReload());
	}
	
	@Override
	public void action(CommandInvokedEvent event) {
		sendCommandInfo(event.getSender());
	}
	
}
