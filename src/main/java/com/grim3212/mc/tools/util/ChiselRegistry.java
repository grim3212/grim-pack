package com.grim3212.mc.tools.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ChiselRegistry {

	public static List<Block> chiselBlocks = new ArrayList<Block>();
	public static List<Item> chiselItem = new ArrayList<Item>();
	public static List<Integer> chiselItemQuantity = new ArrayList<Integer>();
	public static List<Integer> chiselItemDamage = new ArrayList<Integer>();
	public static List<Block> chiselReturnb = new ArrayList<Block>();

	public static void registerBlock(Block inputBlock, Block returnBlock, Item item, int itemQuantity, int itemDamage) {
		chiselBlocks.add(inputBlock);
		chiselReturnb.add(returnBlock);
		chiselItem.add(item);
		chiselItemQuantity.add(itemQuantity);
		chiselItemDamage.add(itemDamage);
	}
}
