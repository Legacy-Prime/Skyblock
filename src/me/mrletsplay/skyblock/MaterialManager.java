package me.mrletsplay.skyblock;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.mrletsplay.mrcore.misc.FriendlyException;

public class MaterialManager {
	
	private static final NamespacedKey
		TYPE = new NamespacedKey(Skyblock.getPlugin(Skyblock.class), "item_type");
	
	public static void applyType(ItemStack item, CustomMaterial type) {
		if(item == null || item.getItemMeta() == null) throw new FriendlyException("Item must have an ItemMeta");
		ItemMeta m = item.getItemMeta();
		m.getPersistentDataContainer().set(TYPE, PersistentDataType.STRING, type.name());
		item.setItemMeta(m);
	}
	
	public static CustomMaterial getType(ItemStack item) {
		if(item == null || item.getItemMeta() == null) return null;
		String name = item.getItemMeta().getPersistentDataContainer().get(TYPE, PersistentDataType.STRING);
		if(name == null) return null;
		try {
			return CustomMaterial.valueOf(name);
		}catch(IllegalArgumentException e) {
			return null;
		}
	}
	
	public static void applyType(Block block, CustomMaterial type) {
		MetadataStore.setMetadata(block, "type", type.name());
	}
	
	public static CustomMaterial getType(Block block) {
		String t = MetadataStore.getMetadata(block, "type", String.class);
		if(t == null) return null;
		try {
			return CustomMaterial.valueOf(t);
		}catch(IllegalArgumentException e) {
			return null;
		}
	}

}
