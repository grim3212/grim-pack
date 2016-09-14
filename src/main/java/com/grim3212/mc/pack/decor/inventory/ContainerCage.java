package com.grim3212.mc.pack.decor.inventory;

import com.grim3212.mc.pack.decor.tile.TileEntityCage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCage extends Container {

	private TileEntityCage cage;

	public ContainerCage(TileEntityCage cage, InventoryPlayer playerInv) {
		this.cage = cage;

		addSlotToContainer(new SlotCage(cage, 0, 75, 30));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.cage.isUseableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index == 0) {
				if (!mergeItemStack(itemstack1, 1, 37, false)) {
					return null;
				}
			} else if (index >= 1 && index < 37) {
				if (!mergeItemStack(itemstack1, 0, 1, false)) {
					return null;
				}
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize != itemstack.stackSize) {
				slot.onPickupFromSlot(playerIn, itemstack1);
			} else {
				return null;
			}
		}
		return itemstack;
	}
}
