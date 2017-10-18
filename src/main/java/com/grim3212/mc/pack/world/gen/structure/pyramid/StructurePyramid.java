package com.grim3212.mc.pack.world.gen.structure.pyramid;

import java.util.Random;

import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.gen.structure.Structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class StructurePyramid extends Structure {

	public static StructurePyramid INSTANCE = new StructurePyramid();
	public static final String PYRAMID_NAME = "Pyramids";

	@Override
	protected String getStructureName() {
		return PYRAMID_NAME;
	}

	@Override
	protected boolean generateStructureInChunk(long seed, World world, int chunkX, int chunkZ) {
		Random random = new Random(seed);

		int x = chunkX * 16 + 8 + random.nextInt(8);
		int z = chunkZ * 16 + 8 + random.nextInt(8);
		int y = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();

		return generatePyramid(random, world, new BlockPos(x, y, z));
	}

	private boolean generatePyramid(Random random, World world, BlockPos blockPos) {
		int height = 2 * (4 + random.nextInt(16));
		int type = random.nextInt(2);
		int halfWidth = StructurePyramidGenerator.halfWidth(height);

		if (checkStructures(world, blockPos) && new StructurePyramidGenerator(getStructureName(), height, type).generate(world, random, blockPos)) {
			BlockPos start = new BlockPos(blockPos.getX() - halfWidth, blockPos.getY(), blockPos.getZ() - halfWidth);

			addBBSave(world, new StructureBoundingBox(start.getX(), start.getY() - (height / 2) - 1, start.getZ(), start.getX() + (halfWidth * 2), start.getY() + height, start.getZ() + (halfWidth * 2)));
			return true;
		}
		return false;
	}

	@Override
	protected boolean canGenerateInChunk(World world, Random rand, int chunkX, int chunkZ) {
		if (rand.nextInt(WorldConfig.ruinPyramidChance) == 0) {
			BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
			Biome biome = world.getBiomeProvider().getBiome(pos);

			return BiomeDictionary.hasType(biome, Type.SANDY);
		}

		return false;
	}

	@Override
	protected boolean canGenerate() {
		return WorldConfig.subpartRuins && WorldConfig.ruinPyramidChance > 0;
	}

	@Override
	protected int[] getChunkOffsets() {
		return BASIC_3_OFFSETS;
	}
}
