package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.industry.block.BlockRefinery;
import com.grim3212.mc.pack.industry.inventory.ContainerDerrick;
import com.grim3212.mc.pack.industry.inventory.ContainerRefinery;
import com.grim3212.mc.pack.industry.util.MachineRecipes;
import com.grim3212.mc.pack.industry.util.MachineRecipes.MachineType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityMachine extends TileEntityLockable implements ISidedInventory, ITickable {

	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 1 };
	private static final int[] slotsSides = new int[] { 0 };
	/**
	 * The ItemStacks that hold the items currently being used in the inventory
	 */
	private NonNullList<ItemStack> itemstacks = NonNullList.withSize(2, ItemStack.EMPTY);

	private int runTime;
	private int totalRunTime;
	private String customName;
	private MachineType machineType;

	public TileEntityMachine() {
	}

	public TileEntityMachine(MachineType machineType) {
		this.machineType = machineType;
	}

	public MachineType getMachineType() {
		return machineType;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return this.itemstacks.size();
	}

	/**
	 * Returns the stack in the given slot.
	 */
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.itemstacks.get(index);
	}

	public boolean isWorking() {
		return this.runTime != this.totalRunTime && this.runTime != 0;
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and
	 * returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count) {
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

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (!this.itemstacks.get(index).isEmpty()) {
			ItemStack itemstack = this.itemstacks.get(index);
			this.itemstacks.set(index, ItemStack.EMPTY);
			return itemstack;
		} else {
			return ItemStack.EMPTY;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		boolean flag = !stack.isEmpty() && stack.isItemEqual(this.itemstacks.get(index)) && ItemStack.areItemStackTagsEqual(stack, this.itemstacks.get(index));
		this.itemstacks.set(index, stack);

		if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.totalRunTime = this.getCookTime(stack);
			this.runTime = 0;
			this.markDirty();
		}
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container." + (getMachineType() == MachineType.DERRICK ? "derrick" : "refinery");
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName() {
		return this.customName != null && this.customName.length() > 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		ItemStackHelper.loadAllItems(compound, itemstacks);

		this.runTime = compound.getInteger("RunTime");
		this.totalRunTime = compound.getInteger("RunTimeTotal");
		this.machineType = MachineType.values[compound.getInteger("MachineType")];

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("RunTime", this.runTime);
		compound.setInteger("RunTimeTotal", this.totalRunTime);
		compound.setInteger("MachineType", getMachineType().ordinal());
		ItemStackHelper.saveAllItems(compound, itemstacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be
	 * 64, possibly will be extended.
	 */
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	/**
	 * Like the old updateEntity(), except more generic.
	 */
	@Override
	public void update() {
		boolean flag1 = false;

		if (!this.world.isRemote) {

			if (this.getMachineType() == MachineType.REFINERY) {
				if (this.getWorld().getBlockState(getPos()).getValue(BlockRefinery.ACTIVE) != isWorking()) {
					this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockRefinery.ACTIVE, isWorking()));
				}
			}

			if (!this.itemstacks.get(0).isEmpty()) {
				if (this.canChange()) {
					flag1 = true;

					if (!this.itemstacks.get(0).isEmpty() && this.runTime == this.totalRunTime) {
						this.itemstacks.get(0).shrink(1);

						if (this.itemstacks.get(0).getCount() == 0) {
							this.itemstacks.set(0, itemstacks.get(0).getItem().getContainerItem(itemstacks.get(0)));
						}
					}
				}

				if (this.canChange()) {
					++this.runTime;

					if (this.runTime == this.totalRunTime) {
						this.runTime = 0;
						this.totalRunTime = this.getCookTime(this.itemstacks.get(0));
						this.changeItem();
						flag1 = true;
					}
				} else {
					this.runTime = 0;
				}
			}
		}

		if (flag1) {
			this.markDirty();
		}
	}

	public int getCookTime(ItemStack stack) {
		return 200;
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item,
	 * destination stack isn't full, etc.
	 */
	private boolean canChange() {
		if (this.itemstacks.get(0).isEmpty()) {
			return false;
		} else {
			ItemStack itemstack = MachineRecipes.INSTANCE.getResult(this.itemstacks.get(0), machineType);
			if (itemstack.isEmpty())
				return false;
			if (this.itemstacks.get(1).isEmpty())
				return true;
			if (!this.itemstacks.get(1).isItemEqual(itemstack))
				return false;
			int result = itemstacks.get(1).getCount() + itemstack.getCount();
			return result <= getInventoryStackLimit() && result <= this.itemstacks.get(1).getMaxStackSize();
		}
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted
	 * item in the furnace result stack
	 */
	public void changeItem() {
		if (this.canChange()) {
			ItemStack itemstack = MachineRecipes.INSTANCE.getResult(this.itemstacks.get(0), machineType);

			if (this.itemstacks.get(1).isEmpty()) {
				this.itemstacks.set(1, itemstack.copy());
			} else if (this.itemstacks.get(1).getItem() == itemstack.getItem()) {
				this.itemstacks.get(1).setCount(this.itemstacks.get(1).getCount() + itemstack.getCount());
			}

			this.itemstacks.get(0).shrink(1);

			if (this.itemstacks.get(0).getCount() <= 0) {
				this.itemstacks.set(0, ItemStack.EMPTY);
			}
		}
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes
	 * with Container
	 */
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

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring
	 * stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 1 ? false : true;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
	}

	/**
	 * Returns true if automation can insert the given item in the given slot
	 * from the given side. Args: slot, item, side
	 */
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot
	 * from the given side. Args: slot, item, side
	 */
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if (direction == EnumFacing.DOWN && index == 1) {
			Item item = stack.getItem();

			if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String getGuiID() {
		return "grimindustry:" + (getMachineType() == MachineType.DERRICK ? "derrick" : "refinery");
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return getMachineType() == MachineType.DERRICK ? new ContainerDerrick(playerInventory, this) : new ContainerRefinery(playerInventory, this);
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case 0:
			return this.runTime;
		case 1:
			return this.totalRunTime;
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.runTime = value;
			break;
		case 1:
			this.totalRunTime = value;
		}
	}

	@Override
	public int getFieldCount() {
		return 2;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.itemstacks.size(); ++i) {
			this.itemstacks.set(i, ItemStack.EMPTY);
		}
	}

	public void setCustomInventoryName(String name) {
		this.customName = name;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return getMachineType() == MachineType.REFINERY ? oldState.getBlock() != newState.getBlock() : super.shouldRefresh(world, pos, oldState, newState);
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

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.itemstacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}
}
