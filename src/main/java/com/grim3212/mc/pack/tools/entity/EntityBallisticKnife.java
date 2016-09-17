package com.grim3212.mc.pack.tools.entity;

import com.grim3212.mc.pack.core.entity.EntityProjectile;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityBallisticKnife extends EntityProjectile {

	public EntityBallisticKnife(World worldIn) {
		super(worldIn);
		this.setDamage(10.0D);
	}

	public EntityBallisticKnife(World world, double x, double y, double z) {
		super(world, x, y, z);
		this.setDamage(10.0D);
	}

	public EntityBallisticKnife(World world, EntityLivingBase shooter) {
		super(world, shooter);
		this.setDamage(10.0D);
	}

	@Override
	protected ItemStack getPickupStack() {
		return new ItemStack(ToolsItems.ammo_part);
	}
}