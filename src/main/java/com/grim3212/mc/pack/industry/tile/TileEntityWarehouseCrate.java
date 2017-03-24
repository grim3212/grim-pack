package com.grim3212.mc.pack.industry.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class TileEntityWarehouseCrate extends TileEntityStorage {

	@Override
	protected String getStorageName() {
		return "warehouse_crate";
	}

	@Override
	public IBlockState getBreakTextureState() {
		return Blocks.PLANKS.getDefaultState();
	}

	/*
	 * @Override protected ResourceLocation getStateMachine() { return new
	 * ResourceLocation(GrimPack.modID, "asms/block/warehouse_crate.json"); }
	 */

}
