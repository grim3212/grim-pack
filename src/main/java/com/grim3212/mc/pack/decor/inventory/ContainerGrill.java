package com.grim3212.mc.pack.decor.inventory;

import com.grim3212.mc.pack.decor.crafting.GrillRecipeFactory;
import com.grim3212.mc.pack.decor.tile.TileEntityGrill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerGrill extends Container {

	private TileEntityGrill grill;
	private int lastCoalTime = 0;
	private int lastCookTimes0;
	private int lastCookTimes1;
	private int lastCookTimes2;
	private int lastCookTimes3 = 0;

	public ContainerGrill(InventoryPlayer playerInv, TileEntityGrill grillTile) {
		this.grill = grillTile;
		addSlotToContainer(new SlotGrill(grillTile, 0, 71, 26, false));
		addSlotToContainer(new SlotGrill(grillTile, 1, 89, 26, false));
		addSlotToContainer(new SlotGrill(grillTile, 2, 71, 44, false));
		addSlotToContainer(new SlotGrill(grillTile, 3, 89, 44, false));
		addSlotToContainer(new SlotGrill(grillTile, 4, 135, 37, true));

		for (int var3 = 0; var3 < 3; var3++) {
			for (int var4 = 0; var4 < 9; var4++) {
				addSlotToContainer(new Slot(playerInv, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}

		for (int var3 = 0; var3 < 9; var3++) {
			addSlotToContainer(new Slot(playerInv, var3, 8 + var3 * 18, 142));
		}
	}

	@Override
	public void addListener(IContainerListener crafting) {
		super.addListener(crafting);
		crafting.sendAllWindowProperties(this, grill);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int var1 = 0; var1 < this.listeners.size(); var1++) {
			IContainerListener listener = (IContainerListener) this.listeners.get(var1);

			if (this.lastCoalTime != this.grill.grillCoal) {
				listener.sendWindowProperty(this, 0, this.grill.grillCoal);
			}

			if (this.lastCookTimes0 != this.grill.cookTimes[0]) {
				listener.sendWindowProperty(this, 1, this.grill.cookTimes[0]);
			}

			if (this.lastCookTimes1 != this.grill.cookTimes[1]) {
				listener.sendWindowProperty(this, 2, this.grill.cookTimes[1]);
			}

			if (this.lastCookTimes2 != this.grill.cookTimes[2]) {
				listener.sendWindowProperty(this, 3, this.grill.cookTimes[2]);
			}

			if (this.lastCookTimes3 != this.grill.cookTimes[3]) {
				listener.sendWindowProperty(this, 4, this.grill.cookTimes[3]);
			}
		}

		this.lastCoalTime = this.grill.grillCoal;
		this.lastCookTimes0 = this.grill.cookTimes[0];
		this.lastCookTimes1 = this.grill.cookTimes[1];
		this.lastCookTimes2 = this.grill.cookTimes[2];
		this.lastCookTimes3 = this.grill.cookTimes[3];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.grill.grillCoal = par2;
		}

		if (par1 == 1) {
			this.grill.cookTimes[0] = par2;
		}

		if (par1 == 2) {
			this.grill.cookTimes[1] = par2;
		}

		if (par1 == 3) {
			this.grill.cookTimes[2] = par2;
		}

		if (par1 == 4) {
			this.grill.cookTimes[3] = par2;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.grill.isUsableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		Slot slot = (Slot) this.inventorySlots.get(index);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack realstack = slot.getStack();

			if (index < this.grill.getSizeInventory()) {
				if (!mergeItemStack(realstack, this.grill.getSizeInventory() - 1, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}

			} else {
				if (GrillRecipeFactory.grillRecipesContain(realstack)) {
					for (int i = 0; i < 4; i++) {
						if (this.grill.getStackInSlot(i).isEmpty()) {
							ItemStack newstack = new ItemStack(realstack.getItem(), 1, realstack.getMetadata());

							this.grill.setInventorySlotContents(i, newstack);

							if (realstack.getCount() > 1) {
								realstack.shrink(1);
								break;
							}
							slot.putStack(ItemStack.EMPTY);

							break;
						}
					}
				}

				if (realstack.getItem() == Items.COAL) {
					if (!this.grill.getStackInSlot(4).isEmpty() && (this.grill.getStackInSlot(4).getItem() == Items.COAL) && (this.grill.getStackInSlot(4).getCount() < this.grill.getInventoryStackLimit()) && (this.grill.getStackInSlot(4).getItemDamage() == realstack.getItemDamage())) {
						ItemStack fuel = this.grill.getStackInSlot(4);
						int difference = this.grill.getInventoryStackLimit() - fuel.getCount();

						if (realstack.getCount() > difference) {
							realstack.shrink(difference);
							fuel.grow(difference);
						} else {
							fuel.grow(realstack.getCount());
							slot.putStack(ItemStack.EMPTY);
						}
					}

					if (this.grill.getStackInSlot(4).isEmpty()) {
						this.grill.setInventorySlotContents(4, realstack);
						slot.putStack(ItemStack.EMPTY);
					}
				}

				return ItemStack.EMPTY;
			}

			if (realstack.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return ItemStack.EMPTY;
	}
}