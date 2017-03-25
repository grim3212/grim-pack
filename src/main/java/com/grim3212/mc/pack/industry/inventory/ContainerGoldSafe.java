package com.grim3212.mc.pack.industry.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGoldSafe extends Container {
	private IInventory safeInventory;
	private int numRows;

	public ContainerGoldSafe(EntityPlayer player, IInventory safeInventory) {
		this.safeInventory = safeInventory;
		this.numRows = (safeInventory.getSizeInventory() / 9);
		safeInventory.openInventory(player);
		int i = (this.numRows - 4) * 18;

		for (int j = 0; j < this.numRows; j++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new SlotGoldSafe(safeInventory, k + j * 9, 8 + k * 18, 18 + j * 18));
			}
		}

		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
			}
		}

		for (int j = 0; j < 9; j++) {
			addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 161 + i));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.safeInventory.isUsableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < this.numRows * 9) {
				if (!mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(itemstack1, 0, this.numRows * 9, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		this.safeInventory.closeInventory(player);
	}

	public IInventory getSafeInventory() {
		return this.safeInventory;
	}
}