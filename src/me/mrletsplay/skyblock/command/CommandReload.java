package me.mrletsplay.skyblock.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommand;
import me.mrletsplay.mrcore.command.event.CommandInvokedEvent;
import me.mrletsplay.skyblock.CustomMaterial;
import me.mrletsplay.skyblock.composter.Composter;

public class CommandReload extends BukkitCommand {
	
	public CommandReload() {
		super("reload");
		setDescription("Reload LP-Skyblock configs");
		setUsage("/lpskyblock reload");
		getProperties().setPermission("lpskyblock.reload");
		
		setTabCompleter(event -> {
			if(event.getArgs().length != 0) return Collections.emptyList();
			
			return Arrays.stream(CustomMaterial.values())
					.map(m -> m.name().toLowerCase())
					.collect(Collectors.toList());
		});
	}
	
	@Override
	public void action(CommandInvokedEvent event) {
		Composter.reload();
		event.getSender().sendMessage("§aReloaded");
	}

}
