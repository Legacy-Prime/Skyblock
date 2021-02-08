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
		BURNABLE_ITEMS.put(Material.COAL, 40);
		BURNABLE_ITEMS.put(Material.CHARCOAL, 40);
	}
	
}
