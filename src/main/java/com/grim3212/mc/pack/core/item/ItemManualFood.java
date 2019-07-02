package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemManualFood extends Item implements IManualItem {

	private String page;

	public ItemManualFood(String name, String page, Item.Properties props) {
		super(props);
		this.page = page;
		this.setRegistryName(name);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualRegistry.getPageFromString(this.page);
	}

}
