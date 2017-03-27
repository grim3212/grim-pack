package com.grim3212.mc.pack.industry.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class TileEntityWoodCabinet extends TileEntityStorage {

	@Override
	protected String getStorageName() {
		return "wood_cabinet";
	}

	@Override
	public IBlockState getBreakTextureState() {
		return Blocks.LOG.getDefaultState();
	}
}
