package com.grim3212.mc.pack.tools.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBackpack extends Container {

	public ContainerBackpack(BackpackInventory backpackInventoryBase, InventoryPlayer inventoryPlayer) {
		int i = (2 - 4) * 18;
		int j;
		int k;

		for (j = 0; j < 2; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlotToContainer(new SlotBackpack(backpackInventoryBase, k + j * 9, 8 + k * 18, 18 + j * 18));
			}
		}

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(inventoryPlayer, k + j * 9 + 9, 8 + k * 18, 104 + j * 18 + i));
			}
		}

		for (j = 0; j < 9; ++j) {
			this.addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18, 162 + i));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < 18) {
				if (!this.mergeItemStack(itemstack1, 18, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 18, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}
}