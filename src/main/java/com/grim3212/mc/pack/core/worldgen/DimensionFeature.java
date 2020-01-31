package com.grim3212.mc.pack.core.worldgen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class DimensionFeature<FC extends IFeatureConfig, F extends Feature<FC>> extends ConfiguredFeature<FC, F> {

	private final DimensionType dim;

	public DimensionFeature(ConfiguredFeature<FC, F> parent, DimensionType type) {
		super(parent.feature, parent.config);
		this.dim = type;
	}

	@Override
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos) {
		if (worldIn.getDimension().getType().equals(this.dim)) {
			return super.place(worldIn, generator, rand, pos);
		}
		return false;
	}
}
