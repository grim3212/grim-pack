package com.grim3212.mc.pack.industry.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLocksmith extends Slot {
	private boolean allowPlace = true;

	public SlotLocksmith(IInventory inv, int index, int xPosition, int yPosition, boolean allowPlace) {
		super(inv, index, xPosition, yPosition);

		this.allowPlace = allowPlace;
	}

	public int getSlotStackLimit() {
		return 1;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		return this.allowPlace;
	}
}