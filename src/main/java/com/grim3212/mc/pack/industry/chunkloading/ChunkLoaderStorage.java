package com.grim3212.mc.pack.industry.chunkloading;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.industry.GrimIndustry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;

import java.util.ArrayList;
import java.util.List;

public class ChunkLoaderStorage extends WorldSavedData {

	private static final String IDENTIFIER = GrimIndustry.partName + "_ChunkLoaders";

	private List<ChunkLoaderData> chunkLoaders = new ArrayList<ChunkLoaderData>();

	public ChunkLoaderStorage() {
		this(IDENTIFIER);
	}

	public ChunkLoaderStorage(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("ChunkLoaders")) {
			NBTTagList cls = nbt.getTagList("ChunkLoader", NBT.TAG_COMPOUND);

			// Make sure we at least have some tags
			if (!cls.hasNoTags()) {
				List<ChunkLoaderData> loaded = new ArrayList<ChunkLoaderData>();

				for (int i = 0; i < cls.tagCount(); i++) {
					NBTTagCompound data = cls.getCompoundTagAt(i);

					loaded.add(new ChunkLoaderData(data.getString("Owner"), data.getInteger("Dimension"), NBTHelper.getBlockPos(data, "Pos"), data.getLong("LastLogin")));
				}

				this.chunkLoaders = loaded;
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList cls = new NBTTagList();

		for (ChunkLoaderData data : chunkLoaders) {
			NBTTagCompound chunkLoader = new NBTTagCompound();

			NBTHelper.setBlockPos(chunkLoader, "Pos", data.pos);
			chunkLoader.setInteger("Dimension", data.dimension);
			chunkLoader.setString("Owner", data.ownerId);
			chunkLoader.setLong("LastLogin", data.ownerLastLogin);

			cls.appendTag(chunkLoader);
		}

		compound.setTag("ChunkLoaders", cls);

		return compound;
	}

	public List<ChunkLoaderData> getChunkLoaders() {
		return chunkLoaders;
	}

	public void setChunkLoaders(List<ChunkLoaderData> chunkLoaders) {
		this.chunkLoaders = chunkLoaders;
		this.markDirty();
	}

	private static ChunkLoaderStorage instance = null;

	public static ChunkLoaderStorage getStorage(World world) {

		if (instance == null) {
			instance = (ChunkLoaderStorage) world.getMapStorage().getOrLoadData(ChunkLoaderStorage.class, IDENTIFIER);
			if (instance == null) {
				instance = new ChunkLoaderStorage();
				world.getMapStorage().setData(IDENTIFIER, instance);

				// Make sure we save it
				instance.markDirty();
			}
		}

		return instance;
	}

	public void addChunkLoader(ChunkLoaderData data) {
		if (!chunkLoaders.contains(data)) {
			chunkLoaders.add(data);
			this.markDirty();
		}
	}

	public void removeChunkLoader(ChunkLoaderData data) {
		if (data != null) {
			chunkLoaders.remove(data);
			this.markDirty();
		}
	}

	public void updateLoginTime(String ownerId, long loginTime) {
		for (ChunkLoaderData data : chunkLoaders) {
			if (data.ownerId.equals(ownerId)) {
				data.ownerLastLogin = loginTime;
			}
		}

		this.markDirty();
	}
}
