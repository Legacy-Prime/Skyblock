package me.mrletsplay.skyblock;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum CustomMaterial {

	GRINDER(Material.FURNACE, "§6Grinder", "§fGrinds materials"),
	BLOCK_BREAKER(Material.DISPENSER, "§6Block Breaker", "§fBreaks blocks"),
	
	// Basic spawners
	ZOMBIE_SPAWNER(ItemHelper.spawner(EntityType.ZOMBIE, "§bZombie Spawner", "§fSpawns Zombies"), Material.SPAWNER),
	SKELETON_SPAWNER(ItemHelper.spawner(EntityType.SKELETON, "§bSkeleton Spawner", "§fSpawns Skeleton"), Material.SPAWNER),
	SPIDER_SPAWNER(ItemHelper.spawner(EntityType.SPIDER, "§bSpider Spawner", "§fSpawns Spiders"), Material.SPAWNER),
	CREEPER_SPAWNER(ItemHelper.spawner(EntityType.CREEPER, "§bCreeper Spawner", "§fSpawns Creeper"), Material.SPAWNER),
	
	// End game spawners
	BLAZE_SPAWNER(ItemHelper.spawner(EntityType.BLAZE, "§5Blaze Spawner", "§fSpawns Blazes"), Material.SPAWNER),
	ENDERMAN_SPAWNER(ItemHelper.spawner(EntityType.ENDERMAN, "§5Enderman Spawner", "§fSpawns Endermen"), Material.SPAWNER),
	;
	
	private final ItemStack item;
	private final Material blockMaterial;
	
	private CustomMaterial(Material itemMaterial, String name, String description, Material blockMaterial) {
		this.item = new ItemStack(itemMaterial);
		ItemMeta m = item.getItemMeta();
		m.setDisplayName(name);
		m.setLore(Arrays.asList(description.split("\n")));
		item.setItemMeta(m);
		this.blockMaterial = blockMaterial;
	}
	
	private CustomMaterial(Material material, String name, String description) {
		this(material, name, description, material);
	}
	
	private CustomMaterial(ItemStack item, Material blockMaterial) {
		this.item = item;
		this.blockMaterial = blockMaterial;
	}
	
	public ItemStack createItem(int amount) {
		ItemStack it = item.clone();
		MaterialManager.applyType(it, this);
		return it;
	}
	
	public void placeBlock(Location loc) {
		loc.getBlock().setType(blockMaterial);
		MaterialManager.applyType(loc.getBlock(), this);
	}
	
	public Material getBlockMaterial() {
		return blockMaterial;
	}
	
}
