package com.grim3212.mc.pack.decor.tile;

import java.util.Optional;

import com.grim3212.mc.pack.decor.inventory.ContainerCage;
import com.grim3212.mc.pack.decor.util.CageSpawnerLogic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SnowballItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;

public class TileEntityCage extends LockableTileEntity implements ITickableTileEntity {

	public TileEntityCage() {
		super(DecorTileEntities.CAGE);
	}

	public ItemStack cageItem = ItemStack.EMPTY;
	private String customName;
	private Entity cachedEntity;

	private final CageSpawnerLogic spawnerLogic = new CageSpawnerLogic(this);

	public CageSpawnerLogic getSpawnerLogic() {
		return spawnerLogic;
	}

	@Override
	public void tick() {
		this.spawnerLogic.updateSpawner();
	}

	@Override
	public ITextComponent getName() {
		return this.hasCustomName() ? getCustomName() : new TranslationTextComponent("container.cage");
	}

	@Override
	public ITextComponent getCustomName() {
		return new StringTextComponent(customName);
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
		return index == 0 ? cageItem : ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (index == 0) {
			this.cachedEntity = null;

			if (this.cageItem.getCount() <= count) {
				ItemStack stack = this.cageItem;
				this.cageItem = ItemStack.EMPTY;
				return stack;
			}

			ItemStack stack = this.cageItem.split(count);

			if (this.cageItem.getCount() == 0) {
				this.cageItem = ItemStack.EMPTY;
			}

			return stack;
		}

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (index == 0) {
			this.cachedEntity = null;
			return cageItem;
		}

		return ItemStack.EMPTY;
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
		// TODO: Change to Pokeball
		return stack.getItem() instanceof SnowballItem;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new StringTextComponent("decor:cage");
	}

	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerIn) {
		return this.canOpen(playerIn) ? this.createMenu(id, playerInventory) : null;
	}

	protected Container createMenu(int id, PlayerInventory playerInventory) {
		return new ContainerCage(id, playerInventory, this);
	}

	@Override
	public void clear() {
		this.cageItem = ItemStack.EMPTY;
		this.cachedEntity = null;
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return write(new CompoundNBT());
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		read(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);

		if (!this.cageItem.isEmpty()) {
			CompoundNBT cageCompound = new CompoundNBT();
			this.cageItem.write(cageCompound);
			compound.put("CageItem", cageCompound);
		}

		if (this.hasCustomName()) {
			compound.putString("CustomName", this.customName);
		}

		return compound;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);

		if (compound.contains("CageItem", Constants.NBT.TAG_COMPOUND))
			this.cageItem = ItemStack.read(compound.getCompound("CageItem"));

		if (compound.contains("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbtTagCompound = new CompoundNBT();
		write(nbtTagCompound);
		return new SUpdateTileEntityPacket(this.pos, 1, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		read(pkt.getNbtCompound());
	}

	public Entity getPokeballEntity() {
		if (this.cachedEntity == null) {
			if (!this.cageItem.isEmpty()) {
				if (this.cageItem.hasTag()) {
					Optional<Entity> e = EntityType.loadEntityUnchecked(this.cageItem.getTag(), this.world);
					if (e.isPresent()) {
						Entity ent = e.get();
						ent.read(this.cageItem.getTag());
						this.cachedEntity = ent;
					}
				}
			}
		}
		return this.cachedEntity;
	}

	@Override
	public boolean isEmpty() {
		return this.cageItem.isEmpty();
	}
}