package com.grim3212.mc.pack.industry.tile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.grim3212.mc.pack.industry.block.BlockSpecificSensor;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.inventory.ContainerSpecificSensor;
import com.grim3212.mc.pack.industry.util.Specific;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntitySpecificSensor extends LockableTileEntity implements ITickable {

	private BlockPos sensorPos;
	private SensorMode mode;
	private Specific specific;
	private String customName;
	private boolean goodPosition = false;
	private boolean renderSensorPos;
	private AxisAlignedBB senseBox;

	public TileEntitySpecificSensor() {
		this.mode = SensorMode.PLAYER;
		this.specific = new Specific();
		this.senseBox = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if (this.senseBox != null)
			return super.getRenderBoundingBox().union(senseBox);
		return super.getRenderBoundingBox();
	}

	@Override
	public void update() {
		// Check to make sure we are not checking a pos that is already taken
		if (sensorPos == null)
			return;

		BlockState state = this.getWorld().getBlockState(sensorPos);
		BlockState self = this.getWorld().getBlockState(pos);

		// Make sure that the position is not occupied by a block entities can't
		// enter. So full cubes or opaque cubes
		if (state.isFullCube() || state.isOpaqueCube() || (this.getBlockType() == IndustryBlocks.specific_sensor ? sensorPos.distanceSq(getPos()) > 48 : sensorPos.distanceSq(getPos()) > 112)) {
			goodPosition = false;
			if (self.getValue(BlockSpecificSensor.ACTIVE)) {
				this.getWorld().setBlockState(pos, self.withProperty(BlockSpecificSensor.ACTIVE, false));
			}
			return;
		} else {
			goodPosition = true;
		}

		List<Entity> entities = this.getWorld().getEntitiesWithinAABB(Entity.class, this.senseBox.offset(sensorPos));

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
		List<PlayerEntity> compatibleEntities = new ArrayList<PlayerEntity>();

		while (itr.hasNext()) {
			Entity e = itr.next();
			if (e instanceof PlayerEntity) {
				compatibleEntities.add((PlayerEntity) e);
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
		List<LivingEntity> compatibleEntities = new ArrayList<LivingEntity>();

		while (itr.hasNext()) {
			Entity e = itr.next();
			if (e instanceof LivingEntity) {
				compatibleEntities.add((LivingEntity) e);
			}
		}

		if (compatibleEntities.size() > 0) {
			if (this.specific.hasSpecific(mode)) {
				for (int i = 0; i < compatibleEntities.size(); i++) {
					if (EntityList.isMatchingName(compatibleEntities.get(i), new ResourceLocation(this.specific.getEntityName()))) {
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
		List<ItemEntity> compatibleEntities = new ArrayList<ItemEntity>();

		while (itr.hasNext()) {
			Entity e = itr.next();
			if (e instanceof ItemEntity) {
				compatibleEntities.add((ItemEntity) e);
			}
		}

		if (compatibleEntities.size() > 0) {
			if (this.specific.hasSpecific(mode)) {
				for (int i = 0; i < compatibleEntities.size(); i++) {
					ItemStack stack = compatibleEntities.get(i).getItem();
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
	public void readFromNBT(CompoundNBT compound) {
		super.readFromNBT(compound);

		sensorPos = new BlockPos(compound.getInteger("SensorPosX"), compound.getInteger("SensorPosY"), compound.getInteger("SensorPosZ"));
		mode = SensorMode.valueOf(compound.getString("Mode"));
		renderSensorPos = compound.getBoolean("RenderPos");

		specific.readFromNBT(compound);

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}

		CompoundNBT boundingBox = compound.getCompoundTag("SenseBox");
		this.senseBox = new AxisAlignedBB(boundingBox.getDouble("MinX"), boundingBox.getDouble("MinY"), boundingBox.getDouble("MinZ"), boundingBox.getDouble("MaxX"), boundingBox.getDouble("MaxY"), boundingBox.getDouble("MaxZ"));
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT compound) {
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
		compound.setBoolean("RenderPos", renderSensorPos);

		specific.writeToNBT(compound);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		CompoundNBT boundingBox = new CompoundNBT();
		boundingBox.setDouble("MinX", this.senseBox.minX);
		boundingBox.setDouble("MinY", this.senseBox.minY);
		boundingBox.setDouble("MinZ", this.senseBox.minZ);
		boundingBox.setDouble("MaxX", this.senseBox.maxX);
		boundingBox.setDouble("MaxY", this.senseBox.maxY);
		boundingBox.setDouble("MaxZ", this.senseBox.maxZ);
		compound.setTag("SenseBox", boundingBox);

		return compound;
	}

	public AxisAlignedBB getSenseBox() {
		return senseBox;
	}

	public void setSenseBox(AxisAlignedBB senseBox) {
		this.senseBox = senseBox;
	}

	public boolean renderSensorPos() {
		return renderSensorPos;
	}

	public void setRenderSensorPos(boolean renderSensorPos) {
		this.renderSensorPos = renderSensorPos;
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
	public Container createContainer(PlayerInventory playerInventory, PlayerEntity playerIn) {
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
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

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
	public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
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

	@Override
	public boolean isEmpty() {
		return true;
	}
}
