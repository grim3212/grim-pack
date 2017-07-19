package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.tile.TileEntityWallClock;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWallClock extends BlockManual implements ITileEntityProvider {

	protected static final AxisAlignedBB CLOCK_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB CLOCK_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
	protected static final AxisAlignedBB CLOCK_WEST_AABB = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB CLOCK_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyInteger TIME = PropertyInteger.create("time", 0, 63);

	protected BlockWallClock() {
		super("wall_clock", Material.CIRCUITS, SoundType.WOOD);
		setHardness(0.75F);
		setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
	}

	@Override
	protected IBlockState getState() {
		return super.getState().withProperty(FACING, EnumFacing.SOUTH);
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
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntityWallClock tile = (TileEntityWallClock) worldIn.getTileEntity(pos);
		return state.withProperty(TIME, tile.getTime());
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, TIME);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(FACING)) {
		case EAST:
			return CLOCK_EAST_AABB;
		case WEST:
			return CLOCK_WEST_AABB;
		case SOUTH:
			return CLOCK_SOUTH_AABB;
		case NORTH:
			return CLOCK_NORTH_AABB;
		default:
			return CLOCK_NORTH_AABB;
		}
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos.west()).isOpaqueCube()) {
			return true;
		}
		if (worldIn.getBlockState(pos.east()).isOpaqueCube()) {
			return true;
		}
		if (worldIn.getBlockState(pos.north()).isOpaqueCube()) {
			return true;
		} else {
			return worldIn.getBlockState(pos.south()).isOpaqueCube();
		}
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IBlockState state = this.getDefaultState();

		if (facing == EnumFacing.NORTH && world.getBlockState(pos.south()).isOpaqueCube()) {
			state = state.withProperty(FACING, EnumFacing.NORTH);
		}
		if (facing == EnumFacing.SOUTH && world.getBlockState(pos.north()).isOpaqueCube()) {
			state = state.withProperty(FACING, EnumFacing.SOUTH);
		}
		if (facing == EnumFacing.WEST && world.getBlockState(pos.east()).isOpaqueCube()) {
			state = state.withProperty(FACING, EnumFacing.WEST);
		}
		if (facing == EnumFacing.EAST && world.getBlockState(pos.west()).isOpaqueCube()) {
			state = state.withProperty(FACING, EnumFacing.EAST);
		}

		return state;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		EnumFacing facing = (EnumFacing) state.getValue(FACING);
		boolean flag = false;
		if (facing == EnumFacing.NORTH && worldIn.getBlockState(pos.south()).isOpaqueCube()) {
			flag = true;
		}
		if (facing == EnumFacing.SOUTH && worldIn.getBlockState(pos.north()).isOpaqueCube()) {
			flag = true;
		}
		if (facing == EnumFacing.WEST && worldIn.getBlockState(pos.east()).isOpaqueCube()) {
			flag = true;
		}
		if (facing == EnumFacing.EAST && worldIn.getBlockState(pos.west()).isOpaqueCube()) {
			flag = true;
		}
		if (!flag) {
			dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityWallClock();
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.clock_page;
	}
}
