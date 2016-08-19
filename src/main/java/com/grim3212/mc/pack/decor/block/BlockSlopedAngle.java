package com.grim3212.mc.pack.decor.block;

import java.util.List;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.block.BlockSloped.EnumHalf;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSlopedAngle extends BlockSlopedRotate {

	private static final AxisAlignedBB BASE_BOTTOM_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.5f, 1.0F);
	private static final AxisAlignedBB EAST_BOTTOM_AABB = new AxisAlignedBB(0.5F, 0.0F, 0.5F, 1.0F, 1.0F, 0.0F);
	private static final AxisAlignedBB NORTH_BOTTOM_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 0.5F);
	private static final AxisAlignedBB WEST_BOTTOM_AABB = new AxisAlignedBB(0.0F, 0.0F, 1.0F, 0.5F, 1.0F, 0.5F);
	private static final AxisAlignedBB SOUTH_BOTTOM_AABB = new AxisAlignedBB(0.5F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);

	private static final AxisAlignedBB BASE_TOP_AABB = new AxisAlignedBB(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
	private static final AxisAlignedBB EAST_TOP_AABB = new AxisAlignedBB(1.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D);
	private static final AxisAlignedBB NORTH_TOP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D);
	private static final AxisAlignedBB WEST_TOP_AABB = new AxisAlignedBB(0.0D, 0.0D, 1.0D, 0.5D, 1.0D, 0.5D);
	private static final AxisAlignedBB SOUTH_TOP_AABB = new AxisAlignedBB(1.0D, 0.0D, 1.0D, 0.5D, 1.0D, 0.5D);

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.slopedAngle_page;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
		if (state.getValue(BlockSloped.HALF) == EnumHalf.BOTTOM) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_BOTTOM_AABB);

			switch (state.getValue(FACING)) {
			case EAST:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_BOTTOM_AABB);
				break;
			case NORTH:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_BOTTOM_AABB);
				break;
			case SOUTH:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_BOTTOM_AABB);
				break;
			case WEST:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_BOTTOM_AABB);
				break;
			default:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, FULL_BLOCK_AABB);
				break;
			}
		} else {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_TOP_AABB);

			switch (state.getValue(FACING)) {
			case EAST:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_TOP_AABB);
				break;
			case NORTH:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_TOP_AABB);
				break;
			case SOUTH:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_TOP_AABB);
				break;
			case WEST:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_TOP_AABB);
				break;
			default:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, FULL_BLOCK_AABB);
				break;
			}
		}
	}

}
