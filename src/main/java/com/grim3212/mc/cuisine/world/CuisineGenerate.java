package com.grim3212.mc.cuisine.world;

import java.util.Random;

import com.grim3212.mc.core.part.GrimWorldGen;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CuisineGenerate extends GrimWorldGen {

	@Override
	protected void generateSurface(World world, Random random, int i, int j) {
		for (int k = 0; k < 10; k++) {
			int l = random.nextInt(66) + 63;
			int i1 = i + random.nextInt(16);
			int j1 = j + random.nextInt(16);
			BlockPos pos = new BlockPos(i1, l, j1);
			if ((world.getBlockState(pos.down()).getBlock() == Blocks.grass || world.getBlockState(pos.down()).getBlock() == Blocks.dirt) && random.nextInt(10) < 1) {
				(new WorldGenCocoaTrees()).generate(world, random, pos);
			}
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
