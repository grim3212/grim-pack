package com.grim3212.mc.pack.decor.block.colorizer;

import java.util.Map;

import com.google.common.collect.Maps;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class BlockColorizerTable extends BlockColorizer {

	public static final BooleanProperty NORTH = BooleanProperty.create("north");
	public static final BooleanProperty EAST = BooleanProperty.create("east");
	public static final BooleanProperty SOUTH = BooleanProperty.create("south");
	public static final BooleanProperty WEST = BooleanProperty.create("west");
	public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), (map) -> {
		map.put(Direction.NORTH, NORTH);
		map.put(Direction.EAST, EAST);
		map.put(Direction.SOUTH, SOUTH);
		map.put(Direction.WEST, WEST);
	});

	public BlockColorizerTable() {
		super(DecorNames.TABLE);
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, false);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return BlockColorizerCounter.COUNTER;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(NORTH, SOUTH, WEST, EAST);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos blockpos = context.getPos();
		BlockPos northPos = blockpos.north();
		BlockPos eastPos = blockpos.east();
		BlockPos southPos = blockpos.south();
		BlockPos westPos = blockpos.west();
		return super.getStateForPlacement(context).with(NORTH, this.canConnectTo(context.getWorld(), northPos)).with(EAST, this.canConnectTo(context.getWorld(), eastPos)).with(SOUTH, this.canConnectTo(context.getWorld(), southPos)).with(WEST, this.canConnectTo(context.getWorld(), westPos));
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), this.canConnectTo(worldIn, facingPos)) : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	public boolean canConnectTo(IBlockReader worldIn, BlockPos pos) {
		BlockState blockState = worldIn.getBlockState(pos).getBlock().getDefaultState();
		return blockState == this.getDefaultState();
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.table_page;
	}
}
