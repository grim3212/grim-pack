package com.grim3212.mc.pack.world.entity;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.world.client.ManualWorld;
import com.grim3212.mc.pack.world.event.WorldAchievements;
import com.grim3212.mc.pack.world.util.LootTables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityTreasureMob extends EntityTameable implements IManualEntity {

	private EntityAIAvoidEntity<EntityPlayer> avoidEntity;

	public EntityTreasureMob(World world) {
		super(world);
		this.setTamed(false);
		setSize(0.9F, 1.3F);
		this.tasks.taskEntries.clear();
		this.setupTamedAI();
		this.aiSit = new EntityAISit(this);
		this.tasks.addTask(0, new EntityAISwimming(this));
		// this.tasks.addTask(3, new EntityAIAvoidEntity<EntityPlayer>(this,
		// EntityPlayer.class, 10.0F, 1.0D, 1.2D));
		this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(2, this.aiSit);
		// this.setCanPickUpLoot(canPickup);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}

	@Override
	protected ResourceLocation getLootTable() {
		return LootTables.ENTITIES_TREASURE_MOB;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return null;
	}

	@Override
	public Page getPage(Entity entity) {
		return ManualWorld.treasureChest_page;
	}

	@Override
	public void setTamed(boolean tamed) {
		super.setTamed(tamed);

		if (tamed) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
		} else {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		}
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (this.isTamed()) {
			if (this.isOwner(player) && !this.world.isRemote) {
				this.aiSit.setSitting(!this.isSitting());
				this.isJumping = false;
				this.navigator.clearPathEntity();
			}

		} else {

			if (!player.getHeldItem(hand).isEmpty() && player.getHeldItem(hand).getItem() == Items.GOLD_NUGGET) {
				if (!player.capabilities.isCreativeMode) {
					player.getHeldItem(hand).shrink(1);
				}

				if (!this.world.isRemote) {
					// if (this.rand.nextInt(3) == 0)
					// {
					player.addStat(WorldAchievements.TREASURE);
					this.setTamed(true);
					this.navigator.clearPathEntity();
					this.aiSit.setSitting(true);
					this.setHealth(100.0F);
					this.setOwnerId(player.getUniqueID());
					this.playTameEffect(true);
					this.world.setEntityState(this, (byte) 7);
					this.setupTamedAI();
				}
				// else
				// {
				// this.playTameEffect(false);
				// this.worldObj.setEntityState(this, (byte)6);
				// }
				// }

				return true;
			}
		}

		return super.processInteract(player, hand);
	}

	// From Ocelot
	@Override
	protected void setupTamedAI() {
		if (this.avoidEntity == null) {
			this.avoidEntity = new EntityAIAvoidEntity<EntityPlayer>(this, EntityPlayer.class, 10.0F, 1.0D, 1.2D);
		}

		this.tasks.removeTask(this.avoidEntity);

		if (!this.isTamed()) {
			this.tasks.addTask(3, this.avoidEntity);
		}
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote && this.world.getGameRules().getBoolean("showDeathMessages") && this.getOwner() instanceof EntityPlayerMP) {
			this.getOwner().sendMessage(this.getCombatTracker().getDeathMessage());
		}

		super.onDeath(cause);
	}
}
