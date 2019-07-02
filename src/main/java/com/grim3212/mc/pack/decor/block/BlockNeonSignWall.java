package com.grim3212.mc.pack.decor.block;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BlockNeonSignWall extends BlockNeonSign {

	private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(0.0D, 4.5D, 14.0D, 16.0D, 12.5D, 16.0D), Direction.SOUTH, Block.makeCuboidShape(0.0D, 4.5D, 0.0D, 16.0D, 12.5D, 2.0D), Direction.EAST, Block.makeCuboidShape(0.0D, 4.5D, 0.0D, 2.0D, 12.5D, 16.0D), Direction.WEST, Block.makeCuboidShape(14.0D, 4.5D, 0.0D, 16.0D, 12.5D, 16.0D)));

	public BlockNeonSignWall() {
		super(DecorNames.NEON_SIGN_WALL, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(1f).doesNotBlockMovement());
		setDefaultState(this.stateContainer.getBaseState().with(WallSignBlock.FACING, Direction.NORTH).with(WallSignBlock.WATERLOGGED, false));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(WallSignBlock.FACING, WallSignBlock.WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext ctx) {
		return SHAPES.get(state.get(WallSignBlock.FACING));
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.offset(state.get(WallSignBlock.FACING).getOpposite())).getMaterial().isSolid();
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState iblockstate = this.getDefaultState();
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		IWorldReader iworldreaderbase = context.getWorld();
		BlockPos blockpos = context.getPos();
		Direction[] aenumfacing = context.getNearestLookingDirections();

		for (Direction enumfacing : aenumfacing) {
			if (enumfacing.getAxis().isHorizontal()) {
				Direction enumfacing1 = enumfacing.getOpposite();
				iblockstate = iblockstate.with(WallSignBlock.FACING, enumfacing1);
				if (iblockstate.isValidPosition(iworldreaderbase, blockpos)) {
					return iblockstate.with(WallSignBlock.WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
				}
			}
		}

		return null;
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return facing.getOpposite() == stateIn.get(WallSignBlock.FACING) && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(WallSignBlock.FACING, rot.rotate(state.get(WallSignBlock.FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(WallSignBlock.FACING)));
	}
}
