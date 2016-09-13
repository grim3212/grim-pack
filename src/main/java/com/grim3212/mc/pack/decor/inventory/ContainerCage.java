package com.grim3212.mc.pack.decor.inventory;

import com.grim3212.mc.pack.decor.tile.TileEntityCage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerCage extends Container {

	private TileEntityCage cage;

	public ContainerCage(TileEntityCage cage, InventoryPlayer playerInv) {
		this.cage = cage;

		addSlotToContainer(new SlotCage(cage, 0, 75, 30));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.cage.isUseableByPlayer(playerIn);
	}

}
