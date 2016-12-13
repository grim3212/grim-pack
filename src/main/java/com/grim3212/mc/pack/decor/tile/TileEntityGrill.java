package com.grim3212.mc.pack.decor.tile;

import javax.annotation.Nonnull;

import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.decor.block.BlockGrill;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.inventory.ContainerGrill;
import com.grim3212.mc.pack.decor.network.MessageParticles;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileEntityGrill extends TileEntityColorizer implements ITickable, IInventory, IInteractionObject, ILockableContainer {

	private LockCode code = LockCode.EMPTY_CODE;
	public NonNullList<ItemStack> inventory = NonNullList.withSize(5, ItemStack.EMPTY);
	public int[] cookTimes = new int[4];
	public int grillCoal = 0;
	private String customName;
	private int nextUpdate = 0;

	public TileEntityGrill() {
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
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
		if (this.code != null) {
			this.code.toNBT(compound);
		}

		compound.setInteger("GrillCoal", this.grillCoal);
		compound.setInteger("CookTimes0", this.cookTimes[0]);
		compound.setInteger("CookTimes1", this.cookTimes[1]);
		compound.setInteger("CookTimes2", this.cookTimes[2]);
		compound.setInteger("CookTimes3", this.cookTimes[3]);

		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.inventory.size(); i++) {
			if (!this.inventory.get(i).isEmpty()) {
				NBTTagCompound itemCompound = new NBTTagCompound();
				itemCompound.setByte("Slot", (byte) i);
				this.inventory.get(i).writeToNBT(itemCompound);
				tagList.appendTag(itemCompound);
			}
		}

		compound.setTag("Items", tagList);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.code = LockCode.fromNBT(compound);

		this.grillCoal = compound.getInteger("GrillCoal");
		this.cookTimes[0] = compound.getInteger("CookTimes0");
		this.cookTimes[1] = compound.getInteger("CookTimes1");
		this.cookTimes[2] = compound.getInteger("CookTimes2");
		this.cookTimes[3] = compound.getInteger("CookTimes3");

		NBTTagList tagList = compound.getTagList("Items", 10);
		this.inventory = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);

		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound itemCompound = (NBTTagCompound) tagList.getCompoundTagAt(i);
			byte slot = itemCompound.getByte("Slot");

			if ((slot >= 0) && (slot < this.inventory.size())) {
				this.inventory.set(slot, new ItemStack(itemCompound));
			}
		}

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}
	}

	@Override
	public void update() {
		if (this.nextUpdate <= 0) {
			this.nextUpdate = 50;
		} else {
			this.nextUpdate -= 1;
		}

		if (DecorConfig.infiniteGrillFuel)
			this.grillCoal = 4000;

		if ((this.grillCoal <= 1) && (getWorld().getBlockState(getPos()).getValue(BlockGrill.ACTIVE))) {
			if (!getStackInSlot(4).isEmpty() && (getStackInSlot(4).getItem() == Items.COAL)) {
				this.grillCoal = 4001;

				if (getStackInSlot(4).getCount() > 1) {
					getStackInSlot(4).shrink(1);
				} else
					setInventorySlotContents(4, ItemStack.EMPTY);
			}
		}

		if ((this.grillCoal <= 0) && (getWorld().getBlockState(getPos()).getValue(BlockGrill.ACTIVE)) && this.nextUpdate == 50) {
			if (!world.isRemote) {
				PacketDispatcher.sendToDimension(new MessageParticles(pos), world.provider.getDimension());
				world.setBlockState(getPos(), getWorld().getBlockState(getPos()).withProperty(BlockGrill.ACTIVE, false));
			}
			world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.4F + 0.8F, false);
		}

		if (isGrillBurning()) {
			this.grillCoal -= 1;

			int tiertime = (int) getTierTime();

			for (int i = 0; i < 4; i++) {
				if (!getStackInSlot(i).isEmpty() && (DecorConfig.grillRecipes.keySet().contains(getStackInSlot(i).getItem()))) {
					this.cookTimes[i] += 1;

					if (this.cookTimes[i] > tiertime) {
						this.inventory.set(i, new ItemStack((DecorConfig.grillRecipes.get(this.inventory.get(i).getItem()))));
						this.cookTimes[i] = 0;
					} else {
						this.cookTimes[i] += 1;
					}
				} else {
					this.cookTimes[i] = 0;
				}
			}
		} else {
			for (int i = 0; i < 4; i++) {
				if (this.cookTimes[i] > 0) {
					this.cookTimes[i] -= this.world.rand.nextInt(2);
				}
			}
		}
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.grill";
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.size();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (!this.inventory.get(index).isEmpty()) {
			if (this.inventory.get(index).getCount() <= count) {
				ItemStack stack = this.inventory.get(index);
				this.inventory.set(index, ItemStack.EMPTY);
				return stack;
			}

			ItemStack stack = this.inventory.get(index).splitStack(count);

			if (this.inventory.get(index).getCount() == 0) {
				this.inventory.set(index, ItemStack.EMPTY);
			}

			return stack;
		}

		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
		this.inventory.set(index, stack);
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

	public boolean isGrillBurning() {
		if (getWorld().getBlockState(getPos()).getValue(BlockGrill.ACTIVE) && (this.grillCoal > 0))
			return true;
		return false;
	}

	public int getTier() {
		IBlockState grillType = this.getBlockState();

		if (grillType.getBlock() == Blocks.DIAMOND_BLOCK || grillType.getBlock() == Blocks.EMERALD_BLOCK) {
			return 6;
		} else if (grillType.getMaterial() == Material.IRON) {
			return 5;
		} else if (grillType.getBlock() == Blocks.OBSIDIAN || grillType.getBlock() == Blocks.END_STONE || grillType.getBlock() == Blocks.LAPIS_BLOCK) {
			return 4;
		} else if (grillType.getMaterial() == Material.ROCK) {
			return 3;
		} else if (grillType.getMaterial() == Material.SAND) {
			return 1;
		}
		return 2;
	}

	public float getTierTime() {
		return 1000 + (6 - getTier()) * 500;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case 0:
			return this.grillCoal;
		case 1:
			return this.cookTimes[0];
		case 2:
			return this.cookTimes[1];
		case 3:
			return this.cookTimes[2];
		case 4:
			return this.cookTimes[3];
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.grillCoal = value;
			break;
		case 1:
			this.cookTimes[0] = value;
			break;
		case 2:
			this.cookTimes[1] = value;
			break;
		case 3:
			this.cookTimes[2] = value;
			break;
		case 4:
			this.cookTimes[3] = value;
			break;
		}
	}

	@Override
	public int getFieldCount() {
		return 5;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.inventory.size(); ++i) {
			this.inventory.set(i, ItemStack.EMPTY);
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

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerGrill(playerInventory, this);
	}

	@Override
	public String getGuiID() {
		return "fireplaces:grill";
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public boolean isLocked() {
		return this.code != null && !this.code.isEmpty();
	}

	@Override
	public LockCode getLockCode() {
		return this.code;
	}

	@Override
	public void setLockCode(LockCode code) {
		this.code = code;
	}

	/**
	 * Get the formatted ChatComponent that will be used for the sender's
	 * username in chat
	 */
	@Override
	public ITextComponent getDisplayName() {
		return (ITextComponent) (this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
	}

	private IItemHandler itemHandler;

	protected IItemHandler createUnSidedHandler() {
		return new InvWrapper(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, net.minecraft.util.EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) (itemHandler == null ? (itemHandler = createUnSidedHandler()) : itemHandler);
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, net.minecraft.util.EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.inventory) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}
}