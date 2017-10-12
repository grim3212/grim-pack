package com.grim3212.mc.pack.world.gen.structure.fountain;

import java.util.Random;

import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.gen.structure.Structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class StructureFountain extends Structure {

	public static StructureFountain INSTANCE = new StructureFountain();

	@Override
	protected String getStructureName() {
		return "Fountains";
	}

	@Override
	protected boolean generateStructureInChunk(long seed, World world, int chunkX, int chunkZ) {
		Random random = new Random(seed);

		int x = chunkX * 16 + 4 + random.nextInt(8);
		int z = chunkZ * 16 + 4 + random.nextInt(8);
		int y = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();

		return generateFountain(random, world, new BlockPos(x, y, z));
	}

	private boolean generateFountain(Random random, World world, BlockPos pos) {
		int height = 4 * (3 + random.nextInt(3));
		int type = random.nextInt(2);
		int halfWidth = StructureFountainGenerator.halfWidth(height);

		if (new StructureFountainGenerator(height, type).generate(world, random, pos)) {
			BlockPos start = new BlockPos(pos.getX() - halfWidth, pos.getY(), pos.getZ() - halfWidth);

			// save it
			addBBSave(world, new StructureBoundingBox(start.getX(), start.getY(), start.getZ(), start.getX() + (halfWidth * 2), start.getY() + height, start.getZ() + (halfWidth * 2)));
			return true;
		}
		return false;
	}

	@Override
	protected boolean canGenerate() {
		return WorldConfig.subpartRuins;
	}

	@Override
	protected boolean canGenerateInChunk(World world, Random rand, int chunkX, int chunkZ) {
		if (rand.nextInt(WorldConfig.ruinChance) == 0) {
			BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
			Biome biome = world.getBiomeProvider().getBiome(pos);

			return BiomeDictionary.hasType(biome, Type.SWAMP);
		}

		return false;
	}
}
