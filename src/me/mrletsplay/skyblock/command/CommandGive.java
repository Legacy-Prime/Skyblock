package me.mrletsplay.skyblock.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import me.mrletsplay.mrcore.bukkitimpl.ItemUtils;
import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommand;
import me.mrletsplay.mrcore.bukkitimpl.command.BukkitCommandSender;
import me.mrletsplay.mrcore.command.event.CommandInvokedEvent;
import me.mrletsplay.skyblock.CustomMaterial;

public class CommandGive extends BukkitCommand {
	
	public CommandGive() {
		super("give");
		setDescription("Give yourself a LP-Skyblock item");
		setUsage("/lpskyblock give <type>");
		getProperties().setPermission("lpskyblock.give");
		
		setTabCompleter(event -> {
			if(event.getArgs().length != 0) return Collections.emptyList();
			
			return Arrays.stream(CustomMaterial.values())
					.map(m -> m.name().toLowerCase())
					.collect(Collectors.toList());
		});
	}
	
	@Override
	public void action(CommandInvokedEvent event) {
		BukkitCommandSender s = (BukkitCommandSender) event.getSender();
		Player p = s.asPlayer();
		if(p == null) {
			event.getSender().sendMessage("§cOnly players can use this command");
			return;
		}
		
		if(event.getArguments().length != 1) {
			sendCommandInfo(event.getSender());
			return;
		}
		
		CustomMaterial m;
		try {
			m = CustomMaterial.valueOf(event.getArguments()[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			sendCommandInfo(event.getSender());
			return;
		}
		
		ItemUtils.addItemOrDrop(p, m.createItem(1));
	}

}
