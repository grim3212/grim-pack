package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class BlockColorizerFacing extends BlockColorizer {

	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.values());
	private static final VoxelShape X = Block.makeCuboidShape(0.0F, 4F, 4F, 16F, 12F, 12F);
	private static final VoxelShape Y = Block.makeCuboidShape(4F, 0.0F, 4F, 12F, 16F, 12F);
	private static final VoxelShape Z = Block.makeCuboidShape(4F, 4F, 0.0F, 12F, 12F, 16F);

	public BlockColorizerFacing(String name) {
		super(name);
	}

	@Override
	protected BlockState getState() {
		return super.getDefaultState().with(FACING, Direction.NORTH);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.getBlock() == DecorBlocks.pillar) {
			switch (state.get(FACING)) {
			case WEST:
			case EAST:
				return X;
			case NORTH:
			case SOUTH:
				return Z;
			case UP:
			case DOWN:
			default:
				return Y;
			}
		}
		return VoxelShapes.fullCube();
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	@Override
	public Page getPage(BlockState state) {
		if (state.getBlock() == DecorBlocks.pillar) {
			return ManualDecor.pillar_page;
		}

		return null;
	}

}
