package com.grim3212.mc.pack.decor.block;

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
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumLanternType.paper));
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
		return state.getValue(VARIANT).ordinal();
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, EnumLanternType.values[meta]);
	}

	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).ordinal();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT });
	}

	public enum EnumLanternType implements IStringSerializable {
		paper, bone, iron;

		public static final EnumLanternType values[] = values();
		
		public static String[] names() {
			EnumLanternType[] states = values;
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
