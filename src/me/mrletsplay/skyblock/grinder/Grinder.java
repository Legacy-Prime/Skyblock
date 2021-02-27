package me.mrletsplay.skyblock.grinder;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.type.Furnace;
import org.bukkit.inventory.ItemStack;

import co.aikar.timings.Timing;
import co.aikar.timings.Timings;
import me.mrletsplay.skyblock.CustomMaterial;
import me.mrletsplay.skyblock.GUIs;
import me.mrletsplay.skyblock.MetadataStore;
import me.mrletsplay.skyblock.Skyblock;

public class Grinder {
	
	public static final Map<Material, ItemStack> GRINDABLE_MATERIALS = new HashMap<>();
	public static final Map<Material, Integer> BURNABLE_ITEMS = new HashMap<>();
	private static final Timing TIMING = Timings.of(Skyblock.getPlugin(), "runGrinders");
	
	static {
		GRINDABLE_MATERIALS.put(Material.COBBLESTONE, new ItemStack(Material.SAND));
		GRINDABLE_MATERIALS.put(Material.QUARTZ_BLOCK, new ItemStack(Material.QUARTZ, 4));
		GRINDABLE_MATERIALS.put(Material.GRAVEL, new ItemStack(Material.FLINT, 1));
		GRINDABLE_MATERIALS.put(Material.COAL_ORE, new ItemStack(Material.COAL, 2));
		GRINDABLE_MATERIALS.put(Material.LAPIS_ORE, new ItemStack(Material.LAPIS_LAZULI, 7));
		GRINDABLE_MATERIALS.put(Material.NETHER_QUARTZ_ORE, new ItemStack(Material.QUARTZ, 2));
		GRINDABLE_MATERIALS.put(Material.REDSTONE_ORE, new ItemStack(Material.REDSTONE, 5));
		GRINDABLE_MATERIALS.put(Material.DIAMOND_ORE, new ItemStack(Material.DIAMOND, 1));
		GRINDABLE_MATERIALS.put(Material.EMERALD_ORE, new ItemStack(Material.EMERALD, 1));
		
		BURNABLE_ITEMS.put(Material.COAL, 40);
		BURNABLE_ITEMS.put(Material.COAL_BLOCK, 40 * 9);
		BURNABLE_ITEMS.put(Material.CHARCOAL, 40);
	}
	
	public static void breakGrinder(Location grinder) {
		ItemStack input = MetadataStore.getMetadata(grinder, "grinder_item", ItemStack.class);
		ItemStack output = MetadataStore.getMetadata(grinder, "grinder_output", ItemStack.class);
		ItemStack fuel = MetadataStore.getMetadata(grinder, "grinder_fuel", ItemStack.class);
		
		if(input != null && input.getType() != Material.AIR) grinder.getWorld().dropItemNaturally(grinder, input);
		if(output != null && output.getType() != Material.AIR) grinder.getWorld().dropItemNaturally(grinder, output);
		if(fuel != null && fuel.getType() != Material.AIR) grinder.getWorld().dropItemNaturally(grinder, fuel);
	}
	
	public static void runGrinders() {
		TIMING.startTiming();
		for(Location l : MetadataStore.getByMetadataValue("type", String.class, CustomMaterial.GRINDER.name())) {
			if(!l.getChunk().isLoaded()) continue;
			
			Furnace furnace = (Furnace) l.getBlock().getBlockData();
			furnace.setLit(false);
			
			ItemStack i = MetadataStore.getMetadata(l, "grinder_item", ItemStack.class);
			if(i != null && Grinder.GRINDABLE_MATERIALS.containsKey(i.getType())) {
				int fuelLevel = MetadataStore.getMetadataOrDefault(l, "grinder_fuel_level", Integer.class, 0);
				if(fuelLevel <= 0) {
					ItemStack f = MetadataStore.getMetadata(l, "grinder_fuel", ItemStack.class);
					if(f != null && Grinder.BURNABLE_ITEMS.containsKey(f.getType())) {
						int fuel = Grinder.BURNABLE_ITEMS.get(f.getType());
						fuelLevel = fuel;
						MetadataStore.setMetadata(l, "grinder_fuel_level", fuel);
						MetadataStore.setMetadata(l, "grinder_fuel_level_max", fuel);
						
						if(f.getAmount() <= 1) {
							f = null;
						}else {
							f.setAmount(f.getAmount() - 1);
						}
						
						MetadataStore.setMetadata(l, "grinder_fuel", f);
					}
				}
				
				if(fuelLevel > 0) {
					furnace.setLit(true);
					
					int p = MetadataStore.getMetadataOrDefault(l, "grinder_progress", Integer.class, 0) + 4;
					if(p >= 64) {
						// TODO: Produce item
						ItemStack out = Grinder.GRINDABLE_MATERIALS.get(i.getType());
						
						ItemStack output = MetadataStore.getMetadata(l, "grinder_output", ItemStack.class);
						
						if(output == null || output.getType() == Material.AIR) {
							output = out;
						}else if(output.getType() == out.getType() && (output.getAmount() + out.getAmount() <= output.getMaxStackSize())) {
							output.setAmount(output.getAmount() + out.getAmount());
						}else {
							continue; // Something is blocking the output slot. Wait for it to be removed
						}
						
						MetadataStore.setMetadata(l, "grinder_output", output);

						if(i.getAmount() <= 1) {
							i = null;
						}else {
							i.setAmount(i.getAmount() - 1);
						}
						
						MetadataStore.setMetadata(l, "grinder_item", i);
						
						p = 0;
					}
					
					MetadataStore.setMetadata(l, "grinder_fuel_level", fuelLevel - 1);
					MetadataStore.setMetadata(l, "grinder_progress", p);
					
					l.getBlock().setBlockData(furnace);
					continue;
				}
			}
			
			l.getBlock().setBlockData(furnace);

			if(MetadataStore.getMetadataOrDefault(l, "grinder_progress", Integer.class, 0) > 0) MetadataStore.setMetadata(l, "grinder_progress", 0);
		}
		
		GUIs.GRINDER.refreshAllInstances();
		TIMING.stopTiming();
	}
	
}
