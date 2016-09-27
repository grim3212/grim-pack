package com.grim3212.mc.pack.industry.inventory;

import com.grim3212.mc.pack.industry.tile.TileEntityMachine;
import com.grim3212.mc.pack.industry.util.MachineRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerMachine extends Container {

	private TileEntityMachine machine;
	private int runTime = 0;
	private int totalRunTime = 0;

	public ContainerMachine(InventoryPlayer var1, TileEntityMachine machine) {
		this.machine = machine;
		this.addSlotToContainer(new Slot(machine, 0, 56, 35));
		this.addSlotToContainer(new SlotFurnaceOutput(var1.player, machine, 1, 116, 35));

		int var3;
		for (var3 = 0; var3 < 3; ++var3) {
			for (int var4 = 0; var4 < 9; ++var4) {
				this.addSlotToContainer(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}

		for (var3 = 0; var3 < 9; ++var3) {
			this.addSlotToContainer(new Slot(var1, var3, 8 + var3 * 18, 142));
		}
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.machine);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener crafting = (IContainerListener) this.listeners.get(i);
			if (this.runTime != this.machine.getField(0)) {
				crafting.sendProgressBarUpdate(this, 0, this.machine.getField(0));
			}

			if (this.totalRunTime != this.machine.getField(1)) {
				crafting.sendProgressBarUpdate(this, 1, this.machine.getField(1));
			}
		}

		this.runTime = this.machine.getField(0);
		this.totalRunTime = this.machine.getField(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.machine.setField(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.machine.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 1) {
				if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 0) {
				if (MachineRecipes.INSTANCE.getResult(itemstack1, machine.getMachineType()) != null) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (index >= 2 && index < 29) {
					if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
						return null;
					}
				} else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}
}
