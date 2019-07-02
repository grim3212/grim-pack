package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizer;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerFacing;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerSlopedRotate;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerSlopedRotate.EnumHalf;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemSloped extends ItemColorizer {
	/*
	 * Determines if the rotation should be based on the direction the player is
	 * looking
	 */
	private boolean simpleRotate = false;

	public ItemSloped(Block block) {
		this(block, false);
	}

	public ItemSloped(Block block, boolean simpleRotate) {
		super(block);
		this.simpleRotate = simpleRotate;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		ActionResultType result = super.onItemUse(context);
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		Direction facing = context.getFace();

		EnumHalf half = EnumHalf.BOTTOM;
		if (!(facing != Direction.DOWN && (facing == Direction.UP || (double) context.getHitVec().getY() <= 0.5D))) {
			half = EnumHalf.TOP;
		}

		Block block = this.getBlock();
		// Placement successful
		if (block instanceof BlockColorizer) {
			BlockState state = world.getBlockState(pos);
			if (block instanceof BlockColorizerSlopedRotate) {
				if (!simpleRotate) {
					world.setBlockState(pos, state.with(BlockColorizerSlopedRotate.HORIZONTAL_FACING, Direction.byHorizontalIndex(MathHelper.floor(context.getPlayer().rotationYaw * 4.0F / 360.0F) & 0x3).rotateY()).with(BlockColorizerSlopedRotate.HALF, half), 3);
				} else {
					world.setBlockState(pos, state.with(BlockColorizerSlopedRotate.HORIZONTAL_FACING, context.getPlayer().getHorizontalFacing()).with(BlockColorizerSlopedRotate.HALF, half), 3);
				}
			} else if (block instanceof BlockColorizerFacing) {
				world.setBlockState(pos, state.with(BlockColorizerFacing.FACING, facing), 3);
			} else {
				world.setBlockState(pos, state.with(BlockColorizerSlopedRotate.HALF, half), 3);
			}
		}

		return result;
	}
}
