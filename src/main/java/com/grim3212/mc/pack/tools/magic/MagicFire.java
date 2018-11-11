package com.grim3212.mc.pack.tools.magic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagicFire extends BaseMagic {

	public MagicFire() {
		super("fire");
	}

	@Override
	public int performMagic(World world, EntityPlayer playerIn, EnumHand hand, int dmgLeft, float range) {
		Vec3d vec3d = playerIn.getLook(1.0F);

		double x = playerIn.posX + (double) (vec3d.x * 0.3F);
		double y = playerIn.posY + playerIn.getEyeHeight() + (double) (vec3d.y * 0.3F);
		double z = playerIn.posZ + (double) (vec3d.z * 0.3F);

		EntityFireball entityfireball = new EntitySmallFireball(world, x, y, z, vec3d.x, vec3d.y, vec3d.z);
		entityfireball.shootingEntity = playerIn;

		if (!world.isRemote)
			world.spawnEntity(entityfireball);

		return 1;
	}

}
