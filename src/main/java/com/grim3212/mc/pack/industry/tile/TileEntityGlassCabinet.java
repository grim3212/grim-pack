package com.grim3212.mc.pack.industry.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class TileEntityGlassCabinet extends TileEntityStorage {

	@Override
	protected String getStorageName() {
		return "glass_cabinet";
	}

	@Override
	public IBlockState getBreakTextureState() {
		return Blocks.LOG.getDefaultState();
	}

}
