package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockWall extends BlockFurnitureRotate implements IManualBlock {

	private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.81F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.19F, 1.0F, 1.0F);
	private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.19F);
	private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.81F, 1.0F, 1F, 1F);

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(FACING)) {
		case EAST:
			return EAST_AABB;
		case NORTH:
			return NORTH_AABB;
		case SOUTH:
			return SOUTH_AABB;
		case WEST:
			return WEST_AABB;
		default:
			return FULL_BLOCK_AABB;
		}
	}
	
	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.wall_page;
	}
}
