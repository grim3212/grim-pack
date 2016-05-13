package com.grim3212.mc.cuisine.world;

import java.util.Random;

import com.grim3212.mc.cuisine.block.CuisineBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCocoaTrees extends WorldGenerator {

	public WorldGenCocoaTrees() {
	}

	public boolean generate(World world, Random random, BlockPos pos) {
		int l = random.nextInt(3) + 4;
		boolean flag = true;
		if (pos.getY() < 1 || pos.getY() + l + 1 > 128) {
			return false;
		}
		for (int i1 = pos.getY(); i1 <= pos.getY() + 1 + l; i1++) {
			byte byte0 = 1;
			if (i1 == pos.getY()) {
				byte0 = 0;
			}
			if (i1 >= (pos.getY() + 1 + l) - 2) {
				byte0 = 2;
			}
			for (int i2 = pos.getX() - byte0; i2 <= pos.getX() + byte0 && flag; i2++) {
				for (int l2 = pos.getZ() - byte0; l2 <= pos.getZ() + byte0 && flag; l2++) {
					if (i1 >= 0 && i1 < 128) {
						Block j3 = world.getBlockState(new BlockPos(i2, i1, l2)).getBlock();
						if (j3 != Blocks.air && !(j3 instanceof BlockLeaves)) {
							flag = false;
						}
					} else {
						flag = false;
					}
				}
			}
		}

		if (!flag) {
			return false;
		}
		Block j1 = world.getBlockState(pos.down()).getBlock();
		if (j1 != Blocks.grass && j1 != Blocks.dirt || pos.getY() >= 128 - l - 1) {
			return false;
		}

		world.setBlockState(pos.down(), Blocks.dirt.getDefaultState());
		for (int k1 = (pos.getY() - 3) + l; k1 <= pos.getY() + l; k1++) {
			int j2 = k1 - (pos.getY() + l);
			int i3 = 1 - j2 / 2;
			for (int k3 = pos.getX() - i3; k3 <= pos.getX() + i3; k3++) {
				int l3 = k3 - pos.getX();
				for (int i4 = pos.getZ() - i3; i4 <= pos.getZ() + i3; i4++) {
					int j4 = i4 - pos.getZ();
					BlockPos newPos = new BlockPos(k3, k1, i4);

					if (Math.abs(l3) == i3 && Math.abs(j4) == i3 && (random.nextInt(2) == 0 || j2 == 0) || world.getBlockState(newPos).isOpaqueCube()) {
						continue;
					}
					if (random.nextInt(60) < 10) {
						if (world.getBlockState(newPos) == Blocks.log.getDefaultState()) {
							continue;
						}
						if (world.getBlockState(newPos.down()) == CuisineBlocks.cocoa_block.getDefaultState()) {
							world.setBlockState(newPos, Blocks.leaves.getDefaultState());
						} else {
							world.setBlockState(newPos, CuisineBlocks.cocoa_block.getDefaultState());
							world.setBlockState(newPos.up(), Blocks.leaves.getDefaultState());
						}
					} else {
						world.setBlockState(newPos, Blocks.leaves.getDefaultState());
					}
				}
			}
		}

		for (int l1 = 0; l1 < l; l1++) {
			Block k2 = world.getBlockState(pos.up(l1)).getBlock();
			if (k2 == Blocks.air || k2 instanceof BlockLeaves) {
				world.setBlockState(pos.up(l1), Blocks.log.getDefaultState());
			}
		}
		return true;
	}
}
