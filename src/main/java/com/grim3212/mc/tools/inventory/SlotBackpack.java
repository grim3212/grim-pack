package com.grim3212.mc.tools.inventory;

import com.grim3212.mc.tools.items.ToolsItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBackpack extends Slot {

	public SlotBackpack(IInventory inventory, int x, int y, int z) {
		super(inventory, x, y, z);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if (itemstack.getItem() != ToolsItems.backpack) {
			return true;
		} else {
			return false;
		}
	}
}
