package com.grim3212.mc.pack.world.gen.structure;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;

public abstract class StructureStorage extends WorldSavedData {

	private final List<StructureBoundingBox> bbs = Lists.newArrayList();
	// I honestly don't know if we need a concurrent hashset, but can't be too
	// sure for compatibility
	private final Map<ChunkPos, Long> chunksToGenerate = new ConcurrentHashMap<>();

	public StructureStorage(String name) {
		super(name);
	}

	public abstract String getStructureName();

	@Override
	public void readFromNBT(@Nonnull NBTTagCompound nbt) {
		// Clear all bbs on read
		bbs.clear();

		NBTTagList tagList = nbt.getTagList(getStructureName(), NBT.TAG_INT_ARRAY);
		for (int i = 0; i < tagList.tagCount(); i++) {
			bbs.add(new StructureBoundingBox(tagList.getIntArrayAt(i)));
		}
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound nbt) {
		NBTTagList tagList = new NBTTagList();
		for (StructureBoundingBox sbb : bbs) {
			tagList.appendTag(sbb.toNBTTagIntArray());
		}

		nbt.setTag(getStructureName(), tagList);

		return nbt;
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

	public List<StructureBoundingBox> getStructures() {
		return bbs;
	}
}
