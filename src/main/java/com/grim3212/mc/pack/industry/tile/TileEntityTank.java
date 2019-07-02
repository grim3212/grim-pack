package com.grim3212.mc.pack.industry.tile;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityTank extends TileEntity {

	public FluidTank tank;

	public TileEntityTank() {
		tank = new FluidTank(Fluid.BUCKET_VOLUME * 8);
	}

	public FluidStack getFluidStack() {
		return this.tank.getFluid();
	}

	public int getLightLevel() {
		if (tank == null || tank.getFluid() == null || tank.getFluid().getFluid() == null)
			return 0;

		return this.tank.getFluid().getFluid().getLuminosity(getWorld(), getPos());
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, Direction facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}

		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, Direction facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.tank);
		}
		return super.getCapability(capability, facing);
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

		if (world.isRemote) {
			world.checkLight(pos);
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
	public CompoundNBT writeToNBT(CompoundNBT compound) {
		super.writeToNBT(compound);
		this.tank.writeToNBT(compound);
		return compound;
	}

	@Override
	public void readFromNBT(CompoundNBT compound) {
		super.readFromNBT(compound);
		this.tank.readFromNBT(compound);
	}
}
