package me.mrletsplay.skyblock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.mrletsplay.mrcore.bukkitimpl.ItemUtils;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUI;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUIBuilder;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUIElement;
import me.mrletsplay.mrcore.bukkitimpl.gui.event.GUIBuildEvent;
import me.mrletsplay.mrcore.bukkitimpl.versioned.VersionedMaterial;
import me.mrletsplay.skyblock.composter.Composter;
import me.mrletsplay.skyblock.composter.ComposterLevel;
import world.bentobox.bentobox.BentoBox;

public class GUIs {
	
	public static GUI
		GRINDER,
		COMPOSTER_SELECT;
	
	static {
		loadGUIs();
	}
	
	public static void loadGUIs() {
		GRINDER = buildGrinderGUI();
		COMPOSTER_SELECT = buildComposterSelectGUI();
	}
	
	private static GUI buildGrinderGUI() {
		GUIBuilder b = new GUIBuilder("§6Grinder", 5);
		for(int i = 0; i < 5*9; i++) {
			final int fI = i;
			b.addElement(i, new GUIElement() {
				
				@Override
				public ItemStack getItem(GUIBuildEvent event) {
					Location grinder = (Location) event.getGUIHolder().getProperty(Skyblock.getPlugin(), "grinder_location");

					int x = fI % 9;
					int y = Math.floorDiv(fI, 9);
					
					if(x != 8) {
						int progress = MetadataStore.getMetadataOrDefault(grinder, "grinder_progress", Integer.class, 0);
						boolean green = x / 8D < progress / 64D;
						return ItemUtils.createItem(green ? VersionedMaterial.GREEN_STAINED_GLASS_PANE : VersionedMaterial.BLACK_STAINED_GLASS_PANE, 1, "§0");
					}else {
						int level = MetadataStore.getMetadataOrDefault(grinder, "grinder_fuel_level", Integer.class, 0);
						int levelMax = MetadataStore.getMetadataOrDefault(grinder, "grinder_fuel_level_max", Integer.class, 1);
						boolean orange = (4 - y) / 5D < level / (double) levelMax;
						return ItemUtils.createItem(orange ? VersionedMaterial.ORANGE_STAINED_GLASS_PANE : VersionedMaterial.GRAY_STAINED_GLASS_PANE, 1, "§0");
					}
				}
			});
		}
		
		b.addElement(10, new GUIElement() {
			
			@Override
			public ItemStack getItem(GUIBuildEvent event) {
				Location grinder = (Location) event.getGUIHolder().getProperty(Skyblock.getPlugin(), "grinder_location");
				return MetadataStore.getMetadata(grinder, "grinder_item", ItemStack.class);
			}
		});
		
		b.addElement(28, new GUIElement() {
			
			@Override
			public ItemStack getItem(GUIBuildEvent event) {
				Location grinder = (Location) event.getGUIHolder().getProperty(Skyblock.getPlugin(), "grinder_location");
				return MetadataStore.getMetadata(grinder, "grinder_fuel", ItemStack.class);
			}
		});
		
		b.addElement(24, new GUIElement() {
			
			@Override
			public ItemStack getItem(GUIBuildEvent event) {
				Location grinder = (Location) event.getGUIHolder().getProperty(Skyblock.getPlugin(), "grinder_location");
				return MetadataStore.getMetadata(grinder, "grinder_output", ItemStack.class);
			}
		});
		
		b.setDragDropListener(event -> {
			event.setCancelled(false);
		});
		
		b.setActionListener(event -> {
			if(event.getEvent().getAction() != InventoryAction.PICKUP_ALL
					&& event.getEvent().getAction() != InventoryAction.PLACE_ALL
					&& event.getEvent().getAction() != InventoryAction.SWAP_WITH_CURSOR) return;
			
			if(event.getEvent().getSlot() == 10 || event.getEvent().getSlot() == 28) {
				Location grinder = (Location) event.getGUIHolder().getProperty(Skyblock.getPlugin(), "grinder_location");
				MetadataStore.setMetadata(grinder, event.getEvent().getSlot() == 10 ? "grinder_item" : "grinder_fuel", event.getItemClickedWith());
				
				event.setCancelled(false);
			}
			
			if(event.getEvent().getSlot() == 24 && event.getEvent().getAction() == InventoryAction.PICKUP_ALL) {
				Location grinder = (Location) event.getGUIHolder().getProperty(Skyblock.getPlugin(), "grinder_location");
				MetadataStore.setMetadata(grinder, "grinder_output", null);
				event.setCancelled(false);
			}
		});
		
		return b.create();
	}
	
