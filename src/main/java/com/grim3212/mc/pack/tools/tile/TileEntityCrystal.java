package com.grim3212.mc.pack.tools.tile;

import com.grim3212.mc.pack.core.tile.TileEntityGrim;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCrystal extends TileEntityGrim {

	private int age;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("Age", age);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		age = compound.getInteger("Age");
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
