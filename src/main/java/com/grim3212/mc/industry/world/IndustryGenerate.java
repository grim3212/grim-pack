package com.grim3212.mc.industry.world;

import java.util.Random;

import com.grim3212.mc.core.part.GrimWorldGen;
import com.grim3212.mc.industry.block.IndustryBlocks;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class IndustryGenerate extends GrimWorldGen {

	@Override
	protected void generateSurface(World world, Random random, int i, int j) {
		for (int k = 0; k < 18; k++) {
			int l = i + random.nextInt(17);
			int i1 = random.nextInt(17);
			int j1 = j + random.nextInt(17);
			(new WorldGenMinable(IndustryBlocks.uranium_ore.getDefaultState(), 5)).generate(world, random, new BlockPos(l, i1, j1));
		}
	}

	@Override
	protected void generateNether(World world, Random random, int i, int j) {
	}

	@Override
	protected void generateEnd(World world, Random random, int i, int j) {
	}

	@Override
	protected void generateCustom(int dimensionId, World world, Random random, int i, int j) {
	}

}
