package com.grim3212.mc.pack.world.gen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenMelons extends WorldGenerator {

	// maximum number of melons for the chunk
	int maxNum;

	public WorldGenMelons(int i) {
		maxNum = i;
	}

	@Override
	public boolean generate(World worldIn, Random random, BlockPos pos) {
		int numMade = 0; // # of melons already placed
		for (int l = 0; l < 40; l++) {
			int i1 = (pos.getX() + random.nextInt(16)) - random.nextInt(16);
			int j1 = (pos.getY() + random.nextInt(8)) - random.nextInt(8);
			int k1 = (pos.getZ() + random.nextInt(16)) - random.nextInt(16);

			BlockPos newPos = new BlockPos(i1, j1, k1);

			if (worldIn.getBlockState(newPos).getBlock() == Blocks.air && worldIn.getBlockState(newPos.down()).getBlock() == Blocks.grass) {
				worldIn.setBlockState(newPos, Blocks.melon_block.getDefaultState());
				numMade++;
			}
			if (numMade > maxNum)
				return true;
		}
		if (numMade == 0)
			return false;
		else
			return true;
	}
}
