package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

public class BlockSiding extends BlockManual {

	public static final PropertyEnum<EnumSidingColor> COLOR = PropertyEnum.<EnumSidingColor> create("color", EnumSidingColor.class);

	public BlockSiding() {
		super(Material.IRON, SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumSidingColor.white));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { COLOR });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(COLOR).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(COLOR, EnumSidingColor.values[meta]);
	}

	public static enum EnumSidingColor implements IStringSerializable {
		white(16777215), red(8331810), green(4489028), blue(4145023);

		public static final EnumSidingColor values[] = values();

		public final int color;

		EnumSidingColor(int color) {
			this.color = color;
		}

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

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.decoration_page;
	}
}
