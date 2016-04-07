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

public class BlockTable extends BlockTextured {

	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
		this.setBlockBounds(0.0F, 0.8F, 0.0F, 1.0F, 1F, 1.0F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
	}

	@Override
	protected BlockState createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { NORTH, SOUTH, WEST, EAST }, new IUnlistedProperty[] { BLOCKID, BLOCKMETA });
	}

	@Override
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(NORTH, this.canConnectTo(worldIn, pos.north())).withProperty(EAST, this.canConnectTo(worldIn, pos.east())).withProperty(SOUTH, this.canConnectTo(worldIn, pos.south())).withProperty(WEST, this.canConnectTo(worldIn, pos.west()));
	}

	public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		IBlockState blockState = worldIn.getBlockState(pos).getBlock().getDefaultState();
		return blockState == this.getDefaultState();
	}
}
