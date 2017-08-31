package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.core.item.ItemManualBlock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

@SuppressWarnings("deprecation")
public class ItemShapedCharge extends ItemManualBlock {

	public ItemShapedCharge(Block i) {
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return super.getItemStackDisplayName(stack) + I18n.translateToLocalFormatted("tile.shaped_charge.radius", stack.getMetadata());
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}
}
