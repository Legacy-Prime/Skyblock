package me.mrletsplay.skyblock.composter;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.mrletsplay.mrcore.misc.Probability;
import me.mrletsplay.skyblock.PlayerDataStore;

public class ComposterEvents implements Listener {
	
	private static final Random RANDOM = new Random();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.COMPOSTER) {
			Levelled l = (Levelled) event.getClickedBlock().getBlockData();
			if(l.getLevel() == l.getMaximumLevel()) {
				// Drop stuff
				l.setLevel(0);
				event.getClickedBlock().setBlockData(l);
				event.getClickedBlock().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_COMPOSTER_EMPTY, SoundCategory.BLOCKS, 1, 0.8f);
				
				int selLevel = PlayerDataStore.getDataOrElse(event.getPlayer().getUniqueId(), "composter", Integer.class, 1);
				ComposterLevel lvl = Composter.LEVELS.stream()
						.filter(lv -> lv.getLevel() == selLevel)
						.findFirst().orElse(null);
				
				for(int i = 0; i < 2; i++) {
					Material m = Probability.chooseValue(lvl.getProbabilities());
					if(m != Material.AIR) event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), new ItemStack(m));
				}
				
				event.setCancelled(true);
				return;
			}
			
			if(event.getItem() != null && isCompostable(event.getItem())) {
				event.setCancelled(true);
				
				if(compost(event.getClickedBlock(), event.getItem())) {
					if(event.getPlayer().getGameMode() != GameMode.CREATIVE) event.getItem().setAmount(event.getItem().getAmount() - 1);
				}
			}
		}
	}
	
	public boolean isCompostable(ItemStack item) {
		return Composter.COMPOSTABLE_MATERIALS.containsKey(item.getType());
	}
	
	public boolean compost(Block composter, ItemStack item) {
		Integer ch = Composter.COMPOSTABLE_MATERIALS.get(item.getType());
		if(ch == null) return false;
		
		Levelled l = (Levelled) composter.getBlockData();
		if(l.getLevel() == l.getMaximumLevel() - 1) return false; // Composter is composting
		
		if(RANDOM.nextInt(100) < ch) {
			l.setLevel(l.getLevel() + 1);
			composter.setBlockData(l);
			composter.getWorld().playSound(composter.getLocation(), Sound.BLOCK_COMPOSTER_FILL_SUCCESS, SoundCategory.BLOCKS, 1, 1);
		}else {
			composter.getWorld().playSound(composter.getLocation(), Sound.BLOCK_COMPOSTER_FILL, SoundCategory.BLOCKS, 0.3f, 0.8f);
		}
		
		return true;
	}

}
