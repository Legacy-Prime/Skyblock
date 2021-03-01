package me.mrletsplay.skyblock.grinder;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.mrletsplay.skyblock.CustomMaterial;
import me.mrletsplay.skyblock.GUIs;
import me.mrletsplay.skyblock.MaterialManager;

public class GrinderEvents implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		CustomMaterial m;
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && (m = MaterialManager.getType(event.getClickedBlock())) != null && m == CustomMaterial.GRINDER) {
			if(event.getPlayer().isSneaking() && event.getItem() != null && event.getItem().getType() != Material.AIR) return;
			event.setCancelled(true);
			event.getPlayer().openInventory(GUIs.getGrinderGUI(event.getPlayer(), event.getClickedBlock().getLocation()));
		}
	}
	
	@EventHandler
	public void onInventory(InventoryMoveItemEvent event) {
		if(event.getSource().getLocation() != null) {
			CustomMaterial m = MaterialManager.getType(event.getSource().getLocation().getBlock());
			if(m == CustomMaterial.GRINDER) {
				event.setCancelled(true);
				return;
			}
		}
		
		if(event.getDestination().getLocation() != null) {
			CustomMaterial m = MaterialManager.getType(event.getDestination().getLocation().getBlock());
			if(m == CustomMaterial.GRINDER) {
				event.setCancelled(true);
				return;
			}
		}
	}

}
