package com.grim3212.mc.pack.industry.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityBridgeGravity extends TileEntityBridge {

	private EnumFacing gravFacing;

	public TileEntityBridgeGravity() {
		gravFacing = EnumFacing.NORTH;
	}

	public EnumFacing getGravFacing() {
		return gravFacing;
	}

	public void setGravFacing(EnumFacing gravFacing) {
		this.gravFacing = gravFacing;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound comp = super.writeToNBT(compound);
		comp.setInteger("GravFacing", gravFacing.getIndex());

		return comp;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.gravFacing = EnumFacing.getFront(compound.getInteger("GravFacing"));
	}
}
