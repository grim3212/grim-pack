package com.grim3212.mc.industry.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockFuel extends Block {

	protected BlockFuel() {
		super(Material.iron);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);

		if (!worldIn.isRemote)
			worldIn.createExplosion((Entity) null, pos.getX(), pos.getY(), pos.getZ(), 4.0F, true);
	}
}
