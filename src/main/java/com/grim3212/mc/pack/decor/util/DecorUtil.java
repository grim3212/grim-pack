package com.grim3212.mc.pack.decor.util;

import com.grim3212.mc.pack.core.util.NBTHelper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class DecorUtil {

	public static ItemStack createFurnitureWithState(Block block, IBlockState toPlace) {
		ItemStack furniture = new ItemStack(block);
		NBTHelper.setString(furniture, "registryName", Block.REGISTRY.getNameForObject(toPlace.getBlock()).toString());
		NBTHelper.setInteger(furniture, "meta", toPlace.getBlock().getMetaFromState(toPlace));
		return furniture;
	}

	public static ItemStack createStackFromState(IBlockState state) {
		return new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
	}
}
