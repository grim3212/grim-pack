package com.grim3212.mc.industry.tile;

import com.grim3212.mc.industry.block.BlockRefinery;
import com.grim3212.mc.industry.inventory.ContainerMachine;
import com.grim3212.mc.industry.util.MachineRecipes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityMachine extends TileEntityLockable implements ISidedInventory, ITickable {

	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 1 };
	private static final int[] slotsSides = new int[] { 0 };
	/**
	 * The ItemStacks that hold the items currently being used in the inventory
	 */
	private ItemStack[] itemstacks = new ItemStack[2];

	private int runTime;
	private int totalRunTime;
	private String customName;
	private int machineType;

	public TileEntityMachine(int machineType) {
		this.machineType = machineType;
	}

	public int getMachineType() {
		return machineType;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return this.itemstacks.length;
	}

	/**
	 * Returns the stack in the given slot.
	 */
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.itemstacks[index];
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
		if (this.itemstacks[index] != null) {
			if (this.itemstacks[index].stackSize <= count) {
				ItemStack itemstack1 = this.itemstacks[index];
				this.itemstacks[index] = null;
				return itemstack1;
			} else {
				ItemStack itemstack = this.itemstacks[index].splitStack(count);

				if (this.itemstacks[index].stackSize == 0) {
					this.itemstacks[index] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (this.itemstacks[index] != null) {
			ItemStack itemstack = this.itemstacks[index];
			this.itemstacks[index] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		boolean flag = stack != null && stack.isItemEqual(this.itemstacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.itemstacks[index]);
		this.itemstacks[index] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
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
		return this.hasCustomName() ? this.customName : "container." + (getMachineType() == 0 ? "derrick" : "refinery");
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
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.itemstacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");

			if (j >= 0 && j < this.itemstacks.length) {
				this.itemstacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		this.runTime = compound.getInteger("RunTime");
		this.totalRunTime = compound.getInteger("RunTimeTotal");
		this.machineType = compound.getInteger("MachineType");

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("RunTime", this.runTime);
		compound.setInteger("RunTimeTotal", this.totalRunTime);
		compound.setInteger("MachineType", getMachineType());
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.itemstacks.length; ++i) {
			if (this.itemstacks[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				this.itemstacks[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		compound.setTag("Items", nbttaglist);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}
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

		if (!this.worldObj.isRemote) {

			if (this.getMachineType() == 1) {
				if (this.getWorld().getBlockState(getPos()).getValue(BlockRefinery.ACTIVE) != isWorking()) {
					this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockRefinery.ACTIVE, isWorking()));
				}
			}

			if (this.itemstacks[0] != null) {
				if (this.canChange()) {
					flag1 = true;

					if (this.itemstacks[0] != null && this.runTime == this.totalRunTime) {
						--this.itemstacks[0].stackSize;

						if (this.itemstacks[0].stackSize == 0) {
							this.itemstacks[0] = itemstacks[0].getItem().getContainerItem(itemstacks[0]);
						}
					}
				}

				if (this.canChange()) {
					++this.runTime;

					if (this.runTime == this.totalRunTime) {
						this.runTime = 0;
						this.totalRunTime = this.getCookTime(this.itemstacks[0]);
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
		if (this.itemstacks[0] == null) {
			return false;
		} else {
			ItemStack itemstack = MachineRecipes.INSTANCE.getResult(this.itemstacks[0], getMachineType() == 0 ? MachineRecipes.INSTANCE.getDerrickList() : MachineRecipes.INSTANCE.getRefineryList());
			if (itemstack == null)
				return false;
			if (this.itemstacks[1] == null)
				return true;
			if (!this.itemstacks[1].isItemEqual(itemstack))
				return false;
			int result = itemstacks[1].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.itemstacks[1].getMaxStackSize();
		}
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted
	 * item in the furnace result stack
	 */
	public void changeItem() {
		if (this.canChange()) {
			ItemStack itemstack = MachineRecipes.INSTANCE.getResult(this.itemstacks[0], getMachineType() == 0 ? MachineRecipes.INSTANCE.getDerrickList() : MachineRecipes.INSTANCE.getRefineryList());

			if (this.itemstacks[1] == null) {
				this.itemstacks[1] = itemstack.copy();
			} else if (this.itemstacks[1].getItem() == itemstack.getItem()) {
				this.itemstacks[1].stackSize += itemstack.stackSize;
			}

			--this.itemstacks[0].stackSize;

			if (this.itemstacks[0].stackSize <= 0) {
				this.itemstacks[0] = null;
			}
		}
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes
	 * with Container
	 */
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

			if (item != Items.water_bucket && item != Items.bucket) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String getGuiID() {
		return "grimindustry:" + (getMachineType() == 0 ? "derrick" : "refinery");
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerMachine(playerInventory, this);
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
		for (int i = 0; i < this.itemstacks.length; ++i) {
			this.itemstacks[i] = null;
		}
	}

	public void setCustomInventoryName(String name) {
		this.customName = name;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return getMachineType() == 1 ? oldState.getBlock() != newState.getBlock() : super.shouldRefresh(world, pos, oldState, newState);
	}
}
