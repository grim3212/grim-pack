package com.grim3212.mc.industry.item;

import com.grim3212.mc.industry.GrimIndustry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;

public class ItemModernDoor extends ItemDoor {

	public ItemModernDoor(Block block) {
		super(block);
		this.setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
	}

}
