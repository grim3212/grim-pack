package com.grim3212.mc.pack.decor.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRoad extends Block {

	public BlockRoad() {
		super(Material.rock);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {
		double maxSpeed = 1.5D;
		double speedMultiplier = 2.0D;

		if (entityIn instanceof EntityPlayer) {
			double entMotX = entityIn.motionX;
			double entMotZ = entityIn.motionZ;
			double speedUp = Math.sqrt(entMotX * entMotX + entMotZ * entMotZ);
			if (speedUp != 0.0D) {
				double var12 = speedUp * speedMultiplier;
				var12 = Math.min(var12, maxSpeed);
				entityIn.motionX *= var12 / speedUp;
				entityIn.motionZ *= var12 / speedUp;
			}
		}
	}
}
