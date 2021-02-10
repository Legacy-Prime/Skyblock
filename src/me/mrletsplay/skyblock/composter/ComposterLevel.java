package me.mrletsplay.skyblock.composter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import me.mrletsplay.mrcore.misc.Probability.ProbabilityElement;

public class ComposterLevel {
	
	private int level;
	private int requiredIslandLevel;
	private Map<Material, Integer> lootWeights;
	
	private List<ProbabilityElement<Material>> probabilities;
	
	public ComposterLevel(int level, int requiredIslandLevel, Map<Material, Integer> lootWeights) {
		this.level = level;
		this.lootWeights = lootWeights;
		
		this.probabilities = new ArrayList<>();
		lootWeights.forEach((it, w) -> probabilities.add(new ProbabilityElement<>(it, w)));
	}
	
	public int getLevel() {
		return level;
	}
	
	public Map<Material, Integer> getLootWeights() {
		return lootWeights;
	}
	
	public List<ProbabilityElement<Material>> getProbabilities() {
		return probabilities;
	}
	
	public int getRequiredIslandLevel() {
		return requiredIslandLevel;
	}

}
