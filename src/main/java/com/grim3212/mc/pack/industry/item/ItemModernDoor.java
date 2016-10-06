package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;

public class ItemModernDoor extends ItemDoor implements IManualItem {

	public ItemModernDoor(Block block) {
		super(block);
		this.setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualIndustry.doors_page;
	}

}
