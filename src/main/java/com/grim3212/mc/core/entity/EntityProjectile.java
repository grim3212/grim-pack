package com.grim3212.mc.core.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public class EntityProjectile extends EntityArrow implements IThrowableEntity {

	protected int newXTile = -1;
	protected int newYTile = -1;
	protected int newZTile = -1;
	protected Block newInTile;
	protected int newInData;
	protected boolean newInGround;
	protected int numTicksInGround;
	protected int numTicksInAir;
	protected double throwableDamage = 0.0D;

	public EntityProjectile(World worldIn) {
		super(worldIn);
	}

	public EntityProjectile(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityProjectile(World world, EntityLivingBase shooter, EntityLivingBase entityliving, float par4, float par5) {
		super(world, shooter, entityliving, par4, par5);
	}

	public EntityProjectile(World world, EntityLivingBase shooter, float par3) {
		super(world, shooter, par3);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
	}

	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
		super.setThrowableHeading(x, y, z, velocity, inaccuracy);
	}

	@Override
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean flag) {
		super.setPositionAndRotation2(x, y, z, yaw, pitch, posRotationIncrements, flag);
	}

	@Override
	public void setVelocity(double x, double y, double z) {
		super.setVelocity(x, y, z);
	}

	@Override
	public void onUpdate() {
		super.onEntityUpdate();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f) * 180.0D / Math.PI);
		}

		BlockPos blockpos = new BlockPos(this.newXTile, this.newYTile, this.newZTile);
		IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
		Block block = iblockstate.getBlock();

		if (block.getMaterial() != Material.air) {
			block.setBlockBoundsBasedOnState(this.worldObj, blockpos);
			AxisAlignedBB axisalignedbb = block.getCollisionBoundingBox(this.worldObj, blockpos, iblockstate);

			if (axisalignedbb != null && axisalignedbb.isVecInside(new Vec3(this.posX, this.posY, this.posZ))) {
				this.newInGround = true;
			}
		}

		if (this.arrowShake > 0) {
			--this.arrowShake;
		}

		if (this.newInGround) {
			int j = block.getMetaFromState(iblockstate);

			if (block == this.newInTile && j == this.newInData) {
				++this.numTicksInGround;

				if (this.numTicksInGround >= 1200) {
					this.setDead();
				}
			} else {
				this.newInGround = false;
				this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
				this.numTicksInGround = 0;
				this.numTicksInAir = 0;
			}
		} else {
			++this.numTicksInAir;
			Vec3 vec31 = new Vec3(this.posX, this.posY, this.posZ);
			Vec3 vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec31, vec3, false, true, false);
			vec31 = new Vec3(this.posX, this.posY, this.posZ);
			vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (movingobjectposition != null) {
				vec3 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}

			Entity entity = null;
			List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			int i;
			float f1;

			for (i = 0; i < list.size(); ++i) {
				Entity entity1 = (Entity) list.get(i);

				if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.numTicksInAir >= 5)) {
					f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand((double) f1, (double) f1, (double) f1);
					MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

					if (movingobjectposition1 != null) {
						double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

						if (d1 < d0 || d0 == 0.0D) {
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null) {
				movingobjectposition = new MovingObjectPosition(entity);
			}

			if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;

				if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
					movingobjectposition = null;
				}
			}

			float f2;
			float f3;
			float f4;

			if (movingobjectposition != null) {
				if (movingobjectposition.entityHit != null) {
					f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					int k = MathHelper.ceiling_double_int((double) f2 * this.throwableDamage);

					if (this.getIsCritical()) {
						k += this.rand.nextInt(k / 2 + 2);
					}

					DamageSource damagesource;

					if (this.shootingEntity == null) {
						damagesource = DamageSource.causeThrownDamage(this, this);
					} else {
						damagesource = DamageSource.causeThrownDamage(this, this.shootingEntity);
					}

					if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman)) {
						movingobjectposition.entityHit.setFire(5);
					}

					if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float) k)) {
						if (movingobjectposition.entityHit instanceof EntityLivingBase) {
							EntityLivingBase entitylivingbase = (EntityLivingBase) movingobjectposition.entityHit;

							if (this.shootingEntity instanceof EntityLivingBase) {
								EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
								EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) this.shootingEntity, entitylivingbase);
							}

							if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
								((EntityPlayerMP) this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
							}
						}

						this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

						if (!(movingobjectposition.entityHit instanceof EntityEnderman)) {
							this.setDead();
						}
					} else {
						this.motionX *= -0.10000000149011612D;
						this.motionY *= -0.10000000149011612D;
						this.motionZ *= -0.10000000149011612D;
						this.rotationYaw += 180.0F;
						this.prevRotationYaw += 180.0F;
						this.numTicksInAir = 0;
					}
				} else {
					BlockPos blockpos1 = movingobjectposition.getBlockPos();
					this.newXTile = blockpos1.getX();
					this.newYTile = blockpos1.getY();
					this.newZTile = blockpos1.getZ();
					iblockstate = this.worldObj.getBlockState(blockpos1);
					this.newInTile = iblockstate.getBlock();
					this.newInData = this.newInTile.getMetaFromState(iblockstate);
					this.motionX = (double) ((float) (movingobjectposition.hitVec.xCoord - this.posX));
					this.motionY = (double) ((float) (movingobjectposition.hitVec.yCoord - this.posY));
					this.motionZ = (double) ((float) (movingobjectposition.hitVec.zCoord - this.posZ));
					f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					this.posX -= this.motionX / (double) f3 * 0.05000000074505806D;
					this.posY -= this.motionY / (double) f3 * 0.05000000074505806D;
					this.posZ -= this.motionZ / (double) f3 * 0.05000000074505806D;
					this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					this.newInGround = true;
					this.arrowShake = 7;
					this.setIsCritical(false);

					if (this.newInTile.getMaterial() != Material.air) {
						this.newInTile.onEntityCollidedWithBlock(this.worldObj, blockpos1, iblockstate, this);
					}
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

			for (this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
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

			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			f3 = 0.99F;
			f1 = 0.05F;

			if (this.isInWater()) {
				for (int l = 0; l < 4; ++l) {
					f4 = 0.25F;
					this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double) f4, this.posY - this.motionY * (double) f4, this.posZ - this.motionZ * (double) f4, this.motionX, this.motionY, this.motionZ, new int[0]);
				}

				f3 = 0.6F;
			}

			if (this.isWet()) {
				this.extinguish();
			}

			this.motionX *= (double) f3;
			this.motionY *= (double) f3;
			this.motionZ *= (double) f3;
			this.motionY -= (double) f1;
			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();
		}
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setShort("xTile", (short) this.newXTile);
		tagCompound.setShort("yTile", (short) this.newYTile);
		tagCompound.setShort("zTile", (short) this.newZTile);
		tagCompound.setShort("life", (short) this.numTicksInGround);
		tagCompound.setByte("inTile", (byte) Block.getIdFromBlock(newInTile));
		tagCompound.setByte("inData", (byte) this.newInData);
		tagCompound.setByte("shake", (byte) this.arrowShake);
		tagCompound.setByte("inGround", (byte) (this.newInGround ? 1 : 0));
		tagCompound.setByte("pickup", (byte) this.canBePickedUp);
		tagCompound.setDouble("damage", this.throwableDamage);
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		this.newXTile = tagCompund.getShort("xTile");
		this.newYTile = tagCompund.getShort("yTile");
		this.newZTile = tagCompund.getShort("zTile");
		this.numTicksInGround = tagCompund.getShort("life");
		this.newInTile = Block.getBlockById(tagCompund.getByte("inTile") & 255);
		this.newInData = tagCompund.getByte("inData") & 255;
		this.arrowShake = tagCompund.getByte("shake") & 255;
		this.newInGround = tagCompund.getByte("inGround") == 1;

		if (tagCompund.hasKey("damage", 99)) {
			this.throwableDamage = tagCompund.getDouble("damage");
		}

		if (tagCompund.hasKey("pickup", 99)) {
			this.canBePickedUp = tagCompund.getByte("pickup");
		} else if (tagCompund.hasKey("player", 99)) {
			this.canBePickedUp = tagCompund.getBoolean("player") ? 1 : 0;
		}
	}

	/**
	 * Gets the item to be picked up when the played walks over the entity
	 * 
	 * @return Item to be picked up
	 */
	public Item getItemPickup() {
		return null;
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn) {
		if (!this.worldObj.isRemote && this.newInGround && this.arrowShake <= 0) {
			boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && entityIn.capabilities.isCreativeMode;

			if (this.canBePickedUp == 1 && !entityIn.inventory.addItemStackToInventory(new ItemStack(this.getItemPickup(), 1))) {
				flag = false;
			}

			if (flag) {
				this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				entityIn.onItemPickup(this, 1);
				this.setDead();
			}
		}
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void setDamage(double damage) {
		this.throwableDamage = damage;
	}

	@Override
	public double getDamage() {
		return this.throwableDamage;
	}

	@Override
	public boolean canAttackWithItem() {
		return false;
	}

	@Override
	public Entity getThrower() {
		return this.shootingEntity;
	}

	@Override
	public void setThrower(Entity entity) {
		this.shootingEntity = entity;
	}
}
