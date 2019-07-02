package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.tile.TileEntityBridgeControl;
import com.grim3212.mc.pack.industry.util.EnumBridgeType;

import net.minecraft.block.*;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBridgeControl extends BlockManual {

	public static PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockBridgeControl() {
		super("bridge_control", Material.GROUND, SoundType.METAL);
		setHardness(1.0F);
		setResistance(1.0F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	protected BlockState getState() {
		return super.getState().withProperty(DirectionalBlock.FACING, Direction.NORTH).withProperty(ACTIVE, false);
	}

	@Override
	public Page getPage(World world, BlockPos pos, BlockState state) {
		if (world != null && pos != null) {
			return state.getActualState(world, pos).getValue(BlockBridge.TYPE).getPage();
		}

		return super.getPage(world, pos, state);
	}

	@Override
	public void getSubBlocks(ItemGroup itemIn, NonNullList<ItemStack> items) {
		for (int i = 0; i < EnumBridgeType.values.length; i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		ItemStack heldItem = playerIn.getHeldItem(hand);

		if (!heldItem.isEmpty() && !playerIn.isSneaking() && tileentity instanceof TileEntityBridgeControl) {
			TileEntityBridgeControl te = (TileEntityBridgeControl) tileentity;
			Block block = Block.getBlockFromItem(heldItem.getItem());

			if (block != null) {
				BlockState oldState = te.getBlockState();
				BlockState toSetState = block.getStateFromMeta(heldItem.getMetadata());

				if (oldState != toSetState && toSetState.getMaterial() != Material.CIRCUITS) {
					te.setBlockState(toSetState != null ? toSetState : Blocks.AIR.getDefaultState());

					// Toggle to update blocks
					worldIn.setBlockState(pos, state.withProperty(ACTIVE, false));

					worldIn.playSound(playerIn, pos, block.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (block.getSoundType().getVolume() + 1.0F) / 2.0F, block.getSoundType().getPitch() * 0.8F);

				}
				return true;
			} else {
				return false;
			}
		}

		if (playerIn.isSneaking()) {
			if (tileentity instanceof TileEntityBridgeControl) {
				TileEntityBridgeControl te = (TileEntityBridgeControl) tileentity;
				te.setBlockState(Blocks.AIR.getDefaultState());

				// Toggle to update blocks
				worldIn.setBlockState(pos, state.withProperty(ACTIVE, false));
			}
		}

		return true;
	}

	@Override
	public BlockState getActualState(BlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);

		if (te instanceof TileEntityBridgeControl)
			return state.withProperty(BlockBridge.TYPE, ((TileEntityBridgeControl) te).getType());

		return state;
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(DirectionalBlock.FACING, Direction.getFront(meta & 7)).withProperty(ACTIVE, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		byte b0 = 0;
		int i = b0 | state.getValue(DirectionalBlock.FACING).getIndex();

		if (state.getValue(ACTIVE)) {
			i |= 8;
		}

		return i;
	}

	@Override
	public BlockState withRotation(BlockState state, Rotation rot) {
		return state.withProperty(DirectionalBlock.FACING, rot.rotate(state.getValue(DirectionalBlock.FACING)));
	}

	@Override
	public BlockState withMirror(BlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(DirectionalBlock.FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { DirectionalBlock.FACING, ACTIVE, BlockBridge.TYPE });
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, BlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		this.setDefaultDirection(worldIn, pos, state);
	}

	@Override
	public BlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer, Hand hand) {
		return this.getDefaultState().withProperty(DirectionalBlock.FACING, Direction.getDirectionFromEntityLiving(pos, placer)).withProperty(ACTIVE, false);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(DirectionalBlock.FACING, Direction.getDirectionFromEntityLiving(pos, placer)), 2);
		checkPower(worldIn, pos);
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player) {
		return new ItemStack(IndustryBlocks.bridge_control, 1, state.getActualState(world, pos).getValue(BlockBridge.TYPE).ordinal());
	}

	public void checkPower(World worldIn, BlockPos pos) {
		BlockState state = worldIn.getBlockState(pos);
		TileEntity te = worldIn.getTileEntity(pos);

		int powerLevel = worldIn.isBlockIndirectlyGettingPowered(pos);

		if (powerLevel > 0 && te instanceof TileEntityBridgeControl) {
			worldIn.setBlockState(pos, state.withProperty(ACTIVE, true));
		} else {
			worldIn.setBlockState(pos, state.withProperty(ACTIVE, false));
		}
	}

	private void setDefaultDirection(World worldIn, BlockPos pos, BlockState state) {
		if (!worldIn.isRemote) {
			Direction enumfacing = state.getValue(DirectionalBlock.FACING);
			boolean flag = worldIn.getBlockState(pos.north()).isFullBlock();
			boolean flag1 = worldIn.getBlockState(pos.south()).isFullBlock();

			if (enumfacing == Direction.NORTH && flag && !flag1) {
				enumfacing = Direction.SOUTH;
			} else if (enumfacing == Direction.SOUTH && flag1 && !flag) {
				enumfacing = Direction.NORTH;
			} else {
				boolean flag2 = worldIn.getBlockState(pos.west()).isFullBlock();
				boolean flag3 = worldIn.getBlockState(pos.east()).isFullBlock();

				if (enumfacing == Direction.WEST && flag2 && !flag3) {
					enumfacing = Direction.EAST;
				} else if (enumfacing == Direction.EAST && flag3 && !flag2) {
					enumfacing = Direction.WEST;
				}
			}

			worldIn.setBlockState(pos, state.withProperty(DirectionalBlock.FACING, enumfacing).withProperty(ACTIVE, false), 2);
		}
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		checkPower(worldIn, pos);
	}

	@Override
	public TileEntity createTileEntity(World world, BlockState state) {
		return new TileEntityBridgeControl(state.getValue(BlockBridge.TYPE));
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
}
