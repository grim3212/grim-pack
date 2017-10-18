package com.grim3212.mc.pack.world.gen.structure.waterdome;

import java.util.Random;

import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.gen.structure.Structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class StructureWaterDome extends Structure {

	public static StructureWaterDome INSTANCE = new StructureWaterDome();
	private static final int[] OFFSET = new int[] { -4, 4, -4, 4 };

	@Override
	protected String getStructureName() {
		return "WaterDomes";
	}

	@Override
	protected boolean generateStructureInChunk(long seed, World world, int chunkX, int chunkZ) {
		Random random = new Random(seed);

		int x = chunkX * 16 + 4 + random.nextInt(8);
		int z = chunkZ * 16 + 4 + random.nextInt(8);
		int y = 32 + random.nextInt(64);
		BlockPos pos = new BlockPos(x, y, z);

		int radius = 3 + random.nextInt(5);

		return checkStructures(world, pos) && new StructureWaterDomeGenerator(getStructureName(), radius, getStructureStorage(world)).generate(world, random, pos);
	}

	@Override
	protected boolean canGenerateInChunk(World world, Random rand, int chunkX, int chunkZ) {
		if (rand.nextInt(WorldConfig.ruinWaterDomeChance) == 0) {
			BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
			Biome biome = world.getBiomeProvider().getBiome(pos);

			return BiomeDictionary.hasType(biome, Type.OCEAN);
		}

		return false;
	}

	@Override
	protected boolean canGenerate() {
		return WorldConfig.subpartRuins && WorldConfig.ruinWaterDomeChance > 0;
	}

	@Override
	protected int[] getChunkOffsets() {
		return OFFSET;
	}
}
