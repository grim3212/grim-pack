package com.grim3212.mc.pack.decor.tile;

import com.grim3212.mc.pack.decor.inventory.ContainerCage;
import com.grim3212.mc.pack.decor.util.CageSpawnerLogic;
import com.grim3212.mc.pack.tools.items.ItemPokeball;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.util.Constants;

public class TileEntityCage extends TileEntityLockable implements ITickable {

	public ItemStack cageItem;
	private String customName;
	private Entity cachedEntity;

	private final CageSpawnerLogic spawnerLogic = new CageSpawnerLogic(this);

	public CageSpawnerLogic getSpawnerLogic() {
		return spawnerLogic;
	}

	@Override
	public void update() {
		this.spawnerLogic.updateSpawner();
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerCage(this, playerInventory);
	}

	@Override
	public String getGuiID() {
		return "decor:cage";
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.cage";
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && this.customName.length() > 0;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return index == 0 ? cageItem : null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (index == 0) {
			this.cachedEntity = null;

			if (this.cageItem.stackSize <= count) {
				ItemStack stack = this.cageItem;
				this.cageItem = null;
				return stack;
			}

			ItemStack stack = this.cageItem.splitStack(count);

			if (this.cageItem.stackSize == 0) {
				this.cageItem = null;
			}

			return stack;
		}

		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (index == 0) {
			this.cachedEntity = null;
			return cageItem;
		}

		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index == 0) {
			this.cageItem = stack;
			this.cachedEntity = null;
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return stack.getItem() instanceof ItemPokeball;
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
		this.cageItem = null;
		this.cachedEntity = null;
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		if (this.cageItem != null) {
			NBTTagCompound cageCompound = new NBTTagCompound();
			this.cageItem.writeToNBT(cageCompound);
			compound.setTag("CageItem", cageCompound);
		}

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		if (compound.hasKey("CageItem", Constants.NBT.TAG_COMPOUND))
			this.cageItem = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("CageItem"));

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return new SPacketUpdateTileEntity(this.pos, 1, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	public Entity getPokeballEntity() {
		if (this.cachedEntity == null) {
			if (this.cageItem != null) {
				if (this.cageItem.hasTagCompound()) {
					Entity e = EntityList.createEntityFromNBT(this.cageItem.getTagCompound(), this.worldObj);
					e.readFromNBT(this.cageItem.getTagCompound());
					this.cachedEntity = e;
				}
			}
		}
		return this.cachedEntity;
	}
}
