package me.mrletsplay.skyblock.blockbreaker;

import java.util.EnumSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import co.aikar.timings.Timing;
import co.aikar.timings.Timings;
import me.mrletsplay.skyblock.CustomMaterial;
import me.mrletsplay.skyblock.MaterialManager;
import me.mrletsplay.skyblock.MetadataStore;
import me.mrletsplay.skyblock.Skyblock;

public class BlockBreaker {
	
	private static final EnumSet<Material> UNBREAKABLE_BLOCKS = EnumSet.of(Material.BEDROCK, Material.PISTON_HEAD, Material.MOVING_PISTON);
	private static final EnumSet<BlockFace> FACES = EnumSet.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN);
	private static final Timing TIMING = Timings.of(Skyblock.getPlugin(), "runBlockBreakers");
	
	public static void runBlockBreakers() {
		TIMING.startTiming();
		for(Location l : MetadataStore.getByMetadataValue("type", String.class, CustomMaterial.BLOCK_BREAKER.name())) {
			if(!l.getChunk().isLoaded()) continue;
			Dispenser d = (Dispenser) l.getBlock().getBlockData();
			BlockFace f = d.getFacing();
			if(FACES.stream().anyMatch(fc -> fc != f && l.getBlock().getBlockPower(fc) > 0)) continue;
			Location toBreak = l.clone().add(f.getModX(), f.getModY(), f.getModZ());
			if(toBreak.getBlock() != null
					&& !toBreak.getBlock().isEmpty()
					&& !toBreak.getBlock().isLiquid()
					&& !UNBREAKABLE_BLOCKS.contains(toBreak.getBlock().getType())
					&& MaterialManager.getType(toBreak.getBlock()) == null) {
				Block b = toBreak.getBlock();
				ItemStack upgrade = MetadataStore.getMetadata(l, "block_breaker_upgrade", ItemStack.class);
				boolean silk = false;
				int fortune = 0;
				if(upgrade != null && upgrade.getType() == Material.ENCHANTED_BOOK) {
					EnchantmentStorageMeta m = (EnchantmentStorageMeta) upgrade.getItemMeta();
					if(m.getStoredEnchants().containsKey(Enchantment.SILK_TOUCH)) {
						silk = true;
						fortune = 0;
					}else if(m.getStoredEnchants().containsKey(Enchantment.LOOT_BONUS_BLOCKS)){
						silk = false;
						fortune = m.getStoredEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
					}
				}
				
				ItemStack it = new ItemStack(Material.NETHERITE_PICKAXE);
				if(silk) {
					it.addEnchantment(Enchantment.SILK_TOUCH, 1);
				}else if(fortune > 0) {
					it.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, fortune);
				}
				
				b.breakNaturally(it, true);
			}
		}
		TIMING.stopTiming();
	}
	
	public static void breakBlockBreaker(Location blockBreaker) {
		ItemStack upgrade = MetadataStore.getMetadata(blockBreaker, "block_breaker_upgrade", ItemStack.class);
		if(upgrade != null && upgrade.getType() != Material.AIR) blockBreaker.getWorld().dropItemNaturally(blockBreaker, upgrade);
	}

}
