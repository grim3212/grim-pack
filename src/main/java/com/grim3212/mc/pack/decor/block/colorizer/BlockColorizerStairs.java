package com.grim3212.mc.pack.decor.block.colorizer;

import java.util.stream.IntStream;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.StairsBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class BlockColorizerStairs extends BlockColorizerFurnitureRotate implements IWaterLoggable {

	protected static final VoxelShape SLAB_TOP = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape SLAB_BOTTOM = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
	protected static final VoxelShape NWD_CORNER = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
	protected static final VoxelShape SWD_CORNER = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
	protected static final VoxelShape NWU_CORNER = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
	protected static final VoxelShape SWU_CORNER = Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
	protected static final VoxelShape NED_CORNER = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
	protected static final VoxelShape SED_CORNER = Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
	protected static final VoxelShape NEU_CORNER = Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
	protected static final VoxelShape SEU_CORNER = Block.makeCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape[] SLAB_TOP_SHAPES = makeShapes(SLAB_TOP, NWD_CORNER, NED_CORNER, SWD_CORNER, SED_CORNER);
	protected static final VoxelShape[] SLAB_BOTTOM_SHAPES = makeShapes(SLAB_BOTTOM, NWU_CORNER, NEU_CORNER, SWU_CORNER, SEU_CORNER);
	private static final int[] field_196522_K = new int[] { 12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8 };

	public BlockColorizerStairs() {
		super(DecorNames.STAIRS);
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(StairsBlock.FACING, Direction.NORTH).with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.SHAPE, StairsShape.STRAIGHT).with(StairsBlock.WATERLOGGED, false);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(StairsBlock.FACING, StairsBlock.HALF, StairsBlock.SHAPE, StairsBlock.WATERLOGGED);
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.stairs_page;
	}

	private static VoxelShape[] makeShapes(VoxelShape slabShape, VoxelShape nwCorner, VoxelShape neCorner, VoxelShape swCorner, VoxelShape seCorner) {
		return IntStream.range(0, 16).mapToObj((i) -> {
			return combineShapes(i, slabShape, nwCorner, neCorner, swCorner, seCorner);
		}).toArray((j) -> {
			return new VoxelShape[j];
		});
	}

	private static VoxelShape combineShapes(int bitfield, VoxelShape slabShape, VoxelShape nwCorner, VoxelShape neCorner, VoxelShape swCorner, VoxelShape seCorner) {
		VoxelShape voxelshape = slabShape;
		if ((bitfield & 1) != 0) {
			voxelshape = VoxelShapes.or(slabShape, nwCorner);
		}

		if ((bitfield & 2) != 0) {
			voxelshape = VoxelShapes.or(voxelshape, neCorner);
		}

		if ((bitfield & 4) != 0) {
			voxelshape = VoxelShapes.or(voxelshape, swCorner);
		}

		if ((bitfield & 8) != 0) {
			voxelshape = VoxelShapes.or(voxelshape, seCorner);
		}

		return voxelshape;
	}

	@Override
	public boolean func_220074_n(BlockState state) {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return (state.get(StairsBlock.HALF) == Half.TOP ? SLAB_TOP_SHAPES : SLAB_BOTTOM_SHAPES)[field_196522_K[this.func_196511_x(state)]];
	}

	private int func_196511_x(BlockState state) {
		return state.get(StairsBlock.SHAPE).ordinal() * 4 + state.get(StairsBlock.FACING).getHorizontalIndex();
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction direction = context.getFace();
		BlockPos blockpos = context.getPos();
		IFluidState ifluidstate = context.getWorld().getFluidState(blockpos);
		BlockState blockstate = this.getDefaultState().with(StairsBlock.FACING, context.getPlacementHorizontalFacing()).with(StairsBlock.HALF, direction != Direction.DOWN && (direction == Direction.UP || !(context.getHitVec().y - (double) blockpos.getY() > 0.5D)) ? Half.BOTTOM : Half.TOP).with(StairsBlock.WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
		return blockstate.with(StairsBlock.SHAPE, getShapeProperty(blockstate, context.getWorld(), blockpos));
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(StairsBlock.WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		return facing.getAxis().isHorizontal() ? stateIn.with(StairsBlock.SHAPE, getShapeProperty(stateIn, worldIn, currentPos)) : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	private static StairsShape getShapeProperty(BlockState state, IBlockReader worldIn, BlockPos pos) {
		Direction direction = state.get(StairsBlock.FACING);
		BlockState blockstate = worldIn.getBlockState(pos.offset(direction));
		if (isBlockStairs(blockstate) && state.get(StairsBlock.HALF) == blockstate.get(StairsBlock.HALF)) {
			Direction direction1 = blockstate.get(StairsBlock.FACING);
			if (direction1.getAxis() != state.get(StairsBlock.FACING).getAxis() && isDifferentStairs(state, worldIn, pos, direction1.getOpposite())) {
				if (direction1 == direction.rotateYCCW()) {
					return StairsShape.OUTER_LEFT;
				}

				return StairsShape.OUTER_RIGHT;
			}
		}

		BlockState blockstate1 = worldIn.getBlockState(pos.offset(direction.getOpposite()));
		if (isBlockStairs(blockstate1) && state.get(StairsBlock.HALF) == blockstate1.get(StairsBlock.HALF)) {
			Direction direction2 = blockstate1.get(StairsBlock.FACING);
			if (direction2.getAxis() != state.get(StairsBlock.FACING).getAxis() && isDifferentStairs(state, worldIn, pos, direction2)) {
				if (direction2 == direction.rotateYCCW()) {
					return StairsShape.INNER_LEFT;
				}

				return StairsShape.INNER_RIGHT;
			}
		}

		return StairsShape.STRAIGHT;
	}

	private static boolean isDifferentStairs(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
		BlockState blockstate = worldIn.getBlockState(pos.offset(face));
		return !isBlockStairs(blockstate) || blockstate.get(StairsBlock.FACING) != state.get(StairsBlock.FACING) || blockstate.get(StairsBlock.HALF) != state.get(StairsBlock.HALF);
	}

	public static boolean isBlockStairs(BlockState state) {
		return state.getBlock() instanceof StairsBlock;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(StairsBlock.FACING, rot.rotate(state.get(StairsBlock.FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		Direction direction = state.get(StairsBlock.FACING);
		StairsShape stairsshape = state.get(StairsBlock.SHAPE);
		switch (mirrorIn) {
		case LEFT_RIGHT:
			if (direction.getAxis() == Direction.Axis.Z) {
				switch (stairsshape) {
				case INNER_LEFT:
					return state.rotate(Rotation.CLOCKWISE_180).with(StairsBlock.SHAPE, StairsShape.INNER_RIGHT);
				case INNER_RIGHT:
					return state.rotate(Rotation.CLOCKWISE_180).with(StairsBlock.SHAPE, StairsShape.INNER_LEFT);
				case OUTER_LEFT:
					return state.rotate(Rotation.CLOCKWISE_180).with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT);
				case OUTER_RIGHT:
					return state.rotate(Rotation.CLOCKWISE_180).with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT);
				default:
					return state.rotate(Rotation.CLOCKWISE_180);
				}
			}
			break;
		case FRONT_BACK:
			if (direction.getAxis() == Direction.Axis.X) {
				switch (stairsshape) {
				case INNER_LEFT:
					return state.rotate(Rotation.CLOCKWISE_180).with(StairsBlock.SHAPE, StairsShape.INNER_LEFT);
				case INNER_RIGHT:
					return state.rotate(Rotation.CLOCKWISE_180).with(StairsBlock.SHAPE, StairsShape.INNER_RIGHT);
				case OUTER_LEFT:
					return state.rotate(Rotation.CLOCKWISE_180).with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT);
				case OUTER_RIGHT:
					return state.rotate(Rotation.CLOCKWISE_180).with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT);
				case STRAIGHT:
					return state.rotate(Rotation.CLOCKWISE_180);
				}
			}
		default:
			return super.mirror(state, mirrorIn);
		}

		return super.mirror(state, mirrorIn);
	}

	@Override
	@SuppressWarnings("deprecation")
	public IFluidState getFluidState(BlockState state) {
		return state.get(StairsBlock.WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

}
