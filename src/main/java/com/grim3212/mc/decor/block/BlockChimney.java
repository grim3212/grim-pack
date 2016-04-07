package com.grim3212.mc.decor.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockChimney extends BlockTextured {

	public BlockChimney() {
		setTickRandomly(true);
	}

	@Override
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		double d = (float) pos.getX() + 0.375F + rand.nextFloat() / 4F;
		double d1 = (float) pos.getY() + 0.5F + rand.nextFloat() / 4F;
		double d2 = (float) pos.getZ() + 0.375F + rand.nextFloat() / 4F;
		worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d, d1, d2, 0.0D, 0.0D, 0.0D);
		worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d, d1, d2, 0.0D, 0.0D, 0.0D);
		worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d, d1, d2, 0.0D, 0.0D, 0.0D);
		int l = worldIn.getBlockState(pos).getBlock().getMetaFromState(getDefaultState());
		if (l == 7) {
			d += (rand.nextFloat() - 0.5F) / 8F;
			d1 += (rand.nextFloat() - 0.5F) / 8F;
			d2 += (rand.nextFloat() - 0.5F) / 8F;
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d + 0.10000000149011612D, d1, d2, 0.0D, 0.0D, 0.0D);
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d - 0.10000000149011612D, d1, d2, 0.0D, 0.0D, 0.0D);
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d + 0.20000000298023224D, d1, d2, 0.0D, 0.0D, 0.0D);
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d - 0.20000000298023224D, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}
}