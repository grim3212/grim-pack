package com.grim3212.mc.pack.core.inventory;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class InventoryCapability implements ICapabilitySerializable<CompoundNBT> {

	private final IItemHandler inv;

	public InventoryCapability(IItemHandler handler) {
		inv = handler;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> inv));
	}

	@Override
	public CompoundNBT serializeNBT() {
		return (CompoundNBT) CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inv, null);
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inv, null, nbt);
	}

}
