package com.grim3212.mc.pack.world.entity;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.world.client.ManualWorld;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

public class EntityPsycho extends EntityMob implements IManualEntity {

	protected int attackStrength;

	public EntityPsycho(World world) {
		super(world);
		this.tasks.taskEntries.clear();
		((PathNavigateGround) this.getNavigator()).setAvoidSun(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));

		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.27000000417232513D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
	}

	@Override
	public Page getPage(Entity entity) {
		return ManualWorld.psycho_page;
	}
}
