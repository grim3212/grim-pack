package com.grim3212.mc.pack.decor.block.colorizer;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;
import com.grim3212.mc.pack.decor.util.BlockHelper;
import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.event.DoubleFenceGate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockColorizerFenceGate extends BlockColorizerFurnitureRotate {

	protected static final AxisAlignedBB AABB_COLLIDE_ZAXIS = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
	protected static final AxisAlignedBB AABB_COLLIDE_XAXIS = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_COLLIDE_ZAXIS_INWALL = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 0.8125D, 0.625D);
	protected static final AxisAlignedBB AABB_COLLIDE_XAXIS_INWALL = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 0.8125D, 1.0D);
	protected static final AxisAlignedBB AABB_CLOSED_SELECTED_ZAXIS = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.5D, 0.625D);
	protected static final AxisAlignedBB AABB_CLOSED_SELECTED_XAXIS = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.5D, 1.0D);

	public BlockColorizerFenceGate() {
		super("fence_gate");
	}

	@Override
	protected IBlockState getState() {
		return this.getDefaultState().withProperty(BlockFenceGate.OPEN, false).withProperty(BlockFenceGate.POWERED, false).withProperty(BlockFenceGate.IN_WALL, false);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).getMaterial().isSolid() ? super.canPlaceBlockAt(worldIn, pos) : false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = this.getActualState(state, source, pos);
		return state.getValue(BlockFenceGate.IN_WALL) ? state.getValue(FACING).getAxis() == EnumFacing.Axis.X ? AABB_COLLIDE_XAXIS_INWALL : AABB_COLLIDE_ZAXIS_INWALL : state.getValue(FACING).getAxis() == EnumFacing.Axis.X ? AABB_COLLIDE_XAXIS : AABB_COLLIDE_ZAXIS;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		EnumFacing.Axis enumfacing$axis = state.getValue(FACING).getAxis();

		if (enumfacing$axis == EnumFacing.Axis.Z && (canFenceGateConnectTo(worldIn, pos, EnumFacing.WEST) || canFenceGateConnectTo(worldIn, pos, EnumFacing.EAST)) || enumfacing$axis == EnumFacing.Axis.X && (canFenceGateConnectTo(worldIn, pos, EnumFacing.NORTH) || canFenceGateConnectTo(worldIn, pos, EnumFacing.SOUTH))) {
			state = state.withProperty(BlockFenceGate.IN_WALL, true);
		}

		return state;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return blockState.getValue(BlockFenceGate.OPEN) ? NULL_AABB : blockState.getValue(FACING).getAxis() == EnumFacing.Axis.Z ? AABB_CLOSED_SELECTED_ZAXIS : AABB_CLOSED_SELECTED_XAXIS;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getValue(BlockFenceGate.OPEN);
	}

	@SuppressWarnings("deprecation")
	public boolean changeGates(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		ItemStack heldItem = playerIn.getHeldItem(hand);

		if (!heldItem.isEmpty() && tileentity instanceof TileEntityColorizer) {
			TileEntityColorizer te = (TileEntityColorizer) tileentity;
			Block block = Block.getBlockFromItem(heldItem.getItem());

			if (block != null && !(block instanceof BlockColorizer)) {
				if (BlockHelper.getUsableBlocks().contains(block)) {
					// Can only set blockstate if it contains nothing or if
					// in creative mode
					if (te.getBlockState() == Blocks.AIR.getDefaultState() || playerIn.capabilities.isCreativeMode) {

						IBlockState toPlaceState = block.getStateFromMeta(heldItem.getMetadata());
						this.setColorizer(worldIn, pos, state, toPlaceState, playerIn, hand, true);

						worldIn.playSound(playerIn, pos, block.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (block.getSoundType().getVolume() + 1.0F) / 2.0F, block.getSoundType().getPitch() * 0.8F);
						return true;
					} else if (te.getBlockState() != Blocks.AIR.getDefaultState()) {
						return false;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = playerIn.getHeldItem(hand);

		if (!heldItem.isEmpty()) {
			if (heldItem.getItem() == DecorItems.brush) {
				if (this.tryUseBrush(worldIn, playerIn, hand, pos)) {
					return true;
				}
			}

			Block block = Block.getBlockFromItem(heldItem.getItem());
			if (block != Blocks.AIR) {
				if (changeGates(worldIn, pos, state, playerIn, hand, side)) {
					return true;
				}
			}
		}

		if (state.getValue(BlockFenceGate.OPEN)) {
			state = state.withProperty(BlockFenceGate.OPEN, false);
			worldIn.setBlockState(pos, state, 10);
		} else {
			EnumFacing enumfacing = EnumFacing.fromAngle((double) playerIn.rotationYaw);

			if (state.getValue(FACING) == enumfacing.getOpposite()) {
				state = state.withProperty(FACING, enumfacing);
			}

			state = state.withProperty(BlockFenceGate.OPEN, true);
			worldIn.setBlockState(pos, state, 10);
		}

		if (UtilConfig.doubleDoors) {
			DoubleFenceGate.setFenceGate(worldIn, pos, state);
		}

		worldIn.playEvent(playerIn, state.getValue(BlockFenceGate.OPEN) ? 1008 : 1014, pos, 0);
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote) {
			boolean flag = worldIn.isBlockPowered(pos);

			if (flag || blockIn.getDefaultState().canProvidePower()) {
				if (flag && !state.getValue(BlockFenceGate.OPEN) && !state.getValue(BlockFenceGate.POWERED)) {
					worldIn.setBlockState(pos, state.withProperty(BlockFenceGate.OPEN, true).withProperty(BlockFenceGate.POWERED, true), 2);
					worldIn.playEvent((EntityPlayer) null, 1008, pos, 0);
				} else if (!flag && state.getValue(BlockFenceGate.OPEN) && state.getValue(BlockFenceGate.POWERED)) {
					worldIn.setBlockState(pos, state.withProperty(BlockFenceGate.OPEN, false).withProperty(BlockFenceGate.POWERED, false), 2);
					worldIn.playEvent((EntityPlayer) null, 1014, pos, 0);
				} else if (flag != state.getValue(BlockFenceGate.POWERED)) {
					worldIn.setBlockState(pos, state.withProperty(BlockFenceGate.POWERED, flag), 2);
				}
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BlockFenceGate.POWERED, (meta & 8) != 0).withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(BlockFenceGate.OPEN, (meta & 4) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | state.getValue(FACING).getHorizontalIndex();

		if (state.getValue(BlockFenceGate.POWERED)) {
			i |= 8;
		}

		if (state.getValue(BlockFenceGate.OPEN)) {
			i |= 4;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { FACING, BlockFenceGate.OPEN, BlockFenceGate.POWERED, BlockFenceGate.IN_WALL }, new IUnlistedProperty[] { BLOCK_STATE });
	}

	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		Block connector = world.getBlockState(pos.offset(facing)).getBlock();
		return connector instanceof BlockColorizerFence || connector instanceof BlockColorizerWall;
	}

	private boolean canFenceGateConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		Block block = world.getBlockState(pos.offset(facing)).getBlock();
		return block.canBeConnectedTo(world, pos.offset(facing), facing.getOpposite());
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.fenceGate_page;
	}
}
