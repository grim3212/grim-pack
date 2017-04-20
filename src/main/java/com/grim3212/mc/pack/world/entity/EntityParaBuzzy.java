package com.grim3212.mc.pack.world.entity;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.grim3212.mc.pack.core.entity.EntityUtils;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.world.GrimWorld;
import com.grim3212.mc.pack.world.client.ManualWorld;
import com.grim3212.mc.pack.world.util.WorldLootTables;
import com.grim3212.mc.pack.world.util.WorldSounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityParaBuzzy extends EntityTameable implements IManualEntity {

	private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.<Float>createKey(EntityParaBuzzy.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> PARABUZZY_TYPE = EntityDataManager.<Integer>createKey(EntityParaBuzzy.class, DataSerializers.VARINT);

	public EntityParaBuzzy(World world) {
		super(world);
		this.setSize(0.2F, 0.6F);
		this.setTamed(false);
	}

	@Override
	protected void initEntityAI() {
		this.aiSit = new EntityAISit(this);
		this.tasks.taskEntries.clear();
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(4, new EntityAILeapAtTarget(this, 0.45F));
		this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(10, new EntityAILookIdle(this));

		this.targetTasks.taskEntries.clear();
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed<EntityLiving>(this, EntityLiving.class, true, new Predicate<EntityLiving>() {
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
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
	}

	@Override
	public void setAttackTarget(EntityLivingBase entitylivingbaseIn) {
		super.setAttackTarget(entitylivingbaseIn);

		if (entitylivingbaseIn == null) {
			this.setAngry(false);
		} else if (!this.isTamed()) {
			this.setAngry(true);
		}
	}

	@Override
	protected void updateAITasks() {
		this.dataManager.set(DATA_HEALTH_ID, this.getHealth());
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(DATA_HEALTH_ID, this.getHealth());
		this.dataManager.register(PARABUZZY_TYPE, 0);
	}

	@Override
	public float getBlockPathWeight(BlockPos pos) {
		return pos.getY();
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		int i = -1;

		int num = rand.nextInt(100);

		if (num <= 65) {
			i = 0;
		} else if (num <= 85) {
			i = 1;
		} else if (num <= 95) {
			i = 2;
		} else {
			i = 3;
		}

		chooseType(i);
		return super.onInitialSpawn(difficulty, livingdata);
	}

	public void chooseType(int type) {
		this.dataManager.set(PARABUZZY_TYPE, type);
		this.setPosition(this.posX, this.posY, this.posZ);

		if (type == 0) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
		}

		if (type == 1) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0D);
			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
		}

		if (type == 2) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(45.0D);
			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
		}

		if (type == 3) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
		}

		this.setHealth(this.getMaxHealth());
	}

	@Override
	protected ResourceLocation getLootTable() {
		if (this.getType() == EnumParaBuzzyType.BLUE || this.getType() == EnumParaBuzzyType.BLUE_SPIKED)
			return WorldLootTables.ENTITIES_PARABUZZY;
		return super.getLootTable();
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return WorldSounds.parabuzzy_living;
	}

	@Override
	protected SoundEvent getHurtSound() {
		return WorldSounds.parabuzzy_hurt;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return WorldSounds.parabuzzy_dead;
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		compound.setBoolean("Angry", this.isAngry());
		compound.setInteger("Type", this.getType().ordinal());

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		this.setAngry(compound.getBoolean("Angry"));
		if (compound.hasKey("Type", 99)) {
			this.setType(EnumParaBuzzyType.getTypeFromInt(compound.getInteger("Type")));
		}
	}

	public boolean isAngry() {
		return (this.dataManager.get(TAMED) & 2) != 0;
	}

	public void setAngry(boolean angry) {
		byte b0 = this.dataManager.get(TAMED);

		if (angry) {
			this.dataManager.set(TAMED, (byte) (b0 | 2));
		} else {
			this.dataManager.set(TAMED, (byte) (b0 & -3));
		}
	}

	public EnumParaBuzzyType getType() {
		return EnumParaBuzzyType.getTypeFromInt(this.dataManager.get(PARABUZZY_TYPE));
	}

	public void setType(EnumParaBuzzyType type) {
		this.dataManager.set(PARABUZZY_TYPE, type.ordinal());
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else {
			Entity entity = source.getEntity();

			if (this.aiSit != null) {
				this.aiSit.setSitting(false);
			}

			if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
				amount = (amount + 1.0F) / 2.0F;
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

		if (flag) {
			this.applyEnchantments(this, entityIn);
		}

		return flag;
	}

	@Override
	public boolean canBeLeashedTo(EntityPlayer player) {
		return !this.isAngry() && super.canBeLeashedTo(player);
	}

	@Override
	protected boolean canDespawn() {
		return !isTamed();
	}

	@Override
	public boolean shouldAttackEntity(EntityLivingBase entity, EntityLivingBase otherEntity) {
		if (!(entity instanceof EntityCreeper) && !(entity instanceof EntityGhast)) {
			if (entity instanceof EntityParaBuzzy) {
				EntityParaBuzzy parabuzzy = (EntityParaBuzzy) entity;

				if (parabuzzy.isTamed() && parabuzzy.getOwner() == otherEntity) {
					return false;
				}
			}

			return entity instanceof EntityPlayer && otherEntity instanceof EntityPlayer && !((EntityPlayer) otherEntity).canAttackPlayer((EntityPlayer) entity) ? false : !(entity instanceof AbstractHorse) || !((AbstractHorse) entity).isTame();
		} else {
			return false;
		}
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return false;
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);

		if (this.isTamed()) {
			if (!itemstack.isEmpty()) {
				if (itemstack.getItem() instanceof ItemFood) {
					ItemFood itemfood = (ItemFood) itemstack.getItem();

					if (this.getHealth() < this.getMaxHealth()) {
						if (!player.capabilities.isCreativeMode) {
							itemstack.shrink(1);
						}

						this.heal(itemfood.getHealAmount(itemstack));
						return true;
					}
				}
			}

			if (!this.isRiding() && !player.isSneaking() && !EntityUtils.isTypePassenger(player, EntityBobomb.class) && !EntityUtils.isTypePassenger(player, EntityParaBuzzy.class)) {
				this.startRiding(player, true);
				GrimWorld.proxy.displayDismountMessage(player);
				return true;
			}

			if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(itemstack)) {
				this.aiSit.setSitting(!this.isSitting());
				this.isJumping = false;
				this.navigator.clearPathEntity();
				this.setAttackTarget((EntityLivingBase) null);
			}
		} else if (itemstack.getItem() == Items.FISH && !this.isAngry()) {
			if (!player.capabilities.isCreativeMode) {
				itemstack.shrink(1);
			}

			if (!this.world.isRemote) {
				if (this.rand.nextInt(3) == 0) {
					this.setTamed(true);
					this.navigator.clearPathEntity();
					this.setAttackTarget((EntityLivingBase) null);
					this.aiSit.setSitting(true);
					this.setHealth(20.0F);
					this.setOwnerId(player.getUniqueID());
					this.playTameEffect(true);
					this.world.setEntityState(this, (byte) 7);
				} else {
					this.playTameEffect(false);
					this.world.setEntityState(this, (byte) 6);
				}
			}

			return true;
		}

		return super.processInteract(player, hand);
	}

	@Override
	public double getYOffset() {
		if (this.getRidingEntity() instanceof EntityPlayer) {
			return super.getYOffset() + 0.3F;
		} else {
			return super.getYOffset();
		}
	}

	@Override
	public void onLivingUpdate() {
		if (this.isRiding() && this.getHealth() < this.getMaxHealth() && rand.nextInt(200) == 0) {
			this.heal(2.0f);

			if (!world.isRemote) {
				this.playTameEffect(true);
				this.world.setEntityState(this, (byte) 7);
			}
		}

		if (!onGround && motionY < 0.0D) {
			motionY *= 0.6D;
		}

		super.onLivingUpdate();
	}

	@Override
	public void onUpdate() {
		if (this.isRiding()) {
			if (this.getRidingEntity().fallDistance > 0)
				this.getRidingEntity().fallDistance = 0;

			if (this.getRidingEntity().isSneaking() && this.getRidingEntity().onGround) {
				this.dismountRidingEntity();
			}
		}

		if (this.isRiding() && (this.getRidingEntity() instanceof EntityLiving)) {
			EntityLiving entityliving = (EntityLiving) this.getRidingEntity();
			rotationYaw = entityliving.rotationYaw;

			if (!entityliving.onGround && !entityliving.isDead) {
				if (entityliving.motionY < 0.0D) {
					entityliving.motionY *= 0.8D;
					entityliving.fallDistance = 0.0F;
				} else if (entityliving.motionY > 0.1D && entityliving.motionY < 0.3D && this.getType() == EnumParaBuzzyType.RED_SPIKED) {
					entityliving.motionY *= 1.255D;
				} else if (entityliving.motionY > 0.1D && entityliving.motionY < 0.5D) {
					entityliving.motionY *= 1.2D;
				}
			}
		}

		super.onUpdate();
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}

	public enum EnumParaBuzzyType {
		BLUE, RED, BLUE_SPIKED, RED_SPIKED;

		public static final EnumParaBuzzyType values[] = values();

		public static EnumParaBuzzyType getTypeFromInt(int type) {
			return type < values.length && type >= 0 ? values[type] : BLUE;
		}
	}

	@Override
	public Page getPage(Entity entity) {
		return ManualWorld.parabuzzy_page;
	}
}
