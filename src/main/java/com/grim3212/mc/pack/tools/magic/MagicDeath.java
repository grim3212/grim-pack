package com.grim3212.mc.pack.tools.magic;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MagicDeath extends BaseMagic {

	public MagicDeath() {
		super("death");
	}

	@Override
	public int performMagic(World world, EntityPlayer playerIn, EnumHand hand, int dmgLeft, float range) {
		int i = MathHelper.floor(playerIn.posX - (double) range - 1.0D);
		int j = MathHelper.floor(playerIn.posX + (double) range + 1.0D);
		int k = MathHelper.floor(playerIn.posY - (double) range - 1.0D);
		int l = MathHelper.floor(playerIn.posY + (double) range + 1.0D);
		int i1 = MathHelper.floor(playerIn.posZ - (double) range - 1.0D);
		int j1 = MathHelper.floor(playerIn.posZ + (double) range + 1.0D);

		int count = 0;

		// Construct the bounding box and grab all of the entities
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB(i, k, i1, j, l, j1));

		for (int k1 = 0; k1 < list.size(); k1++) {
			Entity entity = (Entity) list.get(k1);
			if (count <= dmgLeft && entity instanceof EntityLivingBase) {
				// Attack the entities
				entity.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), 20);

				count++;
			}
		}

		return count;
	}

}
