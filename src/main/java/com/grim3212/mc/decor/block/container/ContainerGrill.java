package com.grim3212.mc.decor.block.container;

import com.grim3212.mc.decor.config.DecorConfig;
import com.grim3212.mc.decor.tile.TileEntityGrill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
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

	public ContainerGrill(InventoryPlayer par1InventoryPlayer, TileEntityGrill par2TileEntityGrill) {
		this.grill = par2TileEntityGrill;
		addSlotToContainer(new SlotGrill(par2TileEntityGrill, 0, 71, 26, false));
		addSlotToContainer(new SlotGrill(par2TileEntityGrill, 1, 89, 26, false));
		addSlotToContainer(new SlotGrill(par2TileEntityGrill, 2, 71, 44, false));
		addSlotToContainer(new SlotGrill(par2TileEntityGrill, 3, 89, 44, false));
		addSlotToContainer(new SlotGrill(par2TileEntityGrill, 4, 135, 37, true));

		for (int var3 = 0; var3 < 3; var3++) {
			for (int var4 = 0; var4 < 9; var4++) {
				addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}

		for (int var3 = 0; var3 < 9; var3++) {
			addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
		}
	}

	@Override
	public void onCraftGuiOpened(ICrafting crafting) {
		super.onCraftGuiOpened(crafting);
		crafting.sendAllWindowProperties(this, grill);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int var1 = 0; var1 < this.crafters.size(); var1++) {
			ICrafting var2 = (ICrafting) this.crafters.get(var1);

			if (this.lastCoalTime != this.grill.grillCoal) {
				var2.sendProgressBarUpdate(this, 0, this.grill.grillCoal);
			}

			if (this.lastCookTimes0 != this.grill.cookTimes[0]) {
				var2.sendProgressBarUpdate(this, 1, this.grill.cookTimes[0]);
			}

			if (this.lastCookTimes1 != this.grill.cookTimes[1]) {
				var2.sendProgressBarUpdate(this, 2, this.grill.cookTimes[1]);
			}

			if (this.lastCookTimes2 != this.grill.cookTimes[2]) {
				var2.sendProgressBarUpdate(this, 3, this.grill.cookTimes[2]);
			}

			if (this.lastCookTimes3 != this.grill.cookTimes[3]) {
				var2.sendProgressBarUpdate(this, 4, this.grill.cookTimes[3]);
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
		return this.grill.isUseableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if ((var4 != null) && (var4.getHasStack())) {
			ItemStack realstack = var4.getStack();

			if (par2 < this.grill.getSizeInventory()) {
				if (!mergeItemStack(realstack, this.grill.getSizeInventory() - 1, this.inventorySlots.size(), true)) {
					return null;
				}

			} else {
				if (DecorConfig.grillRecipes.keySet().contains(realstack.getItem())) {
					for (int i = 0; i < 4; i++) {
						if (this.grill.getStackInSlot(i) == null) {
							ItemStack newstack = new ItemStack(realstack.getItem(), 1, 0);

							this.grill.setInventorySlotContents(i, newstack);

							if (realstack.stackSize > 1) {
								realstack.stackSize -= 1;
								break;
							}
							var4.putStack((ItemStack) null);

							break;
						}
					}

				}

				if (realstack.getItem() == Items.coal) {
					if ((this.grill.getStackInSlot(4) != null) && (this.grill.getStackInSlot(4).getItem() == Items.coal) && (this.grill.getStackInSlot(4).stackSize < this.grill.getInventoryStackLimit()) && (this.grill.getStackInSlot(4).getItemDamage() == realstack.getItemDamage())) {
						ItemStack fuel = this.grill.getStackInSlot(4);
						int difference = this.grill.getInventoryStackLimit() - fuel.stackSize;

						if (realstack.stackSize > difference) {
							realstack.stackSize -= difference;
							fuel.stackSize += difference;
						} else {
							fuel.stackSize += realstack.stackSize;
							var4.putStack((ItemStack) null);
						}
					}

					if (this.grill.getStackInSlot(4) == null) {
						this.grill.setInventorySlotContents(4, realstack);
						var4.putStack((ItemStack) null);
					}
				}

				return null;
			}

			if (realstack.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}
		}

		return null;
	}
}