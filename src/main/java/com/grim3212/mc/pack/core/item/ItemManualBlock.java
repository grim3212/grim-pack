package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemManualBlock extends ItemBlock implements IManualItem {

	private IManualBlock manual;

	public ItemManualBlock(Block block) {
		super(block);
		if (block instanceof IManualBlock)
			this.manual = (IManualBlock) block;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("deprecation")
	public Page getPage(ItemStack stack) {
		if (manual != null)
			return manual.getPage(Minecraft.getMinecraft().world, null, Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getMetadata()));
		else
			return null;
	}

}
