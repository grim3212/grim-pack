package com.grim3212.mc.decor.item;

import com.grim3212.mc.decor.block.BlockLantern;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemLantern extends ItemBlock {

	public ItemLantern(Block i) {
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		BlockLantern.EnumLanternType type = BlockLantern.EnumLanternType.values[stack.getMetadata()];
		return super.getUnlocalizedName() + "_" + type.getName();
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}
}
