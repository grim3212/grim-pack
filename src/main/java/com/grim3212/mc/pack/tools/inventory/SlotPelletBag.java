package com.grim3212.mc.pack.tools.inventory;

import com.grim3212.mc.pack.tools.items.ItemSlingPellet;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotPelletBag extends SlotItemHandler {

	public SlotPelletBag(IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof ItemSlingPellet;
	}
}
