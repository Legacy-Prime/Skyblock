package me.mrletsplay.skyblock;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.mrletsplay.mrcore.bukkitimpl.ItemUtils;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUI;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUIBuilder;
import me.mrletsplay.mrcore.bukkitimpl.gui.GUIElement;
import me.mrletsplay.mrcore.bukkitimpl.gui.event.GUIBuildEvent;
import me.mrletsplay.mrcore.bukkitimpl.versioned.VersionedMaterial;

public class GUIs {
	
	public static final GUI
		GRINDER = buildGrinderGUI();
	
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
	
	public static Inventory getGrinderGUI(Player p, Location grinder) {
		Map<String, Object> props = new HashMap<>();
		props.put("grinder_location", grinder);
		return GRINDER.getForPlayer(p, Skyblock.getPlugin(), props);
	}

}
