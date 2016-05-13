package com.grim3212.mc.pack.tools.entity;

import java.util.Random;

import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPokeball extends EntityThrowable {

	public Random random;
	private boolean notCaught;
	private ItemStack currentPokeball = new ItemStack(ToolsItems.pokeball, 1);

	public EntityPokeball(World world) {
		super(world);
	}

	public EntityPokeball(World world, EntityLivingBase entityliving, ItemStack pokeball) {
		super(world, entityliving);
		this.currentPokeball = pokeball;

		if (this.currentPokeball.hasTagCompound()) {
			this.notCaught = false;
		} else {
			this.notCaught = true;
		}
	}

	public EntityPokeball(World world, double var2, double var4, double var6) {
		super(world, var2, var4, var6);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		Entity hitEntity = mop.entityHit;

		for (int j = 0; j < 8; ++j) {
			this.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}

		if (!worldObj.isRemote) {
			if (hitEntity != null && this.notCaught && !(hitEntity instanceof EntityPlayer || hitEntity instanceof EntityDragon)) {
				if (hitEntity instanceof EntityLivingBase) {
					NBTTagCompound entity = new NBTTagCompound();
					hitEntity.writeToNBT(entity);
					entity.setString("id", (String) EntityList.classToStringMapping.get(hitEntity.getClass()));

					this.currentPokeball.setTagCompound(entity);
					this.currentPokeball.damageItem(1, (EntityLivingBase) hitEntity);
					this.currentPokeball.stackSize = 1;
					this.entityDropItem(this.currentPokeball, 0.0F);
					hitEntity.setDead();
					this.setDead();
				}
			}

			if (!this.notCaught) {
				Entity spawnEntity = EntityList.createEntityFromNBT(this.currentPokeball.getTagCompound(), this.worldObj);
				if (spawnEntity != null) {
					spawnEntity.readFromNBT(this.currentPokeball.getTagCompound());
				}

				if (spawnEntity != null) {
					spawnEntity.setLocationAndAngles(this.posX, this.posY + 1.0D, this.posZ, this.rotationYaw, 0.0F);
					this.worldObj.spawnEntityInWorld(spawnEntity);
					this.entityDropItem(new ItemStack(ToolsItems.pokeball), 0.0F);
				}
			}

			if (mop.entityHit == null && this.notCaught) {
				this.entityDropItem(new ItemStack(ToolsItems.pokeball), 0.0F);
			}
		}

		if (!this.worldObj.isRemote) {
			this.setDead();
		}
	}
}