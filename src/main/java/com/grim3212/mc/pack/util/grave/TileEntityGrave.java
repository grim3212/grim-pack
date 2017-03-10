package com.grim3212.mc.pack.util.grave;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityGrave extends TileEntityLockable {

	private NonNullList<ItemStack> itemstacks = NonNullList.<ItemStack>withSize(45, ItemStack.EMPTY);
	public ITextComponent[] signText = new ITextComponent[] { new TextComponentString(""), new TextComponentString(""), new TextComponentString(""), new TextComponentString("") };

	public TileEntityGrave() {
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		for (int i = 0; i < 4; ++i) {
			String s = ITextComponent.Serializer.componentToJson(this.signText[i]);
			compound.setString("Text" + (i + 1), s);
		}

		ItemStackHelper.saveAllItems(compound, this.itemstacks);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		for (int i = 0; i < 4; ++i) {
			String s = compound.getString("Text" + (i + 1));
			ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(s);
			this.signText[i] = itextcomponent;
		}

		this.itemstacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.itemstacks);
	}

	@Override
	public int getSizeInventory() {
		return itemstacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < itemstacks.size(); i++) {
			if (!itemstacks.get(i).isEmpty())
				return false;
		}

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < this.getSizeInventory() && index >= 0)
			return itemstacks.get(index);
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (index < this.getSizeInventory() && index >= 0) {
			if (this.itemstacks.get(index).getCount() <= count) {
				ItemStack stack = this.itemstacks.get(index);
				this.itemstacks.set(index, ItemStack.EMPTY);
				return stack;
			}

			ItemStack stack = this.itemstacks.get(index).splitStack(count);

			if (this.itemstacks.get(index).getCount() == 0) {
				this.itemstacks.set(index, ItemStack.EMPTY);
			}

			return stack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (index < this.getSizeInventory() && index >= 0) {
			ItemStack stack = this.itemstacks.get(index);
			this.itemstacks.set(index, ItemStack.EMPTY);
			return stack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < this.getSizeInventory() && index >= 0)
			this.itemstacks.set(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index < this.getSizeInventory() && index >= 0)
			return true;
		return false;
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
		for (int i = 0; i < this.itemstacks.size(); i++) {
			this.itemstacks.set(i, ItemStack.EMPTY);
		}
	}

	@Override
	public String getName() {
		return "container.grave";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerGrave(this, playerInventory);
	}

	@Override
	public String getGuiID() {
		return "util:grave";
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readFromNBT(tag);
	}
}
