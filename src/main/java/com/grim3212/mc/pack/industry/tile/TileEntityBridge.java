package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.core.tile.TileEntityGrim;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityBridge extends TileEntityGrim {

	protected BlockState blockState = Blocks.AIR.getDefaultState();

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newState) {
		return oldState.getBlock() != newState.getBlock();
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
