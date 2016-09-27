package com.grim3212.mc.pack.decor.block;

import java.util.Iterator;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.tile.TileEntityCalendar;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCalendar extends BlockManual implements ITileEntityProvider {

	protected static final AxisAlignedBB CALENDAR_NORTH_AABB = new AxisAlignedBB(0.25D, 0.13D, 0.935D, 0.75D, 0.935D, 1.0D);
	protected static final AxisAlignedBB CALENDAR_SOUTH_AABB = new AxisAlignedBB(0.25D, 0.13D, 0.0D, 0.75D, 0.935D, 0.065D);
	protected static final AxisAlignedBB CALENDAR_WEST_AABB = new AxisAlignedBB(0.935D, 0.13D, 0.25D, 1.0D, 0.935D, 0.75D);
	protected static final AxisAlignedBB CALENDAR_EAST_AABB = new AxisAlignedBB(0.0D, 0.13D, 0.25D, 0.065D, 0.935D, 0.75D);
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	protected BlockCalendar() {
		super(Material.WOOD, SoundType.WOOD);
		this.setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch ((EnumFacing) state.getValue(FACING)) {
		case EAST:
			return CALENDAR_EAST_AABB;
		case WEST:
			return CALENDAR_WEST_AABB;
		case SOUTH:
			return CALENDAR_SOUTH_AABB;
		case NORTH:
			return CALENDAR_NORTH_AABB;
		default:
			return CALENDAR_NORTH_AABB;
		}
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
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
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return true;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if (facing.getAxis().isHorizontal() && this.canBlockStay(worldIn, pos, facing)) {
			return this.getDefaultState().withProperty(FACING, facing);
		} else {
			Iterator<EnumFacing> iterator = EnumFacing.Plane.HORIZONTAL.iterator();
			EnumFacing enumfacing1;

			do {
				if (!iterator.hasNext()) {
					return this.getDefaultState();
				}

				enumfacing1 = (EnumFacing) iterator.next();
			} while (!this.canBlockStay(worldIn, pos, enumfacing1));

			return this.getDefaultState().withProperty(FACING, enumfacing1);
		}
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.isSideSolid(pos.west(), EnumFacing.EAST, true) || worldIn.isSideSolid(pos.east(), EnumFacing.WEST, true) || worldIn.isSideSolid(pos.north(), EnumFacing.SOUTH, true) || worldIn.isSideSolid(pos.south(), EnumFacing.NORTH, true);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

		if (!this.canBlockStay(worldIn, pos, enumfacing)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	protected boolean canBlockStay(World worldIn, BlockPos pos, EnumFacing facing) {
		return worldIn.isSideSolid(pos.offset(facing.getOpposite()), facing, true);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityCalendar();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
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
	public Page getPage(IBlockState state) {
		return ManualDecor.calendar_page;
	}
}
