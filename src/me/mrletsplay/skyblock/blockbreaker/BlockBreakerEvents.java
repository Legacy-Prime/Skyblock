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
		CustomMaterial m = MaterialManager.getType(event.getBlock());
		if(m == CustomMaterial.BLOCK_BREAKER || m == CustomMaterial.ADVANCED_BLOCK_BREAKER) {
			event.shouldPlayEffect(false);
		}
	}
	
	@EventHandler
	public void onDispense(BlockDispenseEvent event) {
		CustomMaterial m = MaterialManager.getType(event.getBlock());
		if(m == CustomMaterial.BLOCK_BREAKER || m == CustomMaterial.ADVANCED_BLOCK_BREAKER) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onHopper(InventoryMoveItemEvent event) {
		if(event.getSource().getLocation() != null) {
			CustomMaterial m = MaterialManager.getType(event.getSource().getLocation().getBlock());
			if(m == CustomMaterial.BLOCK_BREAKER || m == CustomMaterial.ADVANCED_BLOCK_BREAKER) {
				event.setCancelled(true);
				return;
			}
		}
		
		if(event.getDestination().getLocation() != null) {
			CustomMaterial m = MaterialManager.getType(event.getDestination().getLocation().getBlock());
			if(m == CustomMaterial.BLOCK_BREAKER || m == CustomMaterial.ADVANCED_BLOCK_BREAKER) {
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		CustomMaterial m;
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& (m = MaterialManager.getType(event.getClickedBlock())) != null
				&& (m == CustomMaterial.BLOCK_BREAKER || m == CustomMaterial.ADVANCED_BLOCK_BREAKER)) {
			if(event.getPlayer().isSneaking() && event.getItem() != null && event.getItem().getType() != Material.AIR) return;
			event.setCancelled(true);
			event.getPlayer().openInventory(GUIs.getBlockBreakerGUI(event.getPlayer(), event.getClickedBlock().getLocation(), m == CustomMaterial.ADVANCED_BLOCK_BREAKER));
		}
	}

}
