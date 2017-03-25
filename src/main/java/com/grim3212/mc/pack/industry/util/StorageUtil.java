package com.grim3212.mc.pack.industry.util;

import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.tile.TileEntityStorage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.LockCode;

public class StorageUtil {

	public static boolean canAccess(TileEntityStorage tileentity, EntityPlayer entityplayer) {
		if (tileentity.isLocked()) {
			for (int slot = 0; slot < entityplayer.inventory.getSizeInventory(); slot++) {
				ItemStack itemstack = entityplayer.inventory.getStackInSlot(slot);

				if ((!itemstack.isEmpty()) && (itemstack.getItem() == IndustryItems.locksmith_key) && (itemstack.hasTagCompound()) && (itemstack.getTagCompound().hasKey("Lock"))) {
					if (itemstack.getTagCompound().getString("Lock").equals(tileentity.getLockCode().getLock())) {
						return true;
					}
				}
			}

			return false;
		}

		return true;
	}

	public static boolean tryPlaceLock(TileEntityStorage tileentity, EntityPlayer entityplayer, EnumHand hand) {
		ItemStack itemstack = entityplayer.getHeldItem(hand);

		if ((!itemstack.isEmpty()) && (itemstack.getItem() == IndustryItems.locksmith_lock) && (itemstack.hasTagCompound()) && (itemstack.getTagCompound().hasKey("Lock")) && (!itemstack.getTagCompound().getString("Lock").isEmpty())) {
			itemstack.shrink(1);
			tileentity.setLockCode(new LockCode(itemstack.getTagCompound().getString("Lock")));
			tileentity.getWorld().playSound(entityplayer, tileentity.getPos(), tileentity.getLockedSound(), SoundCategory.BLOCKS, 0.5F, tileentity.getWorld().rand.nextFloat() * 0.1F + 0.9F);
			return true;
		}

		return false;
	}
}
