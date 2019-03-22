package com.grim3212.mc.pack.cuisine.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDamage extends ItemManual {

	public ItemDamage(String name, int uses) {
		super(name, new Item.Properties().defaultMaxDamage(uses).group(GrimItemGroups.GRIM_CUISINE));
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		if (stack.getDamage() >= stack.getMaxDamage()-1)
			return false;
		else
			return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		itemStack.setDamage(itemStack.getDamage() + 1);
		return itemStack.copy();
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualCuisine.extras_page;
	}
}
