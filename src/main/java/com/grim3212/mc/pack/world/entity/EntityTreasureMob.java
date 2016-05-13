package com.grim3212.mc.pack.world.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTreasureMob extends EntityAnimal {

	public EntityTreasureMob(World world) {
		super(world);
		setSize(0.9F, 1.3F);
		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAvoidEntity<EntityPlayer>(this, EntityPlayer.class, 10.0F, 1.0D, 1.2D));
		this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(4, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int i = rand.nextInt(14);
		if (i == 0) {
			dropItem(Items.arrow, 1 + rand.nextInt(2));
		}
		if (i == 1) {
			dropItem(Items.gold_ingot, 1 + rand.nextInt(3));
		}
		if (i == 2) {
			dropItem(Items.apple, 1 + rand.nextInt(3));
		}
		if (i == 3) {
			dropItem(Items.slime_ball, 1 + rand.nextInt(4));
		}
		if (i == 4) {
			dropItem(Items.saddle, 1);
		}
		if (i == 5) {
			dropItem(Items.cake, 1);
		}
		if (i == 6) {
			dropItem(Items.cookie, 1 + rand.nextInt(4));
		}
		if (i == 7) {
			dropItem(Items.diamond, 1 + rand.nextInt(2));
		}
		if (i == 8) {
			dropItem(Items.string, 1 + rand.nextInt(3));
		}
		if (i == 9) {
			dropItem(Items.iron_ingot, 1 + rand.nextInt(4));
		}
		if (i == 10) {
			dropItem(Items.name_tag, 1);
		}
		if (i == 11) {
			dropItem(Items.wheat, 1 + rand.nextInt(5));
		}
		if (i == 12) {
			dropItem(Items.bread, 1 + rand.nextInt(3));
		}
		if (i == 13) {
			dropItem(Items.coal, 1 + rand.nextInt(7));
		}
		if (i == 14) {
			dropItem(Items.redstone, 1 + rand.nextInt(8));
		}
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return null;
	}
}
