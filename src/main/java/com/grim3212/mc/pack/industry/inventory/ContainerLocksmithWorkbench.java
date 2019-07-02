package com.grim3212.mc.pack.industry.inventory;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import org.apache.commons.lang3.StringUtils;

import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.item.ItemCombination;
import com.grim3212.mc.pack.industry.network.MessageSetLock;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerLocksmithWorkbench extends Container {

	private final IInventory outputSlot;
	private final IInventory inputSlot;
	private BlockPos pos;
	private World world;
	private String lock = "";

	public ContainerLocksmithWorkbench(PlayerInventory inventory, World worldIn, BlockPos pos) {
		this.world = worldIn;
		this.pos = pos;
		this.outputSlot = new CraftResultInventory();
		this.inputSlot = new Inventory("container.locksmith_workbench", false, 1) {
			/**
			 * For tile entities, ensures the chunk containing the tile entity
			 * is saved to disk later - the game won't think it hasn't changed
			 * and skip it.
			 */
			public void markDirty() {
				super.markDirty();
				ContainerLocksmithWorkbench.this.onCraftMatrixChanged(this);
			}
		};

		addSlotToContainer(new Slot(this.inputSlot, 0, 41, 35) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemCombination;
			}
		});
		addSlotToContainer(new Slot(this.outputSlot, 1, 120, 35) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			@Override
			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {

				inputSlot.setInventorySlotContents(0, ItemStack.EMPTY);

				return super.onTake(thePlayer, stack);
			}
		});

		for (int l = 0; l < 3; l++) {
			for (int i1 = 0; i1 < 9; i1++) {
				addSlotToContainer(new Slot(inventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
			}
		}

		for (int l = 0; l < 9; l++) {
			addSlotToContainer(new Slot(inventory, l, 8 + l * 18, 142));
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		super.onCraftMatrixChanged(inventoryIn);

		if (inventoryIn == this.inputSlot) {
			this.updateLocksmithOutput();

			if (world.isRemote)
				PacketDispatcher.sendToServer(new MessageSetLock(lock));
		}
	}

	private void updateLocksmithOutput() {
		ItemStack itemstack = this.inputSlot.getStackInSlot(0);

		if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemCombination) {
			if (!StringUtils.isBlank(this.lock)) {
				this.outputSlot.setInventorySlotContents(0, NBTHelper.setStringItemStack(new ItemStack(itemstack.getItem()), "Lock", lock));
			} else {
				this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
			}
		} else {
			this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
		}

		this.detectAndSendChanges();
	}

	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);

		if (!this.world.isRemote) {
			for (int i = 0; i < this.inputSlot.getSizeInventory(); ++i) {
				ItemStack itemstack = this.inputSlot.removeStackFromSlot(i);

				if (!itemstack.isEmpty()) {
					playerIn.dropItem(itemstack, false);
				}
			}
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.world.getBlockState(this.pos).getBlock() != IndustryBlocks.locksmith_workbench ? false : playerIn.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 1) {
				if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 0) {
				if (index >= 2 && index < 38 && !this.mergeItemStack(itemstack1, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	public void updateLock(String newName) {
		this.lock = newName;
		this.updateLocksmithOutput();
	}
}