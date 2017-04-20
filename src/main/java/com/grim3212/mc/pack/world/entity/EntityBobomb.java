package com.grim3212.mc.pack.world.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.grim3212.mc.pack.core.entity.EntityUtils;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.world.GrimWorld;
import com.grim3212.mc.pack.world.client.ManualWorld;
import com.grim3212.mc.pack.world.items.WorldItems;
import com.grim3212.mc.pack.world.util.WorldSounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityBobomb extends EntityMob implements IManualEntity {

	private static final DataParameter<Boolean> ACTIVATED = EntityDataManager.<Boolean>createKey(EntityBobomb.class, DataSerializers.BOOLEAN);

	public int activatedTime;
	public int fireTimer;
	public int countdown;
	public float rotation;

	public EntityBobomb(World world) {
		super(world);
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		setSize(0.3F, 0.5F);
		this.isImmuneToFire = true;
		countdown = 30;
		activatedTime = 50;
		rotation = 0f;
	}

	@Override
	protected void initEntityAI() {
		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(4, new EntityAIFollowPlayer(this, 1.0D, 8.0F, 2.0F));
		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));

		this.targetTasks.taskEntries.clear();
		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 0, true, true, new Predicate<EntityLiving>() {
			public boolean apply(@Nullable EntityLiving entity) {
				return entity != null && IMob.VISIBLE_MOB_SELECTOR.apply(entity) && !(entity instanceof EntityBobomb) && !(entity instanceof EntityParaBuzzy);
			}
		}));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ACTIVATED, false);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return WorldSounds.bobomb_living;
	}

	@Override
	protected SoundEvent getHurtSound() {
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return null;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	public void setDead() {
		this.isDead = true;
	}

	@Override
	public boolean shouldDismountInWater(Entity rider) {
		return true;
	}

	protected void waterKills() {
		if (inWater) {
			this.setHealth(0);
		}
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.isRiding() && !player.isSneaking() && !EntityUtils.isTypePassenger(player, EntityBobomb.class) && !EntityUtils.isTypePassenger(player, EntityParaBuzzy.class)) {
			this.startRiding(player, true);
			GrimWorld.proxy.displayDismountMessage(player);
			return true;
		}

		if (player.getHeldItem(hand).isEmpty() && this.getAttackTarget() == null) {
			poof();

			if (!world.isRemote && !isDead)
				entityDropItem(new ItemStack(WorldItems.bobomb), 1);

			isDead = true;
		}

		return super.processInteract(player, hand);
	}

	@Override
	public double getYOffset() {
		return this.isRiding() ? super.getYOffset() + 0.50F : super.getYOffset();
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float i) {
		Entity entity = damagesource.getEntity();

		if ((entity instanceof EntityBobomb) || (entity instanceof EntityParaBuzzy)) {
			return false;
		}

		if (entity instanceof EntityPlayer) {
			world.playSound((EntityPlayer) entity, this.getPosition(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			performHurtAnimation();
			this.dataManager.set(ACTIVATED, true);
			return true;
		} else if (entity instanceof EntityLiving) {
			if (!world.isRemote) {
				boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
				this.isDead = true;
				world.createExplosion(this, posX, posY, posZ, 3F, flag);
				this.setDead();
			}

			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(4D, 4D, 4D));

			for (int j = 0; j < list.size(); j++) {
				Entity entity1 = (Entity) list.get(j);

				if (entity1 instanceof EntityLiving) {
					EntityLiving entityliving = (EntityLiving) entity1;
					entityliving.setFire(300);
				}
			}
			return true;
		}

		return super.attackEntityFrom(DamageSource.causeMobDamage(this), i);
	}

	public boolean isActivated() {
		return this.dataManager.get(ACTIVATED);
	}

	@Override
	protected boolean isMovementBlocked() {
		return this.isActivated() ? true : super.isMovementBlocked();
	}

	public void tryActivate() {
		if (this.isActivated()) {
			activatedTime--;

			if (activatedTime <= 0) {
				if (!world.isRemote) {
					boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
					this.isDead = true;
					world.createExplosion(this, posX, posY, posZ, 3F, flag);
					this.setDead();
				}

				List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(4D, 4D, 4D));

				for (int i = 0; i < list.size(); i++) {
					Entity entity = (Entity) list.get(i);

					if (entity instanceof EntityLiving) {
						EntityLiving entityliving = (EntityLiving) entity;
						entityliving.setFire(300);
					}
				}
			}
		}
	}

	@Override
	public void performHurtAnimation() {
		hurtTime = maxHurtTime = 100;
		attackedAtYaw = 0.0F;
	}

	public void poof() {
		for (int i = 0; i < 20; i++) {
			double d = rand.nextGaussian() * 0.02D;
			double d1 = rand.nextGaussian() * 0.02D;
			double d2 = rand.nextGaussian() * 0.02D;

			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double) (rand.nextFloat() * width * 2.0F)) - (double) width, posY + (double) (rand.nextFloat() * height), (posZ + (double) (rand.nextFloat() * width * 2.0F)) - (double) width, d, d1, d2, new int[0]);
		}
	}

	public void fuseFX() {
		if (this.getHealth() > 0) {
			fireTimer = rand.nextInt(10);

			if (fireTimer <= 6) {
				this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY + 0.85D, this.posZ, 0.01D, 0.01D, 0.01D, new int[0]);
			}

			if (fireTimer <= 2) {
				this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.8D, this.posZ, 0.01D, 0.01D, 0.01D, new int[0]);
			}
		}
	}

	@Override
	public boolean canRenderOnFire() {
		return false;
	}

	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}

	@Override
	public void onEntityUpdate() {
		this.rotation += 0.25f;

		this.tryActivate();

		if (this.isRiding()) {
			if (this.getRidingEntity().isSneaking() && this.getRidingEntity().onGround) {
				this.dismountRidingEntity();
			}
		}

		fuseFX();
		waterKills();
		super.onEntityUpdate();
	}

	@Override
	public boolean isEntityInsideOpaqueBlock() {
		return !this.isRiding() && super.isEntityInsideOpaqueBlock();
	}

	@Override
	public Page getPage(Entity entity) {
		return ManualWorld.bobomb_page;
	}
}
