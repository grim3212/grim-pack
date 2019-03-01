package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class ItemManual extends Item implements IManualItem {

	public ItemManual(String name, Item.Properties props) {
		super(props);
		this.setRegistryName(new ResourceLocation(GrimPack.modID, name));
	}

	@Override
	public abstract Page getPage(ItemStack stack);

}
