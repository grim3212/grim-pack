package com.grim3212.mc.pack.industry.inventory;

import com.grim3212.mc.pack.industry.block.IndustryBlocks;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotGoldSafe extends Slot {

	public SlotGoldSafe(IInventory inv, int index, int x, int y) {
		super(inv, index, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return itemstack.isItemEqual(new ItemStack(IndustryBlocks.gold_safe));
	}
}