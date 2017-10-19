package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.tile.TileEntityBridgeControl;
import com.grim3212.mc.pack.industry.util.EnumBridgeType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
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
	protected IBlockState getState() {
		return super.getState().withProperty(BlockDirectional.FACING, EnumFacing.NORTH).withProperty(ACTIVE, false);
	}

	@Override
	public Page getPage(World world, BlockPos pos, IBlockState state) {
		if (world != null && pos != null) {
			return state.getActualState(world, pos).getValue(BlockBridge.TYPE).getPage();
		}

		return super.getPage(world, pos, state);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (int i = 0; i < EnumBridgeType.values.length; i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		ItemStack heldItem = playerIn.getHeldItem(hand);

		if (!heldItem.isEmpty() && !playerIn.isSneaking() && tileentity instanceof TileEntityBridgeControl) {
			TileEntityBridgeControl te = (TileEntityBridgeControl) tileentity;
			Block block = Block.getBlockFromItem(heldItem.getItem());

			if (block != null) {
				IBlockState oldState = te.getBlockState();
				IBlockState toSetState = block.getStateFromMeta(heldItem.getMetadata());

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
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);

		if (te instanceof TileEntityBridgeControl)
			return state.withProperty(BlockBridge.TYPE, ((TileEntityBridgeControl) te).getType());

		return state;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.getFront(meta & 7)).withProperty(ACTIVE, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | state.getValue(BlockDirectional.FACING).getIndex();

		if (state.getValue(ACTIVE)) {
			i |= 8;
		}

		return i;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(BlockDirectional.FACING, rot.rotate(state.getValue(BlockDirectional.FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(BlockDirectional.FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { BlockDirectional.FACING, ACTIVE, BlockBridge.TYPE });
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		this.setDefaultDirection(worldIn, pos, state);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)).withProperty(ACTIVE, false);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(BlockDirectional.FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);
		checkPower(worldIn, pos);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(IndustryBlocks.bridge_control, 1, state.getActualState(world, pos).getValue(BlockBridge.TYPE).ordinal());
	}

	public void checkPower(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity te = worldIn.getTileEntity(pos);

		int powerLevel = worldIn.isBlockIndirectlyGettingPowered(pos);

		if (powerLevel > 0 && te instanceof TileEntityBridgeControl) {
			worldIn.setBlockState(pos, state.withProperty(ACTIVE, true));
		} else {
			worldIn.setBlockState(pos, state.withProperty(ACTIVE, false));
		}
	}

	private void setDefaultDirection(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			EnumFacing enumfacing = state.getValue(BlockDirectional.FACING);
			boolean flag = worldIn.getBlockState(pos.north()).isFullBlock();
			boolean flag1 = worldIn.getBlockState(pos.south()).isFullBlock();

			if (enumfacing == EnumFacing.NORTH && flag && !flag1) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && flag1 && !flag) {
				enumfacing = EnumFacing.NORTH;
			} else {
				boolean flag2 = worldIn.getBlockState(pos.west()).isFullBlock();
				boolean flag3 = worldIn.getBlockState(pos.east()).isFullBlock();

				if (enumfacing == EnumFacing.WEST && flag2 && !flag3) {
					enumfacing = EnumFacing.EAST;
				} else if (enumfacing == EnumFacing.EAST && flag3 && !flag2) {
					enumfacing = EnumFacing.WEST;
				}
			}

			worldIn.setBlockState(pos, state.withProperty(BlockDirectional.FACING, enumfacing).withProperty(ACTIVE, false), 2);
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		checkPower(worldIn, pos);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityBridgeControl(state.getValue(BlockBridge.TYPE));
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
}
