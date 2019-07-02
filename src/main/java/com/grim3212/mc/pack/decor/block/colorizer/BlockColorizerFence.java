package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LeadItem;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockColorizerFence extends BlockColorizerFourWay {

	private final VoxelShape[] renderShapes;

	public BlockColorizerFence() {
		super(DecorNames.FENCE, 2.0F, 2.0F, 16.0F, 16.0F, 24.0F);
		this.renderShapes = this.makeShapes(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false);
	}

	@Override
	public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return this.renderShapes[this.getIndex(state)];
	}

	public boolean canAttach(BlockState state, boolean solid, Direction face) {
		Block block = state.getBlock();
		boolean flag = block.isIn(BlockTags.FENCES) && state.getMaterial() == this.material;
		boolean flag1 = block instanceof FenceGateBlock && FenceGateBlock.isParallel(state, face);
		return !cannotAttach(block) && solid || flag || flag1;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack heldItem = player.getHeldItem(handIn);

		if (!heldItem.isEmpty()) {
			Block block = Block.getBlockFromItem(heldItem.getItem());
			if (block != null) {
				return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
			}
		}

		if (!worldIn.isRemote) {
			return LeadItem.attachToFence(player, worldIn, pos);
		} else {
			ItemStack itemstack = player.getHeldItem(handIn);
			return itemstack.getItem() == Items.LEAD || itemstack.isEmpty();
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IBlockReader iblockreader = context.getWorld();
		BlockPos blockpos = context.getPos();
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		BlockPos blockpos1 = blockpos.north();
		BlockPos blockpos2 = blockpos.east();
		BlockPos blockpos3 = blockpos.south();
		BlockPos blockpos4 = blockpos.west();
		BlockState blockstate = iblockreader.getBlockState(blockpos1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
		BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
		return super.getStateForPlacement(context).with(NORTH, this.canAttach(blockstate, Block.hasSolidSide(blockstate, iblockreader, blockpos1, Direction.SOUTH), Direction.SOUTH)).with(EAST, this.canAttach(blockstate1, Block.hasSolidSide(blockstate1, iblockreader, blockpos2, Direction.WEST), Direction.WEST)).with(SOUTH, this.canAttach(blockstate2, Block.hasSolidSide(blockstate2, iblockreader, blockpos3, Direction.NORTH), Direction.NORTH))
				.with(WEST, this.canAttach(blockstate3, Block.hasSolidSide(blockstate3, iblockreader, blockpos4, Direction.EAST), Direction.EAST)).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		return facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), this.canAttach(facingState, Block.hasSolidSide(facingState, worldIn, facingPos, facing.getOpposite()), facing.getOpposite())) : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.fence_page;
	}
}