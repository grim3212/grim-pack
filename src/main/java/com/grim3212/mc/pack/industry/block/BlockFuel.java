package com.grim3212.mc.pack.industry.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockFuel extends Block {

	protected BlockFuel() {
		super(Material.IRON);
		this.setSoundType(SoundType.METAL);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);

		if (!worldIn.isRemote)
			worldIn.createExplosion((Entity) null, pos.getX(), pos.getY(), pos.getZ(), 4.0F, true);
	}
}
