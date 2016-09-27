package com.grim3212.mc.pack.world.entity;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.world.client.ManualWorld;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityLumberJack extends EntityCreature implements IManualEntity {

	public EntityLumberJack(World world) {
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
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int i = rand.nextInt(3);
		for (int j = 0; j < i; j++) {
			dropItem(Item.getItemFromBlock(Blocks.LOG), 1);
		}

		i = rand.nextInt(3);
		for (int k = 0; k < i; k++) {
			dropItem(Items.STICK, 1);
		}

	}

	@Override
	public ItemStack getHeldItemMainhand() {
		return new ItemStack(Items.STONE_AXE);
	}

	@Override
	public Page getPage(Entity entity) {
		return ManualWorld.lumberjack_page;
	}
}
