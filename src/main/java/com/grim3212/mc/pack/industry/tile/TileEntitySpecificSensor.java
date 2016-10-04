package com.grim3212.mc.pack.industry.tile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.grim3212.mc.pack.industry.block.BlockSpecificSensor;
import com.grim3212.mc.pack.industry.inventory.ContainerSpecificSensor;
import com.grim3212.mc.pack.industry.util.Specific;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntitySpecificSensor extends TileEntityLockable implements ITickable {

	private BlockPos sensorPos;
	private SensorMode mode;
	private Specific specific;
	private String customName;
	private boolean goodPosition = false;

	public TileEntitySpecificSensor() {
		this.mode = SensorMode.PLAYER;
		this.specific = new Specific();
	}

	@Override
	public void update() {
		// Check to make sure we are not checking a pos that is already taken
		if (sensorPos == null || sensorPos == this.getPos())
			return;

		IBlockState state = this.getWorld().getBlockState(sensorPos);

		// Make sure that the position is not occupied by a block entities can't
		// enter. So full cubes or opaque cubes
		if (state.isFullCube() || state.isOpaqueCube()) {
			goodPosition = false;
			return;
		} else {
			goodPosition = true;
		}

		List<Entity> entities = this.getWorld().getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F).offset(sensorPos));

		IBlockState self = this.getWorld().getBlockState(pos);

		if (self.getBlock() instanceof BlockSpecificSensor) {
			if (mode == SensorMode.ITEM) {
				if (checkItems(entities)) {
					if (!self.getValue(BlockSpecificSensor.ACTIVE)) {
						this.getWorld().setBlockState(pos, self.withProperty(BlockSpecificSensor.ACTIVE, true));
					}
				} else {
					if (self.getValue(BlockSpecificSensor.ACTIVE)) {
						this.getWorld().setBlockState(pos, self.withProperty(BlockSpecificSensor.ACTIVE, false));
					}
				}
			} else if (mode == SensorMode.MOB) {
				if (checkMobs(entities)) {
					if (!self.getValue(BlockSpecificSensor.ACTIVE)) {
						this.getWorld().setBlockState(pos, self.withProperty(BlockSpecificSensor.ACTIVE, true));
					}
				} else {
					if (self.getValue(BlockSpecificSensor.ACTIVE)) {
						this.getWorld().setBlockState(pos, self.withProperty(BlockSpecificSensor.ACTIVE, false));
					}
				}
			} else if (mode == SensorMode.PLAYER) {
				if (checkPlayer(entities)) {
					if (!self.getValue(BlockSpecificSensor.ACTIVE)) {
						this.getWorld().setBlockState(pos, self.withProperty(BlockSpecificSensor.ACTIVE, true));
					}
				} else {
					if (self.getValue(BlockSpecificSensor.ACTIVE)) {
						this.getWorld().setBlockState(pos, self.withProperty(BlockSpecificSensor.ACTIVE, false));
					}
				}
			}
		}
	}

	private boolean checkPlayer(List<Entity> list) {
		Iterator<Entity> itr = list.iterator();
		List<EntityPlayer> compatibleEntities = new ArrayList<EntityPlayer>();

		while (itr.hasNext()) {
			Entity e = itr.next();
			if (e instanceof EntityPlayer) {
				compatibleEntities.add((EntityPlayer) e);
			}
		}

		if (compatibleEntities.size() > 0) {
			if (this.specific.hasSpecific(mode)) {
				for (int i = 0; i < compatibleEntities.size(); i++) {
					if (compatibleEntities.get(i).getGameProfile().getName() != null) {
						if (compatibleEntities.get(i).getGameProfile().getName().equalsIgnoreCase(this.specific.getPlayerName())) {
							return true;
						}
					}
				}
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean checkMobs(List<Entity> list) {
		Iterator<Entity> itr = list.iterator();
		List<EntityLivingBase> compatibleEntities = new ArrayList<EntityLivingBase>();

		while (itr.hasNext()) {
			Entity e = itr.next();
			if (e instanceof EntityLivingBase) {
				compatibleEntities.add((EntityLivingBase) e);
			}
		}

		if (compatibleEntities.size() > 0) {
			if (this.specific.hasSpecific(mode)) {
				for (int i = 0; i < compatibleEntities.size(); i++) {
					if (EntityList.isStringEntityName(compatibleEntities.get(i), this.specific.getEntityName())) {
						return true;
					}
				}
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean checkItems(List<Entity> list) {
		Iterator<Entity> itr = list.iterator();
		List<EntityItem> compatibleEntities = new ArrayList<EntityItem>();

		while (itr.hasNext()) {
			Entity e = itr.next();
			if (e instanceof EntityItem) {
				compatibleEntities.add((EntityItem) e);
			}
		}

		if (compatibleEntities.size() > 0) {
			if (this.specific.hasSpecific(mode)) {
				for (int i = 0; i < compatibleEntities.size(); i++) {
					ItemStack stack = compatibleEntities.get(i).getEntityItem();
					if (ItemStack.areItemStacksEqual(stack, this.specific.getStack())) {
						return true;
					}
				}
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		sensorPos = new BlockPos(compound.getInteger("SensorPosX"), compound.getInteger("SensorPosY"), compound.getInteger("SensorPosZ"));
		mode = SensorMode.valueOf(compound.getString("Mode"));

		specific.readFromNBT(compound);

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		if (sensorPos == null) {
			compound.setInteger("SensorPosX", this.getPos().getX());
			compound.setInteger("SensorPosY", this.getPos().getY());
			compound.setInteger("SensorPosZ", this.getPos().getZ());
		} else {
			compound.setInteger("SensorPosX", sensorPos.getX());
			compound.setInteger("SensorPosY", sensorPos.getY());
			compound.setInteger("SensorPosZ", sensorPos.getZ());
		}
		compound.setString("Mode", mode.name());

		specific.writeToNBT(compound);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}

	public boolean isGoodPosition() {
		return goodPosition;
	}

	public void setMode(SensorMode mode) {
		this.mode = mode;
	}

	public SensorMode getMode() {
		return mode;
	}

	public void setSensorPos(BlockPos sensorPos) {
		this.sensorPos = sensorPos;
	}

	public BlockPos getSensorPos() {
		return sensorPos;
	}

	public void setSpecific(Specific specific) {
		this.specific = specific;
	}

	public Specific getSpecific() {
		return specific;
	}

	public enum SensorMode implements IStringSerializable {
		PLAYER, MOB, ITEM;

		public static SensorMode[] VALUES = SensorMode.values();

		public SensorMode getNext() {
			return VALUES[(this.ordinal() + 1) % VALUES.length];
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerSpecificSensor(this.getPos(), playerInventory);
	}

	@Override
	public String getGuiID() {
		return "grimindustry:specific_sensor";
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.specific_sensor";
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomInventoryName(String name) {
		this.customName = name;
	}

	@Override
	public int getSizeInventory() {
		return 64;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
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
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
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
