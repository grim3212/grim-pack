package com.grim3212.mc.pack.world.gen.structure;

import java.util.Optional;
import java.util.Random;

import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.fml.common.IWorldGenerator;

public abstract class Structure implements IWorldGenerator {

	private static final int[] BASE_OFFSETS = new int[] { -1, 1, -1, 1 };

	protected TIntObjectHashMap<StructureStorage> structureData = new TIntObjectHashMap<>();

	protected abstract String getStructureName();

	public boolean isStructureAt(World world, BlockPos pos) {
		for (StructureBoundingBox data : getStructureData(world).getStructures()) {
			if (data.isVecInside(pos)) {
				return true;
			}
		}
		return false;
	}

	protected StructureStorage getStructureStorage(World world) {
		int dimensionId = world.provider.getDimension();
		if (!structureData.containsKey(dimensionId)) {
			StructureStorage data = StructureStorage.getStructureStorage(world);
			structureData.put(dimensionId, data);
		}

		return structureData.get(dimensionId);
	}

	protected StructureData getStructureData(World world) {
		return getStructureStorage(world).getStructureData(getStructureName());
	}

	protected void addBBSave(World world, StructureBoundingBox bb) {
		getStructureStorage(world).addBBSave(getStructureName(), bb);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		// Is the structure allowed to generate
		if (!canGenerate()) {
			return;
		}

		// is the structure allowed in this dimension
		if (!canGenerateInDimension(world.provider.getDimension())) {
			return;
		}

		// predetermine the seed so the generation is the same no matter when
		// the structure is generated
		long generationSeed = random.nextLong();

		// Is the structure allowed to generate in this chunk
		if (!canGenerateInChunk(world, random, chunkX, chunkZ)) {
			StructureData structureData = getStructureData(world);

			int[] offsets = getChunkOffsets();
			if (offsets.length != 4)
				throw new IllegalArgumentException("Chunk offsets length must be 4 got '" + offsets.length + "'");

			// check if we need to generate in one of the surrounding chunks
			for (int x = chunkX + offsets[0]; x <= chunkX + offsets[1]; x++) {
				for (int z = chunkZ + offsets[2]; z <= chunkZ + offsets[3]; z++) {
					Optional<Long> optionalGenerationSeed = structureData.getSeedForChunkToGenerate(x, z);
					if (optionalGenerationSeed.isPresent() && areSurroundingChunksLoaded(x, z, chunkProvider)) {
						generateStructureInChunk(optionalGenerationSeed.get(), world, x, z);
						// GrimLog.info("Marked Generated", "Chunk: x: " + x +
						// ", z: " + z);
						structureData.markChunkAsGenerated(x, z);
					}
				}
			}
			return;
		}

		// Make sure that the chunks are laoded
		if (!areSurroundingChunksLoaded(chunkX, chunkZ, chunkProvider)) {
			// GrimLog.info("Marked For Generated", "Chunk: x: " + chunkX + ",
			// z: " + chunkZ);
			getStructureData(world).markChunkForGeneration(chunkX, chunkZ, generationSeed);
			return;
		}

		// Try and generate structure in chunk
		generateStructureInChunk(generationSeed, world, chunkX, chunkZ);
	}

	/**
	 * Must return 4 integers for chunk offsets to check that the area is loaded
	 * before we generate
	 * 
	 * 0: xNeg, 1: xPos, 2: zNeg, 3: zPos
	 * 
	 * @return int[4]
	 */
	protected int[] getChunkOffsets() {
		return BASE_OFFSETS;
	}

	protected abstract boolean generateStructureInChunk(long seed, World world, int chunkX, int chunkZ);

	protected abstract boolean canGenerateInChunk(World world, Random rand, int chunkX, int chunkZ);

	protected abstract boolean canGenerate();

	protected boolean canGenerateInDimension(int id) {
		if (id == DimensionType.OVERWORLD.getId()) {
			return true;
		}
		return false;
	}

	private boolean areSurroundingChunksLoaded(int chunkX, int chunkZ, IChunkProvider chunkprovider) {
		int[] offsets = getChunkOffsets();
		if (offsets.length != 4)
			throw new IllegalArgumentException("Chunk offsets length must be 4 got '" + offsets.length + "'");

		for (int x = chunkX + offsets[0]; x <= chunkX + offsets[1]; x++) {
			for (int z = chunkZ + offsets[2]; z <= chunkZ + offsets[3]; z++) {
				if (chunkprovider.getLoadedChunk(x, z) == null) {
					return false;
				}
			}
		}
		return true;
	}
}
