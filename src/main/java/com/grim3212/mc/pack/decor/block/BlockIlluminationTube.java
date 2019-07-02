package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockIlluminationTube extends BlockManual {

	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.values());

	protected static final VoxelShape STANDING_AABB = Block.makeCuboidShape(6.4f, 0f, 6.4f, 9.6f, 9.6f, 9.6f);
	protected static final VoxelShape STANDING_FLIPPED_AABB = Block.makeCuboidShape(6.4f, 6.28f, 6.4f, 9.6f, 16f, 9.6f);
	protected static final VoxelShape TORCH_NORTH_AABB = Block.makeCuboidShape(5.6f, 3.2f, 11.04f, 10.4f, 12.8f, 16f);
	protected static final VoxelShape TORCH_SOUTH_AABB = Block.makeCuboidShape(5.6f, 3.2f, 0f, 10.4f, 12.8f, 4.8f);
	protected static final VoxelShape TORCH_WEST_AABB = Block.makeCuboidShape(11.04f, 3.2f, 5.6f, 16f, 12.8f, 10.4f);
	protected static final VoxelShape TORCH_EAST_AABB = Block.makeCuboidShape(0f, 3.2f, 5.6f, 4.8f, 12.8f, 10.4f);

	public BlockIlluminationTube() {
		super(DecorNames.ILLUMINATION_TUBE, Block.Properties.create(Material.MISCELLANEOUS).sound(SoundType.GLASS).lightValue(15).doesNotBlockMovement().hardnessAndResistance(0f).tickRandomly());
	}

	@Override
	protected BlockState getState() {
		return this.stateContainer.getBaseState().with(FACING, Direction.UP);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.illuminationTube_page;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(FACING)) {
		case EAST:
			return TORCH_EAST_AABB;
		case WEST:
			return TORCH_WEST_AABB;
		case SOUTH:
			return TORCH_SOUTH_AABB;
		case NORTH:
			return TORCH_NORTH_AABB;
		case DOWN:
			return STANDING_FLIPPED_AABB;
		default:
			return STANDING_AABB;
		}
	}

	private boolean canPlaceOn(IWorldReader worldIn, BlockPos pos) {
		return Block.func_220055_a(worldIn, pos, Direction.UP);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		for (Direction enumfacing : FACING.getAllowedValues()) {
			if (this.canPlaceAt(worldIn, pos, enumfacing)) {
				return true;
			}
		}

		return false;
	}

	private boolean canPlaceAt(IWorldReader worldIn, BlockPos pos, Direction facing) {
		BlockPos blockpos = pos.offset(facing.getOpposite());
		BlockState iblockstate = worldIn.getBlockState(blockpos);

		if (facing.equals(Direction.UP) && this.canPlaceOn(worldIn, blockpos)) {
			return true;
		} else if (facing != Direction.UP) {
			return Block.hasSolidSide(iblockstate, worldIn, blockpos, facing);
		} else {
			return false;
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		if (this.canPlaceAt(context.getWorld(), context.getPos(), context.getFace())) {
			return this.getDefaultState().with(FACING, context.getFace());
		} else {
			for (Direction enumfacing : Direction.Plane.HORIZONTAL) {
				if (this.canPlaceAt(context.getWorld(), context.getPos(), enumfacing)) {
					return this.getDefaultState().with(FACING, enumfacing);
				}
			}

			return this.getDefaultState();
		}
	}

	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean flag) {
		this.checkForDrop(worldIn, pos, state);
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean flag) {
		this.onNeighborChangeInternal(worldIn, pos, state);
	}

	protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, BlockState state) {
		if (!this.checkForDrop(worldIn, pos, state)) {
			return true;
		} else {
			Direction enumfacing = state.get(FACING);
			Direction.Axis enumfacing$axis = enumfacing.getAxis();
			Direction enumfacing1 = enumfacing.getOpposite();
			BlockPos blockpos = pos.offset(enumfacing1);
			boolean flag = false;

			if (enumfacing$axis.isHorizontal() && !Block.hasSolidSide(worldIn.getBlockState(blockpos), worldIn, blockpos, enumfacing)) {
				flag = true;
			} else if (enumfacing$axis.isVertical() && !this.canPlaceOn(worldIn, blockpos)) {
				flag = true;
			}

			if (flag) {
				worldIn.destroyBlock(pos, true);
				return true;
			} else {
				return false;
			}
		}
	}

	protected boolean checkForDrop(World worldIn, BlockPos pos, BlockState state) {
		if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, state.get(FACING))) {
			return true;
		} else {
			if (worldIn.getBlockState(pos).getBlock() == this) {
				worldIn.destroyBlock(pos, true);
			}

			return false;
		}
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
		return state.with(FACING, direction.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}
}
