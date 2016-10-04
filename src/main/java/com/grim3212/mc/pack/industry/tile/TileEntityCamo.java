package com.grim3212.mc.pack.industry.tile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityCamo extends TileEntity {

	protected IBlockState blockState = Blocks.AIR.getDefaultState();

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
	@SuppressWarnings("deprecation")
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		Block block = Block.REGISTRY.getObject(new ResourceLocation(nbt.getString("registryName")));
		this.blockState = block.getStateFromMeta(nbt.getInteger("meta"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("registryName", this.blockState.getBlock().getRegistryName().toString());
		nbt.setInteger("meta", this.blockState.getBlock().getMetaFromState(blockState));
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

	public IBlockState getBlockState() {
		return blockState;
	}

	public void setBlockState(IBlockState blockState) {
		this.blockState = blockState;
	}

	@SuppressWarnings("deprecation")
	public void setBlockState(String registryName, int meta) {
		this.blockState = Block.REGISTRY.getObject(new ResourceLocation(registryName)).getStateFromMeta(meta);
	}
}