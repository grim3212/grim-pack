package com.grim3212.mc.decor.tile;

import com.grim3212.mc.decor.GrimDecor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumSkyBlock;

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
	}

	public int getLightValue() {
		return isActive() ? 15 : 0;
	}

	public void extinguish() {
		GrimDecor.proxy.produceSmoke(worldObj, pos, 0.5D, 0.3D, 0.5D, 3, true);
		worldObj.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.fizz", 1.0F, worldObj.rand.nextFloat() * 0.4F + 0.8F);
		worldObj.checkLightFor(EnumSkyBlock.BLOCK, pos);
		this.active = false;
	}
}