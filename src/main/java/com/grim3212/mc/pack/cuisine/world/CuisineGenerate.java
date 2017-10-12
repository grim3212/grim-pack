package com.grim3212.mc.pack.cuisine.world;

import java.util.Random;

import com.grim3212.mc.pack.core.part.GrimWorldGen;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CuisineGenerate extends GrimWorldGen {

	@Override
	protected void generateSurface(World world, Random random, int i, int j) {
		if (CuisineConfig.generateCocoaTrees) {
			for (int k = 0; k < 10; k++) {
				int x = i + 8 + random.nextInt(16);
				int y = random.nextInt(66) + 63;
				int z = j + 8 + random.nextInt(16);
				BlockPos pos = new BlockPos(x, y, z);
				if ((world.getBlockState(pos.down()).getBlock() == Blocks.GRASS || world.getBlockState(pos.down()).getBlock() == Blocks.DIRT) && random.nextInt(10) < 1) {
					(new WorldGenCocoaTrees()).generate(world, random, pos);
				}
			}
		}
	}
}
