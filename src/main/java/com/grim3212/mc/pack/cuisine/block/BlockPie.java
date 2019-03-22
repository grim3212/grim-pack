package com.grim3212.mc.pack.cuisine.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockPie extends BlockCake implements IManualBlock {

	public BlockPie(String name) {
		super(Block.Properties.create(Material.CAKE).sound(SoundType.CLOTH).hardnessAndResistance(0.5f));
		setRegistryName(name);
	}

	@Override
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualCuisine.bakedPie_page;
	}
}
