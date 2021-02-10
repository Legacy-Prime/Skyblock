package me.mrletsplay.skyblock;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class Events implements Listener {
	
	@EventHandler
	public void onHoe(PlayerInteractEvent event) {
		if(event.getItem() != null && event.getItem().getType().name().endsWith("_HOE")
				&& event.getClickedBlock().getType() == Material.COARSE_DIRT) {
			event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), new ItemStack(Material.GRAVEL));
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if(event.getBlock().getState() instanceof CreatureSpawner && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setExpToDrop(0);
			ItemStack i = new ItemStack(Material.SPAWNER);
			BlockStateMeta m = (BlockStateMeta) i.getItemMeta();
			m.setBlockState(event.getBlock().getState());
			i.setItemMeta(m);
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), i);
		}
		
		CustomMaterial m;
		if((m = MaterialManager.getType(event.getBlock())) != null) {
			event.setDropItems(false);
			event.setExpToDrop(0);
			if(m == CustomMaterial.GRINDER) BlockGrinder.breakGrinder(event.getBlock().getLocation());
			if(event.getPlayer().getGameMode() != GameMode.CREATIVE) event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), m.createItem(1));
		}
		
		MetadataStore.unsetMetadata(event.getBlock());
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if(event.getItemInHand().getType() == Material.SPAWNER && event.getBlock().getType() == Material.SPAWNER) {
			BlockStateMeta m = (BlockStateMeta) event.getItemInHand().getItemMeta();
			CreatureSpawner i = (CreatureSpawner) m.getBlockState();
			CreatureSpawner b = (CreatureSpawner) event.getBlock().getState();
			b.setSpawnedType(i.getSpawnedType());
			b.update();
		}
		
		CustomMaterial m;
		if((m = MaterialManager.getType(event.getItemInHand())) != null
				&& event.getBlock().getType() == m.getBlockMaterial()) {
			MaterialManager.applyType(event.getBlock(), m);
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Skyblock.recipes.forEach(r -> event.getPlayer().discoverRecipe(r));
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		CustomMaterial m;
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && (m = MaterialManager.getType(event.getClickedBlock())) != null && m == CustomMaterial.GRINDER) {
			event.setCancelled(true);
			event.getPlayer().openInventory(GUIs.getGrinderGUI(event.getPlayer(), event.getClickedBlock().getLocation()));
		}
	}
	
	@EventHandler
	public void onHarvest(PlayerHarvestBlockEvent event) {
		System.out.println("HARVEST");
		System.out.println(event.getHarvestedBlock());
	}
	
}
