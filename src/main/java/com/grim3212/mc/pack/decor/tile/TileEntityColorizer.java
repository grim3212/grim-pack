package com.grim3212.mc.pack.decor.tile;

import com.grim3212.mc.pack.core.tile.TileEntityGrim;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityColorizer extends TileEntityGrim {

	public static final ModelProperty<BlockState> BLOCK_STATE = new ModelProperty<BlockState>();
	protected BlockState blockState = Blocks.AIR.getDefaultState();

	public TileEntityColorizer(TileEntityType<?> type) {
		super(type);
	}

	public TileEntityColorizer() {
		super(DecorTileEntities.COLORIZER);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if (this.blockState.getBlock().getRegistryName() != null)
			compound.put("stored_state", NBTUtil.writeBlockState(this.blockState));
		else
			compound.put("stored_state", NBTUtil.writeBlockState(Blocks.AIR.getDefaultState()));

		return compound;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		this.blockState = NBTUtil.readBlockState(compound.getCompound("stored_state"));
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbtTagCompound = new CompoundNBT();
		write(nbtTagCompound);
		return new SUpdateTileEntityPacket(this.pos, 1, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		read(pkt.getNbtCompound());
		ModelDataManager.requestModelDataRefresh(this);
		world.markForRerender(pos);
		world.notifyNeighborsOfStateChange(pos, getBlockState().getBlock());
	}
	
	@Override
	public IModelData getModelData() {
		return new ModelDataMap.Builder().withInitial(BLOCK_STATE, blockState).build();
	}

	public BlockState getStoredBlockState() {
		return blockState;
	}

	public void setStoredBlockState(BlockState blockState) {
		this.blockState = blockState;
	}

	public void setStoredBlockState(String registryName) {
		this.blockState = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(registryName)).getDefaultState();
	}
}
