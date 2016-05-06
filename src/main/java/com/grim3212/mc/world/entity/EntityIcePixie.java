package com.grim3212.mc.world.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityIcePixie extends EntityMob implements IRangedAttackMob {

	public EntityIcePixie(World world) {
		super(world);
		height = 1.25F;
		this.tasks.taskEntries.clear();
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAvoidBlock(this, 1.4D, Blocks.torch, 2));
		this.tasks.addTask(4, new EntityAIArrowAttack(this, 1.0D, 20, 40, 10.0F));
		this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));

		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	@Override
	public float getEyeHeight() {
		return 0.8125F;
	}

	@Override
	protected String getLivingSound() {
		return "step.snow";
	}

	@Override
	protected String getHurtSound() {
		return "random.wood_click";
	}

	@Override
	protected String getDeathSound() {
		return "random.wood_click";
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEFINED;
	}

	@Override
	public void onLivingUpdate() {
		int i = 0;
		for (int j = (int) (-3D + posX); (double) j <= 3D + posX; j++) {
			for (int k = (int) (-3D + posY); (double) k <= 3D + posY; k++) {
				for (int l = (int) (-3D + posZ); (double) l <= 3D + posZ; l++) {
					Block i1 = worldObj.getBlockState(new BlockPos(j, k, l)).getBlock();
					if (i1 == Blocks.torch) {
						i++;
					}
				}
			}
		}

		if (i >= 2) {
			attackEntityFrom(DamageSource.generic, i / 2);
		}

		BlockPos pos = new BlockPos((int) posX, (int) posY - 1, (int) posZ);
		if (worldObj.getBlockState(pos).getBlock() == Blocks.ice || worldObj.getBlockState(pos).getBlock() == Blocks.snow) {
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.40D);
		} else {
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		}
		super.onLivingUpdate();
	}

	@Override
	protected Item getDropItem() {
		return Item.getItemFromBlock(Blocks.ice);
	}

	@Override
	protected void dropFewItems(boolean flag, int par2) {
		int i = rand.nextInt(3) + 1;
		for (int j = 0; j < i; j++) {
			dropItem(Items.stick, 1);
		}

		i = rand.nextInt(4) + 2;
		for (int k = 0; k < i; k++) {
			dropItem(Item.getItemFromBlock(Blocks.ice), 1);
		}

		if (rand.nextInt(10) == 5) {
			dropItem(Items.fish, 1);
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float i) {
		Entity entity = damagesource.getEntity();
		if (entity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			ItemStack itemstack = entityplayer.getCurrentEquippedItem();
			if (itemstack != null && (itemstack.getItem() == Items.flint_and_steel || itemstack.getItem() == Item.getItemFromBlock(Blocks.torch))) {
				return super.attackEntityFrom(damagesource, i);
			}
		}
		if (damagesource == DamageSource.generic) {
			return super.attackEntityFrom(damagesource, i);
		} else {
			return false;
		}
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase entity, float f) {
		EntityIceCube entityicecube = new EntityIceCube(this.worldObj, this, entity, 1.2F, (float) (14 - this.worldObj.getDifficulty().getDifficultyId() * 4));
		worldObj.playSoundAtEntity(this, "mob.slime.attack", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
		worldObj.spawnEntityInWorld(entityicecube);
	}
}
