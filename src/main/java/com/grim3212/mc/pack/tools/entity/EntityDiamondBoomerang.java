package com.grim3212.mc.pack.tools.entity;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.config.ToolsConfig;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityDiamondBoomerang extends EntityBoomerang {

	public EntityDiamondBoomerang(World worldIn) {
		super(worldIn);
	}

	public EntityDiamondBoomerang(World worldIn, EntityPlayer entity, ItemStack itemstack) {
		super(worldIn, entity, itemstack);
		this.timeBeforeTurnAround = ToolsConfig.diamondBoomerangRange <= 0 ? 30 : ToolsConfig.diamondBoomerangRange;
	}

	@Override
	public void beforeTurnAround(EntityPlayer player) {
		// Following is diamond boomerang only
		// Follows where the entity is looking
		if (!isBouncing && ToolsConfig.diamondBoomerangFollows) {
			double x = -MathHelper.sin((player.rotationYaw * 3.141593F) / 180F);
			double z = MathHelper.cos((player.rotationYaw * 3.141593F) / 180F);
			motionX = 0.5D * x * (double) MathHelper.cos((player.rotationPitch / 180F) * 3.141593F);
			motionY = -0.5D * (double) MathHelper.sin((player.rotationPitch / 180F) * 3.141593F);
			motionZ = 0.5D * z * (double) MathHelper.cos((player.rotationPitch / 180F) * 3.141593F);
		}
	}

	@Override
	public void onEntityHit(Entity hitEntity, EntityPlayer player) {
		if (ToolsConfig.diamondBoomerangDamage > 0) {
			hitEntity.attackEntityFrom(causeNewDamage(this, player), ToolsConfig.diamondBoomerangDamage);
		} else {
			hitEntity.attackEntityFrom(causeNewDamage(this, player), 0);
		}
	}

	@Override
	public DamageSource causeNewDamage(EntityBoomerang entityboomerang, Entity entity) {
		return new EntityDamageSourceIndirect("diamond_boomerang", entityboomerang, entity);
	}

	@Override
	public Page getPage(Entity entity) {
		return ManualTools.diamondBoomerang_page;
	}
}
