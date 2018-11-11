package com.grim3212.mc.pack.tools.magic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MagicHeal extends BaseMagic {

	public MagicHeal() {
		super("heal");
	}

	@Override
	public int performMagic(World world, EntityPlayer playerIn, EnumHand hand, int dmgLeft, float range) {
		if (playerIn.getHealth() < playerIn.getMaxHealth()) {

			float toHeal = MathHelper.clamp((playerIn.getMaxHealth() - playerIn.getHealth()), 0, 10);

			playerIn.heal(toHeal);

			for (int i = 0; i < 20; i++) {
				double d3 = world.rand.nextGaussian() * 0.02D;
				double d4 = world.rand.nextGaussian() * 0.02D;
				double d5 = world.rand.nextGaussian() * 0.02D;
				world.spawnParticle(EnumParticleTypes.HEART, playerIn.posX + (double) (world.rand.nextFloat() * playerIn.width), playerIn.posY + (double) (world.rand.nextFloat() * playerIn.height), playerIn.posZ + (double) (world.rand.nextFloat() * playerIn.width), d3, d4, d5);
			}

			return (int) toHeal;
		}

		return 0;
	}

}
