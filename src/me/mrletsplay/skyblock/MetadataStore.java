package me.mrletsplay.skyblock;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

import me.mrletsplay.mrcore.bukkitimpl.config.BukkitCustomConfig;
import me.mrletsplay.mrcore.config.ConfigLoader;

public class MetadataStore {
	
	private static BukkitCustomConfig config;
	
	static {
		File f = new File(Skyblock.getPlugin().getDataFolder(), "metadata.yml");
		config = ConfigLoader.loadConfigFromFile(new BukkitCustomConfig(f), f);
	}
	
	public static void setMetadata(Location loc, String key, Object value) {
		config.set("metadata." + locToString(loc) + "." + key, value);
		config.saveToFile();
	}
	
	public static void setMetadata(Block block, String key, Object value) {
		setMetadata(block.getLocation(), key, value);
	}
	
	public static void unsetMetadata(Location loc) {
		config.unset("metadata." + locToString(loc));
		config.saveToFile();
	}
	
	public static void unsetMetadata(Block block) {
		unsetMetadata(block.getLocation());
	}
	
	public static <T> T getMetadata(Location loc, String key, Class<T> type) {
		return config.getGeneric("metadata." + locToString(loc) + "." + key, type);
	}
	
	public static <T> T getMetadata(Block block, String key, Class<T> type) {
		return getMetadata(block.getLocation(), key, type);
	}
	
	public static <T> T getMetadataOrDefault(Location loc, String key, Class<T> type, T defaultValue) {
		return config.getGeneric("metadata." + locToString(loc) + "." + key, type, defaultValue, false);
	}
	
	public static <T> T getMetadataOrDefault(Block block, String key, Class<T> type, T defaultValue) {
		return getMetadataOrDefault(block.getLocation(), key, type, defaultValue);
	}
	
	public static <T> List<Location> getByMetadataValue(String key, Class<T> type, T value) {
		return config.getKeys("metadata", false, false).stream()
				.filter(l -> Objects.equals(config.getGeneric("metadata." + l + "." + key, type), value))
				.map(MetadataStore::stringToLoc)
				.collect(Collectors.toList());
	}
	
	private static String locToString(Location loc) {
		return loc.getWorld().getName() + "~" + loc.getBlockX() + "~" + loc.getBlockY() + "~" + loc.getBlockZ();
	}
	
	private static Location stringToLoc(String str) {
		String[] s = str.split("~");
		return new Location(Bukkit.getWorld(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
	}

}
