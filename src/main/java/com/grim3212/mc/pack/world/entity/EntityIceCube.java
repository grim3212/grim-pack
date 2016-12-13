package com.grim3212.mc.pack.world.entity;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityIceCube extends Entity {

	public Entity shootingEntity;
	private int ticksInAir;

	public EntityIceCube(World world) {
		super(world);
		ticksInAir = 0;
		setSize(0.5F, 0.5F);
	}

	public EntityIceCube(World world, double d, double d1, double d2) {
		super(world);
		ticksInAir = 0;
		setSize(0.5F, 0.5F);
		setPosition(d, d1, d2);
	}

	public EntityIceCube(World worldIn, EntityLivingBase shooter, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_) {
		super(worldIn);
		this.shootingEntity = shooter;

		this.posY = shooter.posY + (double) shooter.getEyeHeight() - 0.10000000149011612D;
		double d0 = p_i1755_3_.posX - shooter.posX;
		double d1 = p_i1755_3_.getEntityBoundingBox().minY + (double) (p_i1755_3_.height / 3.0F) - this.posY;
		double d2 = p_i1755_3_.posZ - shooter.posZ;
		double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);

		if (d3 >= 1.0E-7D) {
			float f2 = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
			float f3 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			double d4 = d0 / d3;
			double d5 = d2 / d3;
			this.setLocationAndAngles(shooter.posX + d4, this.posY, shooter.posZ + d5, f2, f3);
			float f4 = (float) (d3 * 0.20000000298023224D);
			this.setArrowHeading(d0, d1 + (double) f4, d2, p_i1755_4_, p_i1755_5_);
		}
	}

	public EntityIceCube(World world, EntityLiving entityliving, float f) {
		super(world);
		ticksInAir = 0;
		shootingEntity = entityliving;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(entityliving.posX, entityliving.posY + (double) entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		posX -= (double) MathHelper.cos((rotationYaw / 180F) * (float) Math.PI) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= (double) MathHelper.sin((rotationYaw / 180F) * (float) Math.PI) * 0.16F;
		setPosition(posX, posY, posZ);
		motionX = (double) -MathHelper.sin((rotationYaw / 180F) * (float) Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float) Math.PI);
		motionZ = (double) MathHelper.cos((rotationYaw / 180F) * (float) Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float) Math.PI);
		motionY = (double) -MathHelper.sin((rotationPitch / 180F) * (float) Math.PI);
		setArrowHeading(motionX, motionY, motionZ, f * 1.5F, 1.0F);
	}

	@Override
	public boolean isInRangeToRenderDist(double distance) {
		return distance <= 10.0D ? true : false;
	}

	@Override
	protected void entityInit() {
	}

	public void setArrowHeading(double d, double d1, double d2, float f, float f1) {
		float f2 = MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
		d /= f2;
		d1 /= f2;
		d2 /= f2;
		d += rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
		d1 += rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
		d2 += rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
		d *= f;
		d1 *= f;
		d2 *= f;
		motionX = d;
		motionY = d1;
		motionZ = d2;
		float f3 = MathHelper.sqrt(d * d + d2 * d2);
		prevRotationYaw = rotationYaw = (float) ((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
		prevRotationPitch = rotationPitch = (float) ((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
	}

	public void setVelocity(double d, double d1, double d2) {
		motionX = d;
		motionY = d1;
		motionZ = d2;
		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(d * d + d2 * d2);
			prevRotationYaw = rotationYaw = (float) ((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
			prevRotationPitch = rotationPitch = (float) ((Math.atan2(d1, f) * 180D) / 3.1415927410125732D);
			prevRotationPitch = rotationPitch;
			prevRotationYaw = rotationYaw;
			setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float) ((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
			prevRotationPitch = rotationPitch = (float) ((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D);
		}
		ticksInAir++;
		Vec3d vec3d = new Vec3d(posX, posY, posZ);
		Vec3d vec3d1 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
		RayTraceResult raytrace = this.world.rayTraceBlocks(vec3d, vec3d1, false, true, false);
		vec3d = new Vec3d(posX, posY, posZ);
		vec3d1 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
		if (raytrace != null) {
			vec3d1 = new Vec3d(raytrace.hitVec.xCoord, raytrace.hitVec.yCoord, raytrace.hitVec.zCoord);
		}
		Entity entity = null;
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
		double d = 0.0D;
		for (int i = 0; i < list.size(); i++) {
			Entity entity1 = (Entity) list.get(i);
			if (!entity1.canBeCollidedWith() || entity1 == shootingEntity && ticksInAir < 5) {
				continue;
			}
			float f5 = 0.3F;
			AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f5, f5, f5);
			RayTraceResult raytrace1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
			if (raytrace1 == null) {
				continue;
			}
			double d4 = vec3d.distanceTo(raytrace1.hitVec);
			if (d4 < d || d == 0.0D) {
				entity = entity1;
				d = d4;
			}
		}

		if (entity != null) {
			raytrace = new RayTraceResult(entity);
		}
		if (raytrace != null) {
			if (raytrace.entityHit != null) {
				float f1 = MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
				int j = (int) Math.ceil((double) f1 * 2D);
				if (raytrace.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, shootingEntity), j)) {
					world.playSound((EntityPlayer) null, this.getPosition(), SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.HOSTILE, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					setDead();
				} else if (ticksInAir > 10) {
					motionX *= -0.10000000149011612D;
					motionY *= -0.10000000149011612D;
					motionZ *= -0.10000000149011612D;
					rotationYaw += 180F;
					prevRotationYaw += 180F;
					ticksInAir = 0;
				}
			} else {
				motionX = (float) (raytrace.hitVec.xCoord - posX);
				motionY = (float) (raytrace.hitVec.yCoord - posY);
				motionZ = (float) (raytrace.hitVec.zCoord - posZ);
				float f2 = MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
				posX -= (motionX / (double) f2) * 0.05000000074505806D;
				posY -= (motionY / (double) f2) * 0.05000000074505806D;
				posZ -= (motionZ / (double) f2) * 0.05000000074505806D;
				world.playSound((EntityPlayer) null, this.getPosition(), SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.HOSTILE, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
				Random random = new Random();
				double d1 = (float) posX + random.nextFloat();
				double d3 = (float) posY + 1.0F + random.nextFloat() * 0.1F;
				double d6 = (float) posZ + random.nextFloat();
				double d8 = 0.0D;
				double d10 = 0.0D;
				double d12 = 0.0D;
				d8 = ((double) random.nextFloat() - 0.5D) * 0.5D;
				d10 = ((double) random.nextFloat() - 0.5D) * 0.5D;
				d12 = ((double) random.nextFloat() - 0.5D) * 0.5D;
				world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, d1, d3, d6, d8, d10, d12);
				setDead();
			}
		}
		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		float f3 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) ((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
		for (rotationPitch = (float) ((Math.atan2(motionY, f3) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) {
		}
		for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) {
		}
		for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) {
		}
		for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) {
		}
		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		float f4 = 0.99F;
		float f6 = 0.05F;
		if (isInWater()) {
			for (int l = 0; l < 4; l++) {
				float f7 = 0.25F;
				world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * (double) f7, posY - motionY * (double) f7, posZ - motionZ * (double) f7, motionX, motionY, motionZ);
			}

			f4 = 0.8F;
		}
		motionX *= f4;
		motionY *= f4;
		motionZ *= f4;
		motionY -= f6;
		setPosition(posX, posY, posZ);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}
}
