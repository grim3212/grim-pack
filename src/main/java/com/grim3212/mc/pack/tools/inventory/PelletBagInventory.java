package com.grim3212.mc.pack.tools.inventory;

import com.grim3212.mc.pack.core.inventory.ItemHandlerBase;

import net.minecraft.item.ItemStack;

public class PelletBagInventory extends ItemHandlerBase {

	public PelletBagInventory(ItemStack itemStack) {
		super(itemStack);
	}

	@Override
	public String getUnlocalizedName() {
		return "container.pellet_bag";
	}
}