package com.grim3212.mc.cuisine.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHealthPack extends Item {

	private int healAmount;

	public ItemHealthPack(int healAmnt) {
		healAmount = healAmnt;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.shouldHeal()) {
			itemstack.stackSize--;
			entityplayer.heal(healAmount);
		}
		return itemstack;
	}
}
