package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BlockColorizerWall extends BlockColorizerFourWay {

	public static final BooleanProperty UP = BooleanProperty.create("up");
	private final VoxelShape[] wallShapes;
	private final VoxelShape[] wallCollisionShapes;

	public BlockColorizerWall() {
		super(DecorNames.WALL, 0.0F, 3.0F, 0.0F, 14.0F, 24.0F);

		this.wallShapes = this.makeShapes(4.0F, 3.0F, 16.0F, 0.0F, 14.0F);
		this.wallCollisionShapes = this.makeShapes(4.0F, 3.0F, 24.0F, 0.0F, 24.0F);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.get(UP) ? this.wallShapes[this.getIndex(state)] : super.getShape(state, worldIn, pos, context);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.get(UP) ? this.wallCollisionShapes[this.getIndex(state)] : super.getCollisionShape(state, worldIn, pos, context);
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(UP, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(UP, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.wall_page;
	}

	private boolean canAttach(BlockState state, boolean solid, Direction face) {
		Block block = state.getBlock();
		boolean flag = block.isIn(BlockTags.WALLS) || block instanceof FenceGateBlock && FenceGateBlock.isParallel(state, face);
		return !cannotAttach(block) && solid || flag;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IWorldReader iworldreader = context.getWorld();
		BlockPos blockpos = context.getPos();
		IFluidState ifluidstate = iworldreader.getFluidState(blockpos);
		BlockPos blockpos1 = blockpos.north();
		BlockPos blockpos2 = blockpos.east();
		BlockPos blockpos3 = blockpos.south();
		BlockPos blockpos4 = blockpos.west();
		BlockState blockstate = iworldreader.getBlockState(blockpos1);
		BlockState blockstate1 = iworldreader.getBlockState(blockpos2);
		BlockState blockstate2 = iworldreader.getBlockState(blockpos3);
		BlockState blockstate3 = iworldreader.getBlockState(blockpos4);
		boolean flag = this.canAttach(blockstate, Block.hasSolidSide(blockstate, iworldreader, blockpos1, Direction.SOUTH), Direction.SOUTH);
		boolean flag1 = this.canAttach(blockstate1, Block.hasSolidSide(blockstate1, iworldreader, blockpos2, Direction.WEST), Direction.WEST);
		boolean flag2 = this.canAttach(blockstate2, Block.hasSolidSide(blockstate2, iworldreader, blockpos3, Direction.NORTH), Direction.NORTH);
		boolean flag3 = this.canAttach(blockstate3, Block.hasSolidSide(blockstate3, iworldreader, blockpos4, Direction.EAST), Direction.EAST);
		boolean flag4 = (!flag || flag1 || !flag2 || flag3) && (flag || !flag1 || flag2 || !flag3);
		return this.getDefaultState().with(UP, flag4 || !iworldreader.isAirBlock(blockpos.up())).with(NORTH, flag).with(EAST, flag1).with(SOUTH, flag2).with(WEST, flag3).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		if (facing == Direction.DOWN) {
			return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		} else {
			Direction direction = facing.getOpposite();
			boolean flag = facing == Direction.NORTH ? this.canAttach(facingState, Block.hasSolidSide(facingState, worldIn, facingPos, direction), direction) : stateIn.get(NORTH);
			boolean flag1 = facing == Direction.EAST ? this.canAttach(facingState, Block.hasSolidSide(facingState, worldIn, facingPos, direction), direction) : stateIn.get(EAST);
			boolean flag2 = facing == Direction.SOUTH ? this.canAttach(facingState, Block.hasSolidSide(facingState, worldIn, facingPos, direction), direction) : stateIn.get(SOUTH);
			boolean flag3 = facing == Direction.WEST ? this.canAttach(facingState, Block.hasSolidSide(facingState, worldIn, facingPos, direction), direction) : stateIn.get(WEST);
			boolean flag4 = (!flag || flag1 || !flag2 || flag3) && (flag || !flag1 || flag2 || !flag3);
			return stateIn.with(UP, flag4 || !worldIn.isAirBlock(currentPos.up())).with(NORTH, flag).with(EAST, flag1).with(SOUTH, flag2).with(WEST, flag3);
		}
	}
}
