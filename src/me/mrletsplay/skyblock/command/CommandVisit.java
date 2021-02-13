package me.mrletsplay.skyblock.command;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommand;
import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommandSender;
import me.mrletsplay.mrcore.command.event.CommandInvokedEvent;

public class CommandVisit extends BukkitCommand {
	
	public CommandVisit(PluginCommand c) {
		super(c);
		setDescription("Visit");
		setUsage("/visit <player>");
	}
	
	public void action(CommandInvokedEvent event) {
		if(event.getArguments().length != 1) {
			sendCommandInfo(event.getSender());
			return;
		}
		
		Bukkit.dispatchCommand(((BukkitCommandSender) event.getSender()).getBukkitSender(), "is visit " + event.getArguments()[0]);
	}
	
}
