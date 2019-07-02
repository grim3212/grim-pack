package com.grim3212.mc.pack.cuisine.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.init.CuisineNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockChocolateCake extends CakeBlock implements IManualBlock {

	protected BlockChocolateCake() {
		super(Block.Properties.create(Material.CAKE).sound(SoundType.CLOTH).hardnessAndResistance(0.5f));
		setRegistryName(CuisineNames.CHOCOLATE_CAKE);
	}

	@Override
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(CuisineBlocks.chocolate_cake);
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualCuisine.chocolateCake_page;
	}
}
