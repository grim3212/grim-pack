package com.grim3212.mc.pack.cuisine.block;

import net.minecraft.block.BlockCake;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockChocolateCake extends BlockCake {

	protected BlockChocolateCake() {
		super();
		setSoundType(SoundType.CLOTH);
	}

	@Override
	public ItemStack getItem(World worldIn, net.minecraft.util.math.BlockPos pos, IBlockState state) {
		return new ItemStack(CuisineBlocks.chocolate_cake);
	}
}
