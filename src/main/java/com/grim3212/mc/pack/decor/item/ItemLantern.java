package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.part.GrimItemGroups;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ItemLantern extends ItemManualBlock {

	public ItemLantern(Block i) {
		super(i, new Item.Properties().group(GrimItemGroups.GRIM_DECOR));
	}
}
