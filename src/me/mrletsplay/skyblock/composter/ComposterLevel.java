package me.mrletsplay.skyblock.composter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import me.mrletsplay.mrcore.misc.Probability.ProbabilityElement;

public class ComposterLevel {
	
	private static final List<Material> FLOWERS = Arrays.asList(
			Material.DANDELION,
			Material.POPPY,
			Material.BLUE_ORCHID,
			Material.ALLIUM,
			Material.AZURE_BLUET,
			Material.RED_TULIP,
			Material.ORANGE_TULIP,
			Material.WHITE_TULIP,
			Material.PINK_TULIP,
			Material.OXEYE_DAISY,
			Material.CORNFLOWER,
			Material.LILY_OF_THE_VALLEY
		);
	
	private int level;
	private int requiredIslandLevel;
	private Map<String, Integer> lootWeights;
	private List<ProbabilityElement<Material>> probabilities;
	
	public ComposterLevel(int level, int requiredIslandLevel, Map<String, Integer> lootWeights) {
		this.level = level;
		this.requiredIslandLevel = requiredIslandLevel;
		this.lootWeights = lootWeights;
		
		this.probabilities = new ArrayList<>();
		lootWeights.forEach((it, w) -> {
			if(it.equalsIgnoreCase("flower")) {
				for(Material m : FLOWERS) {
					probabilities.add(new ProbabilityElement<>(m, w / (double) FLOWERS.size()));
				}
			}else {
				probabilities.add(new ProbabilityElement<>(Material.valueOf(it), w));
			}
		});
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getRequiredIslandLevel() {
		return requiredIslandLevel;
	}
	
	public Map<String, Integer> getLootWeights() {
		return lootWeights;
	}
	
	public List<ProbabilityElement<Material>> getProbabilities() {
		return probabilities;
	}
	
}
