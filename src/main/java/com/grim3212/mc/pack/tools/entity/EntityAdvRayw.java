package com.grim3212.mc.pack.tools.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityAdvRayw extends EntityRayw {

	public EntityAdvRayw(World world) {
		super(world);
		this.damage = 20;
	}
	
	public EntityAdvRayw(World world, EntityLivingBase entity) {
		super(world, entity);	
		this.damage = 20;
	}
	
	public EntityAdvRayw(World world, double x, double y, double z) {
		super(world, x, y, z);
		this.damage = 20;
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer playerIn) {
		if (this.inGroundRayw && this.shootingEntity == playerIn && this.shakeRayw <= 0 && playerIn.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1))) {
			this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			playerIn.onItemPickup(this, 1);
			this.setDead();
		}

	}
}
