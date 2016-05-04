package com.grim3212.mc.decor.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TileEntityFireplace extends TileEntityTextured {

	public TileEntityFireplace() {
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}

	public void notifyUpdate() {
		worldObj.markBlockForUpdate(getPos());
		worldObj.notifyNeighborsOfStateChange(getPos(), blockType);
		worldObj.notifyLightSet(getPos());
		worldObj.checkLight(getPos());
	}
}