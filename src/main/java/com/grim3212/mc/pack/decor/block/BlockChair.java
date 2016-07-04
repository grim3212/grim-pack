package com.grim3212.mc.pack.decor.block;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockChair extends BlockFurnitureRotate {

	private static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0, 0, 0, 1, 0.5f, 1);
	private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.81F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.19F);
	private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.19F, 1.0F, 1.0F);
	private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.81F, 1.0F, 1.0F, 1.0F);

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_AABB);

		switch (state.getValue(FACING)) {
		case EAST:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
			break;
		case NORTH:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
			break;
		case SOUTH:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
			break;
		case WEST:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
			break;
		default:
			break;
		}
	}
}
