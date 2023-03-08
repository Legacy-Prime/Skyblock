package me.mrletsplay.skyblock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.RecipeChoice.ExactChoice;
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
		
		ShapedRecipe blockBreaker = new ShapedRecipe(createRecipeKey("block_breaker"), CustomMaterial.BLOCK_BREAKER.createItem(1))
				.shape("DDD", "OWO", "OWO")
				.setIngredient('D', Material.DIAMOND_BLOCK)
				.setIngredient('O', Material.OBSIDIAN)
				.setIngredient('W', new MaterialChoice(Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.CRIMSON_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.WARPED_PLANKS));
		blockBreaker.setGroup("block_breaker");
		Bukkit.addRecipe(blockBreaker);
		
		ShapedRecipe advancedBlockBreaker = new ShapedRecipe(createRecipeKey("advanced_block_breaker"), CustomMaterial.ADVANCED_BLOCK_BREAKER.createItem(1))
				.shape("NNN", "OWO", "OWO")
				.setIngredient('N', Material.NETHERITE_BLOCK)
				.setIngredient('O', CustomMaterial.BLOCK_BREAKER.createItem(1))
				.setIngredient('W', new ExactChoice(Arrays.asList(CustomMaterial.ENCHANTED_CRIMSON_PLANKS.createItem(1), CustomMaterial.ENCHANTED_WARPED_PLANKS.createItem(1))));
		advancedBlockBreaker.setGroup("block_breaker");
		Bukkit.addRecipe(advancedBlockBreaker);
		
		Bukkit.addRecipe(new ShapedRecipe(createRecipeKey("soul_soil"), new ItemStack(Material.SOUL_SOIL, 1))
				.shape("SS", "SS")
				.setIngredient('S', Material.SOUL_SAND));
		
		Bukkit.addRecipe(new ShapedRecipe(createRecipeKey("elytra"), new ItemStack(Material.ELYTRA))
				.shape("L L", "NSN", "L L")
				.setIngredient('L', Material.LEATHER)
				.setIngredient('N', Material.NETHER_STAR)
				.setIngredient('S', Material.STICK));
		
		Bukkit.addRecipe(new ShapedRecipe(createRecipeKey("end_stone"), new ItemStack(Material.END_STONE, 2))
				.shape("SQ", "QS")
				.setIngredient('S', Material.SMOOTH_STONE)
				.setIngredient('Q', Material.QUARTZ_BLOCK));
		
		ShapedRecipe crimson = new ShapedRecipe(createRecipeKey("enchanted_crimson_planks"), CustomMaterial.ENCHANTED_CRIMSON_PLANKS.createItem(8))
				.shape("CCC", "CSC", "CCC")
				.setIngredient('C', Material.CRIMSON_PLANKS)
				.setIngredient('S', Material.NETHER_STAR);
		crimson.setGroup("enchanted_planks");
		Bukkit.addRecipe(crimson);
		
		ShapedRecipe warped = new ShapedRecipe(createRecipeKey("enchanted_warped_planks"), CustomMaterial.ENCHANTED_WARPED_PLANKS.createItem(8))
			.shape("WWW", "WSW", "WWW")
			.setIngredient('W', Material.WARPED_PLANKS)
			.setIngredient('S', Material.NETHER_STAR);
		warped.setGroup("enchanted_planks");
		Bukkit.addRecipe(warped);
		
		registerSpawnerRecipe(CustomMaterial.ZOMBIE_SPAWNER, Material.ROTTEN_FLESH, false);
		registerSpawnerRecipe(CustomMaterial.SKELETON_SPAWNER, Material.BONE, false);
		registerSpawnerRecipe(CustomMaterial.SPIDER_SPAWNER, Material.SPIDER_EYE, false);
		registerSpawnerRecipe(CustomMaterial.CREEPER_SPAWNER, Material.GUNPOWDER, false);
		
		registerSpawnerRecipe(CustomMaterial.BLAZE_SPAWNER, Material.BLAZE_ROD, true);
		registerSpawnerRecipe(CustomMaterial.ENDERMAN_SPAWNER, Material.ENDER_PEARL, true);
		
		Bukkit.addRecipe(new ShapelessRecipe(createRecipeKey("slime_ball"), new ItemStack(Material.SLIME_BALL, 1))
				.addIngredient(Material.WATER_BUCKET)
				.addIngredient(Material.MAGMA_CREAM));
		
		Bukkit.addRecipe(new ShapedRecipe(createRecipeKey("blaze_rod"), new ItemStack(Material.BLAZE_ROD, 1))
				.shape("CGC", "LSL", "CGC")
				.setIngredient('C', Material.MAGMA_CREAM)
				.setIngredient('G', Material.GOLD_BLOCK)
				.setIngredient('L', Material.LAVA_BUCKET)
				.setIngredient('S', Material.STICK));
	}
	
	private static void registerSpawnerRecipe(CustomMaterial spawnerType, Material recipeType, boolean endGame) {
		ShapedRecipe r = new ShapedRecipe(createRecipeKey(spawnerType.name().toLowerCase()), spawnerType.createItem(1))
			.shape("nDn", "ITI", "NBN")
			.setIngredient('n', Material.NETHERITE_INGOT)
			.setIngredient('T', recipeType)
			.setIngredient('B', Material.BEACON);
		
		if(endGame) {
			r.setIngredient('N', Material.NETHERITE_BLOCK);
			r.setIngredient('D', Material.MUSIC_DISC_PIGSTEP);
			r.setIngredient('I', Material.BEACON);
		}else {
			r.setIngredient('N', Material.CRYING_OBSIDIAN);
			r.setIngredient('D', new MaterialChoice(Material.MUSIC_DISC_11, Material.MUSIC_DISC_13, Material.MUSIC_DISC_BLOCKS, Material.MUSIC_DISC_CAT, Material.MUSIC_DISC_CHIRP, Material.MUSIC_DISC_FAR, Material.MUSIC_DISC_MALL, Material.MUSIC_DISC_MELLOHI, Material.MUSIC_DISC_STAL, Material.MUSIC_DISC_STRAD, Material.MUSIC_DISC_WAIT, Material.MUSIC_DISC_WARD, Material.MUSIC_DISC_PIGSTEP));
			r.setIngredient('I', Material.IRON_BARS);
		}
		
		r.setGroup(endGame ? "end_game_spawner" : "spawner");
		
		Bukkit.addRecipe(r);
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
