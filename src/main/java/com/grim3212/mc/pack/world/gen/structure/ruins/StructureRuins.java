package com.grim3212.mc.pack.world.gen.structure.ruins;

import java.util.Random;

import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.gen.structure.Structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class StructureRuins extends Structure {

	public static StructureRuins INSTANCE = new StructureRuins();

	@Override
	protected String getStructureName() {
		return "Ruins";
	}

	@Override
	protected boolean generateStructureInChunk(long seed, World world, int chunkX, int chunkZ) {
		Random random = new Random(seed);
		boolean atLeastOne = false;

		for (int i = 0; i < WorldConfig.ruinTries; i++) {
			int x = chunkX * 16 + 8 + random.nextInt(16);
			int z = chunkZ * 16 + 8 + random.nextInt(16);
			int y = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();
			BlockPos pos = new BlockPos(x, y, z);

			if (checkStructures(world, pos) && generateRuin(random, world, pos)) {
				atLeastOne = true;
			}
		}

		return atLeastOne;
	}

	private boolean generateRuin(Random random, World world, BlockPos pos) {
		int radius = 3 + random.nextInt(5);
		int skip = random.nextInt(4);
		int type = random.nextInt(9);
		int size = radius * 2 + 1;

		if (new StructureRuinGenerator(getStructureName(), radius, skip, type).generate(world, random, pos)) {
			BlockPos start = new BlockPos(pos.getX() - radius, pos.getY(), pos.getZ() - radius);

			// save it
			addBBSave(world, new StructureBoundingBox(start.getX(), start.getY() - radius, start.getZ(), start.getX() + size, start.getY() + size, start.getZ() + size));
			return true;
		}
		return false;
	}

	@Override
	protected boolean canGenerateInChunk(World world, Random rand, int chunkX, int chunkZ) {
		if (rand.nextInt(WorldConfig.ruinChance) == 0) {
			BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
			Biome biome = world.getBiomeProvider().getBiome(pos);

			return BiomeDictionary.hasType(biome, Type.PLAINS);
		}

		return false;
	}

	@Override
	protected boolean canGenerate() {
		return WorldConfig.subpartRuins && WorldConfig.ruinChance > 0;
	}

}
