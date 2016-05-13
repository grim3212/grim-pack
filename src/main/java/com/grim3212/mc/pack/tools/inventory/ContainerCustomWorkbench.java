package com.grim3212.mc.pack.tools.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerCustomWorkbench extends ContainerWorkbench {

	public ContainerCustomWorkbench(InventoryPlayer invPlayer, World world, BlockPos pos) {
		super(invPlayer, world, pos);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}