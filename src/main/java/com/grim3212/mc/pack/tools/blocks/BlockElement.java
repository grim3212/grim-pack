package com.grim3212.mc.pack.tools.blocks;

import java.util.Random;

import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockElement extends Block {

	protected BlockElement() {
		super(Material.rock);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ToolsItems.element_115;
	}

	@Override
	public int quantityDropped(Random var1) {
		return 3 + var1.nextInt(2);
	}
}
