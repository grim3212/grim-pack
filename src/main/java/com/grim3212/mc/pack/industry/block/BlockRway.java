package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.item.ItemPaintRollerColor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRway extends BlockManual {

	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 14);

	public BlockRway() {
		super("rway", Material.ROCK, SoundType.STONE);
		setHardness(1.0F);
		setResistance(15.0F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	protected IBlockState getState() {
		return this.blockState.getBaseState().withProperty(TYPE, 0);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (!worldIn.isRemote) {
			if (playerIn.getActiveItemStack() != null && ((playerIn.getActiveItemStack().getItem() instanceof ItemPaintRollerColor))) {
				worldIn.setBlockState(pos, worldIn.getBlockState(pos).cycleProperty(TYPE));
			}
		}
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.rways_page;
	}
}
