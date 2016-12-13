package com.grim3212.mc.pack.tools.capabilities;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.tools.items.ItemBetterBucket;
import com.grim3212.mc.pack.tools.items.ItemBetterMilkBucket;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FluidHandlerBetterBucket implements IFluidHandler, ICapabilityProvider {

	protected ItemStack container;
	protected final ItemStack emptyContainer;
	protected final int capacity;

	/**
	 * @param container
	 *            The container itemStack, data is stored on it directly as NBT.
	 * @param capacity
	 *            The maximum capacity of this fluid tank.
	 */
	public FluidHandlerBetterBucket(ItemStack container, ItemStack emptyContainer, int capacity) {
		this.container = container;
		this.emptyContainer = emptyContainer;
		this.capacity = capacity;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? (T) this : null;
	}

	@Nullable
	public FluidStack getFluid() {
		NBTTagCompound tagCompound = container.getTagCompound();
		if (tagCompound == null) {
			return null;
		}
		return FluidStack.loadFluidStackFromNBT(tagCompound);
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new FluidTankProperties[] { new FluidTankProperties(getFluid(), capacity) };
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		// has to be exactly 1, must be handled from the caller
		if (container.getCount() != 1) {
			return 0;
		}

		// can only fill exact capacity
		if (resource == null || resource.amount % Fluid.BUCKET_VOLUME != 0) {
			return 0;
		}

		// fill the container
		if (doFill) {
			int fillAmount = NBTHelper.getInt(container, "Amount") + resource.amount;
			NBTHelper.setString(container, "Fluid", FluidRegistry.getFluidName(resource.getFluid()));
			NBTHelper.setInteger(container, "Amount", fillAmount);
			return fillAmount;
		} else {
			if (!ItemBetterBucket.isEmptyOrContains(container, FluidRegistry.getFluidName(resource.getFluid()))) {
				return 0;
			}
		}
		return resource.amount;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (container.getCount() != 1 || resource == null || resource.amount <= 0 || !resource.isFluidEqual(getFluid())) {
			return null;
		}
		return drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if (maxDrain % Fluid.BUCKET_VOLUME != 0 || maxDrain > capacity) {
			return null;
		}

		if (doDrain) {
			int amount = NBTHelper.getInt(container, "Amount");
			NBTHelper.setInteger(container, "Amount", amount - maxDrain);

			if (NBTHelper.getInt(container, "Amount") <= 0) {

				if (container.getItem() instanceof ItemBetterMilkBucket) {
					container = emptyContainer;
				} else {
					container.setTagCompound(emptyContainer.getTagCompound());
				}
			}
		}

		return getFluid();
	}

}
