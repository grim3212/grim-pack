package com.grim3212.mc.pack.decor.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;

public class ItemDecorDoor extends ItemColorizer {

	public ItemDecorDoor(Block block) {
		super(block);
	}

	@Override
	protected boolean placeBlock(BlockItemUseContext context, BlockState state) {
		context.getWorld().setBlockState(context.getPos().up(), Blocks.AIR.getDefaultState(), 27);
		return super.placeBlock(context, state);
	}
}
