package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;
import com.grim3212.mc.pack.decor.tile.TileEntityWallClock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
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

public class BlockWallClock extends BlockManual {

	protected static final VoxelShape CLOCK_NORTH_AABB = Block.makeCuboidShape(0f, 0f, 14f, 16f, 16f, 16f);
	protected static final VoxelShape CLOCK_SOUTH_AABB = Block.makeCuboidShape(0f, 0f, 0f, 16f, 16f, 2f);
	protected static final VoxelShape CLOCK_WEST_AABB = Block.makeCuboidShape(14f, 0f, 0f, 16f, 16f, 16f);
	protected static final VoxelShape CLOCK_EAST_AABB = Block.makeCuboidShape(0f, 0f, 0f, 2f, 16f, 16f);
	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
	public static final IntegerProperty TIME = IntegerProperty.create("time", 0, 63);

	protected BlockWallClock() {
		super(DecorNames.WALL_CLOCK, Block.Properties.create(Material.MISCELLANEOUS).sound(SoundType.WOOD).hardnessAndResistance(0.75f));
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(FACING, Direction.SOUTH).with(TIME, 0);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING, TIME);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext ctx) {
		switch (state.get(FACING)) {
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
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos.west()).isOpaqueCube(worldIn, pos)) {
			return true;
		}
		if (worldIn.getBlockState(pos.east()).isOpaqueCube(worldIn, pos)) {
			return true;
		}
		if (worldIn.getBlockState(pos.north()).isOpaqueCube(worldIn, pos)) {
			return true;
		} else {
			return worldIn.getBlockState(pos.south()).isOpaqueCube(worldIn, pos);
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = this.getDefaultState();
		Direction facing = context.getFace();
		World world = context.getWorld();
		BlockPos pos = context.getPos();

		if (facing == Direction.NORTH && world.getBlockState(pos.south()).isOpaqueCube(world, pos)) {
			state = state.with(FACING, Direction.NORTH);
		}
		if (facing == Direction.SOUTH && world.getBlockState(pos.north()).isOpaqueCube(world, pos)) {
			state = state.with(FACING, Direction.SOUTH);
		}
		if (facing == Direction.WEST && world.getBlockState(pos.east()).isOpaqueCube(world, pos)) {
			state = state.with(FACING, Direction.WEST);
		}
		if (facing == Direction.EAST && world.getBlockState(pos.west()).isOpaqueCube(world, pos)) {
			state = state.with(FACING, Direction.EAST);
		}

		return state;
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean flg) {
		Direction facing = state.get(FACING);
		boolean flag = false;
		if (facing == Direction.NORTH && worldIn.getBlockState(pos.south()).isOpaqueCube(worldIn, pos)) {
			flag = true;
		}
		if (facing == Direction.SOUTH && worldIn.getBlockState(pos.north()).isOpaqueCube(worldIn, pos)) {
			flag = true;
		}
		if (facing == Direction.WEST && worldIn.getBlockState(pos.east()).isOpaqueCube(worldIn, pos)) {
			flag = true;
		}
		if (facing == Direction.EAST && worldIn.getBlockState(pos.west()).isOpaqueCube(worldIn, pos)) {
			flag = true;
		}
		if (!flag) {
			worldIn.destroyBlock(pos, true);
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityWallClock();
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.clock_page;
	}
}