	private static GUI buildComposterSelectGUI() {
		GUIBuilder b = new GUIBuilder("§6Composter", 1);
		int i = 0;
		for(ComposterLevel l : Composter.LEVELS) {
			b.addElement(i, new GUIElement() {
				
				@Override
				public ItemStack getItem(GUIBuildEvent event) {
					Player p = event.getPlayer();
					UUID ownerID = BentoBox.getInstance().getIslands().getIsland(Bukkit.getWorld("bskyblock_world"), p.getUniqueId()).getOwner();
					long iL = Skyblock.getIslandLevel(Bukkit.getWorld("bskyblock_world"), p.getUniqueId());
					return createIcon(l, iL < l.getRequiredIslandLevel(), PlayerDataStore.getDataOrElse(ownerID, "composter", Integer.class, 1) == l.getLevel());
				}
			}.setAction(event -> {
				Player p = event.getPlayer();
				long iL = Skyblock.getIslandLevel(Bukkit.getWorld("bskyblock_world"), p.getUniqueId());
				if(iL < l.getRequiredIslandLevel()) return;
				
				UUID ownerID = BentoBox.getInstance().getIslands().getIsland(Bukkit.getWorld("bskyblock_world"), p.getUniqueId()).getOwner();
				PlayerDataStore.setData(ownerID, "composter", l.getLevel());
				
				COMPOSTER_SELECT.refreshAllInstances();
			}));
			
			i++;
		}
		return b.create();
	}
	
	private static ItemStack createIcon(ComposterLevel level, boolean locked, boolean selected) {
		ItemStack it = new ItemStack(locked ? Material.BARRIER : Material.COMPOSTER);
		ItemMeta m = it.getItemMeta();
		m.setDisplayName("§7Composter Lvl. " + level.getLevel());
		List<String> lore = new ArrayList<>();
		
		int sum = level.getLootWeights().values().stream().mapToInt(i -> i).sum();
		System.out.println(sum);
		
		lore.add("§7§lLoot:");
		
		List<Map.Entry<String, Integer>> entries = new ArrayList<>(level.getLootWeights().entrySet());
		Collections.sort(entries, Comparator.comparingInt((Map.Entry<String, Integer> en) -> en.getValue()).reversed());
		entries.forEach(en -> {
			BigDecimal d = BigDecimal.valueOf(en.getValue())
					.multiply(BigDecimal.valueOf(100))
					.divide(BigDecimal.valueOf(sum), 5, RoundingMode.HALF_UP)
					.setScale(2, RoundingMode.HALF_UP);
			
			lore.add("§8" + materialName(en.getKey()) + " - " + d.toString() + "%");
		});
		
		if(locked) {
			lore.add("§c§lRequired Level: " + level.getRequiredIslandLevel());
			lore.add("§cLocked!");
		}
		
		if(selected) {
			m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			m.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			lore.add("§2Active");
		}
		
		m.setLore(lore);
		it.setItemMeta(m);
		
		return it;
	}
	
	private static String materialName(String m) {
		String s = m.replace('_', ' ').toLowerCase();
		s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
		return s;
	}
	
	public static Inventory getGrinderGUI(Player p, Location grinder) {
		Map<String, Object> props = new HashMap<>();
		props.put("grinder_location", grinder);
		return GRINDER.getForPlayer(p, Skyblock.getPlugin(), props);
	}
	
	public static Inventory getComposterSelectGUI(Player p) {
		return COMPOSTER_SELECT.getForPlayer(p);
	}

}
