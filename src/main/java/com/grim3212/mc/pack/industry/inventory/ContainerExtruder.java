package com.grim3212.mc.pack.industry.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerExtruder extends Container {

	private InventoryExtruder extruderInv;

	public ContainerExtruder(InventoryPlayer playerInv, InventoryExtruder extruderInv) {
		this.extruderInv = extruderInv;

		// Extruder fuel slot
		addSlotToContainer(new Slot(extruderInv, 0, 80, 20));

		// Extruder extrusion slots
		for (int i = 1; i < 10; i++) {
			addSlotToContainer(new Slot(extruderInv, i, (i * 18) - 10, 48));
		}

		// Extruder mined slots
		for (int j = 0; j < 3; j++) {
			for (int i1 = 0; i1 < 9; i1++) {
				addSlotToContainer(new Slot(extruderInv, 10 + i1 + j * 9, 8 + i1 * 18, 87 + j * 18));
			}
		}

		// Player inventory
		for (int k = 0; k < 3; k++) {
			for (int j1 = 0; j1 < 9; j1++) {
				addSlotToContainer(new Slot(playerInv, j1 + k * 9 + 9, 8 + j1 * 18, 162 + k * 18));
			}
		}

		// Player hotbar
		for (int l = 0; l < 9; l++) {
			addSlotToContainer(new Slot(playerInv, l, 8 + l * 18, 220));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return extruderInv.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index < 10) {
				if (!mergeItemStack(itemstack1, 38, 73, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 10 && index < 38) {
				if (!mergeItemStack(itemstack1, 38, 73, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 38 && index < 73) {
				if (!mergeItemStack(itemstack1, 0, 38, false)) {
					return ItemStack.EMPTY;
				}
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.getCount() != itemstack.getCount()) {
				slot.onTake(player, itemstack1);
			} else {
				return ItemStack.EMPTY;
			}
		}
		return itemstack;
	}
}
