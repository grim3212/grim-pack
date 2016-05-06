package com.grim3212.mc.tools.entity;

import com.grim3212.mc.core.entity.EntityProjectile;
import com.grim3212.mc.tools.items.ToolsItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityTomahawk extends EntityProjectile {

	public EntityTomahawk(World worldIn) {
		super(worldIn);
		this.setDamage(20.0D);
	}

	public EntityTomahawk(World world, double x, double y, double z) {
		super(world, x, y, z);
		this.setDamage(20.0D);
	}

	public EntityTomahawk(World world, EntityLivingBase shooter, EntityLivingBase entityliving, float par4, float par5) {
		super(world, shooter, entityliving, par4, par5);
		this.setDamage(20.0D);
	}

	public EntityTomahawk(World world, EntityLivingBase shooter, float par3) {
		super(world, shooter, par3);
		this.setDamage(20.0D);
	}

	@Override
	public Item getItemPickup() {
		return ToolsItems.tomahawk;
	}
}