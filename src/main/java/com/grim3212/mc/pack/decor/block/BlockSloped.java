package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public abstract class BlockSloped extends BlockColorizer implements IManualBlock {

	public static final PropertyEnum<EnumHalf> HALF = PropertyEnum.<EnumHalf> create("half", EnumHalf.class);

	public BlockSloped() {
		this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, EnumHalf.BOTTOM));
	}

	// @Override
	// public boolean isVisuallyOpaque() {
	// return false;
	// }

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { HALF }, new IUnlistedProperty[] { BLOCK_STATE });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(HALF, EnumHalf.values[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(HALF) == EnumHalf.TOP ? 0 : 1;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public abstract Page getPage(IBlockState state);

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
