package com.grim3212.mc.pack.core.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class EntityProjectile extends Entity implements IProjectile {

	protected BlockState inBlockState;
	protected boolean inGround;
	protected int timeInGround;
	/** 1 if the player can pick up the projectile */
	public AbstractArrowEntity.PickupStatus pickupStatus;
	/** Seems to be some sort of timer for animating an projectile. */
	public int projectileShake;
	/** The owner of this projectile. */
	public UUID shootingEntity;
	protected int ticksInGround;
	protected int ticksInAir;
	protected double damage;
	/** The amount of knockback an projectile applies when it hits a mob. */
	protected int knockbackStrength;
	protected float entityDrop = 0.05f;

	protected EntityProjectile(EntityType<?> type, World world) {
		super(type, world);
	}

	protected EntityProjectile(EntityType<?> type, double x, double y, double z, World world) {
		this(type, world);
		this.setPosition(x, y, z);
	}

	protected EntityProjectile(EntityType<?> type, LivingEntity entity, World world) {
		this(type, entity.posX, entity.posY + (double) entity.getEyeHeight() - (double) 0.1F, entity.posZ, world);
		this.func_212361_a(entity);
		if (entity instanceof PlayerEntity) {
			this.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
		}
	}

	public void setEntityDrop(float entityDrop) {
		this.entityDrop = entityDrop;
	}

	/**
	 * Checks if the entity is in range to render.
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		double d0 = this.getBoundingBox().getAverageEdgeLength() * 10.0D;

		if (Double.isNaN(d0)) {
			d0 = 1.0D;
		}

		d0 = d0 * 64.0D * getRenderDistanceWeight();
		return distance < d0 * d0;
	}

	@Override
	protected void registerData() {
	}

	public void setAim(Entity entity, float x, float y, float z, float velocity, float inaccuracy) {
		float f = -MathHelper.sin(y * 0.017453292F) * MathHelper.cos(x * 0.017453292F);
		float f1 = -MathHelper.sin(x * 0.017453292F);
		float f2 = MathHelper.cos(y * 0.017453292F) * MathHelper.cos(x * 0.017453292F);
		this.shoot((double) f, (double) f1, (double) f2, velocity, inaccuracy);
		this.setMotion(this.getMotion().add(entity.getMotion().x, entity.onGround ? 0.0D : entity.getMotion().y, entity.getMotion().z));
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(this.rand.nextGaussian() * (double) 0.0075F * (double) inaccuracy, this.rand.nextGaussian() * (double) 0.0075F * (double) inaccuracy, this.rand.nextGaussian() * (double) 0.0075F * (double) inaccuracy).scale((double) velocity);
		this.setMotion(vec3d);
		float f = MathHelper.sqrt(getDistanceSq(vec3d));
		this.rotationYaw = (float) (MathHelper.atan2(vec3d.x, vec3d.z) * (double) (180F / (float) Math.PI));
		this.rotationPitch = (float) (MathHelper.atan2(vec3d.y, (double) f) * (double) (180F / (float) Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
		this.ticksInGround = 0;
	}

	/**
	 * Set the position and rotation values directly without any clamping.
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.setPosition(x, y, z);
		this.setRotation(yaw, pitch);
	}

	/**
	 * Updates the velocity of the entity to a new value.
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public void setVelocity(double x, double y, double z) {
		this.setMotion(x, y, z);

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(x * x + z * z);
			this.prevRotationYaw = this.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
			this.prevRotationPitch = this.rotationPitch = (float) (MathHelper.atan2(y, (double) f) * (180D / Math.PI));
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		super.tick();

		Vec3d vec3d = this.getMotion();
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(getDistanceSq(vec3d));
			this.rotationYaw = (float) (MathHelper.atan2(vec3d.x, vec3d.z) * (double) (180F / (float) Math.PI));
			this.rotationPitch = (float) (MathHelper.atan2(vec3d.y, (double) f) * (double) (180F / (float) Math.PI));
			this.prevRotationYaw = this.rotationYaw;
			this.prevRotationPitch = this.rotationPitch;
		}

		BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
		BlockState iblockstate = this.world.getBlockState(blockpos);
		if (!iblockstate.isAir(this.world, blockpos)) {
			VoxelShape voxelshape = iblockstate.getCollisionShape(this.world, blockpos);
			if (!voxelshape.isEmpty()) {
				for (AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
					if (axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ))) {
						this.inGround = true;
						break;
					}
				}
			}
		}

		if (this.projectileShake > 0) {
			--this.projectileShake;
		}

		if (this.inGround) {
			if (this.inBlockState != iblockstate && this.world.isCollisionBoxesEmpty((Entity) null, this.getBoundingBox().grow(0.05D))) {
				this.inGround = false;
				this.setMotion(vec3d.mul((double) (this.rand.nextFloat() * 0.2F), (double) (this.rand.nextFloat() * 0.2F), (double) (this.rand.nextFloat() * 0.2F)));
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			} else {
				this.tryDespawn();
			}

			++this.timeInGround;
		} else {
			this.timeInGround = 0;
			++this.ticksInAir;
			Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
			Vec3d vec3d2 = vec3d1.add(vec3d);
			RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vec3d1, vec3d2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
			if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
				vec3d2 = raytraceresult.getHitVec();
			}

			while (this.isAlive()) {
				EntityRayTraceResult entityraytraceresult = this.func_213866_a(vec3d1, vec3d2);
				if (entityraytraceresult != null) {
					raytraceresult = entityraytraceresult;
				}

				if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
					Entity entity = ((EntityRayTraceResult) raytraceresult).getEntity();
					Entity entity1 = this.getOwner();
					if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity) entity1).canAttackPlayer((PlayerEntity) entity)) {
						raytraceresult = null;
						entityraytraceresult = null;
					}
				}

				if (raytraceresult != null && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
					this.onHit(raytraceresult);
					this.isAirBorne = true;
				}

				if (entityraytraceresult == null) {
					break;
				}

				raytraceresult = null;
			}

			vec3d = this.getMotion();
			double d1 = vec3d.x;
			double d2 = vec3d.y;
			double d0 = vec3d.z;

			this.posX += d1;
			this.posY += d2;
			this.posZ += d0;
			float f4 = MathHelper.sqrt(getDistanceSq(vec3d));
			this.rotationYaw = (float) (MathHelper.atan2(d1, d0) * (double) (180F / (float) Math.PI));

			for (this.rotationPitch = (float) (MathHelper.atan2(d2, (double) f4) * (double) (180F / (float) Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
				;
			}

			while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
				this.prevRotationPitch += 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
				this.prevRotationYaw -= 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = MathHelper.lerp(0.2F, this.prevRotationPitch, this.rotationPitch);
			this.rotationYaw = MathHelper.lerp(0.2F, this.prevRotationYaw, this.rotationYaw);
			float f1 = 0.99F;
			float f2 = 0.05F;
			if (this.isInWater()) {
				for (int j = 0; j < 4; ++j) {
					float f3 = 0.25F;
					this.world.addParticle(ParticleTypes.BUBBLE, this.posX - d1 * f3, this.posY - d2 * f3, this.posZ - d0 * f3, d1, d2, d0);
				}

				f1 = this.getWaterDrag();
			}

			this.setMotion(vec3d.scale((double) f1));
			if (!this.hasNoGravity()) {
				Vec3d vec3d3 = this.getMotion();
				this.setMotion(vec3d3.x, vec3d3.y - (double) f2, vec3d3.z);
			}

			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();
		}
	}

	/**
	 * Called when the projectile hits a block or an entity
	 */
	protected void onHit(RayTraceResult raytraceResultIn) {
		RayTraceResult.Type raytraceresult$type = raytraceResultIn.getType();
		if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
			Entity entity = ((EntityRayTraceResult) raytraceResultIn).getEntity();

			float f = (float) this.getMotion().length();
			int i = MathHelper.ceil(Math.max((double) f * this.damage, 0.0D));

			Entity owner = this.getOwner();
			DamageSource damagesource;

			if (owner == null) {
				damagesource = DamageSource.causeThrownDamage(this, this);
			} else {
				damagesource = DamageSource.causeThrownDamage(this, owner);
			}

			if (this.isBurning() && !(entity instanceof EndermanEntity)) {
				entity.setFire(5);
			}

			if (entity.attackEntityFrom(damagesource, (float) i)) {
				if (entity instanceof LivingEntity) {
					LivingEntity entitylivingbase = (LivingEntity) entity;

					if (this.knockbackStrength > 0) {
						Vec3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double) this.knockbackStrength * 0.6D);
						if (vec3d.lengthSquared() > 0.0D) {
							entitylivingbase.addVelocity(vec3d.x, 0.1D, vec3d.z);
						}
					}

					if (owner instanceof LivingEntity) {
						EnchantmentHelper.applyThornEnchantments(entitylivingbase, owner);
						EnchantmentHelper.applyArthropodEnchantments((LivingEntity) owner, entitylivingbase);
					}

					this.projectileHit(entitylivingbase);

					if (owner != null && entitylivingbase != owner && entitylivingbase instanceof PlayerEntity && owner instanceof ServerPlayerEntity) {
						((ServerPlayerEntity) owner).connection.sendPacket(new SChangeGameStatePacket(6, 0.0F));
					}
				}

				if (!(entity instanceof EndermanEntity)) {
					this.remove();
				}
			} else {
				this.setMotion(this.getMotion().scale(-0.1D));
				this.rotationYaw += 180.0F;
				this.prevRotationYaw += 180.0F;
				this.ticksInAir = 0;

				if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
					if (this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
						if (this.getPickupStack() != null)
							this.entityDropItem(this.getPickupStack(), 0.1F);
					}

					this.remove();
				}
			}
		} else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
			BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceResultIn;
			BlockState blockstate = this.world.getBlockState(blockraytraceresult.getPos());
			this.inBlockState = blockstate;
			Vec3d vec3d = blockraytraceresult.getHitVec().subtract(this.posX, this.posY, this.posZ);
			this.setMotion(vec3d);
			Vec3d vec3d1 = vec3d.normalize().scale((double) 0.05F);
			this.posX -= vec3d1.x;
			this.posY -= vec3d1.y;
			this.posZ -= vec3d1.z;
			this.projectileLand(raytraceResultIn, blockstate);
			this.projectileShake = 7;
			blockstate.onProjectileCollision(this.world, blockstate, blockraytraceresult, this);
		}
	}

	protected float getWaterDrag() {
		return 0.6F;
	}

	protected void projectileHit(LivingEntity living) {
		this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	}

	protected void projectileLand(RayTraceResult raytraceResultIn, BlockState state) {
		this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
		this.inGround = true;
	}

	@Nullable
	protected EntityRayTraceResult func_213866_a(Vec3d p_213866_1_, Vec3d p_213866_2_) {
		return ProjectileHelper.func_221271_a(this.world, this, p_213866_1_, p_213866_2_, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), (p_213871_1_) -> {
			return !p_213871_1_.isSpectator() && p_213871_1_.isAlive() && p_213871_1_.canBeCollidedWith() && (p_213871_1_ != this.getOwner() || this.ticksInAir >= 5);
		});
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeAdditional(CompoundNBT compound) {
		compound.putShort("life", (short) this.ticksInGround);
		if (this.inBlockState != null) {
			compound.put("inBlockState", NBTUtil.writeBlockState(this.inBlockState));
		}

		compound.putByte("shake", (byte) this.projectileShake);
		compound.putByte("inGround", (byte) (this.inGround ? 1 : 0));
		compound.putByte("pickup", (byte) this.pickupStatus.ordinal());
		compound.putDouble("damage", this.damage);
		if (this.shootingEntity != null) {
			compound.putUniqueId("OwnerUUID", this.shootingEntity);
		}
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		this.ticksInGround = compound.getShort("life");
		if (compound.contains("inBlockState", 10)) {
			this.inBlockState = NBTUtil.readBlockState(compound.getCompound("inBlockState"));
		}

		this.projectileShake = compound.getByte("shake") & 255;
		this.inGround = compound.getByte("inGround") == 1;
		if (compound.contains("damage", 99)) {
			this.damage = compound.getDouble("damage");
		}

		if (compound.contains("pickup", 99)) {
			this.pickupStatus = AbstractArrowEntity.PickupStatus.getByOrdinal(compound.getByte("pickup"));
		} else if (compound.contains("player", 99)) {
			this.pickupStatus = compound.getBoolean("player") ? AbstractArrowEntity.PickupStatus.ALLOWED : AbstractArrowEntity.PickupStatus.DISALLOWED;
		}

		if (compound.hasUniqueId("OwnerUUID")) {
			this.shootingEntity = compound.getUniqueId("OwnerUUID");
		}
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(PlayerEntity entityIn) {
		if (!this.world.isRemote && this.inGround && this.projectileShake <= 0) {
			boolean flag = this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED || this.pickupStatus == AbstractArrowEntity.PickupStatus.CREATIVE_ONLY && entityIn.abilities.isCreativeMode;

			if (this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED && !entityIn.inventory.addItemStackToInventory(this.getPickupStack())) {
				flag = false;
			}

			if (flag) {
				this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				entityIn.onItemPickup(this, 1);
				this.remove();
			}
		}
	}

	protected abstract ItemStack getPickupStack();

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk
	 * on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public int getBrightnessForRender() {
		return 15728880;
	}

	public void setDamage(double damageIn) {
		this.damage = damageIn;
	}

	public double getDamage() {
		return this.damage;
	}

	/**
	 * Sets the amount of knockback the projectile applies when it hits a mob.
	 */
	public void setKnockbackStrength(int knockbackStrengthIn) {
		this.knockbackStrength = knockbackStrengthIn;
	}

	/**
	 * Returns true if it's possible to attack this entity with an item.
	 */
	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}

	@Override
	public float getEyeHeight(Pose p_213307_1_) {
		return 0.0f;
	}

	public void func_212361_a(@Nullable Entity p_212361_1_) {
		this.shootingEntity = p_212361_1_ == null ? null : p_212361_1_.getUniqueID();
	}

	@Nullable
	public Entity getOwner() {
		return this.shootingEntity != null && this.world instanceof ServerWorld ? ((ServerWorld) this.world).getEntityByUuid(this.shootingEntity) : null;
	}

	public boolean canStickInGround() {
		return true;
	}

	protected void tryDespawn() {
		++this.ticksInGround;
		if (this.ticksInGround >= 1200) {
			this.remove();
		}
	}
}
