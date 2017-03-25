package com.grim3212.mc.pack.industry.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

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
		return super.createContainer(playerInventory, playerIn);
	}

	@Override
	public IBlockState getBreakTextureState() {
		return Blocks.GOLD_BLOCK.getDefaultState();
	}

	public ItemStack getStackInSlotNoLock(int index) {
		return this.itemstacks.get(index);
	}
}
