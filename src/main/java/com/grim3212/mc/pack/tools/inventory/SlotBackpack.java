package com.grim3212.mc.pack.tools.inventory;

import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotBackpack extends SlotItemHandler {

	public SlotBackpack(IItemHandler inventory, int x, int y, int z) {
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
