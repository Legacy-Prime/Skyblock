package me.mrletsplay.skyblock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import me.mrletsplay.mrcore.misc.FriendlyException;
import me.mrletsplay.skyblock.blockbreaker.BlockBreaker;
import me.mrletsplay.skyblock.blockbreaker.BlockBreakerEvents;
import me.mrletsplay.skyblock.command.CommandBiomes;
import me.mrletsplay.skyblock.command.CommandChallenges;
import me.mrletsplay.skyblock.command.CommandComposter;
import me.mrletsplay.skyblock.command.CommandGenerator;
import me.mrletsplay.skyblock.command.CommandLPSkyblock;
import me.mrletsplay.skyblock.command.CommandLevel;
import me.mrletsplay.skyblock.composter.ComposterEvents;
import me.mrletsplay.skyblock.grinder.Grinder;
import me.mrletsplay.skyblock.grinder.GrinderEvents;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.Addon;

public class Skyblock extends JavaPlugin {
	
	private static Addon levelAddon;

	@Override
	public void onEnable() {
		PluginCommand lpsb = getCommand("lpskyblock");
		lpsb.setExecutor(new CommandLPSkyblock(lpsb));
		
		PluginCommand gen = getCommand("generator");
		gen.setExecutor(new CommandGenerator(gen));
		
		PluginCommand comp = getCommand("composter");
		comp.setExecutor(new CommandComposter(comp));
		
		PluginCommand bi = getCommand("biomes");
		bi.setExecutor(new CommandBiomes(bi));
		
		PluginCommand lv = getCommand("level");
		lv.setExecutor(new CommandLevel(lv));
		
		PluginCommand ch = getCommand("challenges");
		ch.setExecutor(new CommandChallenges(ch));
		
		Recipes.registerRecipes();
		
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		Bukkit.getPluginManager().registerEvents(new ComposterEvents(), this);
		Bukkit.getPluginManager().registerEvents(new GrinderEvents(), this);
		Bukkit.getPluginManager().registerEvents(new BlockBreakerEvents(), this);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			Grinder.runGrinders();
			BlockBreaker.runBlockBreakers();
		}, 10, 10);
		
		getLogger().info("Enabled");
	}
	
	public static Skyblock getPlugin() {
		return Skyblock.getPlugin(Skyblock.class);
	}
	
	public static Addon getLevelAddon() {
		if(levelAddon == null) 
			levelAddon = BentoBox.getInstance().getAddonsManager().getAddonByName("Level").get();
		return levelAddon;
	}
	
	public static long getIslandLevel(World world, UUID uuid) {
		try {
			Method m = getLevelAddon().getClass().getMethod("getIslandLevel", World.class, UUID.class);
			return (long) m.invoke(levelAddon, world, uuid);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new FriendlyException(e);
		}
	}
	
}
