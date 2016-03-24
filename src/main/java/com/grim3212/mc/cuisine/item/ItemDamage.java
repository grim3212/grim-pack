package com.grim3212.mc.cuisine.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDamage extends Item {

	public ItemDamage(int uses) {
		setMaxStackSize(1);
		setMaxDamage(uses);
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		itemStack.setItemDamage(itemStack.getItemDamage() + 1);
		return itemStack;
	}
}
