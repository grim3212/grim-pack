package com.grim3212.mc.pack.industry.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.Slot;

public class SlotMoveable extends Slot {

	public SlotMoveable(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	public void setSlotPosition(int slotX, int slotY) {
		this.xPos = slotX;
		this.yPos = slotY;
	}
}