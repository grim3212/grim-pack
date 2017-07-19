package com.grim3212.mc.pack.decor.block.colorizer;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;
import com.grim3212.mc.pack.decor.util.BlockHelper;
import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.event.DoubleTrapDoor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockColorizerTrapDoor extends BlockColorizer {

	protected static final AxisAlignedBB EAST_OPEN_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);
	protected static final AxisAlignedBB WEST_OPEN_AABB = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB SOUTH_OPEN_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
	protected static final AxisAlignedBB NORTH_OPEN_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB BOTTOM_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D);
	protected static final AxisAlignedBB TOP_AABB = new AxisAlignedBB(0.0D, 0.8125D, 0.0D, 1.0D, 1.0D, 1.0D);

	public BlockColorizerTrapDoor() {
		super("decor_trap_door");
	}

	@Override
	protected IBlockState getState() {
		return this.blockState.getBaseState().withProperty(BlockTrapDoor.FACING, EnumFacing.NORTH).withProperty(BlockTrapDoor.OPEN, false).withProperty(BlockTrapDoor.HALF, BlockTrapDoor.DoorHalf.BOTTOM);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		AxisAlignedBB axisalignedbb;

		if (state.getValue(BlockTrapDoor.OPEN)) {
			switch (state.getValue(BlockTrapDoor.FACING)) {
			case NORTH:
			default:
				axisalignedbb = NORTH_OPEN_AABB;
				break;
			case SOUTH:
				axisalignedbb = SOUTH_OPEN_AABB;
				break;
			case WEST:
				axisalignedbb = WEST_OPEN_AABB;
				break;
			case EAST:
				axisalignedbb = EAST_OPEN_AABB;
			}
		} else if (state.getValue(BlockTrapDoor.HALF) == BlockTrapDoor.DoorHalf.TOP) {
			axisalignedbb = TOP_AABB;
		} else {
			axisalignedbb = BOTTOM_AABB;
		}

		return axisalignedbb;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks
	 * for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return !worldIn.getBlockState(pos).getValue(BlockTrapDoor.OPEN);
	}

	@SuppressWarnings("deprecation")
	public boolean changeDoors(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side) {
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

	/**
	 * Called when the block is right clicked by a player.
	 */
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = playerIn.getHeldItem(hand);

		if (!heldItem.isEmpty()) {
			if (heldItem.getItem() == DecorItems.brush) {
				if (this.tryUseBrush(worldIn, playerIn, hand, pos)) {
					return true;
				}
			}

			Block block = Block.getBlockFromItem(heldItem.getItem());
			if (block != Blocks.AIR) {
				if (changeDoors(worldIn, pos, state, playerIn, hand, facing)) {
					return true;
				}
			}
		}

		state = state.cycleProperty(BlockTrapDoor.OPEN);

		if (UtilConfig.doubleDoors) {
			DoubleTrapDoor.setDoubleTrap(worldIn, pos, state, true);
		}

		// Set after check to make sure all neighbors are open
		worldIn.setBlockState(pos, state, 2);

		this.playSound(playerIn, worldIn, pos, state.getValue(BlockTrapDoor.OPEN));
		return true;
	}

	protected void playSound(@Nullable EntityPlayer player, World worldIn, BlockPos pos, boolean open) {
		if (open) {
			int i = this.blockMaterial == Material.IRON ? 1037 : 1007;
			worldIn.playEvent(player, i, pos, 0);
		} else {
			int j = this.blockMaterial == Material.IRON ? 1036 : 1013;
			worldIn.playEvent(player, j, pos, 0);
		}
	}

	/**
	 * Called when a neighboring block was changed and marks that this state
	 * should perform any checks during a neighbor change. Cases may include
	 * when redstone power is updated, cactus blocks popping off due to a
	 * neighboring solid block, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote) {
			boolean flag = worldIn.isBlockPowered(pos);

			if (flag || blockIn.getDefaultState().canProvidePower()) {
				boolean flag1 = state.getValue(BlockTrapDoor.OPEN);

				if (flag1 != flag) {
					worldIn.setBlockState(pos, state.withProperty(BlockTrapDoor.OPEN, flag), 2);
					this.playSound((EntityPlayer) null, worldIn, pos, flag);
				}
			}
		}
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to
	 * allow for adjustments to the IBlockstate
	 */
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState iblockstate = this.getDefaultState();

		if (facing.getAxis().isHorizontal()) {
			iblockstate = iblockstate.withProperty(BlockTrapDoor.FACING, facing).withProperty(BlockTrapDoor.OPEN, false);
			iblockstate = iblockstate.withProperty(BlockTrapDoor.HALF, hitY > 0.5F ? BlockTrapDoor.DoorHalf.TOP : BlockTrapDoor.DoorHalf.BOTTOM);
		} else {
			iblockstate = iblockstate.withProperty(BlockTrapDoor.FACING, placer.getHorizontalFacing().getOpposite()).withProperty(BlockTrapDoor.OPEN, false);
			iblockstate = iblockstate.withProperty(BlockTrapDoor.HALF, facing == EnumFacing.UP ? BlockTrapDoor.DoorHalf.BOTTOM : BlockTrapDoor.DoorHalf.TOP);
		}

		if (worldIn.isBlockPowered(pos)) {
			iblockstate = iblockstate.withProperty(BlockTrapDoor.OPEN, true);
		}

		return iblockstate;
	}

	/**
	 * Check whether this Block can be placed on the given side
	 */
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		return true;
	}

	private EnumFacing getFacing(int meta) {
		switch (meta & 3) {
		case 0:
			return EnumFacing.NORTH;
		case 1:
			return EnumFacing.SOUTH;
		case 2:
			return EnumFacing.WEST;
		case 3:
		default:
			return EnumFacing.EAST;
		}
	}

	private int getMetaForFacing(EnumFacing facing) {
		switch (facing) {
		case NORTH:
			return 0;
		case SOUTH:
			return 1;
		case WEST:
			return 2;
		case EAST:
		default:
			return 3;
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BlockTrapDoor.FACING, getFacing(meta)).withProperty(BlockTrapDoor.OPEN, (meta & 4) != 0).withProperty(BlockTrapDoor.HALF, (meta & 8) == 0 ? BlockTrapDoor.DoorHalf.BOTTOM : BlockTrapDoor.DoorHalf.TOP);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0 | getMetaForFacing(state.getValue(BlockTrapDoor.FACING));

		if (state.getValue(BlockTrapDoor.OPEN)) {
			i |= 4;
		}

		if (state.getValue(BlockTrapDoor.HALF) == BlockTrapDoor.DoorHalf.TOP) {
			i |= 8;
		}

		return i;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed
	 * blockstate. If inapplicable, returns the passed blockstate.
	 */
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(BlockTrapDoor.FACING, rot.rotate(state.getValue(BlockTrapDoor.FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(BlockTrapDoor.FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { BlockTrapDoor.FACING, BlockTrapDoor.OPEN, BlockTrapDoor.HALF }, new IUnlistedProperty[] { BLOCK_STATE });
	}

	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
		if (state.getValue(BlockTrapDoor.OPEN)) {
			IBlockState down = world.getBlockState(pos.down());
			if (down.getBlock() == Blocks.LADDER)
				return down.getValue(BlockLadder.FACING) == state.getValue(BlockTrapDoor.FACING);
		}
		return false;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.decorTrapDoor_page;
	}
}
