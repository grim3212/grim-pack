package com.grim3212.mc.pack.tools.entity;

import com.grim3212.mc.pack.core.entity.EntityProjectile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityRayw extends EntityProjectile {

	public EntityRayw(World world) {
		super(world);
		setDamage(10f);
		setEntityDrop(0.0f);
	}

	public EntityRayw(World world, EntityLivingBase shooter) {
		super(world, shooter);
		setDamage(10f);
		setEntityDrop(0.0f);
	}

	public EntityRayw(World world, double x, double y, double z) {
		super(world, x, y, z);
		setDamage(10f);
		setEntityDrop(0.0f);
	}

	@Override
	protected ItemStack getPickupStack() {
		return null;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
	}

	@Override
	protected void projectileHit(EntityLivingBase living) {
		this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
		this.setDead();
	}

	@Override
	protected void projectileLand(RayTraceResult raytraceResultIn, BlockPos pos, IBlockState state) {
		for (int var19 = 0; var19 < 8; ++var19) {
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + 0.15D, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.15D, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ + 0.15D, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX - 0.15D, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ - 0.15D, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY - 0.15D, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + 0.05D, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.05D, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ + 0.05D, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX - 0.05D, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ - 0.05D, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY - 0.05D, this.posZ, 0.0D, 0.0D, 0.0D);
		}

		this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
		this.setDead();
	}
}
