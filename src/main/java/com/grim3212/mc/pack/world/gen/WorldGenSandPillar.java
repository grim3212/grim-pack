package com.grim3212.mc.pack.world.gen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSandPillar extends WorldGenerator {

	private final int size;

	public WorldGenSandPillar(int size) {
		this.size = size;
	}

	@Override
	public boolean generate(World worldIn, Random random, BlockPos pos) {
		int maxAttempts = 2;
		for (int l = 0; l < maxAttempts; l++) {
			int i1 = (pos.getX() + random.nextInt(8)) - random.nextInt(8);
			int j1 = (pos.getY() + random.nextInt(4)) - random.nextInt(4);
			int k1 = (pos.getZ() + random.nextInt(8)) - random.nextInt(8);

			BlockPos newPos = new BlockPos(i1, j1, k1);

			if (worldIn.getBlockState(newPos).getBlock() == Blocks.SAND && j1 < 126 - size) {
				// Sand Pillar
				for (int l1 = 0; l1 < size; l1++)
					for (int l2 = 0; l2 < size / 4; l2++)
						for (int l3 = 0; l3 < size / 4; l3++)
							worldIn.setBlockState(pos.add(l2, l1, l3), Blocks.SANDSTONE.getDefaultState());
				return true;
			}
		}
		return false;
	}
}
