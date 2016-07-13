package com.grim3212.mc.pack.tools.entity;

import com.grim3212.mc.pack.core.entity.EntityProjectile;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
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

	public EntityTomahawk(World world, EntityLivingBase shooter) {
		super(world, shooter);
		this.setDamage(20.0D);
	}

	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(ToolsItems.tomahawk);
	}
}