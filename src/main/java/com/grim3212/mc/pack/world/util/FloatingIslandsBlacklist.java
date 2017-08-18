package com.grim3212.mc.pack.world.util;

import java.util.ArrayList;
import java.util.Random;

import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.gen.WorldGenFloatingIslands;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FloatingIslandsBlacklist {

	public static ArrayList<Block> blacklist = new ArrayList<Block>();

	static {
		blacklist.add(Blocks.WATER);
		blacklist.add(Blocks.FLOWING_LAVA);
		blacklist.add(Blocks.FLOWING_WATER);
		blacklist.add(Blocks.LAVA);
		blacklist.add(Blocks.SAND);
		blacklist.add(Blocks.LOG);
		blacklist.add(Blocks.LEAVES);
		blacklist.add(Blocks.LOG2);
		blacklist.add(Blocks.LEAVES2);
		blacklist.add(Blocks.ICE);
		blacklist.add(Blocks.OBSIDIAN);
		blacklist.add(Blocks.BEDROCK);
	}

	public static void generateFloatingIslands(World world, Random random, int i, int j) {
		if (random.nextInt(WorldConfig.spawnrate) == 0) {
			int height = 128;
			boolean flag = true;
			BlockPos pos = new BlockPos(i, height, j);

			while (flag) {
				if (world.isAirBlock(pos)) {
					pos = pos.down();

					if (pos.getY() <= 0)
						flag = false;

				} else {
					flag = false;
				}
			}

			Block block = world.getBlockState(pos).getBlock();
			if (blacklist.contains(block)) {
				return;
			} else if (world.getBlockState(pos).isOpaqueCube()) {
				(new WorldGenFloatingIslands()).generate(world, random, pos);
			}
		}
	}
}