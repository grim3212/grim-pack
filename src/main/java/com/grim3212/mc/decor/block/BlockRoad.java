package com.grim3212.mc.decor.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRoad extends Block {

	public BlockRoad() {
		super(Material.rock);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {
		double d = Math.abs(entityIn.motionX);
		double d1 = Math.abs(entityIn.motionZ);
		if (d < 0.29999999999999999D) {
			entityIn.motionX *= 3.1000000000000001D;
		}
		if (d1 < 0.29999999999999999D) {
			entityIn.motionZ *= 3.1000000000000001D;
		}
	}
}
