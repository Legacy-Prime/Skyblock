package me.mrletsplay.skyblock.command;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommand;
import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommandSender;
import me.mrletsplay.mrcore.command.event.CommandInvokedEvent;

public class CommandLevel extends BukkitCommand {
	
	public CommandLevel(PluginCommand c) {
		super(c);
		setDescription("Level");
		setUsage(null);
	}
	
	public void action(CommandInvokedEvent event) {
		Bukkit.dispatchCommand(((BukkitCommandSender) event.getSender()).getBukkitSender(), "is level");
	}
	
}
