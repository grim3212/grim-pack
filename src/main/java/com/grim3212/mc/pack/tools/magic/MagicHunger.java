package com.grim3212.mc.pack.tools.magic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MagicHunger extends BaseMagic {

	public MagicHunger() {
		super("hunger");
	}

	@Override
	public int performMagic(World world, EntityPlayer playerIn, EnumHand hand, int dmgLeft, float range) {
		if (playerIn.getFoodStats().needFood()) {

			int toEat = MathHelper.clamp((20 - playerIn.getFoodStats().getFoodLevel()), 0, 10);

			playerIn.getFoodStats().addStats(toEat, 0.3f);

			for (int i = 0; i < 20; i++) {
				double d3 = world.rand.nextGaussian() * 0.02D;
				double d4 = world.rand.nextGaussian() * 0.02D;
				double d5 = world.rand.nextGaussian() * 0.02D;
				world.spawnParticle(EnumParticleTypes.SPELL_MOB_AMBIENT, playerIn.posX + (double) (world.rand.nextFloat() * playerIn.width), playerIn.posY + (double) (world.rand.nextFloat() * playerIn.height), playerIn.posZ + (double) (world.rand.nextFloat() * playerIn.width), d3, d4, d5);
			}

			return toEat;
		}

		return 0;
	}

}
