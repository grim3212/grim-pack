package com.grim3212.mc.pack.decor.block.colorizer;

import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SixWayBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class BlockColorizerFourWay extends BlockColorizer implements IWaterLoggable {

	public static final BooleanProperty NORTH = BooleanProperty.create("north");
	public static final BooleanProperty EAST = BooleanProperty.create("east");
	public static final BooleanProperty SOUTH = BooleanProperty.create("south");
	public static final BooleanProperty WEST = BooleanProperty.create("west");
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	protected static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter((entry) -> {
		return entry.getKey().getAxis().isHorizontal();
	}).collect(Util.toMapCollector());
	protected final VoxelShape[] collisionShapes;
	protected final VoxelShape[] shapes;
	private final Object2IntMap<BlockState> stateMap = new Object2IntOpenHashMap<>();
	
	public BlockColorizerFourWay(String name, float nodeWidth, float extensionWidth, float p_i48420_3_, float p_i48420_4_, float collisionY) {
		super(name);
		this.collisionShapes = this.makeShapes(nodeWidth, extensionWidth, collisionY, 0.0F, collisionY);
		this.shapes = this.makeShapes(nodeWidth, extensionWidth, p_i48420_3_, 0.0F, p_i48420_4_);
	}

	public BlockColorizerFourWay(String name, Properties props, float nodeWidth, float extensionWidth, float p_i48420_3_, float p_i48420_4_, float collisionY) {
		super(name, props);
		this.collisionShapes = this.makeShapes(nodeWidth, extensionWidth, collisionY, 0.0F, collisionY);
		this.shapes = this.makeShapes(nodeWidth, extensionWidth, p_i48420_3_, 0.0F, p_i48420_4_);
	}

	protected VoxelShape[] makeShapes(float nodeWidth, float extensionWidth, float p_196408_3_, float p_196408_4_, float p_196408_5_) {
		float f = 8.0F - nodeWidth;
		float f1 = 8.0F + nodeWidth;
		float f2 = 8.0F - extensionWidth;
		float f3 = 8.0F + extensionWidth;
		VoxelShape voxelshape = Block.makeCuboidShape((double) f, 0.0D, (double) f, (double) f1, (double) p_196408_3_, (double) f1);
		VoxelShape voxelshape1 = Block.makeCuboidShape((double) f2, (double) p_196408_4_, 0.0D, (double) f3, (double) p_196408_5_, (double) f3);
		VoxelShape voxelshape2 = Block.makeCuboidShape((double) f2, (double) p_196408_4_, (double) f2, (double) f3, (double) p_196408_5_, 16.0D);
		VoxelShape voxelshape3 = Block.makeCuboidShape(0.0D, (double) p_196408_4_, (double) f2, (double) f3, (double) p_196408_5_, (double) f3);
		VoxelShape voxelshape4 = Block.makeCuboidShape((double) f2, (double) p_196408_4_, (double) f2, 16.0D, (double) p_196408_5_, (double) f3);
		VoxelShape voxelshape5 = VoxelShapes.or(voxelshape1, voxelshape4);
		VoxelShape voxelshape6 = VoxelShapes.or(voxelshape2, voxelshape3);
		VoxelShape[] avoxelshape = new VoxelShape[] { VoxelShapes.empty(), voxelshape2, voxelshape3, voxelshape6, voxelshape1, VoxelShapes.or(voxelshape2, voxelshape1), VoxelShapes.or(voxelshape3, voxelshape1), VoxelShapes.or(voxelshape6, voxelshape1), voxelshape4, VoxelShapes.or(voxelshape2, voxelshape4), VoxelShapes.or(voxelshape3, voxelshape4), VoxelShapes.or(voxelshape6, voxelshape4), voxelshape5, VoxelShapes.or(voxelshape2, voxelshape5), VoxelShapes.or(voxelshape3, voxelshape5),
				VoxelShapes.or(voxelshape6, voxelshape5) };

		for (int i = 0; i < 16; ++i) {
			avoxelshape[i] = VoxelShapes.or(voxelshape, avoxelshape[i]);
		}

		return avoxelshape;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return !state.get(WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return this.shapes[this.getIndex(state)];
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return this.collisionShapes[this.getIndex(state)];
	}

	private static int getMask(Direction facing) {
		return 1 << facing.getHorizontalIndex();
	}

	protected int getIndex(BlockState state) {
		return this.stateMap.computeIntIfAbsent(state, (mapState) -> {
			int i = 0;
			if (mapState.get(NORTH)) {
				i |= getMask(Direction.NORTH);
			}

			if (mapState.get(EAST)) {
				i |= getMask(Direction.EAST);
			}

			if (mapState.get(SOUTH)) {
				i |= getMask(Direction.SOUTH);
			}

			if (mapState.get(WEST)) {
				i |= getMask(Direction.WEST);
			}

			return i;
		});
	}

	@Override
	@SuppressWarnings("deprecation")
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		switch (rot) {
		case CLOCKWISE_180:
			return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
		case COUNTERCLOCKWISE_90:
			return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
		case CLOCKWISE_90:
			return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
		default:
			return state;
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		switch (mirrorIn) {
		case LEFT_RIGHT:
			return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
		case FRONT_BACK:
			return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
		default:
			return super.mirror(state, mirrorIn);
		}
	}
}
