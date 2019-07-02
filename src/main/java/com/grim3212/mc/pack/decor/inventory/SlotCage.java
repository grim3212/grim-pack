package com.grim3212.mc.pack.decor.inventory;

import com.grim3212.mc.pack.core.inventory.SlotGrim;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SnowballItem;

public class SlotCage extends SlotGrim {

	public SlotCage(IInventory inv, int index, int x, int y) {
		super(inv, index, x, y, 26, 26);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		// TODO: Change to Pokeball
		return stack.getItem() instanceof SnowballItem;
	}
}
