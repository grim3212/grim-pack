package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemManualSword extends ItemSword implements IManualItem {

	private String link;

	public ItemManualSword(ToolMaterial material, String link) {
		super(material);
		this.link = link;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualRegistry.getPageFromString(link);
	}
}
