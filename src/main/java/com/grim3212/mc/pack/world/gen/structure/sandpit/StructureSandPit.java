package com.grim3212.mc.pack.world.gen.structure.sandpit;

import java.util.Random;

import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.gen.structure.Structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class StructureSandPit extends Structure {

	public static StructureSandPit INSTANCE = new StructureSandPit();

	@Override
	protected String getStructureName() {
		return "SandPits";
	}

	@Override
	protected boolean generateStructureInChunk(long seed, World world, int chunkX, int chunkZ) {
		Random random = new Random(seed);

		int x = chunkX * 16 + random.nextInt(16);
		int z = chunkZ * 16 + random.nextInt(16);
		int y = random.nextInt(25) + 60;

		int size = random.nextInt(19) + 4;
		BlockPos pos = new BlockPos(x, y, z);

		if (checkStructures(world, pos) && new StructureSandPitGenerator(getStructureName(), size, 10).generate(world, random, pos)) {
			int depth = (int) (Math.ceil(((double) size) / 2));

			addBBSave(world, new StructureBoundingBox(pos.getX(), pos.getY() - size - depth, pos.getZ(), pos.getX() + size, pos.getY() + (size / 2), pos.getZ() + size));
			return true;
		}

		return false;
	}

	@Override
	protected boolean canGenerateInChunk(World world, Random rand, int chunkX, int chunkZ) {
		if (rand.nextInt(WorldConfig.frequencySandPits) == 0) {
			BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
			Biome biome = world.getBiomeProvider().getBiome(pos);

			return BiomeDictionary.hasType(biome, Type.SANDY);
		}

		return false;
	}

	@Override
	protected int[] getChunkOffsets() {
		return BASIC_2_OFFSETS;
	}

	@Override
	protected boolean canGenerate() {
		return WorldConfig.subpartWorldGenExpanded && WorldConfig.frequencySandPits > 0;
	}

}
