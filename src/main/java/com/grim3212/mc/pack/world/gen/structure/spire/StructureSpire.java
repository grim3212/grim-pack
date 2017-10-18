package com.grim3212.mc.pack.world.gen.structure.spire;

import java.util.Random;

import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.gen.structure.Structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class StructureSpire extends Structure {

	public static StructureSpire INSTANCE = new StructureSpire();

	@Override
	protected String getStructureName() {
		return "Spires";
	}

	@Override
	protected boolean generateStructureInChunk(long seed, World world, int chunkX, int chunkZ) {
		Random random = new Random(seed);
		int spires = 0;

		// Max out at 5 spires
		for (int i = 0; i < WorldConfig.ruinTries && spires < 5; i++) {
			int x = chunkX * 16 + 8 + random.nextInt(16);
			int z = chunkZ * 16 + 8 + random.nextInt(16);
			int y = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();
			BlockPos pos = new BlockPos(x, y, z);

			if (checkStructures(world, pos) && generateSpire(random, world, pos)) {
				spires++;
			}
		}

		return spires > 0;
	}

	private boolean generateSpire(Random random, World world, BlockPos pos) {
		if (pos.getY() == 0) {
			return false;
		}

		int radius = 2;
		if (WorldConfig.spireRadius > 2) {
			radius = random.nextInt(WorldConfig.spireRadius - 2);
		}

		int height = (1 + random.nextInt(WorldConfig.spireHeight * 2)) * 5;
		int type = random.nextInt(9);
		boolean deathSpire = random.nextFloat() < WorldConfig.deathSpireChance;

		if (new StructureSpireGenerator(getStructureName(), radius, height, type, deathSpire).generate(world, random, pos)) {
			BlockPos start = new BlockPos(pos.getX() - radius, pos.getY() - 2, pos.getZ() - radius);

			// save it
			addBBSave(world, new StructureBoundingBox(start.getX(), start.getY(), start.getZ(), start.getX() + (radius * 2) + 1, start.getY() + height, start.getZ() + (radius * 2) + 1));
			return true;
		}
		return false;
	}

	@Override
	protected boolean canGenerateInChunk(World world, Random rand, int chunkX, int chunkZ) {
		if (rand.nextInt(WorldConfig.spireChance) == 0) {
			BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
			Biome biome = world.getBiomeProvider().getBiome(pos);

			return BiomeDictionary.hasType(biome, Type.MOUNTAIN);
		}

		return false;
	}

	@Override
	protected boolean canGenerate() {
		return WorldConfig.subpartRuins && WorldConfig.spireChance > 0;
	}

}
