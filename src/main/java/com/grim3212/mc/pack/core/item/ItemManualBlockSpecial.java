package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;

public class ItemManualBlockSpecial extends ItemBlockSpecial implements IManualItem {

	private IManualBlock manual;

	public ItemManualBlockSpecial(Block block) {
		super(block);
		if (block instanceof IManualBlock)
			this.manual = (IManualBlock) block;
	}

	@Override
	@SuppressWarnings("deprecation")
	public Page getPage(ItemStack stack) {
		if (manual != null)
			return manual.getPage(Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getMetadata()));
		else
			return null;
	}

}
