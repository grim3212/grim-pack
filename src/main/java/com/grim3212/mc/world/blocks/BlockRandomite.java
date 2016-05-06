package com.grim3212.mc.world.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockRandomite extends Block {

	protected BlockRandomite() {
		super(Material.rock);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		int i = rand.nextInt(63);
		return (Item) (i > 0 && i < 18 ? Items.coal : (i > 18 && i < 31 ? Item.getItemFromBlock(Blocks.iron_ore) : (i > 31 && i < 35 ? Item.getItemFromBlock(Blocks.gold_ore) : (i > 35 && i < 38 ? Items.diamond : (i > 38 && i < 48 ? Items.redstone : (i > 48 && i < 51 ? Items.emerald : (i > 51 && i < 58 ? Items.quartz : Items.egg)))))));
	}
}
