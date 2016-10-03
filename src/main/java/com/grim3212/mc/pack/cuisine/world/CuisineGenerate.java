package com.grim3212.mc.pack.cuisine.world;

import java.util.Random;

import com.grim3212.mc.pack.core.part.GrimWorldGen;
<<<<<<< HEAD
=======
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CuisineGenerate extends GrimWorldGen {

	@Override
	protected void generateSurface(World world, Random random, int i, int j) {
<<<<<<< HEAD
		for (int k = 0; k < 10; k++) {
			int l = random.nextInt(66) + 63;
			int i1 = i + random.nextInt(16);
			int j1 = j + random.nextInt(16);
			BlockPos pos = new BlockPos(i1, l, j1);
			if ((world.getBlockState(pos.down()).getBlock() == Blocks.GRASS || world.getBlockState(pos.down()).getBlock() == Blocks.DIRT) && random.nextInt(10) < 1) {
				(new WorldGenCocoaTrees()).generate(world, random, pos);
=======
		if (CuisineConfig.generateCocoaTrees) {
			for (int k = 0; k < 10; k++) {
				int l = random.nextInt(66) + 63;
				int i1 = i + random.nextInt(16);
				int j1 = j + random.nextInt(16);
				BlockPos pos = new BlockPos(i1, l, j1);
				if ((world.getBlockState(pos.down()).getBlock() == Blocks.GRASS || world.getBlockState(pos.down()).getBlock() == Blocks.DIRT) && random.nextInt(10) < 1) {
					(new WorldGenCocoaTrees()).generate(world, random, pos);
				}
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
			}
		}
	}
}
