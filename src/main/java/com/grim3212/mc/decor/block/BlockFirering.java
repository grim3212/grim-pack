package com.grim3212.mc.decor.block;

import java.util.Random;

import com.grim3212.mc.decor.config.DecorConfig;
import com.grim3212.mc.decor.tile.TileEntityFireplace;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockFirering extends BlockFireplaceBase {

	public BlockFirering() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.4F, 1.0F);
	}

	@Override
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (DecorConfig.isFireParticles) {
			TileEntityFireplace tef = (TileEntityFireplace) worldIn.getTileEntity(pos);
			if (tef.isActive()) {
				for (int i = 0; i < 5; i++) {
					double xVar = (worldIn.rand.nextDouble() - 0.5D) / 5.0D;
					double yVar = (worldIn.rand.nextDouble() - 0.5D) / 5.0D;
					double zVar = (worldIn.rand.nextDouble() - 0.5D) / 5.0D;

					double height = rand.nextDouble();
					if (height > 0.7D || height < 0.2D)
						height = 0.3D;
					worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D + xVar, pos.getY() + height + yVar, pos.getZ() + 0.5D + zVar, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}

}
