package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;
import com.grim3212.mc.pack.decor.tile.TileEntityCalendar;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockCalendar extends BlockManual {

	protected static final VoxelShape CALENDAR_NORTH_AABB = Block.makeCuboidShape(4f, 2.08f, 14.96f, 12f, 14.96f, 16f);
	protected static final VoxelShape CALENDAR_SOUTH_AABB = Block.makeCuboidShape(4f, 2.08f, 0f, 12f, 14.96f, 1.04f);
	protected static final VoxelShape CALENDAR_WEST_AABB = Block.makeCuboidShape(14.96f, 2.08f, 4f, 16f, 14.96f, 12f);
	protected static final VoxelShape CALENDAR_EAST_AABB = Block.makeCuboidShape(0f, 2.08f, 4f, 1.04f, 14.96f, 12f);
	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

	protected BlockCalendar() {
		super(DecorNames.CALENDAR, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).doesNotBlockMovement().hardnessAndResistance(1f));
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(FACING, Direction.NORTH);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(FACING)) {
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
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityCalendar();
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		for (Direction enumfacing : Direction.Plane.HORIZONTAL) {
			if (this.canBlockStay(context.getWorld(), context.getPos(), enumfacing)) {
				return this.getDefaultState().with(FACING, enumfacing);
			}
		}

		return this.getDefaultState();
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		for (Direction enumfacing : FACING.getAllowedValues()) {
			if (this.canBlockStay(worldIn, pos, enumfacing)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean flag) {
		Direction enumfacing = state.get(FACING);

		if (!this.canBlockStay(worldIn, pos, enumfacing)) {
			worldIn.destroyBlock(pos, true);
		}
	}

	protected boolean canBlockStay(IWorldReader worldIn, BlockPos pos, Direction facing) {
		BlockPos blockpos = pos.offset(facing.getOpposite());
		BlockState iblockstate = worldIn.getBlockState(blockpos);
		return Block.hasSolidSide(iblockstate, worldIn, blockpos, facing);
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.calendar_page;
	}
}
