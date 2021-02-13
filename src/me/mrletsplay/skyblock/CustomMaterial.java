package me.mrletsplay.skyblock;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum CustomMaterial {

	GRINDER(Material.FURNACE, "Grinder", "Grinds materials"),
	BLOCK_BREAKER(Material.DISPENSER, "Block Breaker", "Breaks blocks");
	
	private final Material itemMaterial;
	private final Material blockMaterial;
	private final String name;
	private final String description;
	
	private CustomMaterial(Material itemMaterial, Material blockMaterial, String name, String description) {
		this.itemMaterial = itemMaterial;
		this.blockMaterial = blockMaterial;
		this.name = name;
		this.description = description;
	}
	
	private CustomMaterial(Material material, String name, String description) {
		this(material, material, name, description);
	}
	
	public ItemStack createItem(int amount) {
		ItemStack it = new ItemStack(itemMaterial);
		ItemMeta m = it.getItemMeta();
		m.setDisplayName("§6" + name);
		m.setLore(Arrays.stream(description.split("\n")).map(s -> "§f" + s).collect(Collectors.toList()));
		it.setItemMeta(m);
		MaterialManager.applyType(it, this);
		return it;
	}
	
	public void placeBlock(Location loc) {
		loc.getBlock().setType(blockMaterial);
		MaterialManager.applyType(loc.getBlock(), this);
	}
	
	public Material getItemMaterial() {
		return itemMaterial;
	}
	
	public Material getBlockMaterial() {
		return blockMaterial;
	}
	
}
