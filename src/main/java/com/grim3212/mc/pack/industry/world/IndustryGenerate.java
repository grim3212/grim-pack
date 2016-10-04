package com.grim3212.mc.pack.industry.world;

import java.util.Random;

import com.grim3212.mc.pack.core.part.GrimWorldGen;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.config.IndustryConfig;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class IndustryGenerate extends GrimWorldGen {

	@Override
	protected void generateSurface(World world, Random random, int i, int j) {
		if (IndustryConfig.generateUranium) {
			for (int k = 0; k < 18; k++) {
				int l = i + random.nextInt(17);
				int i1 = random.nextInt(17);
				int j1 = j + random.nextInt(17);
				(new WorldGenMinable(IndustryBlocks.uranium_ore.getDefaultState(), 5)).generate(world, random, new BlockPos(l, i1, j1));
			}
		}

		if (IndustryConfig.generateAluminum) {
			for (int k = 0; k < 10; ++k) {
				int x = i + random.nextInt(16);
				int y = random.nextInt(64);
				int z = j + random.nextInt(16);
				(new WorldGenMinable(IndustryBlocks.aluminum_ore.getDefaultState(), 8)).generate(world, random, new BlockPos(x, y, z));
			}
		}

		if (IndustryConfig.generateOilOre) {
			for (int k = 0; k < 2; ++k) {
				int x = i + random.nextInt(16);
				int y = random.nextInt(32);
				int z = j + random.nextInt(16);
				(new WorldGenMinable(IndustryBlocks.oil_ore.getDefaultState(), 4)).generate(world, random, new BlockPos(x, y, z));
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
