package com.grim3212.mc.core.property;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyBoolean implements IUnlistedProperty<Boolean> {

	private final String name;

	public UnlistedPropertyBoolean(String name) {
		this.name = name;
	}

	public static UnlistedPropertyBoolean create(String name) {
		return new UnlistedPropertyBoolean(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<Boolean> getType() {
		return Boolean.class;
	}

	@Override
	public String valueToString(Boolean value) {
		return String.valueOf(value);
	}

	@Override
	public boolean isValid(Boolean value) {
		return true;
	}

}
