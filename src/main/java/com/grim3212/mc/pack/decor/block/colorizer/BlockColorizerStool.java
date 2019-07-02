package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class BlockColorizerStool extends BlockColorizer {

	public static final BooleanProperty UP = BooleanProperty.create("up");
	private static final VoxelShape STOOL = Block.makeCuboidShape(2.88F, 0.0F, 2.88F, 13.12F, 10.08F, 13.12F);
	private static final VoxelShape WALKING_STOOL = Block.makeCuboidShape(2.88F, 0.0F, 2.88F, 13.12F, 9.6F, 13.12F);
	private static final VoxelShape POT_STOOL = Block.makeCuboidShape(2.88F, 0.0F, 2.88F, 13.12F, 16F, 13.12F);

	public BlockColorizerStool() {
		super(DecorNames.STOOL);
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(UP, false);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(UP);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).with(UP, context.getWorld().getBlockState(context.getPos().up()).getBlock() == DecorBlocks.pot);
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return facing == Direction.UP ? (worldIn.getBlockState(facingPos).getBlock() == DecorBlocks.pot ? stateIn.with(UP, true) : stateIn.with(UP, false)) : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.get(UP) ? POT_STOOL : STOOL;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.get(UP) ? POT_STOOL : WALKING_STOOL;
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.stool_page;
	}
}