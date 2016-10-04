package com.grim3212.mc.pack.world.gen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class BiomeBetterDesert extends Biome {

	public BiomeBetterDesert(Biome.BiomeProperties properties) {
		super(properties);
	}

	@Override
	public void decorate(World world, Random random, BlockPos pos) {
		super.decorate(world, random, pos);

		if (random.nextInt(1000) == 0) {
			int i = random.nextInt(16) + 8;
			int j = random.nextInt(16) + 8;
			BlockPos blockpos1 = world.getHeight(pos.add(i, 0, j)).up();
			(new WorldGenBetterDesertWells()).generate(world, random, blockpos1);
		}
	}
}