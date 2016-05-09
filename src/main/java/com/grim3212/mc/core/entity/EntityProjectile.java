package com.grim3212.mc.core.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public abstract class EntityProjectile extends EntityArrow implements IThrowableEntity {

	public EntityProjectile(World worldIn) {
		super(worldIn);
	}

	public EntityProjectile(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityProjectile(World world, EntityLivingBase shooter) {
		super(world, shooter);
	}
}
