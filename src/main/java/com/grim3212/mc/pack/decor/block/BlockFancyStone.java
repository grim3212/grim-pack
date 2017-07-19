package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockFancyStone extends BlockManual {

	public BlockFancyStone() {
		super("fancy_stone", Material.ROCK, SoundType.STONE);
		setHardness(0.5F);
		setResistance(10F);
		setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.fancyStone_page;
	}
}
