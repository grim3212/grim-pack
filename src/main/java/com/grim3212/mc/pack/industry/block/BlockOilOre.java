package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.industry.item.IndustryItems;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockOilOre extends Block {

	protected BlockOilOre() {
		super(Material.ROCK);
		this.setSoundType(SoundType.STONE);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return IndustryItems.oily_chunk;
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 2 + random.nextInt(2);
	}
}
