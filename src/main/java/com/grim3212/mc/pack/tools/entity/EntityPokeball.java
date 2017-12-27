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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	protected void onImpact(RayTraceResult result) {
		Entity hitEntity = result.entityHit;

		if (!world.isRemote) {
			if (hitEntity != null && this.notCaught && !(hitEntity instanceof EntityPlayer || hitEntity instanceof EntityDragon)) {
				if (hitEntity instanceof EntityLivingBase) {
					NBTTagCompound entity = new NBTTagCompound();
					hitEntity.writeToNBT(entity);
					entity.setString("id", (String) EntityList.getKey(hitEntity.getClass()).toString());
					entity.setString("name", hitEntity.getName());

					this.currentPokeball.setTagCompound(entity);
					this.currentPokeball.damageItem(1, (EntityLivingBase) hitEntity);
					this.currentPokeball.setCount(1);
					
					hitEntity.setDead();
				}
			}

			if (!this.notCaught) {
				Entity spawnEntity = EntityList.createEntityFromNBT(this.currentPokeball.getTagCompound(), this.world);
				if (spawnEntity != null) {
					spawnEntity.setLocationAndAngles(this.posX, this.posY + 1.0D, this.posZ, this.rotationYaw, 0.0F);
					this.world.spawnEntity(spawnEntity);
				}
				
				//Always reset pokeball
				this.currentPokeball = new ItemStack(ToolsItems.pokeball);
			}
			
			this.entityDropItem(this.currentPokeball, 0.2F);
			this.world.setEntityState(this, (byte) 3);
			this.setDead();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.world.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}
}