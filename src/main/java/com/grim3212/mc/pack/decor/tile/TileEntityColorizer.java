package com.grim3212.mc.pack.decor.tile;

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

public class TileEntityColorizer extends TileEntity {

	protected IBlockState blockState = Blocks.AIR.getDefaultState();

	public TileEntityColorizer() {
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
		if (this.blockState.getBlock().getRegistryName() != null)
			compound.setString("registryName", this.blockState.getBlock().getRegistryName().toString());
		else
			compound.setString("registryName", Blocks.AIR.getDefaultState().getBlock().getRegistryName().toString());
		compound.setInteger("meta", this.blockState.getBlock().getMetaFromState(blockState));

		return compound;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		Block block = Block.REGISTRY.getObject(new ResourceLocation(compound.getString("registryName")));
		this.blockState = block.getStateFromMeta(compound.getInteger("meta"));
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
		worldObj.markBlockRangeForRenderUpdate(pos, pos);
		worldObj.notifyBlockOfStateChange(pos, blockType);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
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
