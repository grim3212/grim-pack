package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemManualPage extends ItemManual implements IManualItem {

	private String page;

	public ItemManualPage(String name, String page, Item.Properties props) {
		super(name, props);
		this.page = page;
	}

	public ItemManualPage(String name, String page) {
		this(name, page, new Item.Properties());
	}
	
	public ItemManualPage(String name, String page, ItemGroup group) {
		this(name, page, new Item.Properties().group(group));
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualRegistry.getPageFromString(this.page);
	}
}
