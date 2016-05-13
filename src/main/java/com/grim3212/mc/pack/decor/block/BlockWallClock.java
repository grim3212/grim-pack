package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.decor.tile.TileEntityWallClock;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWallClock extends Block implements ITileEntityProvider {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyInteger TIME = PropertyInteger.create("time", 0, 63);

	protected BlockWallClock() {
		super(Material.circuits);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntityWallClock tile = (TileEntityWallClock) worldIn.getTileEntity(pos);
		return state.withProperty(TIME, tile.getTime());
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, FACING, TIME);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		EnumFacing face = (EnumFacing) worldIn.getBlockState(pos).getValue(FACING);

		float f = 0.125F;
		if (face == EnumFacing.NORTH) {
			setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
		}
		if (face == EnumFacing.SOUTH) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
		}
		if (face == EnumFacing.WEST) {
			setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
		if (face == EnumFacing.EAST) {
			setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		super.getCollisionBoundingBox(worldIn, pos, state);

		EnumFacing face = (EnumFacing) state.getValue(FACING);

		float f = 0.125F;
		if (face == EnumFacing.NORTH) {
			setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
		}
		if (face == EnumFacing.SOUTH) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
		}
		if (face == EnumFacing.WEST) {
			setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
		if (face == EnumFacing.EAST) {
			setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
		}

		return super.getCollisionBoundingBox(worldIn, pos, state);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
		return super.getSelectedBoundingBox(worldIn, pos);
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos.west()).getBlock().isOpaqueCube()) {
			return true;
		}
		if (worldIn.getBlockState(pos.east()).getBlock().isOpaqueCube()) {
			return true;
		}
		if (worldIn.getBlockState(pos.north()).getBlock().isOpaqueCube()) {
			return true;
		} else {
			return worldIn.getBlockState(pos.south()).getBlock().isOpaqueCube();
		}
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {

		IBlockState state = this.getDefaultState();

		if (facing == EnumFacing.NORTH && worldIn.getBlockState(pos.south()).getBlock().isOpaqueCube()) {
			state = state.withProperty(FACING, EnumFacing.NORTH);
		}
		if (facing == EnumFacing.SOUTH && worldIn.getBlockState(pos.north()).getBlock().isOpaqueCube()) {
			state = state.withProperty(FACING, EnumFacing.SOUTH);
		}
		if (facing == EnumFacing.WEST && worldIn.getBlockState(pos.east()).getBlock().isOpaqueCube()) {
			state = state.withProperty(FACING, EnumFacing.WEST);
		}
		if (facing == EnumFacing.EAST && worldIn.getBlockState(pos.west()).getBlock().isOpaqueCube()) {
			state = state.withProperty(FACING, EnumFacing.EAST);
		}

		return state;
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		EnumFacing facing = (EnumFacing) state.getValue(FACING);
		boolean flag = false;
		if (facing == EnumFacing.NORTH && worldIn.getBlockState(pos.south()).getBlock().isOpaqueCube()) {
			flag = true;
		}
		if (facing == EnumFacing.SOUTH && worldIn.getBlockState(pos.north()).getBlock().isOpaqueCube()) {
			flag = true;
		}
		if (facing == EnumFacing.WEST && worldIn.getBlockState(pos.east()).getBlock().isOpaqueCube()) {
			flag = true;
		}
		if (facing == EnumFacing.EAST && worldIn.getBlockState(pos.west()).getBlock().isOpaqueCube()) {
			flag = true;
		}
		if (!flag) {
			dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}

		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityWallClock();
	}
}
