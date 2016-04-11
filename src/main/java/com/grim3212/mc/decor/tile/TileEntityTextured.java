package com.grim3212.mc.decor.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

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
		return new S35PacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		if (oldState.getBlock() == newState.getBlock()) {
			return false;
		} else {
			return true;
		}
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
