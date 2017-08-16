package com.grim3212.mc.pack.tools.world;

import java.util.Random;

import com.grim3212.mc.pack.core.part.GrimWorldGen;
import com.grim3212.mc.pack.tools.blocks.ToolsBlocks;
import com.grim3212.mc.pack.tools.config.ToolsConfig;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class ToolsGenerate extends GrimWorldGen {

	@Override
	protected void generateSurface(World world, Random random, int i, int j) {
		if (ToolsConfig.subpartBlackDiamond && ToolsConfig.generateBlackDiamond) {
			for (int k = 0; k < 1; k++) {
				int l = i + random.nextInt(16);
				int i1 = random.nextInt(14);
				int j1 = j + random.nextInt(16);
				(new WorldGenMinable(ToolsBlocks.black_diamond_ore.getDefaultState(), 5)).generate(world, random, new BlockPos(l, i1, j1));
			}
		}

		if (ToolsConfig.subpartRayGuns && ToolsConfig.generateElement115) {
			for (int k = 0; k < 7; k++) {
				int x = i + random.nextInt(16);
				int y = random.nextInt(32);
				int z = j + random.nextInt(16);
				(new WorldGenMinable(ToolsBlocks.element_115_ore.getDefaultState(), 4)).generate(world, random, new BlockPos(x, y, z));
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
	protected void generateCustom(DimensionType dimension, World world, Random random, int i, int j) {
	}

}
