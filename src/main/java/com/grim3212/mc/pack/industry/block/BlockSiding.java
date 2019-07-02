package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IProperty;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.chunk.BlockStateContainer;

public class BlockSiding extends BlockManual {

	public static final EnumProperty<EnumSidingColor> COLOR = EnumProperty.<EnumSidingColor>create("color", EnumSidingColor.class);

	public BlockSiding(String name) {
		super(name, Material.IRON, SoundType.STONE);
		setHardness(1.0F);
		setResistance(10.0F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	protected BlockState getState() {
		return this.blockState.getBaseState().withProperty(COLOR, EnumSidingColor.white);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { COLOR });
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return state.getValue(COLOR).ordinal();
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
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
	public Page getPage(BlockState state) {
		return ManualIndustry.decoration_page;
	}
}
