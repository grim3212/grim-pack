package com.grim3212.mc.pack.decor.block;

import java.util.Random;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStove extends BlockFireplaceBase {

	public BlockStove() {
	}
	
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.getBlockState(pos).getValue(ACTIVE) && worldIn.getBlockState(pos.up()).getBlock() == DecorBlocks.chimney) {
			int smokeheight = 1;
			while (worldIn.getBlockState(pos.up(smokeheight)).getBlock() == DecorBlocks.chimney) {
				smokeheight++;
			}

			GrimDecor.proxy.produceSmoke(worldIn, pos.up(smokeheight), 0.5D, 0.0D, 0.5D, 1, true);
		}
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.stove_page;
	}
}
