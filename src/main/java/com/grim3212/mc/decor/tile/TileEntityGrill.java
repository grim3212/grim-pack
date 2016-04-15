package com.grim3212.mc.decor.tile;

import com.grim3212.mc.core.network.PacketDispatcher;
import com.grim3212.mc.decor.config.DecorConfig;
import com.grim3212.mc.decor.inventory.ContainerGrill;
import com.grim3212.mc.decor.network.MessageExtinguish;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;

public class TileEntityGrill extends TileEntityLockable implements ITickable, IInventory {

	public ItemStack[] inventory = new ItemStack[5];
	public int[] cookTimes = new int[4];
	public int grillCoal = 0;
	private String customName;
	private int nextUpdate = 0;

	protected int blockID = 0;
	protected int blockMeta = 0;
	protected boolean active = false;

	public TileEntityGrill() {
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("blockID", this.blockID);
		compound.setInteger("blockMeta", this.blockMeta);
		compound.setBoolean("active", this.active);

		compound.setInteger("GrillCoal", this.grillCoal);
		compound.setInteger("CookTimes0", this.cookTimes[0]);
		compound.setInteger("CookTimes1", this.cookTimes[1]);
		compound.setInteger("CookTimes2", this.cookTimes[2]);
		compound.setInteger("CookTimes3", this.cookTimes[3]);

		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.inventory.length; var3++) {
			if (this.inventory[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				this.inventory[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		compound.setTag("Items", var2);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.blockID = compound.getInteger("blockID");
		this.blockMeta = compound.getInteger("blockMeta");
		this.active = compound.getBoolean("active");

		this.grillCoal = compound.getInteger("GrillCoal");
		this.cookTimes[0] = compound.getInteger("CookTimes0");
		this.cookTimes[1] = compound.getInteger("CookTimes1");
		this.cookTimes[2] = compound.getInteger("CookTimes2");
		this.cookTimes[3] = compound.getInteger("CookTimes3");

		NBTTagList var2 = compound.getTagList("Items", 10);
		this.inventory = new ItemStack[getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); var3++) {
			NBTTagCompound var4 = (NBTTagCompound) var2.getCompoundTagAt(var3);
			byte var5 = var4.getByte("Slot");

			if ((var5 >= 0) && (var5 < this.inventory.length)) {
				this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
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

		if ((this.grillCoal <= 1) && (isActive())) {
			if ((getStackInSlot(4) != null) && (getStackInSlot(4).getItem() == Items.coal)) {
				this.grillCoal = 4001;

				if (getStackInSlot(4).stackSize > 1) {
					getStackInSlot(4).stackSize -= 1;
				} else
					setInventorySlotContents(4, null);
			}
		}

		if ((this.grillCoal <= 0) && (isActive()) && this.nextUpdate == 50) {
			if (!worldObj.isRemote) {
				PacketDispatcher.sendToDimension(new MessageExtinguish(pos), worldObj.provider.getDimensionId());
				setActive(false);
			}
			worldObj.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.fizz", 1.0F, worldObj.rand.nextFloat() * 0.4F + 0.8F);
		}

		if (isGrillBurning()) {
			this.grillCoal -= 1;

			int tiertime = (int) getTierTime();

			for (int i = 0; i < 4; i++) {
				if ((getStackInSlot(i) != null) && (DecorConfig.grillRecipes.keySet().contains(getStackInSlot(i).getItem()))) {
					this.cookTimes[i] += 1;

					if (this.cookTimes[i] > tiertime) {
						this.inventory[i] = new ItemStack((DecorConfig.grillRecipes.get(this.inventory[i].getItem())));
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
					this.cookTimes[i] -= this.worldObj.rand.nextInt(2);
				}
			}
		}
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "fireplaces.grill";
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
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.inventory[index] != null) {
			if (this.inventory[index].stackSize <= count) {
				ItemStack var3 = this.inventory[index];
				this.inventory[index] = null;
				return var3;
			}

			ItemStack var3 = this.inventory[index].splitStack(count);

			if (this.inventory[index].stackSize == 0) {
				this.inventory[index] = null;
			}

			return var3;
		}

		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.inventory[index] = stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
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

	public boolean isGrillBurning() {
		if (isActive() && (this.grillCoal > 0))
			return true;
		return false;
	}

	public int getTier() {
		IBlockState grillType = Block.getBlockById(blockID).getStateFromMeta(blockMeta);

		if (grillType.getBlock() == Blocks.diamond_block || grillType.getBlock() == Blocks.emerald_block) {
			return 6;
		} else if (grillType.getBlock().getMaterial() == Material.iron) {
			return 5;
		} else if (grillType.getBlock() == Blocks.obsidian || grillType.getBlock() == Blocks.end_stone || grillType.getBlock() == Blocks.lapis_block) {
			return 4;
		} else if (grillType.getBlock().getMaterial() == Material.rock) {
			return 3;
		} else if (grillType.getBlock().getMaterial() == Material.sand) {
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
		for (int i = 0; i < this.inventory.length; ++i) {
			this.inventory[i] = null;
		}
	}

	@Override
	public Packet<INetHandlerPlayClient> getDescriptionPacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return new S35PacketUpdateTileEntity(this.pos, 1, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	public int getBlockID() {
		return blockID;
	}

	public void setBlockID(int blockID) {
		this.blockID = blockID;
	}

	public int getBlockMeta() {
		return blockMeta;
	}

	public void setBlockMeta(int blockMeta) {
		this.blockMeta = blockMeta;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		worldObj.markBlockForUpdate(getPos());
		worldObj.notifyNeighborsOfStateChange(getPos(), blockType);
		worldObj.notifyLightSet(getPos());
		worldObj.checkLight(getPos());
	}

	public int getLightValue() {
		return isActive() ? 15 : 0;
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
}