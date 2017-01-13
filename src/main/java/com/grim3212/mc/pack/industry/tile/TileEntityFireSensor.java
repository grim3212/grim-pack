package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.industry.block.BlockFireSensor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
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
			IBlockState state = world.getBlockState(pos);

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
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
}
