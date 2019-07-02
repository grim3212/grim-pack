package com.grim3212.mc.pack.core.worldgen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class DimensionFeature<F extends IFeatureConfig, D extends IPlacementConfig> extends ConfiguredFeature<F> {

	private final DimensionType dim;

	public DimensionFeature(Feature<F> featureIn, F configIn, DimensionType type) {
		super(featureIn, configIn);
		this.dim = type;
	}

	@Override
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos) {
		if (worldIn.getDimension().getType().equals(this.dim)) {
			return super.place(worldIn, generator, rand, pos);
		}
		return false;
	}

	public static <F extends IFeatureConfig, D extends IPlacementConfig> ConfiguredFeature<?> createDimensionFeature(Feature<F> featureIn, F config, Placement<D> placementIn, D placementConfig, DimensionType type) {
		Feature<DecoratedFeatureConfig> feature = featureIn instanceof FlowersFeature ? Feature.DECORATED_FLOWER : Feature.DECORATED;
		return new DimensionFeature<>(feature, new DecoratedFeatureConfig(featureIn, config, placementIn, placementConfig), type);
	}
}
