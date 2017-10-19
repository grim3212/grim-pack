package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.core.tile.TileEntityGrim;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityBridge extends TileEntityGrim {

	protected IBlockState blockState = Blocks.AIR.getDefaultState();

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
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
