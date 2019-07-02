package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.core.tile.TileEntityGrim;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityFlipFlopTorch extends TileEntityGrim {

	private boolean prevState;

	public TileEntityFlipFlopTorch() {
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void readFromNBT(CompoundNBT compound) {
		super.readFromNBT(compound);

		if (compound.hasKey("PrevState")) {
			this.prevState = compound.getBoolean("PrevState");
		}
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT compound) {
		super.writeToNBT(compound);
		compound.setBoolean("PrevState", this.prevState);
		return compound;
	}

	public void setPrevState(boolean prevState) {
		this.prevState = prevState;
	}

	public boolean getPrevState() {
		return this.prevState;
	}
}
