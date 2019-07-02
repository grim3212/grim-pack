package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.industry.block.storage.BlockStorage;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.LockCode;

public abstract class TileEntityStorage extends LockableTileEntity implements ITickable, ISidedInventory {

	private final int[] slots;
	private String customName;
	public int rotation = 0;
	private boolean direction = false;
	// private boolean isOpen = false;
	public int numPlayersUsing = 0;
	// private int ticksSinceSync;
	protected final NonNullList<ItemStack> itemstacks;

	/**
	 * Our Animation state controller. We can use this to, for example, query
	 * the current state, or transition to a different state.
	 *
	 * This is assigned through the proxy and is null server side.
	 */
	// protected final IAnimationStateMachine asm;

	/**
	 * Here we define our variables we wish to act as parameters in the state
	 * machine. These get mapped to parameters that we can access in the asm
	 * json files. Use these to trigger events or transition to a different
	 * state. The values are set through code. See {@link #click()} for an
	 * example.
	 */
	// private final VariableValue clickTime = new
	// VariableValue(Float.NEGATIVE_INFINITY);

	public TileEntityStorage() {
		this.itemstacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		this.slots = new int[getSizeInventory()];
		/**
		 * This is loaded through the proxy. Server side returns null. Here we
		 * also map {@link VariableValue} from above that we wish to access from
		 * the asm json file. See
		 * {@link assets.grimpack.asms.block#warehouse_crate.json}
		 */
		// asm = GrimIndustry.proxy.load(getStateMachine(),
		// ImmutableMap.<String, ITimeValue>of("click_time", clickTime));
	}

	// protected abstract ResourceLocation getStateMachine();

	protected abstract String getStorageName();

	public abstract BlockState getBreakTextureState();

	@Override
	public boolean canRenderBreaking() {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public Container createContainer(PlayerInventory playerInventory, PlayerEntity playerIn) {
		return new ChestContainer(playerInventory, this, playerIn);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return !this.isLocked();
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return this.slots;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
		return true;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		return !this.isLocked();
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

	public SoundEvent getLockedSound() {
		return SoundEvents.BLOCK_CHEST_LOCKED;
	}

	@Override
	public void update() {
		boolean prevdirection = this.direction;
		this.direction = (this.numPlayersUsing > 0);

		if ((!prevdirection) && (this.direction) && getOpenSound() != null)
			this.world.playSound((PlayerEntity) null, pos.getX(), (double) pos.getY() + 0.5D, pos.getZ(), getOpenSound(), SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);

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

		if ((prevrotation > this.rotation) && (this.rotation == 0) && getCloseSound() != null)
			this.world.playSound((PlayerEntity) null, pos.getX(), (double) pos.getY() + 0.5D, pos.getZ(), getCloseSound(), SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);

		/**
		 * if (this.numPlayersUsing > 0 && !this.isOpen) {
		 * 
		 * if (world.isRemote) { this.tryAnimate(true); }
		 * 
		 * this.isOpen = true; this.world.playSound((EntityPlayer) null,
		 * pos.getX(), (double) pos.getY() + 0.5D, pos.getZ(), getOpenSound(),
		 * SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F +
		 * 0.9F); }
		 * 
		 * if (this.numPlayersUsing == 0 && this.isOpen) {
		 * 
		 * if (world.isRemote) { this.tryAnimate(false); }
		 * 
		 * this.isOpen = false; this.world.playSound((EntityPlayer) null,
		 * pos.getX(), (double) pos.getY() + 0.5D, pos.getZ(), getCloseSound(),
		 * SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F +
		 * 0.9F); }
		 * 
		 * ++this.ticksSinceSync; if (world.isRemote && this.numPlayersUsing <=
		 * 0 && this.ticksSinceSync % 100 == 0) { this.tryAnimate(false);
		 * this.ticksSinceSync = 0; }
		 **/
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
		if ((this.isLocked()) && (this.numPlayersUsing == 0))
			return ItemStack.EMPTY;
		return this.itemstacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if ((this.isLocked()) && (this.numPlayersUsing == 0))
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
		if ((this.isLocked()) && (this.numPlayersUsing == 0))
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
	public boolean isUsableByPlayer(PlayerEntity player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(PlayerEntity player) {
		if (!player.isSpectator()) {
			if (this.numPlayersUsing < 0) {
				this.numPlayersUsing = 0;
			}

			++this.numPlayersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
		}

	}

	@Override
	public void closeInventory(PlayerEntity player) {
		if (!player.isSpectator() && this.getBlockType() instanceof BlockStorage) {
			--this.numPlayersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
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
	public void readFromNBT(CompoundNBT compound) {
		super.readFromNBT(compound);
		ItemStackHelper.loadAllItems(compound, itemstacks);

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT compound) {
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
			this.numPlayersUsing = type;
			return true;
		}
		if (id == 2) {
			this.setLockCode(new LockCode(String.valueOf(type)));
			return true;
		}

		return super.receiveClientEvent(id, type);
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

	public NonNullList<ItemStack> getItems() {
		return itemstacks;
	}

	/**
	 * @Override public boolean hasCapability(Capability<?> capability,
	 *           EnumFacing facing) { if (capability ==
	 *           CapabilityAnimation.ANIMATION_CAPABILITY) { return true; }
	 * 
	 *           return super.hasCapability(capability, facing); }
	 * 
	 * @Override public <T> T getCapability(Capability<T> capability, EnumFacing
	 *           facing) { if (capability ==
	 *           CapabilityAnimation.ANIMATION_CAPABILITY) { return
	 *           CapabilityAnimation.ANIMATION_CAPABILITY.cast(asm); } return
	 *           super.getCapability(capability, facing); }
	 **/

	/**
	 * Event handler to process events triggered from animations. It gets called
	 * from your {@link AnimationTESR}. This is only called client side, so if
	 * your events trigger something more than special effects, you'll have to
	 * send a message back to the server. This could potentially be called each
	 * frame tick, so anything computationally intensive should probably be
	 * scheduled for a later update tick.
	 * 
	 * @param time
	 *            The global world time for the current tick + partial tick
	 *            progress, in seconds.
	 * @param pastEvents
	 *            List of events that were triggered since last tick.
	 */
	// public void handleEvents(float time, Iterable<Event> pastEvents) {}

	/**
	 * Example method showing how to change states from code.
	 */
	/**
	 * public void tryAnimate(boolean open) { if (asm != null) { if
	 * (asm.currentState().equals("open") && !open) { float time =
	 * Animation.getWorldTime(getWorld(), Animation.getPartialTickTime());
	 * clickTime.setValue(time); asm.transition("closing"); } else if
	 * (asm.currentState().equals("closed") && open) { float time =
	 * Animation.getWorldTime(getWorld(), Animation.getPartialTickTime());
	 * clickTime.setValue(time); asm.transition("opening"); } } }
	 **/
}
