package com.grim3212.mc.pack.tools.util;

import net.minecraft.util.IStringSerializable;

public enum EnumPowerStaffModes implements IStringSerializable {
	FLOAT_PUSH("FloatingPush"), FLOAT_PULL("FloatingPull"), GRAVITY_PUSH("DroppingPush"), GRAVITY_PULL("DroppingPull");

	private String unlocalized;

	private EnumPowerStaffModes(String unlocalized) {
		this.unlocalized = unlocalized;
	}

	public String getUnlocalized() {
		return unlocalized;
	}

	public static final EnumPowerStaffModes values[] = values();

	public static String[] names() {
		EnumPowerStaffModes[] states = values;
		String[] names = new String[states.length];

		for (int i = 0; i < states.length; i++) {
			names[i] = states[i].getUnlocalized();
		}

		return names;
	}

	@Override
	public String getName() {
		return this.name();
	}

	public EnumPowerStaffModes cycle() {
		for (int i = 0; i < values.length; i++) {
			if (values[i] == this) {
				if (i < values.length - 1) {
					return values[i + 1];
				} else if (i >= values.length - 1) {
					return values[0];
				}
			}
		}
		return this;
	}

	public static EnumPowerStaffModes getFromString(String s) {
		for (int i = 0; i < values.length; i++) {
			if (values[i].getUnlocalized().equals(s)) {
				return values[i];
			}
		}

		return EnumPowerStaffModes.FLOAT_PUSH;
	}
}

