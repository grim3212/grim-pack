package com.grim3212.mc.world.gen;

import java.util.Random;

import com.grim3212.mc.world.blocks.WorldBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGunReeds extends WorldGenerator {

	public boolean generate(World worldIn, Random rand, BlockPos pos) {
		for (int i = 0; i < 20; ++i) {
			BlockPos blockpos1 = pos.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));

			if (worldIn.isAirBlock(blockpos1)) {
				BlockPos blockpos2 = blockpos1.down();

				if (worldIn.getBlockState(blockpos2.west()).getBlock().getMaterial() == Material.water || worldIn.getBlockState(blockpos2.east()).getBlock().getMaterial() == Material.water || worldIn.getBlockState(blockpos2.north()).getBlock().getMaterial() == Material.water || worldIn.getBlockState(blockpos2.south()).getBlock().getMaterial() == Material.water) {
					int j = 2 + rand.nextInt(rand.nextInt(3) + 1);

					for (int k = 0; k < j; ++k) {
						if (WorldBlocks.gunpowder_reed_block.canPlaceBlockAt(worldIn, blockpos1)) {
							worldIn.setBlockState(blockpos1.up(k), WorldBlocks.gunpowder_reed_block.getDefaultState(), 2);
						}
					}
				}
			}
		}
		return true;
	}
}
