package com.grim3212.mc.pack.tools.inventory;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;

public class BackpackInventory extends BackpackInventoryBase {
	protected String uniqueID;
	private ItemStack stack;

	public BackpackInventory(ItemStack itemStack, EntityPlayer entityPlayer, int inventorySize) {
		super(itemStack, entityPlayer, inventorySize);
		this.stack = itemStack;

		uniqueID = "";
		if (!itemStack.hasTagCompound()) {
			itemStack.setTagCompound(new NBTTagCompound());
			uniqueID = UUID.randomUUID().toString();
		}
		size = readInvSizeFromNBT(itemStack.getTagCompound());
		playerInventory = new ItemStack[size];
		readFromNBT(itemStack.getTagCompound());
	}

	public ItemStack getStack() {
		return stack;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? stack.getDisplayName() : "container.backpack";
	}

	@Override
	public int readInvSizeFromNBT(NBTTagCompound nbtTagCompound) {
		if (nbtTagCompound != null) {
			NBTTagCompound contentTag = ((NBTTagCompound) nbtTagCompound.getTag("inventory"));
			if (contentTag == null) {
				return 18;
			}
		}
		return nbtTagCompound.getInteger("inventorySize");
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		NBTTagCompound contentTag = ((NBTTagCompound) nbtTagCompound.getTag("backpackinventory"));
		if (contentTag == null) {
			return;
		}

		if ("".equals(uniqueID)) {
			uniqueID = nbtTagCompound.getString("uniqueID");
			if ("".equals(uniqueID)) {
				uniqueID = UUID.randomUUID().toString();
			}
		}

		NBTTagList myList = contentTag.getTagList("indexList", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < myList.tagCount() && i < playerInventory.length; i++) {
			NBTTagCompound indexTag = (NBTTagCompound) myList.getCompoundTagAt(i);
			int index = indexTag.getInteger("index");
			try {
				playerInventory[index] = ItemStack.loadItemStackFromNBT(indexTag);
			} catch (NullPointerException npe) {
				playerInventory[index] = null;
			}
		}

	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		NBTTagList myList = new NBTTagList();

		for (int i = 0; i < this.playerInventory.length; i++) {
			if (this.playerInventory[i] != null && this.playerInventory[i].stackSize > 0) {
				NBTTagCompound indexTag = new NBTTagCompound();
				myList.appendTag(indexTag);
				indexTag.setInteger("index", i);
				playerInventory[i].writeToNBT(indexTag);
			}
		}
		NBTTagCompound contentTag = new NBTTagCompound();
		contentTag.setTag("indexList", myList);
		nbtTagCompound.setTag("backpackinventory", contentTag);
		nbtTagCompound.setString("uniqueID", uniqueID);
		nbtTagCompound.setInteger("inventorySize", size);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
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
	}

	@Override
	public boolean hasCustomName() {
		return this.stack.hasDisplayName();
	}

	@Override
	public IChatComponent getDisplayName() {
		return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]);
	}
}