package com.grim3212.mc.world.util;

import java.util.ArrayList;
import java.util.Random;

import com.grim3212.mc.world.config.WorldConfig;
import com.grim3212.mc.world.gen.WorldGenFloatingIslands;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class FloatingIslandsBlacklist {

	public static ArrayList<Block> blacklist = new ArrayList<Block>();

	static {
		blacklist.add(Blocks.water);
		blacklist.add(Blocks.flowing_water);
		blacklist.add(Blocks.lava);
		blacklist.add(Blocks.flowing_lava);
		blacklist.add(Blocks.sand);
		blacklist.add(Blocks.log);
		blacklist.add(Blocks.leaves);
		blacklist.add(Blocks.log2);
		blacklist.add(Blocks.leaves2);
		blacklist.add(Blocks.ice);
		blacklist.add(Blocks.obsidian);
		blacklist.add(Blocks.bedrock);
	}

	public static void generateFloatingIslands(World world, Random random, int i, int j) {
		if (WorldConfig.generateFI) {
			if (random.nextInt(WorldConfig.spawnrate) == 0) {
				int height = 128;
				boolean flag = true;
				BlockPos pos = new BlockPos(i, height, j);

				while (flag) {
					if (world.isAirBlock(pos)) {
						pos = pos.down();
					} else {
						flag = false;
					}
				}

				Block block = world.getBlockState(pos).getBlock();
				if (blacklist.contains(block)) {
					return;
				} else if (world.getBlockState(pos).getBlock().isOpaqueCube()) {
					(new WorldGenFloatingIslands()).generate(world, random, pos);
				}
			}
		}
	}

}
