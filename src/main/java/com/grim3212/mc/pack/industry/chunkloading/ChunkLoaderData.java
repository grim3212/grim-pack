package com.grim3212.mc.pack.industry.chunkloading;

import java.io.Serializable;

import net.minecraft.util.math.BlockPos;

public class ChunkLoaderData implements Serializable {

	private static final long serialVersionUID = 1L;

	public String ownerId = "";
	public long ownerLastLogin = 0;
	public int dimension = 0;
	public BlockPos pos = BlockPos.ZERO;

	public ChunkLoaderData(String ownerId, int dimension, BlockPos pos, long ownerLastLogin) {
		this.ownerId = ownerId;
		this.dimension = dimension;
		this.pos = pos;
		this.ownerLastLogin = ownerLastLogin;
	}

	@Override
	public String toString() {
		return "pos: " + pos.toString() + " dimension: " + dimension + " owner: " + ownerId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !ChunkLoaderData.class.isAssignableFrom(obj.getClass())) {
			return false;
		} else {
			ChunkLoaderData rhs = (ChunkLoaderData) obj;
			return this.dimension == rhs.dimension && this.ownerId.equals(rhs.ownerId) && this.pos.getX() == rhs.pos.getX() && this.pos.getY() == rhs.pos.getY() && this.pos.getZ() == rhs.pos.getZ();
		}
	}

}
