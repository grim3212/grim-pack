package com.grim3212.mc.pack.world.gen.structure;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Lists;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class StructureData {

	private final String structName;
	private final List<StructureBoundingBox> bbs = Lists.newArrayList();
	// I honestly don't know if we need a concurrent hashset, but can't be too
	// sure for compatibility
	private final Map<ChunkPos, Long> chunksToGenerate = new ConcurrentHashMap<>();

	public StructureData(String structName) {
		this.structName = structName;
	}

	public String getStructName() {
		return structName;
	}

	public List<StructureBoundingBox> getStructures() {
		return bbs;
	}

	public void markChunkForGeneration(int chunkX, int chunkZ, long seed) {
		chunksToGenerate.put(new ChunkPos(chunkX, chunkZ), seed);
	}

	public Optional<Long> getSeedForChunkToGenerate(int chunkX, int chunkZ) {
		return Optional.ofNullable(chunksToGenerate.get(new ChunkPos(chunkX, chunkZ)));
	}

	public boolean markChunkAsGenerated(int chunkX, int chunkZ) {
		return chunksToGenerate.remove(new ChunkPos(chunkX, chunkZ)) != null;
	}
}