package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.industry.block.BlockFireSensor;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Blocks;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityFireSensor extends TileEntity implements ITickable {

	/*
	 * Allows so this doesn't do a fire check every tick
	 */
	private int tick = 0;

	public TileEntityFireSensor() {
	}

	@Override
	public void update() {
		if (tick == 20) {
			BlockState state = world.getBlockState(pos);

			if (isFireNearby(world, pos)) {
				if (!state.getValue(BlockFireSensor.POWERED)) {
					world.setBlockState(pos, state.withProperty(BlockFireSensor.POWERED, true));
					world.playSound(null, pos, SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 0.3F, 0.6F);
				}
			} else {
				if (state.getValue(BlockFireSensor.POWERED)) {
					world.setBlockState(pos, state.withProperty(BlockFireSensor.POWERED, false));
				}
			}
			tick = 0;
		} else {
			tick++;
		}
	}

	private boolean isFireNearby(World world, BlockPos pos) {
		for (int l = pos.getX() - 5; l <= pos.getX() + 5; l++) {
			for (int i1 = pos.getY() - 5; i1 <= pos.getY() - 1; i1++) {
				for (int j1 = pos.getZ() - 5; j1 <= pos.getZ() + 5; j1++) {
					if (world.getBlockState(new BlockPos(l, i1, j1)).getBlock() == Blocks.FIRE) {
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return writeToNBT(new CompoundNBT());
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		readFromNBT(tag);
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT compound) {
		super.writeToNBT(compound);
		return compound;
	}

	@Override
	public void readFromNBT(CompoundNBT compound) {
		super.readFromNBT(compound);
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbtTagCompound = new CompoundNBT();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SUpdateTileEntityPacket(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
}
