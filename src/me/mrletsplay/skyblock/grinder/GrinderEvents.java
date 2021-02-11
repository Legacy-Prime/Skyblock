package me.mrletsplay.skyblock.grinder;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

import me.mrletsplay.skyblock.CustomMaterial;
import me.mrletsplay.skyblock.GUIs;
import me.mrletsplay.skyblock.MaterialManager;

public class GrinderEvents implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		CustomMaterial m;
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && !event.getPlayer().isSneaking() && (m = MaterialManager.getType(event.getClickedBlock())) != null && m == CustomMaterial.GRINDER) {
			event.setCancelled(true);
			event.getPlayer().openInventory(GUIs.getGrinderGUI(event.getPlayer(), event.getClickedBlock().getLocation()));
		}
	}
	
	@EventHandler
	public void onInventory(InventoryMoveItemEvent event) {
		Location l = event.getDestination().getLocation();
		if(l != null && event.getDestination().getType() == InventoryType.HOPPER) {
			l.add(0, 1, 0);
			if(MaterialManager.getType(l.getBlock()) == CustomMaterial.GRINDER) {
				System.out.println("hey ho");
				event.setCancelled(true);
			}
		}
	}

}
