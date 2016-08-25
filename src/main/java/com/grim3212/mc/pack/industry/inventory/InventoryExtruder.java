package com.grim3212.mc.pack.industry.inventory;

import com.grim3212.mc.pack.industry.entity.EntityExtruder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryExtruder implements IInventory {

	private final EntityExtruder extruder;
	/**
	 * Slot 0 : Fuel Slot
	 * 
	 * Slot 1-9 : Extruder Contents
	 * 
	 * Slot 10-37 : Extruder Mined contents
	 */
	private ItemStack[] extruderContents = new ItemStack[37];
	public int contentHead = 0;

	public InventoryExtruder(EntityExtruder extruder) {
		this.extruder = extruder;
	}

	public EntityExtruder getExtruder() {
		return extruder;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? extruder.getCustomName() : "container.extruder";
	}

	@Override
	public boolean hasCustomName() {
		return extruder.getCustomName() != null && extruder.getCustomName().length() > 0;
	}

	@Override
	public ITextComponent getDisplayName() {
		return (ITextComponent) (this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
	}

	@Override
	public int getSizeInventory() {
		return extruderContents.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return extruderContents[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.extruderContents[index] != null) {
			if (this.extruderContents[index].stackSize <= count) {
				ItemStack itemstack1 = this.extruderContents[index];
				this.extruderContents[index] = null;
				return itemstack1;
			} else {
				ItemStack itemstack = this.extruderContents[index].splitStack(count);

				if (this.extruderContents[index].stackSize == 0) {
					this.extruderContents[index] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (this.extruderContents[index] != null) {
			ItemStack itemstack = this.extruderContents[index];
			this.extruderContents[index] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.extruderContents[index] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSqToEntity(extruder) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) {
			return isItemFuel(stack);
		}

		return true;
	}

	public boolean isItemFuel(ItemStack stack) {
		return OreDictionary.getOres("stickWood").contains(stack) || stack.getItem() == Items.COAL || stack.getItem() == Items.BLAZE_ROD || stack.getItem() == Items.LAVA_BUCKET || stack.getItem() == Items.BLAZE_POWDER || OreDictionary.getOres("dustRedstone").contains(stack);
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
		for (int i = 0; i < this.extruderContents.length; i++) {
			this.extruderContents[i] = null;
		}
	}

	public ItemStack[] getExtruderContents() {
		return extruderContents;
	}

	public void setExtruderContents(ItemStack[] extruderContents) {
		this.extruderContents = extruderContents;
	}
}
