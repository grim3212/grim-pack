package com.grim3212.mc.pack.world.entity;

<<<<<<< HEAD
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.world.client.ManualWorld;
=======
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.world.client.ManualWorld;
import com.grim3212.mc.pack.world.util.LootTables;
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
<<<<<<< HEAD
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityBomber extends EntityMob implements IManualEntity {
=======
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityBomber extends EntityDayMob {
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37

	public EntityBomber(World world) {
		super(world);
		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));

		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
	}

	@Override
<<<<<<< HEAD
	protected Item getDropItem() {
		return Item.getItemFromBlock(Blocks.TNT);
=======
	protected ResourceLocation getLootTable() {
		return LootTables.ENTITIES_BOMBER;
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float i) {
		if (super.attackEntityFrom(damagesource, i)) {
			Entity entity = damagesource.getEntity();
			if (entity instanceof EntityLivingBase) {
				worldObj.createExplosion(this, posX, posY, posZ, 3F, true);
				setDead();
			}
		}

		return true;
	}

	@Override
	public Page getPage(Entity entity) {
		return ManualWorld.suicide_page;
	}
}
