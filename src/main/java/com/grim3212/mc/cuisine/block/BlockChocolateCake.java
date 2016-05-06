package com.grim3212.mc.cuisine.block;

import net.minecraft.block.BlockCake;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockChocolateCake extends BlockCake {

	protected BlockChocolateCake() {
		super();
	}

	@Override
	public Item getItem(World worldIn, BlockPos pos) {
		return Item.getItemFromBlock(CuisineBlocks.chocolate_cake);
	}
}
