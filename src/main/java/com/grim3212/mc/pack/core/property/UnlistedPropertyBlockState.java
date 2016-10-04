package com.grim3212.mc.pack.core.property;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyBlockState implements IUnlistedProperty<IBlockState> {

	private final String name;

	public UnlistedPropertyBlockState(String name) {
		this.name = name;
	}

	public static UnlistedPropertyBlockState create(String name) {
		return new UnlistedPropertyBlockState(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<IBlockState> getType() {
		return IBlockState.class;
	}

	@Override
	public String valueToString(IBlockState value) {
		return String.valueOf(value);
	}

	@Override
	public boolean isValid(IBlockState value) {
		return true;
	}

}
