package com.grim3212.mc.pack.industry.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSwalk extends Block {

	public BlockSwalk() {
		super(Material.ROCK);
		setSoundType(SoundType.STONE);
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
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
