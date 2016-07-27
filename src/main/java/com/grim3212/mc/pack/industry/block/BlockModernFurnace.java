package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.industry.tile.TileEntityMFurnace;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockModernFurnace extends Block implements ITileEntityProvider {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	protected BlockModernFurnace() {
		super(Material.IRON);
		this.setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.setDefaultFacing(worldIn, pos, state);
	}

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState block = worldIn.getBlockState(pos.north());
			IBlockState block1 = worldIn.getBlockState(pos.south());
			IBlockState block2 = worldIn.getBlockState(pos.west());
			IBlockState block3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing).withProperty(ACTIVE, false), 2);
		}
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to
	 * allow for adjustments to the IBlockstate
	 */
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ACTIVE, false);
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow
	 * post-place logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityMFurnace) {
				((TileEntityMFurnace) tileentity).setCustomInventoryName(stack.getDisplayName());
			}
		}
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.getBlockState(pos).getValue(ACTIVE)) {
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = (double) pos.getZ() + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return true;

		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityMFurnace) {
			playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.MODERN_FURNACE_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityMFurnace) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMFurnace) tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() == this && world.getBlockState(pos).getValue(ACTIVE)) {
			return 15;
		}
		return super.getLightValue(state, world, pos);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMFurnace();
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		return Container.calcRedstone(worldIn.getTileEntity(pos));
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, getFacing(meta)).withProperty(ACTIVE, (meta & 8) > 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | ((EnumFacing) state.getValue(FACING)).getIndex();

		if (state.getValue(ACTIVE)) {
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, ACTIVE });
	}

	public static EnumFacing getFacing(int meta) {
		return EnumFacing.getFront(meta & 7);
	}
}
