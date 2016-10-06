package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemManual extends Item implements IManualItem {

	public ItemManual() {
	}

	@Override
	public abstract Page getPage(ItemStack stack);

}
