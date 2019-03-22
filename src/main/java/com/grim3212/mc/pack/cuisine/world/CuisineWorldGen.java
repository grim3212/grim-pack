package com.grim3212.mc.pack.cuisine.world;

import com.grim3212.mc.pack.cuisine.config.CuisineConfig;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.CompositeFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class CuisineWorldGen {

	public static void initWorldGen() {
		if (CuisineConfig.generateCocoaTrees.get())
			for (Biome biome : BiomeDictionary.getBiomes(Type.FOREST)) {
				biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, new CompositeFeature<>(new CocoaTreeFeature(false, false), IFeatureConfig.NO_FEATURE_CONFIG, Biome.AT_SURFACE_WITH_EXTRA, new AtSurfaceWithExtraConfig(1, 0.1F, 1)));
			}
	}
}
