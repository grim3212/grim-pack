package com.grim3212.mc.pack.tools.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;

import com.grim3212.mc.pack.core.entity.EntityProjectile;
import com.grim3212.mc.pack.tools.items.ToolsItems;
import com.grim3212.mc.pack.tools.util.EnumSlingpelletType;
import com.grim3212.mc.pack.tools.util.EnumSpearType;

import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySlingpellet extends EntityProjectile {
	
	protected EnumSlingpelletType type;

	public EntitySlingpellet(World world) {
		super(world);
	}

	public EntitySlingpellet(World world, EntityLivingBase entity, EnumSlingpelletType type) {
		super(world, entity);
		this.type = type;
		//setDamage(this.type.getDamage());
	}

	public EntitySlingpellet(World world, double par2, double par4, double par6, EnumSlingpelletType type) {
		super(world, par2, par4, par6);
		this.type = type;
		//setDamage(this.type.getDamage());
	}
	
	@Override
	public ItemStack getPickupStack() {
		switch (type) {
		case STONE:
			return new ItemStack(ToolsItems.sling_pellet);
		case IRON:
			return new ItemStack(ToolsItems.iron_sling_pellet);
		case NETHERRACK:
			return new ItemStack(ToolsItems.netherrack_sling_pellet);
		case EXPLOSIVE:
			return new ItemStack(ToolsItems.explosive_sling_pellet);
		case FIRE:
			return new ItemStack(ToolsItems.fire_sling_pellet);
		case SLIME:
			return new ItemStack(ToolsItems.slime_sling_pellet);
		case LIGHT:
			return new ItemStack(ToolsItems.light_sling_pellet);
		default:
			return new ItemStack(ToolsItems.sling_pellet);
		}
	}

	@Override
	protected void onHit(RayTraceResult result) {
		if (result.entityHit != null) {

			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), this.type.getDamage());
		}

		for (int i = 0; i < 8; ++i) {
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}
		
		this.inGround = false;

		if (!this.worldObj.isRemote) {
			this.setDead();
		}
	}
}
