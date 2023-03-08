package me.mrletsplay.skyblock;

import java.io.File;
import java.util.UUID;

import me.mrletsplay.mrcore.bukkitimpl.config.BukkitCustomConfig;
import me.mrletsplay.mrcore.config.ConfigLoader;

public class PlayerDataStore {
	
	private static BukkitCustomConfig config;
	
	static {
		File f = new File(Skyblock.getPlugin().getDataFolder(), "playerdata.yml");
		config = ConfigLoader.loadConfigFromFile(new BukkitCustomConfig(f), f);
	}
	
	public static void setData(UUID ownerID, String key, Object value) {
		config.set("data." + ownerID.toString() + "." + key, value);
		config.saveToFile();
	}
	
	public static <T> T getData(UUID ownerID, String key, Class<T> type) {
		return config.getGeneric("data." + ownerID.toString() + "." + key, type);
	}
	
	public static <T> T getDataOrElse(UUID ownerID, String key, Class<T> type, T defaultValue) {
		return config.getGeneric("data." + ownerID.toString() + "." + key, type, defaultValue, false);
	}

}
