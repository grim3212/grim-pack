package com.grim3212.mc.pack.core.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public abstract class ItemHandlerBase implements IItemHandlerModifiable {

	private final IItemHandlerModifiable inventory;
	private final ItemStack stack;

	public ItemHandlerBase(ItemStack itemStack) {
		this.stack = itemStack;
		this.inventory = (IItemHandlerModifiable) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
	}

	@Override
	public int getSlots() {
		return inventory.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory.getStackInSlot(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		return inventory.insertItem(slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return inventory.extractItem(slot, amount, simulate);
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		inventory.setStackInSlot(slot, stack);
	}

	public abstract String getUnlocalizedName();

	public String getName() {
		return this.hasCustomName() ? stack.getDisplayName() : getUnlocalizedName();
	}

	public boolean hasCustomName() {
		return this.stack.hasDisplayName();
	}

	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]);
	}

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}
}
