package com.grim3212.mc.pack.industry.inventory;

import com.grim3212.mc.pack.industry.tile.TileEntityMFurnace;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMFurnaceFuel extends Slot {

	public SlotMFurnaceFuel(IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
		super(inventoryIn, slotIndex, xPosition, yPosition);
	}

	/**
	 * Check if the stack is a valid item for this slot. Always true beside for
	 * the armor slots.
	 */
	@Override
	public boolean isItemValid(ItemStack stack) {
		return TileEntityMFurnace.isItemFuel(stack);
	}
}