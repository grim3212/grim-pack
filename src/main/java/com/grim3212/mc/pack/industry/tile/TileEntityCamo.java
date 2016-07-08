package com.grim3212.mc.pack.industry.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityCamo extends TileEntity {

	protected int blockID = 0;
	protected int blockMeta = 0;

	public TileEntityCamo() {
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		if (oldState.getBlock() == newState.getBlock()) {
			return false;
		} else {
			return true;
		}
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.blockID = nbt.getInteger("blockID");
		this.blockMeta = nbt.getInteger("blockMeta");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("blockID", this.blockID);
		nbt.setInteger("blockMeta", this.blockMeta);
		return nbt;
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

	public int getBlockID() {
		return blockID;
	}

	public void setBlockID(int blockID) {
		this.blockID = blockID;
	}

	public int getBlockMeta() {
		return blockMeta;
	}

	public void setBlockMeta(int blockMeta) {
		this.blockMeta = blockMeta;
	}
}