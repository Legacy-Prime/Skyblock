package me.mrletsplay.skyblock.composter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

public class Composter {
	
	public static final Map<Material, Integer> COMPOSTABLE_MATERIALS = new HashMap<>();
	public static final List<ComposterLevel> LEVELS = ComposterConfig.loadLevels();
	
	static {
//		COMPOSTABLE_MATERIALS.put(Material.BEETROOT_SEEDS, 30);
//		COMPOSTABLE_MATERIALS.put(Material.DRIED_KELP, 30);
//		COMPOSTABLE_MATERIALS.put(Material.GRASS, 30);
//		COMPOSTABLE_MATERIALS.put(Material.KELP, 30);
//		COMPOSTABLE_MATERIALS.put(Material.ACACIA_LEAVES, 30);
//		COMPOSTABLE_MATERIALS.put(Material.BIRCH_LEAVES, 30);
//		COMPOSTABLE_MATERIALS.put(Material.DARK_OAK_LEAVES, 30);
//		COMPOSTABLE_MATERIALS.put(Material.JUNGLE_LEAVES, 30);
//		COMPOSTABLE_MATERIALS.put(Material.OAK_LEAVES, 30);
//		COMPOSTABLE_MATERIALS.put(Material.SPRUCE_LEAVES, 30);
//		COMPOSTABLE_MATERIALS.put(Material.MELON_SEEDS, 30);
//		COMPOSTABLE_MATERIALS.put(Material.NETHER_WART, 30);
//		COMPOSTABLE_MATERIALS.put(Material.PUMPKIN_SEEDS, 30);
//		COMPOSTABLE_MATERIALS.put(Material.ACACIA_SAPLING, 30);
//		COMPOSTABLE_MATERIALS.put(Material.BIRCH_SAPLING, 30);
//		COMPOSTABLE_MATERIALS.put(Material.DARK_OAK_SAPLING, 30);
//		COMPOSTABLE_MATERIALS.put(Material.JUNGLE_SAPLING, 30);
//		COMPOSTABLE_MATERIALS.put(Material.OAK_SAPLING, 30);
//		COMPOSTABLE_MATERIALS.put(Material.SPRUCE_SAPLING, 30);
//		COMPOSTABLE_MATERIALS.put(Material.SEAGRASS, 30);
//		COMPOSTABLE_MATERIALS.put(Material.SWEET_BERRIES, 30);
//		COMPOSTABLE_MATERIALS.put(Material.WHEAT_SEEDS, 30);
//		COMPOSTABLE_MATERIALS.put(Material.DIAMOND_BLOCK, 100);
	}
	
	public static void reload() {
		ComposterConfig.reload();
		LEVELS.clear();
		LEVELS.addAll(ComposterConfig.loadLevels());
	}

}
