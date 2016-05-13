package com.grim3212.mc.pack.decor.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTextured extends TileEntity {

	protected int blockID = 0;
	protected int blockMeta = 0;

	public TileEntityTextured() {
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("blockID", this.blockID);
		compound.setInteger("blockMeta", this.blockMeta);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.blockID = compound.getInteger("blockID");
		this.blockMeta = compound.getInteger("blockMeta");
	}

	@Override
	public Packet<INetHandlerPlayClient> getDescriptionPacket() {
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
