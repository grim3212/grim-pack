package com.grim3212.mc.pack.industry.util;

import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor.SensorMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class Specific {

	private ItemStack stack = ItemStack.EMPTY;
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

	public void writeToNBT(CompoundNBT compound) {
		CompoundNBT specificTag = new CompoundNBT();

		if (stack != null) {
			CompoundNBT itemTag = new CompoundNBT();
			this.stack.write(itemTag);
			specificTag.put("Specific_Item", itemTag);
		}
		if (!playerName.isEmpty()) {
			specificTag.putString("Specific_Player", playerName);
		}
		if (!entityName.isEmpty()) {
			specificTag.putString("Specific_Entity", entityName);
		}

		compound.put("Specific", specificTag);
	}

	public void readFromNBT(CompoundNBT compound) {
		CompoundNBT specificTag = compound.getCompound("Specific");

		if (specificTag.contains("Specific_Item")) {
			this.stack = ItemStack.read(specificTag.getCompound("Specific_Item"));
		}
		if (specificTag.contains("Specific_Player")) {
			this.playerName = specificTag.getString("Specific_Player");
		}
		if (specificTag.contains("Specific_Entity")) {
			this.entityName = specificTag.getString("Specific_Entity");
		}
	}

	public boolean hasSpecific(SensorMode specificity) {
		switch (specificity) {
		case ITEM:
			if (!stack.isEmpty()) {
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
