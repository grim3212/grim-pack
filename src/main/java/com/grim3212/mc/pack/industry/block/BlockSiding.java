package com.grim3212.mc.pack.industry.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;

public class BlockSiding extends Block {

	public static final PropertyEnum<EnumSidingColor> COLOR = PropertyEnum.<EnumSidingColor> create("color", EnumSidingColor.class);

	public BlockSiding() {
		super(Material.iron);
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumSidingColor.white));
	}
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { COLOR });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(COLOR).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(COLOR, EnumSidingColor.values[meta]);
	}

	@Override
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
		IBlockState state = worldIn.getBlockState(pos);
		EnumSidingColor color = state.getValue(COLOR);

		switch (color) {
		case blue:
			return 4145023;
		case green:
			return 4489028;
		case red:
			return 8331810;
		case white:
			return super.colorMultiplier(worldIn, pos, renderPass);
		}

		return super.colorMultiplier(worldIn, pos, renderPass);
	}

	public static enum EnumSidingColor implements IStringSerializable {
		white, red, green, blue;

		public static final EnumSidingColor values[] = values();

		public static String[] names() {
			EnumSidingColor[] states = values;
			String[] names = new String[states.length];

			for (int i = 0; i < states.length; i++) {
				names[i] = states[i].name();
			}

			return names;
		}

		@Override
		public String getName() {
			return this.name();
		}
	}
}
