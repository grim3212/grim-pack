package com.grim3212.mc.pack.tools.inventory;

import com.grim3212.mc.pack.tools.items.ItemSlingPellet;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerPelletBag extends Container {

	private static ItemInventoryBase inventory;
	public static int numRows;
	public static boolean notify;

	public ContainerPelletBag(ItemInventoryBase inventory, InventoryPlayer inventoryPlayer) {
		notify = false;
		ContainerPelletBag.inventory = inventory;
		ContainerPelletBag.numRows = inventory.getSizeInventory() / 9;
		int i = (ContainerPelletBag.numRows - 4) * 18;
		int j;
		int k;

		for (j = 0; j < 2; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlotToContainer(new SlotBackpack(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
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

	public static void saveToNBT(ItemStack itemStack) {
		if (!itemStack.hasTagCompound()) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
		inventory.writeToNBT(itemStack.getTagCompound());
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		ItemStack itemstack2 = slot.getStack();
		if (slot != null && slot.getHasStack() && itemstack2.getItem() instanceof ItemSlingPellet) {

			if (itemstack2.getItem() == ToolsItems.pellet_bag) {

				PelletBagInventory pelletBagInventory = new PelletBagInventory(itemstack2, player, 0);
				if (pelletBagInventory.getName() == inventory.getName()) {
					return itemstack;
				}
			}

			itemstack = itemstack2.copy();

			if (index < 18) {
				if (!this.mergeItemStack(itemstack2, 18, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack2, 0, 18, false)) {
				return null;
			}

			if (itemstack2.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}
		notify = true;
		return itemstack;
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		Slot slot;
		if (slotId >= 0 && slotId < inventorySlots.size()) {
			slot = (Slot) inventorySlots.get(slotId);
		} else {
			slot = null;
		}
		if (slot != null && slot.isHere(player.inventory, player.inventory.currentItem)) {
			return slot.getStack();
		}
		notify = true;

		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}

}
