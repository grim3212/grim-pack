package com.grim3212.mc.pack.industry.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityCamo extends TileEntity {

	protected BlockState blockState = Blocks.AIR.getDefaultState();

	public TileEntityCamo() {
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newState) {
		if (oldState.getBlock() == newState.getBlock()) {
			return false;
		} else {
			return true;
		}
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
	@SuppressWarnings("deprecation")
	public void readFromNBT(CompoundNBT nbt) {
		super.readFromNBT(nbt);
		Block block = Block.REGISTRY.getObject(new ResourceLocation(nbt.getString("registryName")));
		this.blockState = block.getStateFromMeta(nbt.getInteger("meta"));
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT nbt) {
		super.writeToNBT(nbt);
		nbt.setString("registryName", this.blockState.getBlock().getRegistryName().toString());
		nbt.setInteger("meta", this.blockState.getBlock().getMetaFromState(blockState));
		return nbt;
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

	public BlockState getBlockState() {
		return blockState;
	}

	public void setBlockState(BlockState blockState) {
		this.blockState = blockState;
	}

	@SuppressWarnings("deprecation")
	public void setBlockState(String registryName, int meta) {
		this.blockState = Block.REGISTRY.getObject(new ResourceLocation(registryName)).getStateFromMeta(meta);
	}
}