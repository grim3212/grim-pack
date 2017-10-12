package com.grim3212.mc.pack.world.gen.structure.cactusfield;

import java.util.Random;

import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.gen.structure.Structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class StructureCactusField extends Structure {

	public static StructureCactusField INSTANCE = new StructureCactusField();

	@Override
	protected String getStructureName() {
		return "CactusFields";
	}

	@Override
	protected boolean generateStructureInChunk(long seed, World world, int chunkX, int chunkZ) {
		Random random = new Random(seed);

		int x = chunkX * 16 + random.nextInt(16);
		int z = chunkZ * 16 + random.nextInt(16);
		int y = random.nextInt(55) + 60;

		int size = random.nextInt(24) + 3;

		return new StructureCactusFieldGenerator(getStructureName(), size, 2, getStructureStorage(world)).generate(world, random, new BlockPos(x, y, z));
	}

	@Override
	protected boolean canGenerateInChunk(World world, Random rand, int chunkX, int chunkZ) {
		if (rand.nextInt(WorldConfig.frequencyCactusFields) == 0) {
			BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
			Biome biome = world.getBiomeProvider().getBiome(pos);

			return BiomeDictionary.hasType(biome, Type.SANDY);
		}

		return false;
	}

	@Override
	protected boolean canGenerate() {
		return WorldConfig.subpartWorldGenExpanded && WorldConfig.frequencyCactusFields > 0;
	}

	private static final int[] OFFSETS = new int[] { -3, 3, -3, 3 };

	@Override
	protected int[] getChunkOffsets() {
		return OFFSETS;
	}
}
