package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHTorch extends BlockTorch {

	public BlockHTorch() {
		super();
		this.setSoundType(SoundType.GLASS);
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
	}
}
