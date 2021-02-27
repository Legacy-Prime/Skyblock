package me.mrletsplay.skyblock;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemHelper {
	
	public static ItemStack spawner(EntityType type) {
		ItemStack spawner = new ItemStack(Material.SPAWNER);
		BlockStateMeta m = (BlockStateMeta) spawner.getItemMeta();
		CreatureSpawner s = (CreatureSpawner) m.getBlockState();
		s.setSpawnedType(type);
		m.setBlockState(s);
		spawner.setItemMeta(m);
		return spawner;
	}
	
	public static ItemStack spawner(EntityType type, String name, String description) {
		ItemStack spawner = spawner(type);
		ItemMeta m = spawner.getItemMeta();
		m.setDisplayName(name);
		m.setLore(Arrays.asList(description.split("\n")));
		spawner.setItemMeta(m);
		return spawner;
	}
	
	public static ItemStack enchanted(Material type, String name, String description) {
		ItemStack it = new ItemStack(type);
		it.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		it.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		ItemMeta m = it.getItemMeta();
		m.setDisplayName(name);
		m.setLore(Arrays.asList(description.split("\n")));
		it.setItemMeta(m);
		return it;
	}

}
