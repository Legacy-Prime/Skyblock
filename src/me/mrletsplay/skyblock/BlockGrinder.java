package me.mrletsplay.skyblock;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BlockGrinder {
	
	public static final Map<Material, ItemStack> GRINDABLE_MATERIALS = new HashMap<>();
	public static final Map<Material, Integer> BURNABLE_ITEMS = new HashMap<>();
	
	static {
		GRINDABLE_MATERIALS.put(Material.COBBLESTONE, new ItemStack(Material.SAND));
		GRINDABLE_MATERIALS.put(Material.QUARTZ_BLOCK, new ItemStack(Material.QUARTZ, 4));
		GRINDABLE_MATERIALS.put(Material.GRAVEL, new ItemStack(Material.FLINT, 1));
		GRINDABLE_MATERIALS.put(Material.COAL_ORE, new ItemStack(Material.COAL, 2));
		GRINDABLE_MATERIALS.put(Material.LAPIS_ORE, new ItemStack(Material.LAPIS_LAZULI, 7));
		GRINDABLE_MATERIALS.put(Material.NETHER_QUARTZ_ORE, new ItemStack(Material.NETHER_QUARTZ_ORE, 2));
		GRINDABLE_MATERIALS.put(Material.REDSTONE_ORE, new ItemStack(Material.NETHER_QUARTZ_ORE, 5));
		GRINDABLE_MATERIALS.put(Material.DIAMOND_ORE, new ItemStack(Material.DIAMOND, 1));
		GRINDABLE_MATERIALS.put(Material.EMERALD_ORE, new ItemStack(Material.EMERALD, 1));
		
		BURNABLE_ITEMS.put(Material.COAL, 40);
		BURNABLE_ITEMS.put(Material.CHARCOAL, 40);
	}
	
}
