package com.grim3212.mc.pack.core.part;

import java.util.Random;

import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public abstract class GrimWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.getDimensionType()) {
		case NETHER:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		case OVERWORLD:
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
			break;
		case THE_END:
			generateEnd(world, random, chunkX * 16, chunkZ * 16);
			break;
		default:
			generateCustom(world.provider.getDimensionType(), world, random, chunkX * 16, chunkZ * 16);
			break;
		}
	}

	/**
	 * Generate on the surface
	 */
	protected void generateSurface(World world, Random random, int i, int j) {
	}

	/**
	 * Generate in the nether
	 */
	protected void generateNether(World world, Random random, int i, int j) {
	}

	/**
	 * Generate in the end
	 */
	protected void generateEnd(World world, Random random, int i, int j) {
	}

	/**
	 * Generate in a custom dimension make sure to check that it is the right
	 * dimension id before generating
	 */
	protected void generateCustom(DimensionType dimension, World world, Random random, int i, int j) {
	}

}
