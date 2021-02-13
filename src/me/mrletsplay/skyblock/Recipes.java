package me.mrletsplay.skyblock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class Recipes {
	
	private static List<NamespacedKey> recipes;
	
	public static void registerRecipes() {
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
		
		Bukkit.addRecipe(new ShapedRecipe(createRecipeKey("block_breaker"), CustomMaterial.BLOCK_BREAKER.createItem(1))
				.shape("DDD", "OWO", "OWO")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('O', Material.OBSIDIAN)
				.setIngredient('W', new MaterialChoice(Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.CRIMSON_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.WARPED_PLANKS)));
	}
	
	private static NamespacedKey createRecipeKey(String key) {
		NamespacedKey k = new NamespacedKey(Skyblock.getPlugin(), key);
		recipes.add(k);
		return k;
	}
	
	public static List<NamespacedKey> getRecipes() {
		return recipes;
	}

}
