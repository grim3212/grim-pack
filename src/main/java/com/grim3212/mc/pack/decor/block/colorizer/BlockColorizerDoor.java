package com.grim3212.mc.pack.decor.block.colorizer;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.init.DecorNames;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;
import com.grim3212.mc.pack.decor.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;

@SuppressWarnings("deprecation")
public class BlockColorizerDoor extends BlockColorizer {

	protected static final VoxelShape SOUTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
	protected static final VoxelShape NORTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape WEST_AABB = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

	public BlockColorizerDoor() {
		super(DecorNames.DOOR);
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(DoorBlock.FACING, Direction.NORTH).with(DoorBlock.OPEN, false).with(DoorBlock.HINGE, DoorHingeSide.LEFT).with(DoorBlock.POWERED, false).with(DoorBlock.HALF, DoubleBlockHalf.LOWER);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(DoorBlock.HALF, DoorBlock.FACING, DoorBlock.OPEN, DoorBlock.HINGE, DoorBlock.POWERED);
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.doors_page;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Direction direction = state.get(DoorBlock.FACING);
		boolean flag = !state.get(DoorBlock.OPEN);
		boolean flag1 = state.get(DoorBlock.HINGE) == DoorHingeSide.RIGHT;
		switch (direction) {
		case EAST:
		default:
			return flag ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
		case SOUTH:
			return flag ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
		case WEST:
			return flag ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
		case NORTH:
			return flag ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);
		}
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		DoubleBlockHalf doubleblockhalf = stateIn.get(DoorBlock.HALF);
		if (facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
			return facingState.getBlock() == this && facingState.get(DoorBlock.HALF) != doubleblockhalf ? stateIn.with(DoorBlock.FACING, facingState.get(DoorBlock.FACING)).with(DoorBlock.OPEN, facingState.get(DoorBlock.OPEN)).with(DoorBlock.HINGE, facingState.get(DoorBlock.HINGE)).with(DoorBlock.POWERED, facingState.get(DoorBlock.POWERED)) : Blocks.AIR.getDefaultState();
		} else {
			return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		DoubleBlockHalf doubleblockhalf = state.get(DoorBlock.HALF);
		BlockPos blockpos = doubleblockhalf == DoubleBlockHalf.LOWER ? pos.up() : pos.down();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		if (blockstate.getBlock() == this && blockstate.get(DoorBlock.HALF) != doubleblockhalf) {
			worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
			worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
			ItemStack itemstack = player.getHeldItemMainhand();
			if (!worldIn.isRemote && !player.isCreative()) {
				Block.spawnDrops(state, worldIn, pos, (TileEntity) null, player, itemstack);
				Block.spawnDrops(blockstate, worldIn, blockpos, (TileEntity) null, player, itemstack);
			}
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		switch (type) {
		case LAND:
			return state.get(DoorBlock.OPEN);
		case WATER:
			return false;
		case AIR:
			return state.get(DoorBlock.OPEN);
		default:
			return false;
		}
	}

	private int getCloseSound() {
		return 1012;
	}

	private int getOpenSound() {
		return 1006;
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos blockpos = context.getPos();
		if (blockpos.getY() < 255 && context.getWorld().getBlockState(blockpos.up()).isReplaceable(context)) {
			World world = context.getWorld();
			boolean flag = world.isBlockPowered(blockpos) || world.isBlockPowered(blockpos.up());
			return this.getDefaultState().with(DoorBlock.FACING, context.getPlacementHorizontalFacing()).with(DoorBlock.HINGE, this.getHingeSide(context)).with(DoorBlock.POWERED, flag).with(DoorBlock.OPEN, flag).with(DoorBlock.HALF, DoubleBlockHalf.LOWER);
		} else {
			return null;
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlockState(pos.up(), state.with(DoorBlock.HALF, DoubleBlockHalf.UPPER), 3);
	}

	private DoorHingeSide getHingeSide(BlockItemUseContext p_208073_1_) {
		IBlockReader iblockreader = p_208073_1_.getWorld();
		BlockPos blockpos = p_208073_1_.getPos();
		Direction direction = p_208073_1_.getPlacementHorizontalFacing();
		BlockPos blockpos1 = blockpos.up();
		Direction direction1 = direction.rotateYCCW();
		BlockPos blockpos2 = blockpos.offset(direction1);
		BlockState blockstate = iblockreader.getBlockState(blockpos2);
		BlockPos blockpos3 = blockpos1.offset(direction1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos3);
		Direction direction2 = direction.rotateY();
		BlockPos blockpos4 = blockpos.offset(direction2);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos4);
		BlockPos blockpos5 = blockpos1.offset(direction2);
		BlockState blockstate3 = iblockreader.getBlockState(blockpos5);
		int i = (isOpaque(blockstate.getCollisionShape(iblockreader, blockpos2)) ? -1 : 0) + (isOpaque(blockstate1.getCollisionShape(iblockreader, blockpos3)) ? -1 : 0) + (isOpaque(blockstate2.getCollisionShape(iblockreader, blockpos4)) ? 1 : 0) + (isOpaque(blockstate3.getCollisionShape(iblockreader, blockpos5)) ? 1 : 0);
		boolean flag = blockstate.getBlock() == this && blockstate.get(DoorBlock.HALF) == DoubleBlockHalf.LOWER;
		boolean flag1 = blockstate2.getBlock() == this && blockstate2.get(DoorBlock.HALF) == DoubleBlockHalf.LOWER;
		if ((!flag || flag1) && i <= 0) {
			if ((!flag1 || flag) && i >= 0) {
				int j = direction.getXOffset();
				int k = direction.getZOffset();
				Vec3d vec3d = p_208073_1_.getHitVec();
				double d0 = vec3d.x - (double) blockpos.getX();
				double d1 = vec3d.z - (double) blockpos.getZ();
				return (j >= 0 || !(d1 < 0.5D)) && (j <= 0 || !(d1 > 0.5D)) && (k >= 0 || !(d0 > 0.5D)) && (k <= 0 || !(d0 < 0.5D)) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
			} else {
				return DoorHingeSide.LEFT;
			}
		} else {
			return DoorHingeSide.RIGHT;
		}
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

		BlockPos blockpos = state.get(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? pos : pos.down();
		BlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);

		if (iblockstate.getBlock() != this) {
			return false;
		} else {
			state = state.cycle(DoorBlock.OPEN);
			worldIn.setBlockState(pos, state, 10);
			worldIn.playEvent(player, state.get(DoorBlock.OPEN) ? this.getOpenSound() : this.getCloseSound(), pos, 0);
			return true;
		}
	}

	public void toggleDoor(World worldIn, BlockPos pos, boolean open) {
		BlockState blockstate = worldIn.getBlockState(pos);
		if (blockstate.getBlock() == this && blockstate.get(DoorBlock.OPEN) != open) {
			worldIn.setBlockState(pos, blockstate.with(DoorBlock.OPEN, open), 10);
			this.playSound(worldIn, pos, open);
		}
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
		boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.offset(state.get(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
		if (blockIn != this && flag != state.get(DoorBlock.POWERED)) {
			if (flag != state.get(DoorBlock.OPEN)) {
				this.playSound(worldIn, pos, flag);
			}

			worldIn.setBlockState(pos, state.with(DoorBlock.POWERED, flag).with(DoorBlock.OPEN, flag), 2);
		}

	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.down();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		if (state.get(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
			return Block.hasSolidSide(blockstate, worldIn, blockpos, Direction.UP);
		} else {
			return blockstate.getBlock() == this;
		}
	}

	private void playSound(World p_196426_1_, BlockPos p_196426_2_, boolean p_196426_3_) {
		p_196426_1_.playEvent((PlayerEntity) null, p_196426_3_ ? this.getOpenSound() : this.getCloseSound(), p_196426_2_, 0);
	}

	@Override
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(DoorBlock.FACING, rot.rotate(state.get(DoorBlock.FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return mirrorIn == Mirror.NONE ? state : state.rotate(mirrorIn.toRotation(state.get(DoorBlock.FACING))).cycle(DoorBlock.HINGE);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public long getPositionRandom(BlockState state, BlockPos pos) {
		return MathHelper.getCoordinateRandom(pos.getX(), pos.down(state.get(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
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
	public boolean clearColorizer(World worldIn, BlockPos pos, BlockState state, PlayerEntity player, Hand hand) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityColorizer) {
			TileEntityColorizer tileColorizer = (TileEntityColorizer) te;
			BlockState storedState = tileColorizer.getStoredBlockState();

			// Can only clear a filled colorizer
			if (storedState != Blocks.AIR.getDefaultState()) {
				if (DecorConfig.consumeBlock.get() && !player.abilities.isCreativeMode) {
					ItemEntity blockDropped = new ItemEntity(worldIn, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), new ItemStack(tileColorizer.getStoredBlockState().getBlock(), 1));
					if (!worldIn.isRemote) {
						worldIn.addEntity(blockDropped);
						if (!(player instanceof FakePlayer)) {
							blockDropped.onCollideWithPlayer(player);
						}
					}
				}

				// Clear self
				if (super.setColorizer(worldIn, pos, state, null, player, hand, false)) {

					SoundType placeSound = state.getSoundType(worldIn, pos, player);

					worldIn.playSound(player, pos, placeSound.getPlaceSound(), SoundCategory.BLOCKS, (placeSound.getVolume() + 1.0F) / 2.0F, placeSound.getPitch() * 0.8F);

					// Clear other half of door
					if (state.get(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
						return super.setColorizer(worldIn, pos.up(), state, null, player, hand, false);
					} else if (state.get(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
						return super.setColorizer(worldIn, pos.down(), state, null, player, hand, false);
					}
				}
			}
		}

		return false;
	}

	@Override
	public boolean setColorizer(World worldIn, BlockPos pos, BlockState state, BlockState toSetState, PlayerEntity player, Hand hand, boolean consumeItem) {
		// Set self
		if (super.setColorizer(worldIn, pos, state, toSetState, player, hand, consumeItem)) {

			// Set other half of door and consume once
			if (state.get(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
				return super.setColorizer(worldIn, pos.up(), state, toSetState, player, hand, false);
			} else if (state.get(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
				return super.setColorizer(worldIn, pos.down(), state, toSetState, player, hand, false);
			}
		}
		return false;
	}
}
