package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemManualBlock extends BlockItem implements IManualItem {

	private IManualBlock manual;

	public ItemManualBlock(Block block) {
		this(block, new Item.Properties());
	}

	public ItemManualBlock(Block block, Item.Properties props) {
		super(block, props);
		if (block instanceof IManualBlock)
			this.manual = (IManualBlock) block;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public Page getPage(ItemStack stack) {
		if (manual != null)
			return manual.getPage(Minecraft.getInstance().world, null, Block.getBlockFromItem(stack.getItem()).getDefaultState());
		else
			return null;
	}

}
