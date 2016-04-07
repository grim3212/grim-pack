package com.grim3212.mc.decor.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public class BlockLantern extends Block {

	public static final PropertyEnum<BlockLantern.EnumLanternType> VARIANT = PropertyEnum.create("variant", BlockLantern.EnumLanternType.class);

	protected BlockLantern() {
		super(Material.circuits);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumLanternType.PAPER));
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List<ItemStack> list) {
		for (int i = 0; i < EnumLanternType.values().length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		return null;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return ((BlockLantern.EnumLanternType) state.getValue(VARIANT)).getMetadata();
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, BlockLantern.EnumLanternType.byMetadata(meta));
	}

	public int getMetaFromState(IBlockState state) {
		return ((BlockLantern.EnumLanternType) state.getValue(VARIANT)).getMetadata();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT });
	}

	public enum EnumLanternType implements IStringSerializable {
		PAPER(0, "paper"), BONE(1, "bone"), IRON(2, "iron");

		private final int meta;
		private final String name;
		private static final EnumLanternType[] META_LOOKUP = new EnumLanternType[values().length];

		private EnumLanternType(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		@Override
		public String getName() {
			return this.name;
		}

		public int getMetadata() {
			return this.meta;
		}

		public static EnumLanternType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static {
			for (EnumLanternType colour : values()) {
				META_LOOKUP[colour.getMetadata()] = colour;
			}
		}

		public static String[] names() {
			EnumLanternType[] types = values();
			String[] names = new String[types.length];

			for (int i = 0; i < types.length; i++) {
				names[i] = types[i].getName();
			}

			return names;
		}
	}
}
