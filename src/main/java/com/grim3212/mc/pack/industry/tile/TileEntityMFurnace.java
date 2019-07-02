package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.industry.block.BlockModernFurnace;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.inventory.ContainerMFurnace;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.util.MachineRecipes;
import com.grim3212.mc.pack.industry.util.MachineRecipes.MachineType;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMFurnace extends LockableTileEntity implements ITickable, ISidedInventory {

	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };
	/**
	 * The ItemStacks that hold the items currently being used in the furnace
	 */
	private NonNullList<ItemStack> furnaceItemStacks = NonNullList.withSize(3, ItemStack.EMPTY);
	/** The number of ticks that the furnace will keep burning */
	private int furnaceBurnTime;
	/**
	 * The number of ticks that a fresh copy of the currently-burning item would
	 * keep the furnace burning for
	 */
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;
	private String furnaceCustomName;

	public TileEntityMFurnace() {
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return this.furnaceItemStacks.size();
	}

	/**
	 * Returns the stack in the given slot.
	 */
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.furnaceItemStacks.get(index);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and
	 * returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (!this.furnaceItemStacks.get(index).isEmpty()) {
			if (this.furnaceItemStacks.get(index).getCount() <= count) {
				ItemStack itemstack1 = this.furnaceItemStacks.get(index);
				this.furnaceItemStacks.set(index, ItemStack.EMPTY);
				return itemstack1;
			} else {
				ItemStack itemstack = this.furnaceItemStacks.get(index).splitStack(count);

				if (this.furnaceItemStacks.get(index).getCount() == 0) {
					this.furnaceItemStacks.set(index, ItemStack.EMPTY);
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
		if (!this.furnaceItemStacks.get(index).isEmpty()) {
			ItemStack itemstack = this.furnaceItemStacks.get(index);
			this.furnaceItemStacks.set(index, ItemStack.EMPTY);
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
		boolean flag = !stack.isEmpty() && stack.isItemEqual(this.furnaceItemStacks.get(index)) && ItemStack.areItemStackTagsEqual(stack, this.furnaceItemStacks.get(index));
		this.furnaceItemStacks.set(index, stack);

		if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.totalCookTime = this.getCookTime(stack);
			this.cookTime = 0;
			this.markDirty();
		}
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return this.hasCustomName() ? this.furnaceCustomName : "container.modern_furnace";
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName() {
		return this.furnaceCustomName != null && this.furnaceCustomName.length() > 0;
	}

	public void setCustomInventoryName(String name) {
		this.furnaceCustomName = name;
	}

	@Override
	public void readFromNBT(CompoundNBT compound) {
		super.readFromNBT(compound);
		this.furnaceItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);

		this.furnaceBurnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks.get(1));

		if (compound.hasKey("CustomName", 8)) {
			this.furnaceCustomName = compound.getString("CustomName");
		}
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT compound) {
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", this.furnaceBurnTime);
		compound.setInteger("CookTime", this.cookTime);
		compound.setInteger("CookTimeTotal", this.totalCookTime);
		ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.furnaceCustomName);
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
	 * Furnace isBurning
	 */
	public boolean isBurning() {
		return this.furnaceBurnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inv) {
		return inv.getField(0) > 0;
	}

	/**
	 * Like the old updateEntity(), except more generic.
	 */
	@Override
	public void update() {
		boolean flag = this.isBurning();
		boolean flag1 = false;

		if (this.isBurning()) {
			--this.furnaceBurnTime;
		}

		if (!this.world.isRemote) {
			if (this.isBurning() || !this.furnaceItemStacks.get(1).isEmpty() && !this.furnaceItemStacks.get(0).isEmpty()) {
				if (!this.isBurning() && this.canSmelt()) {
					this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks.get(1));

					if (this.isBurning()) {
						flag1 = true;

						if (!this.furnaceItemStacks.get(1).isEmpty()) {
							this.furnaceItemStacks.get(1).shrink(1);

							if (this.furnaceItemStacks.get(1).getCount() == 0) {
								this.furnaceItemStacks.set(1, furnaceItemStacks.get(1).getItem().getContainerItem(furnaceItemStacks.get(1)));
							}
						}
					}
				}

				if (this.isBurning() && this.canSmelt()) {
					++this.cookTime;

					if (this.cookTime == this.totalCookTime) {
						this.cookTime = 0;
						this.totalCookTime = this.getCookTime(this.furnaceItemStacks.get(0));
						this.smeltItem();
						flag1 = true;
					}
				} else {
					this.cookTime = 0;
				}
			} else if (!this.isBurning() && this.cookTime > 0) {
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
			}

			if (flag != this.isBurning()) {
				flag1 = true;
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockModernFurnace.ACTIVE, this.isBurning()), 2);
			}
		}

		if (flag1) {
			this.markDirty();
		}
	}

	/**
	 * Possibly end up making this variable
	 * 
	 * @param stack
	 *            Stack to check for
	 * @return How long it takes to smelt this stack
	 */
	public int getCookTime(ItemStack stack) {
		return 100;
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item,
	 * destination stack isn't full, etc.
	 */
	private boolean canSmelt() {
		if (this.furnaceItemStacks.get(0).isEmpty()) {
			return false;
		} else {
			ItemStack itemstack = checkItem();
			if (itemstack.isEmpty())
				return false;
			if (this.furnaceItemStacks.get(2).isEmpty())
				return true;
			if (!this.furnaceItemStacks.get(2).isItemEqual(itemstack))
				return false;
			int result = furnaceItemStacks.get(2).getCount() + itemstack.getCount();
			return result <= getInventoryStackLimit() && result <= this.furnaceItemStacks.get(2).getMaxStackSize();
		}
	}

	private ItemStack checkItem() {
		ItemStack itemstack = MachineRecipes.INSTANCE.getResult(this.furnaceItemStacks.get(0), MachineType.MODERN_FURNACE);
		if (itemstack.isEmpty()) {
			itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks.get(0));
		}
		return itemstack;
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted
	 * item in the furnace result stack
	 */
	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack itemstack = checkItem();

			if (this.furnaceItemStacks.get(2).isEmpty()) {
				this.furnaceItemStacks.set(2, itemstack.copy());
			} else if (this.furnaceItemStacks.get(2).getItem() == itemstack.getItem()) {
				this.furnaceItemStacks.get(2).setCount(this.furnaceItemStacks.get(2).getCount() + itemstack.getCount());
			}

			if (this.furnaceItemStacks.get(0).getItem() == Item.getItemFromBlock(Blocks.SPONGE) && this.furnaceItemStacks.get(0).getMetadata() == 1 && !this.furnaceItemStacks.get(1).isEmpty() && this.furnaceItemStacks.get(1).getItem() == Items.BUCKET) {
				this.furnaceItemStacks.set(1, new ItemStack(Items.WATER_BUCKET));
			}

			this.furnaceItemStacks.get(0).shrink(1);

			if (this.furnaceItemStacks.get(0).getCount() <= 0) {
				this.furnaceItemStacks.set(0, ItemStack.EMPTY);
			}
		}
	}

	/**
	 * Returns the number of ticks that the supplied fuel item will keep the
	 * furnace burning, or 0 if the item isn't fuel
	 */
	public static int getItemBurnTime(ItemStack stack) {
		if (stack.isEmpty()) {
			return 0;
		} else {
			Item item = stack.getItem();

			if (item == IndustryItems.fuel) {
				return 25000;
			} else if (item == Item.getItemFromBlock(IndustryBlocks.fuel_tank)) {
				return (25000 * 9);
			}
			return 0;
		}
	}

	public static boolean isItemFuel(ItemStack stack) {
		return getItemBurnTime(stack) > 0;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes
	 * with Container
	 */
	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(PlayerEntity player) {
	}

	@Override
	public void closeInventory(PlayerEntity player) {
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring
	 * stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 2 ? false : (index != 1 ? true : isItemFuel(stack));
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return side == Direction.DOWN ? slotsBottom : (side == Direction.UP ? slotsTop : slotsSides);
	}

	/**
	 * Returns true if automation can insert the given item in the given slot
	 * from the given side. Args: slot, item, side
	 */
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot
	 * from the given side. Args: slot, item, side
	 */
	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		if (direction == Direction.DOWN && index == 1) {
			Item item = stack.getItem();

			if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String getGuiID() {
		return "grimindustry:modern_furnace";
	}

	@Override
	public Container createContainer(PlayerInventory playerInventory, PlayerEntity playerIn) {
		return new ContainerMFurnace(playerInventory, this);
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case 0:
			return this.furnaceBurnTime;
		case 1:
			return this.currentItemBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		default:
			return 0;
		}
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.furnaceBurnTime = value;
			break;
		case 1:
			this.currentItemBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
		}
	}

	@Override
	public int getFieldCount() {
		return 4;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.furnaceItemStacks.size(); ++i) {
			this.furnaceItemStacks.set(i, ItemStack.EMPTY);
		}
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbtTagCompound = new CompoundNBT();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SUpdateTileEntityPacket(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return writeToNBT(new CompoundNBT());
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		readFromNBT(tag);
	}

	net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, Direction.UP);
	net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, Direction.DOWN);
	net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, Direction.WEST);

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, Direction facing) {
		if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (facing == Direction.DOWN)
				return (T) handlerBottom;
			else if (facing == Direction.UP)
				return (T) handlerTop;
			else
				return (T) handlerSide;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.furnaceItemStacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}
}
