package com.grim3212.mc.pack.industry.util;

import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor.SensorMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Specific {

	private ItemStack stack = null;
	private int playerId = -1;
	private String entityName = "";

	public Specific() {
	}

	public void setSpecific(ItemStack stack) {
		this.stack = stack;
	}

	public void setSpecific(int playerId) {
		this.playerId = playerId;
	}

	public void setSpecific(String entityName) {
		this.entityName = entityName;
	}

	public ItemStack getStack() {
		return stack;
	}

	public int getPlayerId() {
		return playerId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void writeToNBT(NBTTagCompound compound) {
		NBTTagCompound specificTag = new NBTTagCompound();

		if (stack != null) {
			NBTTagCompound itemTag = new NBTTagCompound();
			this.stack.writeToNBT(itemTag);
			specificTag.setTag("Specific_Item", itemTag);
		}
		if (playerId != -1) {
			specificTag.setInteger("Specific_Player", playerId);
		}
		if (!entityName.isEmpty()) {
			specificTag.setString("Specific_Entity", entityName);
		}

		compound.setTag("Specific", specificTag);
	}

	public void readFromNBT(NBTTagCompound compound) {
		NBTTagCompound specificTag = compound.getCompoundTag("Specific");

		if (specificTag.hasKey("Specific_Item")) {
			this.stack = ItemStack.loadItemStackFromNBT(specificTag.getCompoundTag("Specific_Item"));
		}
		if (specificTag.hasKey("Specific_Player")) {
			this.playerId = specificTag.getInteger("Specific_Player");
		}
		if (specificTag.hasKey("Specific_Entity")) {
			this.entityName = specificTag.getString("Specific_Entity");
		}
	}

	public boolean hasSpecific(SensorMode specificity) {
		if (stack != null) {
			return specificity == SensorMode.ITEM;
		}
		if (playerId != -1) {
			return specificity == SensorMode.PLAYER;
		}
		if (!entityName.isEmpty()) {
			return specificity == SensorMode.MOB;
		}

		return false;
	}

}
