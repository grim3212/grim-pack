package com.grim3212.mc.pack.cuisine.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.init.CuisineNames;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCocoaFruit extends ItemBlock implements IManualItem {

	public ItemCocoaFruit() {
		super(CuisineBlocks.cocoa_tree_sapling, new Item.Properties().maxStackSize(16).group(GrimItemGroups.GRIM_CUISINE));
		setRegistryName(CuisineNames.COCOA_FRUIT);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualCuisine.cocoaFruit_page;
	}
}
