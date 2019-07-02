package com.grim3212.mc.pack.industry.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.math.BlockPos;

public class ContainerSpecificSensor extends Container {

	private BlockPos pos;

	public ContainerSpecificSensor(BlockPos pos, PlayerInventory playerInventory) {
		this.pos = pos;

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 48 + j * 18, 138 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInventory, k, 48 + k * 18, 196));
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return playerIn.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

}
