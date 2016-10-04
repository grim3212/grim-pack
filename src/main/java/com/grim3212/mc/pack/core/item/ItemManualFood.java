package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemManualFood extends ItemFood implements IManualItem {

	private String page;

	public ItemManualFood(int amount, boolean isWolfFood, String page) {
		this(amount, 0.6f, isWolfFood, page);
	}

	public ItemManualFood(int amount, float saturation, boolean isWolfFood, String page) {
		super(amount, saturation, isWolfFood);
		this.page = page;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualRegistry.getPageFromString(this.page);
	}

}
