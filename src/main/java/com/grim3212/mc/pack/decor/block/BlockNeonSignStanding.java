package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.*;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BlockNeonSignStanding extends BlockNeonSign {

	public BlockNeonSignStanding() {
		super(DecorNames.NEON_SIGN_STANDING, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).doesNotBlockMovement().hardnessAndResistance(1f));
		setDefaultState(this.stateContainer.getBaseState().with(StandingSignBlock.ROTATION, 0).with(StandingSignBlock.WATERLOGGED, false));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(StandingSignBlock.ROTATION, StandingSignBlock.WATERLOGGED);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		return this.getDefaultState().with(StandingSignBlock.ROTATION, MathHelper.floor((double) ((180.0F + context.getPlacementYaw()) * 16.0F / 360.0F) + 0.5D) & 15).with(StandingSignBlock.WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return facing == Direction.DOWN && !this.isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(StandingSignBlock.ROTATION, rot.rotate(state.get(StandingSignBlock.ROTATION), 16));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.with(StandingSignBlock.ROTATION, mirrorIn.mirrorRotation(state.get(StandingSignBlock.ROTATION), 16));
	}
}
