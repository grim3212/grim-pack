package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemManualSpade extends ItemSpade implements IManualItem {

	private String link;

	public ItemManualSpade(ToolMaterial material, String link) {
		super(material);
		this.link = link;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualRegistry.getPageFromString(link);
	}

}
