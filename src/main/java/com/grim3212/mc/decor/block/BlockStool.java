package com.grim3212.mc.decor.block;

import java.util.List;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockStool extends BlockTextured {

	public static final PropertyBool UP = PropertyBool.create("up");

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos.up()).getBlock() != DecorBlocks.pot) {
			this.setBlockBounds(0.18F, 0.0F, 0.18F, 0.82F, 0.63F, 0.82F);
		} else if (worldIn.getBlockState(pos.up()).getBlock() == DecorBlocks.pot) {
			this.setBlockBounds(0.18F, 0.0F, 0.18F, 0.82F, 1F, 0.82F);
		} else {
			super.setBlockBoundsBasedOnState(worldIn, pos);
		}
	}

	@Override
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
		if (worldIn.getBlockState(pos.up()).getBlock() != DecorBlocks.pot) {
			this.setBlockBounds(0.18F, 0.0F, 0.18F, 0.82F, 0.60F, 0.82F);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		} else if (worldIn.getBlockState(pos.up()).getBlock() == DecorBlocks.pot) {
			this.setBlockBounds(0.18F, 0.0F, 0.18F, 0.82F, 1F, 0.82F);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		} else {
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		}
	}

	@Override
	protected BlockState createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { UP }, new IUnlistedProperty[] { BLOCKID, BLOCKMETA });
	}

	@Override
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState().withProperty(UP, false);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(UP, this.isPot(worldIn, pos.up()));
	}

	private boolean isPot(IBlockAccess worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getBlock() == DecorBlocks.pot;
	}
}