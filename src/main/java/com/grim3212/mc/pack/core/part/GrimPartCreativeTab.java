package com.grim3212.mc.pack.core.part;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class GrimPartCreativeTab extends CreativeTabs {

	private GrimPart part;

	public GrimPartCreativeTab(GrimPart grimPart) {
		super(CreativeTabs.getNextID(), grimPart.getPartId());
		this.part = grimPart;
	}

	@Override
	public String getTranslatedTabLabel() {
		return "grim.creative." + this.getTabLabel();
	}

	@Override
	public Item getTabIconItem() {
		return this.part.getCreativeTabIcon();
	}
}
