package com.grim3212.mc.pack.world.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemFungus extends ItemBlock {

	public ItemFungus(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return (new StringBuilder()).append(super.getUnlocalizedName()).append(".").append(Integer.toString(itemstack.getItemDamage())).toString();
	}
}
