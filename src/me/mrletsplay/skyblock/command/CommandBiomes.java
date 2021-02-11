package me.mrletsplay.skyblock.command;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommand;
import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommandSender;
import me.mrletsplay.mrcore.command.event.CommandInvokedEvent;

public class CommandBiomes extends BukkitCommand {
	
	public CommandBiomes(PluginCommand c) {
		super(c);
		setDescription("Biomes");
		setUsage(null);
	}
	
	public void action(CommandInvokedEvent event) {
		Bukkit.dispatchCommand(((BukkitCommandSender) event.getSender()).getBukkitSender(), "is biomes");
	}
	
}
