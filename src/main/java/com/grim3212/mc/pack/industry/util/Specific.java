package com.grim3212.mc.pack.industry.util;

import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor.SensorMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Specific {

	private ItemStack stack = null;
	private String playerName = "";
	private String entityName = "";

	public Specific() {
	}

	public void setItemStackSpecific(ItemStack stack) {
		this.stack = stack;
	}

	public void setPlayerSpecific(String playerName) {
		this.playerName = playerName;
	}

	public void setEntitySpecific(String entityName) {
		this.entityName = entityName;
	}

	public ItemStack getStack() {
		return stack;
	}

	public String getPlayerName() {
		return playerName;
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
		if (!playerName.isEmpty()) {
			specificTag.setString("Specific_Player", playerName);
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
			this.playerName = specificTag.getString("Specific_Player");
		}
		if (specificTag.hasKey("Specific_Entity")) {
			this.entityName = specificTag.getString("Specific_Entity");
		}
	}

	public boolean hasSpecific(SensorMode specificity) {
		switch (specificity) {
		case ITEM:
			if (stack != null) {
				return true;
			}
			break;
		case MOB:
			if (!entityName.isEmpty()) {
				return true;
			}
			break;
		case PLAYER:
			if (!playerName.isEmpty()) {
				return true;
			}
			break;
		}

		return false;
	}

}
