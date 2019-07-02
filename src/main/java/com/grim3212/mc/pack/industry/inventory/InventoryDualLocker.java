package com.grim3212.mc.pack.industry.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class InventoryDualLocker implements IInventory {

	private IInventory topLocker;
	private IInventory bottomLocker;

	public InventoryDualLocker(IInventory bottomLocker, IInventory topLocker) {
		this.bottomLocker = bottomLocker;
		this.topLocker = topLocker;
	}

	@Override
	public int getSizeInventory() {
		return this.topLocker.getSizeInventory() + this.bottomLocker.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return getInvFromSlot(i).getStackInSlot(getLocalSlot(i));
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return getInvFromSlot(i).decrStackSize(getLocalSlot(i), j);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		getInvFromSlot(i).setInventorySlotContents(getLocalSlot(i), itemstack);
	}

	@Override
	public String getName() {
		return this.bottomLocker.getName();
	}

	@Override
	public boolean hasCustomName() {
		return this.bottomLocker.hasCustomName();
	}

	@Override
	public int getInventoryStackLimit() {
		return this.bottomLocker.getInventoryStackLimit();
	}

	@Override
	public void markDirty() {
		this.bottomLocker.markDirty();
		this.topLocker.markDirty();
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity entityplayer) {
		return (this.topLocker.isUsableByPlayer(entityplayer)) && (this.bottomLocker.isUsableByPlayer(entityplayer));
	}

	@Override
	public void openInventory(PlayerEntity player) {
		this.bottomLocker.openInventory(player);
	}

	@Override
	public void closeInventory(PlayerEntity player) {
		this.bottomLocker.closeInventory(player);
	}

	private int getLocalSlot(int slot) {
		if (getInvFromSlot(slot) == this.topLocker)
			return slot;
		return slot - this.topLocker.getSizeInventory();
	}

	private IInventory getInvFromSlot(int slot) {
		return slot < this.topLocker.getSizeInventory() ? this.topLocker : this.bottomLocker;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return getInvFromSlot(i).isItemValidForSlot(getLocalSlot(i), itemstack);
	}

	@Override
	public ITextComponent getDisplayName() {
		return this.bottomLocker.getDisplayName();
	}

	@Override
	public boolean isEmpty() {
		return this.bottomLocker.isEmpty() && this.topLocker.isEmpty();
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return this.getInvFromSlot(index).removeStackFromSlot(getLocalSlot(index));
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
		this.bottomLocker.clear();
		this.topLocker.clear();
	}

}