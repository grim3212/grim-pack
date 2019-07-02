package com.grim3212.mc.pack.industry.tile;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class TileEntityObsidianSafe extends TileEntityStorage {

	@Override
	protected String getStorageName() {
		return "obsidian_safe";
	}

	@Override
	public BlockState getBreakTextureState() {
		return Blocks.OBSIDIAN.getDefaultState();
	}

}
