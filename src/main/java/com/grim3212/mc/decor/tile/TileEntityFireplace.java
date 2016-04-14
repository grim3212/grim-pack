package com.grim3212.mc.decor.tile;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityFireplace extends TileEntityTextured {

	protected boolean active = false;

	public TileEntityFireplace() {
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("active", this.active);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.active = compound.getBoolean("active");
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		worldObj.markBlockForUpdate(getPos());
		worldObj.notifyNeighborsOfStateChange(getPos(), blockType);
		worldObj.notifyLightSet(getPos());
		worldObj.checkLight(getPos());
	}

	public int getLightValue() {
		return isActive() ? 15 : 0;
	}
}