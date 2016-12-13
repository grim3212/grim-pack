package com.grim3212.mc.pack.tools.inventory;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public abstract class ItemInventoryBase implements IInventory {

	protected String uniqueID;
	protected ItemStack stack;
	protected ItemStack[] playerInventory;
	protected int size;

	public ItemInventoryBase(ItemStack itemStack, EntityPlayer entityPlayer) {
		this.stack = itemStack;

		uniqueID = "";
		if (!itemStack.hasTagCompound()) {
			itemStack.setTagCompound(new NBTTagCompound());
			uniqueID = UUID.randomUUID().toString();
		}
		size = readInvSizeFromNBT(itemStack.getTagCompound());
		playerInventory = new ItemStack[size];
		readFromNBT(itemStack.getTagCompound());
	}

	public ItemStack getStack() {
		return stack;
	}

	@Override
	public int getSizeInventory() {
		return this.playerInventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return playerInventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int number) {
		if (playerInventory[slot].isEmpty())
			return ItemStack.EMPTY;
		ItemStack itemstack;
		if (playerInventory[slot].getCount() > number) {
			itemstack = playerInventory[slot].splitStack(number);
		} else {
			itemstack = playerInventory[slot];
			playerInventory[slot] = ItemStack.EMPTY;
		}
		markDirty();
		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack itemstack = getStackInSlot(index);
		setInventorySlotContents(index, ItemStack.EMPTY);
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
			if (!itemstack.isEmpty() && itemstack.getCount() == 0) {
				setInventorySlotContents(i, ItemStack.EMPTY);
			}
		}
	}

	public void increaseSize(int i) {
		ItemStack[] itemstack = new ItemStack[size + i];
		System.arraycopy(playerInventory, 0, itemstack, 0, size);
		playerInventory = itemstack;
		size = size + i;
	}

	/**
	 * Get the number of slots that this Inventory should have
	 * 
	 * @param nbtTagCompound
	 *            Tag to check if extra slots are allocated
	 * @return Number of slots
	 */
	public abstract int readInvSizeFromNBT(NBTTagCompound nbtTagCompound);

	public abstract void readFromNBT(NBTTagCompound nbtTagCompound);

	public abstract void writeToNBT(NBTTagCompound nbtTagCompound);

	@Override
	public abstract String getName();
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
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

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
	}

	@Override
	public boolean hasCustomName() {
		return this.stack.hasDisplayName();
	}

	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]);
	}
}