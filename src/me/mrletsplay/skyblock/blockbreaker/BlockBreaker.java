package me.mrletsplay.skyblock.blockbreaker;

import java.util.EnumSet;
import java.util.List;

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
	
	private static final EnumSet<Material> UNBREAKABLE_BLOCKS = EnumSet.of(Material.BEDROCK, Material.PISTON_HEAD, Material.MOVING_PISTON, Material.END_PORTAL_FRAME, Material.END_PORTAL);
	private static final EnumSet<Material> BASIC_DISALLOWED_BLOCKS = EnumSet.of(Material.COAL_BLOCK, Material.IRON_BLOCK, Material.GOLD_BLOCK, Material.LAPIS_BLOCK, Material.DIAMOND_BLOCK, Material.SOUL_SAND, Material.NETHERRACK, Material.MAGMA_BLOCK, Material.GRAVEL, Material.BASALT, Material.NETHER_GOLD_ORE, Material.NETHER_QUARTZ_ORE, Material.ANCIENT_DEBRIS, Material.GLOWSTONE, Material.BLACKSTONE, Material.WARPED_NYLIUM, Material.CRIMSON_NYLIUM);
	private static final EnumSet<BlockFace> FACES = EnumSet.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN);
	private static final Timing TIMING = Timings.of(Skyblock.getPlugin(), "runBlockBreakers");
	
	public static void runBlockBreakers() {
		TIMING.startTiming();
		List<Location> loc = MetadataStore.getByMetadataValue("type", String.class, CustomMaterial.BLOCK_BREAKER.name());
		loc.addAll(MetadataStore.getByMetadataValue("type", String.class, CustomMaterial.ADVANCED_BLOCK_BREAKER.name()));
		
		for(Location l : loc) {
			if(!l.getChunk().isLoaded()) continue;
			if(!(l.getBlock().getBlockData() instanceof Dispenser)) {
				MetadataStore.unsetMetadata(l);
				continue;
			}
			
			CustomMaterial type = MaterialManager.getType(l.getBlock());
			
			Dispenser d = (Dispenser) l.getBlock().getBlockData();
			BlockFace f = d.getFacing();
			if(FACES.stream().anyMatch(fc -> fc != f && l.getBlock().getBlockPower(fc) > 0)) continue;
			Location toBreak = l.clone().add(f.getModX(), f.getModY(), f.getModZ());
			if(toBreak.getBlock() != null
					&& !toBreak.getBlock().isEmpty()
					&& !toBreak.getBlock().isLiquid()
					&& !UNBREAKABLE_BLOCKS.contains(toBreak.getBlock().getType())
					&& (type == CustomMaterial.ADVANCED_BLOCK_BREAKER || !BASIC_DISALLOWED_BLOCKS.contains(toBreak.getBlock().getType()))
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
