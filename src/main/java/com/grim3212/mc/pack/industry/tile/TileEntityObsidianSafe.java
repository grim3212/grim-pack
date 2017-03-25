package com.grim3212.mc.pack.industry.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class TileEntityObsidianSafe extends TileEntityStorage {

	@Override
	protected String getStorageName() {
		return "obsidian_safe";
	}

	@Override
	public IBlockState getBreakTextureState() {
		return Blocks.OBSIDIAN.getDefaultState();
	}

}
