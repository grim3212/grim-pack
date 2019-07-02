package com.grim3212.mc.pack.industry.inventory;

import java.util.List;

import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.tile.TileEntityItemTower;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class InventoryItemTower implements IInventory {

	public List<TileEntityItemTower> itemTowers;
	public TileEntityItemTower mainTower;

	public InventoryItemTower(List<TileEntityItemTower> itemTowers) {
		this.itemTowers = itemTowers;
		// Grab the first element
		if (itemTowers.size() >= 1) {
			this.mainTower = this.itemTowers.get(0);
		} else {
			GrimLog.error(GrimIndustry.partName, "Opened tower without any tileentities! Something went wrong!");
		}
	}

	@Override
	public int getSizeInventory() {
		return this.mainTower.getSizeInventory() * this.itemTowers.size();
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
	public int getInventoryStackLimit() {
		return this.mainTower.getInventoryStackLimit();
	}

	@Override
	public void markDirty() {
		for (IInventory inventory : this.itemTowers)
			inventory.markDirty();
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity entityplayer) {
		for (IInventory inventory : this.itemTowers) {
			if (!inventory.isUsableByPlayer(entityplayer))
				return false;
		}

		return true;
	}

	@Override
	public void openInventory(PlayerEntity entityplayer) {
		for (IInventory inventory : this.itemTowers)
			inventory.openInventory(entityplayer);
	}

	@Override
	public void closeInventory(PlayerEntity entityplayer) {
		for (IInventory inventory : this.itemTowers)
			inventory.closeInventory(entityplayer);
	}

	public void setAnimation(int animID) {
		for (TileEntityItemTower inventory : this.itemTowers)
			inventory.animate(animID);
	}

	private int getLocalSlot(int slot) {
		return slot % this.mainTower.getSizeInventory();
	}

	private IInventory getInvFromSlot(int slot) {
		int inventoryIndex = (int) Math.floor(slot / this.mainTower.getSizeInventory());
		return (IInventory) this.itemTowers.get(inventoryIndex);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return getInvFromSlot(i).isItemValidForSlot(getLocalSlot(i), itemstack);
	}

	@Override
	public String getName() {
		return this.mainTower.getName();
	}

	@Override
	public boolean hasCustomName() {
		return this.mainTower.hasCustomName();
	}

	@Override
	public ITextComponent getDisplayName() {
		return this.mainTower.getDisplayName();
	}

	@Override
	public boolean isEmpty() {
		for (IInventory inv : itemTowers) {
			if (!inv.isEmpty()) {
				return false;
			}
		}

		return true;
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
		for (IInventory inv : itemTowers) {
			inv.clear();
		}
	}
}