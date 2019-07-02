package com.grim3212.mc.pack.decor.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCage extends Container {

	private IInventory cageInv;

	public ContainerCage(int id, PlayerInventory playerInv) {
		this(id, playerInv, new Inventory(1));
	}

	public ContainerCage(int id, PlayerInventory playerInv, IInventory inv) {
		super(DecorContainers.CAGE_TYPE, id);
		this.cageInv = inv;

		addSlot(new SlotCage(this.cageInv, 0, 75, 30));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			addSlot(new Slot(playerInv, k, 8 + k * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.cageInv.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index == 0) {
				if (!mergeItemStack(itemstack1, 1, 37, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 1 && index < 37) {
				if (!mergeItemStack(itemstack1, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.getCount() != itemstack.getCount()) {
				slot.onTake(playerIn, itemstack1);
			} else {
				return ItemStack.EMPTY;
			}
		}
		return itemstack;
	}
}
