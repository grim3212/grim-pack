package com.grim3212.mc.pack.tools.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ItemInventoryBase implements IInventory {
	protected ItemStack[] playerInventory;
	protected int size;

	public ItemInventoryBase(ItemStack itemStack, EntityPlayer entityPlayer, int inventorySize) {
	}

	@Override
	public int getSizeInventory() {
		return this.playerInventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return playerInventory[var1];
	}

	@Override
	public ItemStack decrStackSize(int slot, int number) {
		if (playerInventory[slot] == null)
			return null;
		ItemStack itemstack;
		if (playerInventory[slot].stackSize > number) {
			itemstack = playerInventory[slot].splitStack(number);
		} else {
			itemstack = playerInventory[slot];
			playerInventory[slot] = null;
		}
		markDirty();
		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack itemstack = getStackInSlot(index);
		setInventorySlotContents(index, null);
		return itemstack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		if (0 <= slot && slot < size) {
			playerInventory[slot] = itemstack;
		}

	}

	@Override
	public void markDirty() {
		for (int i = 0; i < size; i++) {
			ItemStack itemstack = getStackInSlot(i);
			if (itemstack != null && itemstack.stackSize == 0) {
				setInventorySlotContents(i, null);
			}
		}
	}

	public void increaseSize(int i) {
		ItemStack[] itemstack = new ItemStack[size + i];
		System.arraycopy(playerInventory, 0, itemstack, 0, size);
		playerInventory = itemstack;
		size = size + i;
	}

	public abstract int readInvSizeFromNBT(NBTTagCompound nbtTagCompound);

	public abstract void readFromNBT(NBTTagCompound nbtTagCompound);

	public abstract void writeToNBT(NBTTagCompound nbtTagCompound);

	@Override
	public abstract String getName();

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}
}