package me.mrletsplay.skyblock.command;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommand;
import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommandSender;
import me.mrletsplay.mrcore.command.event.CommandInvokedEvent;
import me.mrletsplay.skyblock.GUIs;
import world.bentobox.bentobox.BentoBox;

public class CommandComposter extends BukkitCommand {
	
	public CommandComposter(PluginCommand c) {
		super(c);
		setDescription("Composter");
		setUsage(null);
	}
	
	public void action(CommandInvokedEvent event) {
		BukkitCommandSender s = (BukkitCommandSender) event.getSender();
		Player p = s.asPlayer();
		if(p == null) {
			event.getSender().sendMessage("§cOnly players can use this command");
			return;
		}
		
		if(BentoBox.getInstance().getIslands().getIsland(Bukkit.getWorld("bskyblock_world"), p.getUniqueId()) == null) {
			p.sendMessage("§cYou are not part of an island");
			return;
		}
		
		p.openInventory(GUIs.getComposterSelectGUI(p));
	}
	
}
