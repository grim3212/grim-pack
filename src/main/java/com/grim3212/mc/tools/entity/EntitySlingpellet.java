package com.grim3212.mc.tools.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySlingpellet extends EntityThrowable {

	public EntitySlingpellet(World world) {
		super(world);
	}

	public EntitySlingpellet(World world, EntityLivingBase entity) {
		super(world, entity);
	}

	public EntitySlingpellet(World world, double par2, double par4, double par6) {
		super(world, par2, par4, par6);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if (mop.entityHit != null) {

			mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 2);
		}

		for (int i = 0; i < 8; ++i) {
			this.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}

		if (!this.worldObj.isRemote) {
			this.setDead();
		}
	}
}
