package com.grim3212.mc.decor.block;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWall extends BlockFurnitureRotate {

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		EnumFacing facing = (EnumFacing) worldIn.getBlockState(pos).getValue(FACING);

		float f1 = 0;
		float f2 = 0;
		float f3 = 1;
		float f4 = 1;

		if (facing == EnumFacing.EAST) {
			f1 = 0.81F;
		} else if (facing == EnumFacing.NORTH) {
			f4 = 0.19F;
		} else if (facing == EnumFacing.WEST) {
			f3 = 0.19F;
		} else if (facing == EnumFacing.SOUTH) {
			f2 = 0.81F;
		}

		this.setBlockBounds(f1, 0F, f2, f3, 1F, f4);
	}

	@Override
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
		EnumFacing facing = (EnumFacing) worldIn.getBlockState(pos).getValue(FACING);
		float f1 = 0;
		float f2 = 0;
		float f3 = 1;
		float f4 = 1;

		if (facing == EnumFacing.EAST) {
			f1 = 0.81F;
		} else if (facing == EnumFacing.NORTH) {
			f4 = 0.19F;
		} else if (facing == EnumFacing.WEST) {
			f3 = 0.19F;
		} else if (facing == EnumFacing.SOUTH) {
			f2 = 0.81F;
		}

		this.setBlockBounds(f1, 0F, f2, f3, 1F, f4);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
	}

}
