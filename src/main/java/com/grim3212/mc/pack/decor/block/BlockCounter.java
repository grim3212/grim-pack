package com.grim3212.mc.pack.decor.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCounter extends BlockColorizer {

	public static final AxisAlignedBB COUNTER_AABB = new AxisAlignedBB(0.0F, 1.0F, 0.0F, 1.0F, 0.81F, 1.0F);

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return COUNTER_AABB;
	}
}
