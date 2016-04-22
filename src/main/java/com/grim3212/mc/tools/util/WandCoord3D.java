package com.grim3212.mc.tools.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class WandCoord3D {

	public BlockPos pos;
	public Block block;
	public int meta;

	public WandCoord3D() {
		this(new BlockPos(0, 0, 0), Blocks.air, 0);
	}

	public WandCoord3D(BlockPos pos, int meta) {
		this.pos = pos;
		this.meta = meta;
	}

	public WandCoord3D(BlockPos pos, Block block, int meta) {
		this(pos, meta);
		this.block = block;
	}

	public WandCoord3D(BlockPos pos, int blockID, int meta) {
		this(pos, meta);
		block = Block.getBlockById(blockID);
	}

	public WandCoord3D(WandCoord3D a) {
		this(a.pos, a.block, a.meta);
	}

	public WandCoord3D copy() {
		return new WandCoord3D(this);
	}

	public int getArea(WandCoord3D b) {
		return Math.abs(pos.getX() - b.pos.getX() + 1) * Math.abs(pos.getY() - b.pos.getY() + 1) * Math.abs(pos.getZ() - b.pos.getZ() + 1);
	}

	public double getDistance(WandCoord3D b) {
		double d3 = pos.getX() - b.pos.getX();
		double d4 = pos.getY() - b.pos.getY();
		double d5 = pos.getZ() - b.pos.getZ();
		return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
	}

	public double getDistanceFlat(WandCoord3D b) {
		double d3 = pos.getX() - b.pos.getX();
		double d5 = pos.getZ() - b.pos.getZ();
		return MathHelper.sqrt_double(d3 * d3 + d5 * d5);
	}

	public int getFlatArea(WandCoord3D b) {
		return Math.abs(pos.getX() - b.pos.getX() + 1) * Math.abs(pos.getZ() - b.pos.getZ() + 1);
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public void setTo(WandCoord3D a) {
		setPos(a.pos);
	}

	public void writeToNBT(NBTTagCompound compound, String key) {
		if (!compound.hasKey("Coord3d")) {
			compound.setTag("Coord3d", new NBTTagCompound());
		}
		compound.getCompoundTag("Coord3d").setIntArray(key, new int[] { pos.getX(), pos.getY(), pos.getZ(), Block.getIdFromBlock(block), meta });
	}

	public static void findEnds(WandCoord3D a, WandCoord3D b) {
		WandCoord3D n = new WandCoord3D();
		WandCoord3D m = new WandCoord3D();

		int nx = a.pos.getX() > b.pos.getX() ? b.pos.getX() : a.pos.getX();
		int ny = a.pos.getY() > b.pos.getY() ? b.pos.getY() : a.pos.getY();
		int nz = a.pos.getZ() > b.pos.getZ() ? b.pos.getZ() : a.pos.getZ();
		n.pos = new BlockPos(nx, ny, nz);

		int mx = a.pos.getX() < b.pos.getX() ? b.pos.getX() : a.pos.getX();
		int my = a.pos.getY() < b.pos.getY() ? b.pos.getY() : a.pos.getY();
		int mz = a.pos.getZ() < b.pos.getZ() ? b.pos.getZ() : a.pos.getZ();
		m.pos = new BlockPos(mx, my, mz);

		a.setTo(n);
		b.setTo(m);
	}

	public static int getArea(WandCoord3D a, WandCoord3D b) {
		return Math.abs(a.pos.getX() - b.pos.getX() + 1) * Math.abs(a.pos.getY() - b.pos.getY() + 1) * Math.abs(a.pos.getZ() - b.pos.getZ() + 1);
	}

	public static int getFlatArea(WandCoord3D a, WandCoord3D b) {
		return Math.abs(a.pos.getX() - b.pos.getX() + 1) * Math.abs(a.pos.getZ() - b.pos.getZ() + 1);
	}

	public static WandCoord3D getFromNBT(NBTTagCompound compound, String key) {
		if (compound.hasKey("Coord3d")) {
			NBTTagCompound nbt = compound.getCompoundTag("Coord3d");
			if (nbt.hasKey(key)) {
				int[] coord = nbt.getIntArray(key);
				if (coord.length == 5) {
					return new WandCoord3D(new BlockPos(coord[0], coord[1], coord[2]), coord[3], coord[4]);
				}
			}
		}
		return null;
	}
}
