package me.mrletsplay.skyblock.blockbreaker;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import io.papermc.paper.event.block.BlockFailedDispenseEvent;
import me.mrletsplay.skyblock.CustomMaterial;
import me.mrletsplay.skyblock.GUIs;
import me.mrletsplay.skyblock.MaterialManager;

public class BlockBreakerEvents implements Listener {
	
	@EventHandler
	public void onFailedDispense(BlockFailedDispenseEvent event) {
		if(MaterialManager.getType(event.getBlock()) == CustomMaterial.BLOCK_BREAKER) {
			event.shouldPlayEffect(false);
		}
	}
	
	@EventHandler
	public void onDispense(BlockDispenseEvent event) {
		if(MaterialManager.getType(event.getBlock()) == CustomMaterial.BLOCK_BREAKER) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onHopper(InventoryMoveItemEvent event) {
		if((event.getSource().getLocation() != null && MaterialManager.getType(event.getSource().getLocation().getBlock()) == CustomMaterial.BLOCK_BREAKER)
				|| (event.getDestination().getLocation() != null && MaterialManager.getType(event.getSource().getLocation().getBlock()) == CustomMaterial.BLOCK_BREAKER)) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		CustomMaterial m;
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && (m = MaterialManager.getType(event.getClickedBlock())) != null && m == CustomMaterial.BLOCK_BREAKER) {
			if(event.getPlayer().isSneaking() && event.getItem() != null && event.getItem().getType() != Material.AIR) return;
			event.setCancelled(true);
			event.getPlayer().openInventory(GUIs.getBlockBreakerGUI(event.getPlayer(), event.getClickedBlock().getLocation()));
		}
	}

}
