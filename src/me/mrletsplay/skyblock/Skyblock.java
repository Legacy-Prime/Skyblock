package me.mrletsplay.skyblock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import me.mrletsplay.skyblock.command.CommandComposter;
import me.mrletsplay.skyblock.command.CommandGenerator;
import me.mrletsplay.skyblock.command.CommandLPSkyblock;
import me.mrletsplay.skyblock.composter.ComposterEvents;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.level.Level;

public class Skyblock extends JavaPlugin {
	
	public static List<NamespacedKey> recipes;
	
	private static Level levelAddon;

	@Override
	public void onEnable() {
		PluginCommand lpsb = getCommand("lpskyblock");
		lpsb.setExecutor(new CommandLPSkyblock(lpsb));
		
		PluginCommand gen = getCommand("generator");
		gen.setExecutor(new CommandGenerator(gen));
		
		PluginCommand comp = getCommand("composter");
		comp.setExecutor(new CommandComposter(comp));
		
		recipes = new ArrayList<>();
		
		Iterator<Recipe> it = Bukkit.recipeIterator();
		while(it.hasNext()) {
			if(it.next().getResult().getType() == Material.COARSE_DIRT) it.remove();
		}
		
		Bukkit.addRecipe(new ShapelessRecipe(createRecipeKey("water_bucket"), new ItemStack(Material.WATER_BUCKET))
				.addIngredient(Material.BUCKET)
				.addIngredient(Material.CACTUS)
				.addIngredient(Material.POTATO)
				.addIngredient(Material.SUGAR_CANE)
				.addIngredient(new RecipeChoice.MaterialChoice(Material.WHEAT_SEEDS, Material.BEETROOT_SEEDS, Material.MELON_SEEDS, Material.PUMPKIN_SEEDS))
				.addIngredient(new RecipeChoice.MaterialChoice(Material.ACACIA_SAPLING, Material.BIRCH_SAPLING, Material.DARK_OAK_SAPLING, Material.JUNGLE_SAPLING, Material.OAK_SAPLING, Material.SPRUCE_SAPLING))
				.addIngredient(Material.APPLE)
				.addIngredient(Material.CARROT)
				.addIngredient(Material.COARSE_DIRT));
		
		Bukkit.addRecipe(new ShapedRecipe(createRecipeKey("lava_bucket"), new ItemStack(Material.LAVA_BUCKET))
				.shape("FSF", "FBF", "FCF")
				.setIngredient('F', Material.FLINT_AND_STEEL)
				.setIngredient('S', Material.COBBLESTONE)
				.setIngredient('B', Material.BUCKET)
				.setIngredient('C', Material.COAL_BLOCK));
		
		Bukkit.addRecipe(new ShapedRecipe(createRecipeKey("grinder"), CustomMaterial.GRINDER.createItem(1))
				.shape("IAI", "I I", "IPI")
				.setIngredient('I', Material.IRON_BARS)
				.setIngredient('A', Material.ANVIL)
				.setIngredient('P', Material.HEAVY_WEIGHTED_PRESSURE_PLATE));
		
		Bukkit.addRecipe(new ShapedRecipe(createRecipeKey("coarse_dirt"), new ItemStack(Material.COARSE_DIRT, 2))
				.shape("DG", "GD")
				.setIngredient('G', Material.GRAVEL)
				.setIngredient('D', Material.DIRT));
		
		Bukkit.addRecipe(new ShapedRecipe(createRecipeKey("name_tag"), new ItemStack(Material.NAME_TAG))
				.shape("  I", " P ", "P  ")
				.setIngredient('I', Material.IRON_INGOT)
				.setIngredient('P', Material.PAPER));
		
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		Bukkit.getPluginManager().registerEvents(new ComposterEvents(), this);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			for(Location l : MetadataStore.getByMetadataValue("type", String.class, CustomMaterial.GRINDER.name())) {
				ItemStack i = MetadataStore.getMetadata(l, "grinder_item", ItemStack.class);
				if(i != null && BlockGrinder.GRINDABLE_MATERIALS.containsKey(i.getType())) {
					int fuelLevel = MetadataStore.getMetadataOrDefault(l, "grinder_fuel_level", Integer.class, 0);
					if(fuelLevel <= 0) {
						ItemStack f = MetadataStore.getMetadata(l, "grinder_fuel", ItemStack.class);
						if(f != null && BlockGrinder.BURNABLE_ITEMS.containsKey(f.getType())) {
							int fuel = BlockGrinder.BURNABLE_ITEMS.get(f.getType());
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
						int p = MetadataStore.getMetadataOrDefault(l, "grinder_progress", Integer.class, 0) + 4;
						if(p >= 64) {
							// TODO: Produce item
							ItemStack out = BlockGrinder.GRINDABLE_MATERIALS.get(i.getType());
							
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
						continue;
					}
				}
				
				MetadataStore.setMetadata(l, "grinder_progress", 0);
			}
			
			GUIs.GRINDER.refreshAllInstances();
		}, 10, 10);
		
		getLogger().info("Enabled");
	}
	
	private static NamespacedKey createRecipeKey(String key) {
		NamespacedKey k = new NamespacedKey(getPlugin(), key);
		recipes.add(k);
		return k;
	}
	
	public static Skyblock getPlugin() {
		return Skyblock.getPlugin(Skyblock.class);
	}
	
	public static Level getLevelAddon() {
		if(levelAddon == null) 
			levelAddon = (Level) BentoBox.getInstance().getAddonsManager().getAddonByName("Level").get();
		return levelAddon;
	}
	
}
