package com.grim3212.mc.pack.tools.entity;

import com.grim3212.mc.pack.core.entity.EntityProjectile;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.util.EntityDataSerializers;
import com.grim3212.mc.pack.tools.util.EnumDetonatorType;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class EntityDetonator extends EntityProjectile implements IManualEntity {

	private static final DataParameter<EnumDetonatorType> TYPE = EntityDataManager.createKey(EntityDetonator.class, EntityDataSerializers.DETONATOR_TYPE);
	private static final DataParameter<Integer> FUSE_TIMER = EntityDataManager.createKey(EntityDetonator.class, DataSerializers.VARINT);

	protected boolean exploded;

	public EntityDetonator(World worldIn) {
		super(worldIn);
		exploded = false;
	}

	public EntityDetonator(World world, double x, double y, double z, EnumDetonatorType type) {
		super(world, x, y, z);
		this.getDataManager().set(TYPE, type);
		exploded = false;
	}

	public EntityDetonator(World world, EntityLivingBase shooter, EnumDetonatorType type) {
		super(world, shooter);
		this.getDataManager().set(TYPE, type);
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(TYPE, EnumDetonatorType.SOLAR);
		this.getDataManager().register(FUSE_TIMER, 0);
	}

	public EnumDetonatorType getType() {
		return this.getDataManager().get(TYPE);
	}

	public int getFuseTimer() {
		return this.getDataManager().get(FUSE_TIMER);
	}

	public void updateFuseTimer() {
		this.getDataManager().set(FUSE_TIMER, this.getFuseTimer() + 1);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setString("Type", this.getType().getName());
		compound.setInteger("FuseTimer", this.getFuseTimer());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.getDataManager().set(TYPE, EnumDetonatorType.valueOf(compound.getString("Type")));
		this.getDataManager().set(FUSE_TIMER, compound.getInteger("FuseTimer"));
	}

	@Override
	public void onUpdate() {
		super.onEntityUpdate();

		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.039999999105930328D;
		if (world.getBlockState(getPosition()).getMaterial() == Material.LAVA) {
			motionY = 0.20000000298023221D;
			motionX = (rand.nextFloat() - rand.nextFloat()) * 0.2F;
			motionZ = (rand.nextFloat() - rand.nextFloat()) * 0.2F;
			world.playSound((EntityPlayer) null, getPosition(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.4F, 2.0F + rand.nextFloat() * 0.4F);
		}
		pushOutOfBlocks(posX, (getEntityBoundingBox().minY + getEntityBoundingBox().maxY) / 2D, posZ);
		move(MoverType.SELF, motionX, motionY, motionZ);

		float f = 0.99F;
		if (onGround) {
			f = world.getBlockState(getPosition().down()).getBlock().getSlipperiness(world.getBlockState(getPosition().down()), world, getPosition().down(), this);
		}
		motionX *= f;
		motionY *= 0.98000001907348633D;
		motionZ *= f;
		if (onGround) {
			motionY *= -0.5D;
		}

		double d = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
		if (d <= 0.10000000000000001D) {
			this.updateFuseTimer();
			float size = getSizeBasedOnFuseTimer();
			setSize(size, size);
		}
		if (this.getFuseTimer() > this.getType().getFuseDelay()) {
			explode();
			setDead();
		}
	}

	@Override
	protected void setSize(float width, float height) {
		if (width != this.width || height != this.height) {
			this.width = width;
			this.height = height;
		}
	}

	private float getSizeBasedOnFuseTimer() {
		float size = 0.6F;
		float delay = this.getType().getFuseDelay();
		if ((double) ((float) this.getFuseTimer() / delay) >= 0.69999999999999996D) {
			size += 0.5F * ((float) this.getFuseTimer() / delay);
			size = (float) ((double) size + 0.75D * (((double) this.getFuseTimer() - (double) delay * 0.69999999999999996D) / ((double) delay * 0.69999999999999996D)));
		} else {
			size += 0.5F * ((float) this.getFuseTimer() / delay);
		}
		return size;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		explode();
		markVelocityChanged();
		setDead();
		return false;
	}

	protected void explode() {
		if (exploded) {
			return;
		}
		exploded = true;
		if (!world.isRemote) {
			if (this.getType().isEntitiesOnly()) {
				Utils.newExplosion(world, null, posX, posY, posZ, this.getType().getExplosionSize(), this.getType().isFire(), true, false, true);
			} else {
				Utils.newExplosion(world, null, posX, posY, posZ, this.getType().getExplosionSize(), this.getType().isFire(), true, true, true);
			}
		}
	}

	@Override
	public boolean canStickInGround() {
		return false;
	}

	@Override
	protected ItemStack getPickupStack() {
		return ItemStack.EMPTY;
	}

	@Override
	public Page getPage(Entity entity) {
		return ManualTools.detonators_page;
	}
}
