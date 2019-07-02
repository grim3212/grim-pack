package com.grim3212.mc.pack.industry.inventory;

import com.grim3212.mc.pack.industry.entity.EntityExtruder;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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
	private NonNullList<ItemStack> extruderContents = NonNullList.withSize(37, ItemStack.EMPTY);
	public int contentHead = 1;

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
		return (ITextComponent) (this.hasCustomName() ? new StringTextComponent(this.getName()) : new TranslationTextComponent(this.getName(), new Object[0]));
	}

	@Override
	public int getSizeInventory() {
		return extruderContents.size();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return extruderContents.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (!this.extruderContents.get(index).isEmpty()) {
			if (this.extruderContents.get(index).getCount() <= count) {
				ItemStack itemstack1 = this.extruderContents.get(index);
				this.extruderContents.set(index, ItemStack.EMPTY);
				return itemstack1;
			} else {
				ItemStack itemstack = this.extruderContents.get(index).splitStack(count);

				if (this.extruderContents.get(index).getCount() == 0) {
					this.extruderContents.set(index, ItemStack.EMPTY);
				}

				return itemstack;
			}
		} else {
			return ItemStack.EMPTY;
		}
	}

	public ItemStack nextExtruderStack() {
		int slot = contentHead++;
		if (contentHead >= 10) {
			contentHead = 1;
		}
		ItemStack itemstack = extruderContents.get(slot);
		if (!itemstack.isEmpty()) {
			if (itemstack.getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)) {
				contentHead = 2;
				slot = 1;
			}

			return decrStackSize(slot, 1);
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (!this.extruderContents.get(index).isEmpty()) {
			ItemStack itemstack = this.extruderContents.get(index);
			this.extruderContents.set(index, ItemStack.EMPTY);
			return itemstack;
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.extruderContents.set(index, stack);

		if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
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
	public boolean isUsableByPlayer(PlayerEntity player) {
		return player.getDistanceSq(extruder) <= 64.0D;
	}

	@Override
	public void openInventory(PlayerEntity player) {
	}

	@Override
	public void closeInventory(PlayerEntity player) {
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
		for (int i = 0; i < this.extruderContents.size(); i++) {
			this.extruderContents.set(i, ItemStack.EMPTY);
		}
	}

	public NonNullList<ItemStack> getExtruderContents() {
		return extruderContents;
	}

	public int getExtruderSlotStackSize(int index) {
		return this.extruderContents.get(index).getCount();
	}

	public void setExtruderContents(NonNullList<ItemStack> extruderContents) {
		this.extruderContents = extruderContents;
	}

	public void addToMinedInventory(ItemStack itemstack) {
		int slot = 10;
		do {
			if (slot >= 37) {
				break;
			}
			ItemStack itemstack1 = getStackInSlot(slot);
			if (itemstack1.isEmpty()) {
				setInventorySlotContents(slot, itemstack);
				break;
			}
			if (ItemStack.areItemsEqual(itemstack1, itemstack) && itemstack1.getCount() < getInventoryStackLimit()) {
				itemstack1.grow(1);
				break;
			}
			slot++;
		} while (true);
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.extruderContents) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}
}
