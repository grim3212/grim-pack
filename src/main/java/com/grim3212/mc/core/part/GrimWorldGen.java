package com.grim3212.mc.core.part;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public abstract class GrimWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.getDimensionId()) {
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 0:
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 1:
			generateEnd(world, random, chunkX * 16, chunkZ * 16);
			break;
		default:
			generateCustom(world.provider.getDimensionId(), world, random, chunkX * 16, chunkZ * 16);
			break;
		}
	}

	/**
	 * Generate on the surface
	 */
	protected abstract void generateSurface(World world, Random random, int i, int j);

	/**
	 * Generate in the nether
	 */
	protected abstract void generateNether(World world, Random random, int i, int j);

	/**
	 * Generate in the end
	 */
	protected abstract void generateEnd(World world, Random random, int i, int j);

	/**
	 * Generate in a custom dimension make sure to check that it is the right
	 * dimension id before generating
	 */
	protected abstract void generateCustom(int dimensionId, World world, Random random, int i, int j);

}
