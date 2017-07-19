package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockColorizerFacing extends BlockColorizer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	private static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.0F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
	private static final AxisAlignedBB Y_AABB = new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
	private static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 1.0F);

	public BlockColorizerFacing(String name) {
		super(name);
	}

	@Override
	protected IBlockState getState() {
		return this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (state.getBlock() == DecorBlocks.pillar) {
			switch (state.getValue(FACING)) {
			case WEST:
			case EAST:
				return X_AABB;
			case NORTH:
			case SOUTH:
				return Z_AABB;
			case UP:
			case DOWN:
			default:
				return Y_AABB;
			}
		}

		return FULL_BLOCK_AABB;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { FACING }, new IUnlistedProperty[] { BLOCK_STATE });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	public Page getPage(IBlockState state) {
		if (state.getBlock() == DecorBlocks.pillar) {
			return ManualDecor.pillar_page;
		}

		return null;
	}

}
