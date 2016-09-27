package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.item.IndustryItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockOilOre extends BlockManual {

	protected BlockOilOre() {
		super(Material.ROCK, SoundType.STONE);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return IndustryItems.oily_chunk;
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 2 + random.nextInt(2);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.derrick_page;
	}
}
