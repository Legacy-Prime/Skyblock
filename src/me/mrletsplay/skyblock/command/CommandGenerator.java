package me.mrletsplay.skyblock.command;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommand;
import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommandSender;
import me.mrletsplay.mrcore.command.event.CommandInvokedEvent;

public class CommandGenerator extends BukkitCommand {
	
	public CommandGenerator(PluginCommand c) {
		super(c);
		setDescription("Generator");
		setUsage(null);
	}
	
	public void action(CommandInvokedEvent event) {
		Bukkit.dispatchCommand(((BukkitCommandSender) event.getSender()).getBukkitSender(), "is generator");
	}
	
}
