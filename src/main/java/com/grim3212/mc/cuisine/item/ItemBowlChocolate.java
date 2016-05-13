package com.grim3212.mc.cuisine.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBowlChocolate extends Item {

	public ItemBowlChocolate(int stacksize) {
		super();
		maxStackSize = stacksize;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase playerIn) {
		if (playerIn instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) playerIn;

			if (!entityplayer.capabilities.isCreativeMode) {
				--stack.stackSize;
			}

			if (!worldIn.isRemote) {
				entityplayer.heal(4f);
			}

			entityplayer.inventory.consumeInventoryItem(entityplayer.getHeldItem(entityplayer.getActiveHand()).getItem());
			entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.bowl));
		}

		return stack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
		return itemstack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.DRINK;
	}
}
