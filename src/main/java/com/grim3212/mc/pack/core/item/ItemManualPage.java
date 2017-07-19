package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.item.ItemStack;

public class ItemManualPage extends ItemManual implements IManualItem {

	private String page;

	public ItemManualPage(String name, String page, int stackSize) {
		super(name);
		this.page = page;
		this.setMaxStackSize(stackSize);
	}

	public ItemManualPage(String name, String page) {
		this(name, page, 64);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualRegistry.getPageFromString(this.page);
	}
}
