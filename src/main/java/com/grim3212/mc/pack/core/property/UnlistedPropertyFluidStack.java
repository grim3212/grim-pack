package com.grim3212.mc.pack.core.property;

import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fluids.FluidStack;

public class UnlistedPropertyFluidStack implements IUnlistedProperty<FluidStack> {

	private final String name;

	public UnlistedPropertyFluidStack(String name) {
		this.name = name;
	}

	public static UnlistedPropertyFluidStack create(String name) {
		return new UnlistedPropertyFluidStack(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<FluidStack> getType() {
		return FluidStack.class;
	}

	@Override
	public String valueToString(FluidStack value) {
		return String.valueOf(value);
	}

	@Override
	public boolean isValid(FluidStack value) {
		return true;
	}

}
