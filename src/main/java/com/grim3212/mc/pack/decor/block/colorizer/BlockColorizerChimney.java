package com.grim3212.mc.pack.decor.block.colorizer;

import java.util.Random;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockColorizerChimney extends BlockColorizerFireplaceBase {

	public BlockColorizerChimney() {
		super(DecorNames.CHIMNEY);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.FIRE) {
			if (worldIn.rand.nextInt(2) == 0) {
				int smokeheight = 1;
				while (worldIn.getBlockState(pos.up(smokeheight)).getBlock() == DecorBlocks.chimney) {
					smokeheight++;
				}

				GrimDecor.proxy.produceSmoke(worldIn, pos.up(smokeheight), 0.5D, 0.0D, 0.5D, 1, true);
			}
		}
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.chimney_page;
	}

}
