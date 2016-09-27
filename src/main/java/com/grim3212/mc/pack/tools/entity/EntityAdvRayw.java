package com.grim3212.mc.pack.tools.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityAdvRayw extends EntityRayw {

	public EntityAdvRayw(World world) {
		super(world);
		setDamage(20f);
	}

	public EntityAdvRayw(World world, EntityLivingBase entity) {
		super(world, entity);
		setDamage(20f);
	}

	public EntityAdvRayw(World world, double x, double y, double z) {
		super(world, x, y, z);
		setDamage(20f);
	}
}
