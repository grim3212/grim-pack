package com.grim3212.mc.pack.decor.block.colorizer;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;
import com.grim3212.mc.pack.decor.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockColorizerTrapDoor extends BlockColorizerFurnitureRotate implements IWaterLoggable {

	protected static final VoxelShape EAST_OPEN_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
	protected static final VoxelShape WEST_OPEN_AABB = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape SOUTH_OPEN_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
	protected static final VoxelShape NORTH_OPEN_AABB = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape BOTTOM_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
	protected static final VoxelShape TOP_AABB = Block.makeCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	public BlockColorizerTrapDoor() {
		super(DecorNames.TRAP_DOOR);
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(HORIZONTAL_FACING, Direction.NORTH).with(TrapDoorBlock.OPEN, false).with(TrapDoorBlock.HALF, Half.BOTTOM).with(TrapDoorBlock.POWERED, false).with(TrapDoorBlock.WATERLOGGED, false);
	}

	public boolean changeDoors(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, BlockRayTraceResult hit) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		ItemStack heldItem = playerIn.getHeldItem(hand);

		if (!heldItem.isEmpty() && tileentity instanceof TileEntityColorizer) {
			TileEntityColorizer te = (TileEntityColorizer) tileentity;
			Block block = Block.getBlockFromItem(heldItem.getItem());

			if (block != null && !(block instanceof BlockColorizer)) {
				if (BlockHelper.getUsableBlocks().contains(block.getDefaultState())) {
					// Can only set blockstate if it contains nothing or if
					// in creative mode
					if (te.getBlockState() == Blocks.AIR.getDefaultState() || playerIn.abilities.isCreativeMode) {
						BlockState toPlaceState = block.getStateForPlacement(new BlockItemUseContext(new ItemUseContext(playerIn, hand, hit)));
						this.setColorizer(worldIn, pos, state, toPlaceState, playerIn, hand, true);

						SoundType placeSound = toPlaceState.getSoundType(worldIn, pos, playerIn);
						worldIn.playSound(playerIn, pos, placeSound.getPlaceSound(), SoundCategory.BLOCKS, (placeSound.getVolume() + 1.0F) / 2.0F, placeSound.getPitch() * 0.8F);
						return true;
					} else if (te.getBlockState() != Blocks.AIR.getDefaultState()) {
						return false;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack heldItem = player.getHeldItem(handIn);

		if (!heldItem.isEmpty()) {
			if (heldItem.getItem() == DecorItems.brush) {
				if (this.tryUseBrush(worldIn, player, handIn, pos)) {
					return true;
				}
			}

			Block block = Block.getBlockFromItem(heldItem.getItem());
			if (block != Blocks.AIR) {
				if (changeDoors(worldIn, pos, state, player, handIn, hit)) {
					return true;
				}
			}
		}

		state = state.cycle(TrapDoorBlock.OPEN);
		worldIn.setBlockState(pos, state, 2);
		if (state.get(TrapDoorBlock.WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		this.playSound(player, worldIn, pos, state.get(TrapDoorBlock.OPEN));
		return true;
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.decorTrapDoor_page;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (!state.get(TrapDoorBlock.OPEN)) {
			return state.get(TrapDoorBlock.HALF) == Half.TOP ? TOP_AABB : BOTTOM_AABB;
		} else {
			switch (state.get(HORIZONTAL_FACING)) {
			case NORTH:
			default:
				return NORTH_OPEN_AABB;
			case SOUTH:
				return SOUTH_OPEN_AABB;
			case WEST:
				return WEST_OPEN_AABB;
			case EAST:
				return EAST_OPEN_AABB;
			}
		}
	}

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		switch (type) {
		case LAND:
			return state.get(TrapDoorBlock.OPEN);
		case WATER:
			return state.get(TrapDoorBlock.WATERLOGGED);
		case AIR:
			return state.get(TrapDoorBlock.OPEN);
		default:
			return false;
		}
	}

	protected void playSound(@Nullable PlayerEntity player, World worldIn, BlockPos pos, boolean p_185731_4_) {
		if (p_185731_4_) {
			int i = this.material == Material.IRON ? 1037 : 1007;
			worldIn.playEvent(player, i, pos, 0);
		} else {
			int j = this.material == Material.IRON ? 1036 : 1013;
			worldIn.playEvent(player, j, pos, 0);
		}

	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
		if (!worldIn.isRemote) {
			boolean flag = worldIn.isBlockPowered(pos);
			if (flag != state.get(TrapDoorBlock.POWERED)) {
				if (state.get(TrapDoorBlock.OPEN) != flag) {
					state = state.with(TrapDoorBlock.OPEN, flag);
					this.playSound((PlayerEntity) null, worldIn, pos, flag);
				}

				worldIn.setBlockState(pos, state.with(TrapDoorBlock.POWERED, flag), 2);
				if (state.get(TrapDoorBlock.WATERLOGGED)) {
					worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
				}
			}

		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = this.getDefaultState();
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		Direction direction = context.getFace();
		if (!context.replacingClickedOnBlock() && direction.getAxis().isHorizontal()) {
			blockstate = blockstate.with(HORIZONTAL_FACING, direction).with(TrapDoorBlock.HALF, context.getHitVec().y - (double) context.getPos().getY() > 0.5D ? Half.TOP : Half.BOTTOM);
		} else {
			blockstate = blockstate.with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(TrapDoorBlock.HALF, direction == Direction.UP ? Half.BOTTOM : Half.TOP);
		}

		if (context.getWorld().isBlockPowered(context.getPos())) {
			blockstate = blockstate.with(TrapDoorBlock.OPEN, true).with(TrapDoorBlock.POWERED, true);
		}

		return blockstate.with(TrapDoorBlock.WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, TrapDoorBlock.OPEN, TrapDoorBlock.HALF, TrapDoorBlock.POWERED, TrapDoorBlock.WATERLOGGED);
	}

	@Override
	@SuppressWarnings("deprecation")
	public IFluidState getFluidState(BlockState state) {
		return state.get(TrapDoorBlock.WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(TrapDoorBlock.WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public boolean isLadder(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos, LivingEntity entity) {
		if (state.get(TrapDoorBlock.OPEN)) {
			BlockState down = world.getBlockState(pos.down());
			if (down.getBlock() == Blocks.LADDER)
				return down.get(LadderBlock.FACING) == state.get(HORIZONTAL_FACING);
		}
		return false;
	}
}
