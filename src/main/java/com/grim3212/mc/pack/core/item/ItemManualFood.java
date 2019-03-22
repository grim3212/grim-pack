package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemManualFood extends ItemFood implements IManualItem {

	private String page;

	public ItemManualFood(String name, int amount, boolean isWolfFood, String page, Item.Properties props) {
		this(name, amount, 0.6f, isWolfFood, page, props);
	}

	public ItemManualFood(String name, int amount, float saturation, boolean isWolfFood, String page, Item.Properties props) {
		super(amount, saturation, isWolfFood, props);
		this.page = page;
		this.setRegistryName(name);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualRegistry.getPageFromString(this.page);
	}

}
