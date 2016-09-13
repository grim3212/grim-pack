package com.grim3212.mc.pack.core.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotGrim extends Slot {

	public final int xSize;
	public final int ySize;

	public SlotGrim(IInventory inventoryIn, int index, int xPosition, int yPosition, int xSize, int ySize) {
		super(inventoryIn, index, xPosition, yPosition);
		this.xSize = xSize;
		this.ySize = ySize;
	}

}
