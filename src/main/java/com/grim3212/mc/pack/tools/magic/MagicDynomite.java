package com.grim3212.mc.pack.tools.magic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class MagicDynomite extends BaseMagic {

	public MagicDynomite() {
		super("dynomite");
	}

	@Override
	public int performMagic(World world, EntityPlayer playerIn, EnumHand hand, int dmgLeft, float range) {
		if (!world.isRemote)
			world.createExplosion(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, range, true);

		return 1;
	}

}
