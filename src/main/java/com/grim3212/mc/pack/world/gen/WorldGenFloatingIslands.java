package com.grim3212.mc.pack.world.gen;

import java.util.Random;

import com.grim3212.mc.pack.world.config.WorldConfig;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFloatingIslands extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		int var5 = 7 + rand.nextInt(WorldConfig.sizevariancefrom7);
		int var6 = rand.nextInt(25) + 20;

		if (!(world.getWorldChunkManager() instanceof WorldChunkManagerHell))
			if (128 - pos.getY() < 45) {
				return false;
			} else {
				int var7;
				int var8;
				int var9;
				IBlockState block;
				for (var7 = -var5; var7 <= var5; ++var7) {
					for (var8 = -var5; var8 <= var5; ++var8) {
						if (Math.pow((double) var7, 2.0D) + Math.pow((double) var8, 2.0D) <= Math.pow((double) var5, 2.0D)) {
							for (var9 = 1; var9 < 17; ++var9) {
								BlockPos newPos = pos.add(var7, var9, var8);
								BlockPos movedPos = pos.add(var7, var6 + var9, var8);
								block = world.getBlockState(newPos);
								if (block.getBlock() == Blocks.bedrock) {
									return false;
								} else if (block.getBlock() != Blocks.flowing_water && block.getBlock() != Blocks.water && block.getBlock() != Blocks.flowing_lava && block.getBlock() != Blocks.lava && block.getBlock() != Blocks.sand && block.getBlock() != Blocks.gravel) {
									world.setBlockState(movedPos, block, 0);
									world.setBlockToAir(newPos);
								} else {
									world.setBlockState(movedPos, block, 3);
									world.setBlockToAir(newPos);
								}
							}
						}
					}
				}

				for (var7 = -var5; var7 <= var5; ++var7) {
					for (var8 = -var5; var8 <= var5; ++var8) {
						for (var9 = -var5; var9 <= var5; ++var9) {
							if (Math.pow((double) var7, 2.0D) + Math.pow((double) var8, 2.0D) + Math.pow((double) var9, 2.0D) <= Math.pow((double) var5, 2.0D) && pos.getY() + var9 <= pos.getY()) {
								BlockPos newPos = pos.add(var7, var9, var8);
								BlockPos movedPos = pos.add(var7, var6 + var9, var8);
								block = world.getBlockState(newPos);
								if (block.getBlock() == Blocks.bedrock) {
									return false;
								} else if (block.getBlock() != Blocks.flowing_water && block.getBlock() != Blocks.water && block.getBlock() != Blocks.flowing_lava && block.getBlock() != Blocks.lava && block.getBlock() != Blocks.sand && block.getBlock() != Blocks.gravel) {
									world.setBlockState(movedPos, block, 0);
									world.setBlockToAir(newPos);
								} else {
									world.setBlockState(movedPos, block, 3);
									world.setBlockToAir(newPos);
								}
							}
						}
					}
				}
			}
		return true;
	}
}
