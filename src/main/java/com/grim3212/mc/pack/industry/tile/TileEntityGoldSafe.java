package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.industry.inventory.ContainerGoldSafe;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Container;

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
	public Container createContainer(PlayerInventory playerInventory, PlayerEntity playerIn) {
		return new ContainerGoldSafe(playerIn, this);
	}

	@Override
	public BlockState getBreakTextureState() {
		return Blocks.GOLD_BLOCK.getDefaultState();
	}
}
