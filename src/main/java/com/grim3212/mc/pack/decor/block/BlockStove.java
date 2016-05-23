package com.grim3212.mc.pack.decor.block;

import java.util.Random;

import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStove extends BlockFireplaceBase {

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (DecorConfig.isFireParticles) {
			if (worldIn.getBlockState(pos).getValue(ACTIVE)) {
				for (int i = 0; i < 5; i++) {
					double xVar = (worldIn.rand.nextDouble() - 0.5D) / 5.0D;
					double yVar = (worldIn.rand.nextDouble() - 0.5D) / 5.0D;
					double zVar = (worldIn.rand.nextDouble() - 0.5D) / 5.0D;

					double height = rand.nextDouble();
					if (height > 0.7D || height < 0.5D)
						height = 0.5D;
					worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D + xVar, pos.getY() + height + yVar, pos.getZ() + 0.5D + zVar, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
}
