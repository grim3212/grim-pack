package com.grim3212.mc.pack.tools.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityRayw extends Entity implements IProjectile {

	private int xTileRayw = -1;
	private int yTileRayw = -1;
	private int zTileRayw = -1;
	private Block inTileRayw;
	protected boolean inGroundRayw = false;
	public int shakeRayw = 0;
	protected Entity shootingEntity;
	private int field_810_h;
	private int field_809_i = 0;
	protected int damage = 10;

	public EntityRayw(World world) {
		super(world);
		this.setSize(0.2F, 0.2F);
	}

	public EntityRayw(World world, EntityLivingBase shooter) {
		super(world);
		this.shootingEntity = shooter;
		this.setSize(0.2F, 0.2F);
		this.setLocationAndAngles(shooter.posX, shooter.posY + (double) shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
		this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * 0.16F);
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		float var3 = 12.0F;
		this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F) * var3);
		this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F) * var3);
		this.motionY = (double) (-MathHelper.sin(this.rotationPitch / 180.0F * 3.141593F) * var3);
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
	}

	public EntityRayw(World world, double x, double y, double z) {
		super(world);
		this.field_810_h = 0;
		this.setSize(0.2F, 0.2F);
		this.setPosition(x, y, z);
	}

	@Override
	protected void entityInit() {
	}

	@Override
	public boolean isInRangeToRenderDist(double var1) {
		return true;
	}

	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float innacuracy) {
		float var9 = MathHelper.sqrt_double(x * x + y * y + z * z);
		x /= (double) var9;
		y /= (double) var9;
		z /= (double) var9;
		x += this.rand.nextGaussian() * 0.007499999832361937D * (double) innacuracy;
		y += this.rand.nextGaussian() * 0.007499999832361937D * (double) innacuracy;
		z += this.rand.nextGaussian() * 0.007499999832361937D * (double) innacuracy;
		x *= (double) velocity;
		y *= (double) velocity;
		z *= (double) velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float var10 = MathHelper.sqrt_double(x * x + z * z);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / 3.1415927410125732D);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, (double) var10) * 180.0D / 3.1415927410125732D);
		this.field_810_h = 0;
	}

	@Override
	public void setVelocity(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float var7 = MathHelper.sqrt_double(x * x + z * z);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / 3.1415927410125732D);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, (double) var7) * 180.0D / 3.1415927410125732D);
		}

	}

	@Override
	public void onUpdate() {
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		super.onUpdate();
		if (this.shakeRayw > 5) {
			--this.shakeRayw;
		}

		if (this.inGroundRayw) {
			BlockPos pos = new BlockPos(this.xTileRayw, this.yTileRayw, this.zTileRayw);
			Block block = this.worldObj.getBlockState(pos).getBlock();
			if (block == this.inTileRayw) {
				++this.field_810_h;
				if (this.field_810_h == 1200) {
					this.setDead();
				}

				return;
			}

			this.inGroundRayw = false;
			this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
			this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
			this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
			this.field_810_h = 0;
			this.field_809_i = 0;
		} else {
			++this.field_809_i;
		}

		Vec3 var16 = new Vec3(this.posX, this.posY, this.posZ);
		Vec3 var2 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var16, var2);
		var16 = new Vec3(this.posX, this.posY, this.posZ);
		var2 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		if (var3 != null) {
			var2 = new Vec3(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
		}

		if (!this.worldObj.isRemote) {
			Entity var4 = null;
			List<Entity> var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

			for (int var8 = 0; var8 < var5.size(); ++var8) {
				Entity var9 = (Entity) var5.get(var8);
				if (var9.canBeCollidedWith() && (var9 != this.shootingEntity || this.field_809_i >= 5)) {
					float var10 = 0.2F;
					float var11 = 0.2F;
					AxisAlignedBB var12 = var9.getEntityBoundingBox().expand((double) var10, (double) var10, (double) var11);
					MovingObjectPosition var13 = var12.calculateIntercept(var16, var2);
					if (var13 != null) {
						double var14 = var16.distanceTo(var13.hitVec);
						if (var14 < var6 || var6 == 0.0D) {
							var4 = var9;
							var6 = var14;
						}
					}
				}
			}

			if (var4 != null) {
				var3 = new MovingObjectPosition(var4);
			}
		}

		if (var3 != null) {
			if (var3.entityHit != null) {
				DamageSource var17;
				if (this.shootingEntity == null) {
					var17 = DamageSource.causeThrownDamage(this, this);
					if (!var3.entityHit.attackEntityFrom(var17, damage)) {
						;
					}
				} else {
					var17 = DamageSource.causeThrownDamage(this, this.shootingEntity);
					if (!var3.entityHit.attackEntityFrom(var17, damage)) {
						;
					}
				}
			}

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

			this.worldObj.playSoundAtEntity(this, "random.fizz", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
			this.setDead();
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.1415927410125732D);

		for (this.rotationPitch = (float) (Math.atan2(this.motionY, (double) var20) * 180.0D / 3.1415927410125732D); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
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
		float var18 = 1.0F;
		float var21 = 0.0F;
		if (this.handleWaterMovement()) {
			for (int var7 = 0; var7 < 4; ++var7) {
				float var22 = 1.0F;
				this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double) var22, this.posY - this.motionY * (double) var22, this.posZ - this.motionZ * (double) var22, this.motionX, this.motionY, this.motionZ);
			}

			var18 = 1.0F;
		}

		this.motionX *= (double) var18;
		this.motionY *= (double) var18;
		this.motionZ *= (double) var18;
		this.motionY -= (double) var21;
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setShort("xTile", (short) this.xTileRayw);
		nbt.setShort("yTile", (short) this.yTileRayw);
		nbt.setShort("zTile", (short) this.zTileRayw);
		nbt.setByte("inTile", (byte) Block.getIdFromBlock(this.inTileRayw));
		nbt.setByte("shake", (byte) this.shakeRayw);
		nbt.setByte("inGround", (byte) (this.inGroundRayw ? 1 : 0));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		this.xTileRayw = nbt.getShort("xTile");
		this.yTileRayw = nbt.getShort("yTile");
		this.zTileRayw = nbt.getShort("zTile");
		this.inTileRayw = Block.getBlockById(nbt.getByte("inTile") & 255);
		this.shakeRayw = nbt.getByte("shake") & 255;
		this.inGroundRayw = nbt.getByte("inGround") == 1;
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer playerIn) {
		if (this.inGroundRayw && this.shootingEntity == playerIn && this.shakeRayw <= 0 && playerIn.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1))) {
			this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			playerIn.onItemPickup(this, 1);
			this.setDead();
		}

	}
}
