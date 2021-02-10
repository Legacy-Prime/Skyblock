package me.mrletsplay.skyblock.composter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import me.mrletsplay.mrcore.config.ConfigLoader;
import me.mrletsplay.mrcore.config.FileCustomConfig;
import me.mrletsplay.skyblock.Skyblock;

public class ComposterConfig {
	
	private static FileCustomConfig config;
	
	static {
		config = ConfigLoader.loadFileConfig(new File(Skyblock.getPlugin().getDataFolder(), "composter.yml"));
		
		if(config.isEmpty()) {
			config.addDefault("output.1.loot.PAPER", 10);
			config.addDefault("output.1.required-island-level", 5);
			config.applyDefaults();
			config.saveToFile();
		}
	}
	
	public static void reload() {
		config.reload(false);
	}
	
	public static List<ComposterLevel> loadLevels() {
		List<ComposterLevel> ls = new ArrayList<>();
		for(String k : config.getKeys("output", false, false)) {
			int l = Integer.parseInt(k);
			
			Map<Material, Integer> loot = new HashMap<>();
			for(String m : config.getKeys("output.loot." + k)) {
				Material mat = Material.valueOf(m);
				if(mat == null) {
					Skyblock.getPlugin().getLogger().severe("Invalid material in config: " + m);
					continue;
				}
				loot.put(mat, config.getInt("output." + k + ".loot." + m));
			}
			
			ls.add(new ComposterLevel(l, config.getInt("output." + k + ".required-island-level"), loot));
		}
		
		return ls;
	}

}
