package com.grim3212.mc.pack.decor.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockStool extends BlockTextured {

	public static final PropertyBool UP = PropertyBool.create("up");
	private static final AxisAlignedBB STOOL_AABB = new AxisAlignedBB(0.18F, 0.0F, 0.18F, 0.82F, 0.63F, 0.82F);
	private static final AxisAlignedBB WALKING_STOOL_AABB = new AxisAlignedBB(0.18F, 0.0F, 0.18F, 0.82F, 0.60F, 0.82F);
	private static final AxisAlignedBB POT_STOOL_AABB = new AxisAlignedBB(0.18F, 0.0F, 0.18F, 0.82F, 1F, 0.82F);

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getActualState(source, pos).getValue(UP) ? POT_STOOL_AABB : STOOL_AABB;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return blockState.getActualState(worldIn, pos).getValue(UP) ? POT_STOOL_AABB : WALKING_STOOL_AABB;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { UP }, new IUnlistedProperty[] { BLOCKID, BLOCKMETA });
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