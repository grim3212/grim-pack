package com.grim3212.mc.pack.world.items;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.world.blocks.BlockRune;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemRune extends ItemManualBlock {

	public ItemRune(Block i) {
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		BlockRune.EnumRuneType type = BlockRune.EnumRuneType.values[stack.getMetadata()];
		return super.getUnlocalizedName() + "." + type.getName();
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}
}