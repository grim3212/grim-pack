package com.grim3212.mc.pack.decor.block;

import java.util.List;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.block.BlockSloped.EnumHalf;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.util.DecorUtil;
import com.grim3212.mc.pack.decor.util.DecorUtil.SlopeType;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockSlopedRotate extends BlockFurnitureRotate implements IManualBlock {

	private final SlopeType type;

	public BlockSlopedRotate(SlopeType type) {
		this.type = type;
		this.blockState.getBaseState().withProperty(BlockSloped.HALF, EnumHalf.BOTTOM).withProperty(FACING, EnumFacing.NORTH);
	}

	// @Override
	// public boolean isVisuallyOpaque() {
	// return false;
	// }

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { BlockSloped.HALF, FACING }, new IUnlistedProperty[] { BLOCK_STATE });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState().withProperty(BlockSloped.HALF, (meta & 4) > 0 ? EnumHalf.TOP : EnumHalf.BOTTOM);
		iblockstate = iblockstate.withProperty(FACING, EnumFacing.getFront(5 - (meta & 3)));
		return iblockstate;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if (state.getValue(BlockSloped.HALF) == EnumHalf.TOP) {
			i |= 4;
		}

		i = i | 5 - state.getValue(FACING).getIndex();
		return i;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public Page getPage(IBlockState state) {
		switch (this.type) {
		case Corner:
			return ManualDecor.corner_page;
		case SlantedCorner:
			return ManualDecor.slantedCorner_page;
		case Slope:
			return ManualDecor.slope_page;
		case SlopedAngle:
			return ManualDecor.slopedAngle_page;
		case ObliqueSlope:
			return ManualDecor.obliqueSlope_page;
		case SlopedIntersection:
			return ManualDecor.slopedIntersection_page;
		default:
			return null;
		}
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean flag) {
		DecorUtil.addAxisAlignedBoxes(pos, entityBox, collidingBoxes, state, this.type.getNumPieces());
	}
}
