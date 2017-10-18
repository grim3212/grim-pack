package com.grim3212.mc.pack.world.gen.structure.wheatfield;

import java.util.Random;

import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.gen.structure.Structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class StructureWheatField extends Structure {

	public static StructureWheatField INSTANCE = new StructureWheatField();

	@Override
	protected String getStructureName() {
		return "WheatFields";
	}

	@Override
	protected boolean generateStructureInChunk(long seed, World world, int chunkX, int chunkZ) {
		Random random = new Random(seed);

		int x = chunkX * 16 + random.nextInt(16);
		int z = chunkZ * 16 + random.nextInt(16);
		int y = random.nextInt(40) + 60;

		BlockPos pos = new BlockPos(x, y, z);

		return checkStructures(world, pos) && new WorldGenFarmland(getStructureName(), random.nextInt(8) + 4, random.nextInt(6) + 2, 16, getStructureStorage(world)).generate(world, random, pos);
	}

	@Override
	protected boolean canGenerateInChunk(World world, Random rand, int chunkX, int chunkZ) {
		if (rand.nextInt(WorldConfig.frequencyWheatField) == 0) {
			BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
			Biome biome = world.getBiomeProvider().getBiome(pos);

			return BiomeDictionary.hasType(biome, Type.PLAINS);
		}

		return false;
	}

	@Override
	protected boolean canGenerate() {
		return WorldConfig.subpartWorldGenExpanded && WorldConfig.frequencyWheatField > 0;
	}

}
