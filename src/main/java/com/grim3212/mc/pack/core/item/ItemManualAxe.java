package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemManualAxe extends ItemAxe implements IManualItem {

	private String page;

	public ItemManualAxe(ToolMaterial toolMaterial, float damage, float speed, String page) {
		super(toolMaterial, damage, speed);
		this.page = page;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualRegistry.getPageFromString(this.page);
	}

}
