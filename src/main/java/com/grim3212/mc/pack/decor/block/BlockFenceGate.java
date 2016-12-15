package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockFenceGate extends BlockFurnitureRotate {

	public static final PropertyBool OPEN = PropertyBool.create("open");
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	protected static final AxisAlignedBB AABB_COLLIDE_ZAXIS = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
	protected static final AxisAlignedBB AABB_COLLIDE_XAXIS = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_CLOSED_SELECTED_ZAXIS = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.5D, 0.625D);
	protected static final AxisAlignedBB AABB_CLOSED_SELECTED_XAXIS = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.5D, 1.0D);

	public BlockFenceGate() {
		this.setDefaultState(this.getDefaultState().withProperty(OPEN, false).withProperty(POWERED, false));
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).getMaterial().isSolid() ? super.canPlaceBlockAt(worldIn, pos) : false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos) {
		return blockState.getValue(OPEN) ? NULL_AABB : (blockState.getValue(FACING).getAxis() == EnumFacing.Axis.Z ? AABB_CLOSED_SELECTED_ZAXIS : AABB_CLOSED_SELECTED_XAXIS);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getValue(FACING).getAxis() == EnumFacing.Axis.X ? AABB_COLLIDE_XAXIS : AABB_COLLIDE_ZAXIS;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getValue(OPEN);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!playerIn.getHeldItem(hand).isEmpty()) {
			if (super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ)) {
				return true;
			}
		}

		if (state.getValue(OPEN)) {
			state = state.withProperty(OPEN, false);
			worldIn.setBlockState(pos, state, 10);
		} else {
			EnumFacing enumfacing = EnumFacing.fromAngle((double) playerIn.rotationYaw);

			if (state.getValue(FACING) == enumfacing.getOpposite()) {
				state = state.withProperty(FACING, enumfacing);
			}

			state = state.withProperty(OPEN, true);
			worldIn.setBlockState(pos, state, 10);
		}

		worldIn.playEvent(playerIn, state.getValue(OPEN) ? 1008 : 1014, pos, 0);
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote) {
			boolean flag = worldIn.isBlockPowered(pos);

			if (flag || blockIn.getDefaultState().canProvidePower()) {
				if (flag && !state.getValue(OPEN) && !state.getValue(POWERED)) {
					worldIn.setBlockState(pos, state.withProperty(OPEN, true).withProperty(POWERED, true), 2);
					worldIn.playEvent((EntityPlayer) null, 1008, pos, 0);
				} else if (!flag && state.getValue(OPEN) && state.getValue(POWERED)) {
					worldIn.setBlockState(pos, state.withProperty(OPEN, false).withProperty(POWERED, false), 2);
					worldIn.playEvent((EntityPlayer) null, 1014, pos, 0);
				} else if (flag != state.getValue(POWERED)) {
					worldIn.setBlockState(pos, state.withProperty(POWERED, flag), 2);
				}
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(POWERED, (meta & 8) != 0).withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(OPEN, (meta & 4) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | state.getValue(FACING).getHorizontalIndex();

		if (state.getValue(POWERED)) {
			i |= 8;
		}

		if (state.getValue(OPEN)) {
			i |= 4;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { FACING, OPEN, POWERED }, new IUnlistedProperty[] { BLOCK_STATE });
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.fenceGate_page;
	}
}
