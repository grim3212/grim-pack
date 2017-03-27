package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.industry.inventory.ContainerGoldSafe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;

public class TileEntityGoldSafe extends TileEntityStorage {

	@Override
	protected String getStorageName() {
		return "gold_safe";
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerGoldSafe(playerIn, this);
	}

	@Override
	public IBlockState getBreakTextureState() {
		return Blocks.GOLD_BLOCK.getDefaultState();
	}
}
