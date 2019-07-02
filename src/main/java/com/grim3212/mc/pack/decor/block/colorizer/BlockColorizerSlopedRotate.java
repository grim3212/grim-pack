package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.util.DecorUtil;
import com.grim3212.mc.pack.decor.util.DecorUtil.SlopeType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockColorizerSlopedRotate extends BlockColorizerFurnitureRotate {

	public static final EnumProperty<EnumHalf> HALF = EnumProperty.<EnumHalf>create("half", EnumHalf.class);

	private final SlopeType type;

	public BlockColorizerSlopedRotate(SlopeType type) {
		super(type.getName());
		this.type = type;
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(HALF, EnumHalf.BOTTOM).with(HORIZONTAL_FACING, Direction.NORTH);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(HALF, HORIZONTAL_FACING);
	}

	@Override
	public Page getPage(BlockState state) {
		switch (this.type) {
		case Corner:
			return ManualDecor.corner_page;
		case SlantedCorner:
			return ManualDecor.slantedCorner_page;
		case Slope:
			return ManualDecor.slope_page;
		case SlopedAngle:
			return ManualDecor.slopedAngle_page;
		case ObliqueSlope:
			return ManualDecor.obliqueSlope_page;
		case SlopedIntersection:
			return ManualDecor.slopedIntersection_page;
		default:
			return null;
		}
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return DecorUtil.addAxisAlignedBoxes(state, worldIn, pos, context, this.type.getNumPieces());
	}

	// Use own Half just incase more are added (unlikely)
	public static enum EnumHalf implements IStringSerializable {
		TOP("top"), BOTTOM("bottom");

		public static final EnumHalf values[] = values();

		private final String name;

		private EnumHalf(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}

		public String getName() {
			return this.name;
		}
	}
}
