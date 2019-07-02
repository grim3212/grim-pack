package com.grim3212.mc.pack.decor.inventory;

import com.grim3212.mc.pack.decor.crafting.GrillRecipeSerializer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerGrill extends Container {

	private BlockPos pos;
	private IInventory grillInv;
	private final IIntArray progress;

	public ContainerGrill(int id, PlayerInventory playerInv) {
		this(id, playerInv, new Inventory(5), new IntArray(6), BlockPos.ZERO);
	}

	public ContainerGrill(int id, PlayerInventory playerInv, IInventory grillInv, IIntArray progress, BlockPos pos) {
		super(DecorContainers.GRILL_TYPE, id);
		this.progress = progress;
		this.grillInv = grillInv;
		this.pos = pos;
		addSlot(new SlotGrill(grillInv, 0, 71, 26, false));
		addSlot(new SlotGrill(grillInv, 1, 89, 26, false));
		addSlot(new SlotGrill(grillInv, 2, 71, 44, false));
		addSlot(new SlotGrill(grillInv, 3, 89, 44, false));
		addSlot(new SlotGrill(grillInv, 4, 135, 37, true));

		for (int var3 = 0; var3 < 3; var3++) {
			for (int var4 = 0; var4 < 9; var4++) {
				addSlot(new Slot(playerInv, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}

		for (int var3 = 0; var3 < 9; var3++) {
			addSlot(new Slot(playerInv, var3, 8 + var3 * 18, 142));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public int getGrillCoal() {
		return this.progress.get(0);
	}

	@OnlyIn(Dist.CLIENT)
	public int getCookTime(int pos) {
		return this.progress.get(pos);
	}

	@OnlyIn(Dist.CLIENT)
	public int getTier() {
		return this.progress.get(5);
	}

	@OnlyIn(Dist.CLIENT)
	public BlockPos getPos() {
		return this.pos;
	}

	@Override
	public boolean canInteractWith(PlayerEntity par1EntityPlayer) {
		return this.grillInv.isUsableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
		Slot slot = (Slot) this.inventorySlots.get(index);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack realstack = slot.getStack();

			if (index < this.grillInv.getSizeInventory()) {
				if (!mergeItemStack(realstack, this.grillInv.getSizeInventory() - 1, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}

			} else {
				if (GrillRecipeSerializer.grillRecipesContain(realstack)) {
					for (int i = 0; i < 4; i++) {
						if (this.grillInv.getStackInSlot(i).isEmpty()) {
							ItemStack newstack = new ItemStack(realstack.getItem(), 1);

							this.grillInv.setInventorySlotContents(i, newstack);

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
					if (!this.grillInv.getStackInSlot(4).isEmpty() && (this.grillInv.getStackInSlot(4).getItem() == Items.COAL) && (this.grillInv.getStackInSlot(4).getCount() < this.grillInv.getInventoryStackLimit())) {
						ItemStack fuel = this.grillInv.getStackInSlot(4);
						int difference = this.grillInv.getInventoryStackLimit() - fuel.getCount();

						if (realstack.getCount() > difference) {
							realstack.shrink(difference);
							fuel.grow(difference);
						} else {
							fuel.grow(realstack.getCount());
							slot.putStack(ItemStack.EMPTY);
						}
					}

					if (this.grillInv.getStackInSlot(4).isEmpty()) {
						this.grillInv.setInventorySlotContents(4, realstack);
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