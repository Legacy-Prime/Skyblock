package me.mrletsplay.skyblock;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import me.mrletsplay.skyblock.blockbreaker.BlockBreaker;
import me.mrletsplay.skyblock.grinder.Grinder;

public class Events implements Listener {
	
	@EventHandler
	public void onHoe(PlayerInteractEvent event) {
		if(event.getItem() != null
				&& event.getItem().getType().name().endsWith("_HOE")
				&& event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& event.getClickedBlock() != null
				&& event.getClickedBlock().getType() == Material.COARSE_DIRT
				&& (event.getClickedBlock().getRelative(BlockFace.UP) == null
				|| event.getClickedBlock().getRelative(BlockFace.UP).getType() == Material.AIR)) {
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
			switch (m) {
				case GRINDER:
					Grinder.breakGrinder(event.getBlock().getLocation());
					break;
				case BLOCK_BREAKER:
					BlockBreaker.breakBlockBreaker(event.getBlock().getLocation());
					break;
				default:
					break;
			}
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
		Recipes.getRecipes().forEach(r -> event.getPlayer().discoverRecipe(r));
	}
	
	@EventHandler
	public void onWitherDeath(EntityDeathEvent e) {
		if(e.getEntityType() == EntityType.WITHER) {
			e.getDrops().add(new ItemStack(Material.MUSIC_DISC_PIGSTEP));
		}
	}

	@EventHandler
	public void onDispenserPlantSeed(BlockDispenseEvent e) {
		if(e.getBlock().getType() == Material.DISPENSER
				&& e.getItem().getType().name().endsWith("_SEEDS")) {
			Dispenser d = (Dispenser) e.getBlock().getBlockData();
			org.bukkit.block.Dispenser d2 = (org.bukkit.block.Dispenser) e.getBlock().getState();
			Block front = e.getBlock().getRelative(d.getFacing());
			if(front.getType() != Material.AIR) {
				e.setCancelled(true);
				return;
			}
			
			Block farmland = front.getRelative(BlockFace.DOWN);
			if(farmland.getType() == Material.FARMLAND) {
				Material type = null;
				switch(e.getItem().getType()) {
					case WHEAT_SEEDS:
						type = Material.WHEAT;
						break;
					case BEETROOT_SEEDS:
						type = Material.BEETROOTS;
						break;
					case MELON_SEEDS:
						type = Material.MELON_STEM;
						break;
					case PUMPKIN_SEEDS:
						type = Material.PUMPKIN_STEM;
						break;
					default:
						return;
				}
				
				front.setType(type);
				e.setCancelled(true);
				Bukkit.getScheduler().runTask(Skyblock.getPlugin(), () -> {
					d2.getInventory().remove(e.getItem());
					d2.update();
				});
			}
		}
	}
	
}
