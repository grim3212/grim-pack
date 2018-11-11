package com.grim3212.mc.pack.tools.magic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class MagicEarth extends BaseMagic{

	public MagicEarth() {
		super("earth");
	}

	@Override
	public int performMagic(World world, EntityPlayer playerIn, EnumHand hand, int dmgLeft, float range) {
		return 0;
	}

}
