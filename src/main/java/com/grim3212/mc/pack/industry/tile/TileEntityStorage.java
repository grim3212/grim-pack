package com.grim3212.mc.pack.industry.tile;

import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.LockCode;

public abstract class TileEntityStorage extends TileEntityLockable implements ITickable, ISidedInventory {

	private final int[] slots;
	public boolean hasInitialData = false;
	public int rotation = 0;
	private boolean direction = false;
	private String customName;
	private int numUsingPlayers = 0;
	private int hasInitialDataTimeout = 0;
	private final NonNullList<ItemStack> itemstacks;

	public TileEntityStorage() {
		this.itemstacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		this.slots = new int[getSizeInventory()];
	}

	protected abstract String getStorageName();

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerChest(playerInventory, this, playerIn);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return !this.isLocked();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return this.slots;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return true;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return true;
	}

	public void clearLock() {
		this.setLockCode(LockCode.EMPTY_CODE);
	}

	protected SoundEvent getOpenSound() {
		return SoundEvents.BLOCK_CHEST_OPEN;
	}

	protected SoundEvent getCloseSound() {
		return SoundEvents.BLOCK_CHEST_CLOSE;
	}

	protected SoundEvent getLockedSound() {
		return SoundEvents.BLOCK_CHEST_LOCKED;
	}

	@Override
	public void update() {
		if (!this.world.isRemote) {
			if ((!this.hasInitialData) && (this.hasInitialDataTimeout <= 0)) {

				// PacketDispatcher.sendToServer(new MessageMoreStorage(xCoord,
				// yCoord, zCoord, 1));

				this.hasInitialDataTimeout = 10;
			}

			if (this.hasInitialDataTimeout > 0)
				this.hasInitialDataTimeout -= 1;
		}

		boolean prevdirection = this.direction;
		this.direction = (this.numUsingPlayers > 0);

		if ((!prevdirection) && (this.direction))
			this.world.playSound((EntityPlayer) null, this.pos.getX(), (double) this.pos.getY() + 0.5D, this.pos.getZ(), this.getOpenSound(), SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);

		int prevrotation = this.rotation;
		int addspeed = (int) (Math.abs(90 - this.rotation) / 10.0F);

		if (this.direction) {
			if (this.rotation < 90) {
				this.rotation += 6 + addspeed;
			}
		} else if (this.rotation > 0) {
			this.rotation -= 6 + addspeed;
		}

		if (this.rotation < 0)
			this.rotation = 0;
		if (this.rotation > 90)
			this.rotation = 90;

		if ((prevrotation > this.rotation) && (this.rotation == 0)) {
			this.world.playSound((EntityPlayer) null, this.pos.getX(), (double) this.pos.getY() + 0.5D, this.pos.getZ(), this.getCloseSound(), SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
		}
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.itemstacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if ((this.isLocked()) && (this.numUsingPlayers == 0))
			return ItemStack.EMPTY;
		return this.itemstacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if ((this.isLocked()) && (this.numUsingPlayers == 0))
			return ItemStack.EMPTY;

		if (!this.itemstacks.get(index).isEmpty()) {
			if (this.itemstacks.get(index).getCount() <= count) {
				ItemStack itemstack1 = this.itemstacks.get(index);
				this.itemstacks.set(index, ItemStack.EMPTY);
				return itemstack1;
			} else {
				ItemStack itemstack = this.itemstacks.get(index).splitStack(count);

				if (this.itemstacks.get(index).getCount() == 0) {
					this.itemstacks.set(index, ItemStack.EMPTY);
				}

				return itemstack;
			}
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if ((this.isLocked()) && (this.numUsingPlayers == 0))
			return ItemStack.EMPTY;

		if (!this.itemstacks.get(index).isEmpty()) {
			ItemStack itemstack = this.itemstacks.get(index);
			this.itemstacks.set(index, ItemStack.EMPTY);
			return itemstack;
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.getItems().set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		// TODO: Investigate the hasInitialData
		return this.world.getTileEntity(this.pos) != this || (!this.hasInitialData) ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		if (!player.isSpectator()) {
			if (this.numUsingPlayers < 0) {
				this.numUsingPlayers = 0;
			}

			++this.numUsingPlayers;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numUsingPlayers);
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
		}

	}

	@Override
	public void closeInventory(EntityPlayer player) {
		if (!player.isSpectator() && this.getBlockType() instanceof BlockChest) {
			--this.numUsingPlayers;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numUsingPlayers);
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
		}
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
		for (int i = 0; i < this.itemstacks.size(); ++i) {
			this.itemstacks.set(i, ItemStack.EMPTY);
		}
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container." + getStorageName();
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomInventoryName(String name) {
		this.customName = name;
	}

	@Override
	public String getGuiID() {
		return "grimpack:" + this.getStorageName();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		ItemStackHelper.loadAllItems(compound, itemstacks);

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, itemstacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}

	@Override
	public boolean receiveClientEvent(int id, int type) {
		if (id == 1) {
			this.numUsingPlayers = type;
			return true;
		}
		if (id == 2) {
			this.setLockCode(new LockCode(String.valueOf(type)));
			return true;
		}

		return super.receiveClientEvent(id, type);
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

	public NonNullList<ItemStack> getItems() {
		return itemstacks;
	}
}
