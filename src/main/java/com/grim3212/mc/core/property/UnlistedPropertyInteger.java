package com.grim3212.mc.core.property;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyInteger implements IUnlistedProperty<Integer> {

	private final String name;

	public UnlistedPropertyInteger(String name) {
		this.name = name;
	}

	public static UnlistedPropertyInteger create(String name) {
		return new UnlistedPropertyInteger(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isValid(Integer value) {
		return true;
	}

	@Override
	public String valueToString(Integer value) {
		return String.valueOf(value);
	}

	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}
}
