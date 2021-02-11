package me.mrletsplay.skyblock.command;

import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommand;
import me.mrletsplay.mrcore.command.event.CommandInvokedEvent;
import me.mrletsplay.skyblock.GUIs;
import me.mrletsplay.skyblock.composter.Composter;

public class CommandReload extends BukkitCommand {
	
	public CommandReload() {
		super("reload");
		setDescription("Reload LP-Skyblock configs");
		setUsage("/lpskyblock reload");
		getProperties().setPermission("lpskyblock.reload");
	}
	
	@Override
	public void action(CommandInvokedEvent event) {
		Composter.reload();
		GUIs.loadGUIs();
		event.getSender().sendMessage("§aReloaded");
	}

}
