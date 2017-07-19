package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockLantern extends BlockManual {

	public static final PropertyEnum<BlockLantern.EnumLanternType> VARIANT = PropertyEnum.create("variant", BlockLantern.EnumLanternType.class);

	protected BlockLantern() {
		super("lantern", Material.CIRCUITS, SoundType.STONE);
		setLightLevel(0.9375F);
		setHardness(0.1F);
		setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
	}

	@Override
	protected IBlockState getState() {
		return super.getState().withProperty(VARIANT, EnumLanternType.paper);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (int i = 0; i < EnumLanternType.values.length; i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos) {
		return NULL_AABB;
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

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
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

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.lantern_page;
	}
}
