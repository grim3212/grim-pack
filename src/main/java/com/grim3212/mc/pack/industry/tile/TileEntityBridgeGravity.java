package com.grim3212.mc.pack.industry.tile;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction;

public class TileEntityBridgeGravity extends TileEntityBridge {

	private Direction gravFacing;

	public TileEntityBridgeGravity() {
		gravFacing = Direction.NORTH;
	}

	public Direction getGravFacing() {
		return gravFacing;
	}

	public void setGravFacing(Direction gravFacing) {
		this.gravFacing = gravFacing;
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT compound) {
		CompoundNBT comp = super.writeToNBT(compound);
		comp.setInteger("GravFacing", gravFacing.getIndex());

		return comp;
	}

	@Override
	public void readFromNBT(CompoundNBT compound) {
		super.readFromNBT(compound);
		this.gravFacing = Direction.getFront(compound.getInteger("GravFacing"));
	}
}
