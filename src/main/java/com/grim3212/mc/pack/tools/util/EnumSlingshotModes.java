package com.grim3212.mc.pack.tools.util;

import net.minecraft.util.IStringSerializable;

public enum EnumSlingshotModes implements IStringSerializable {
	RANDOM("random"), ALL("all"), STONE("stone"), IRON("iron"), NETHERRACK("netherrack"), LIGHT("light"), FIRE("fire"), EXPLOSIVE("explosive");

	private String unlocalized;

	private EnumSlingshotModes(String unlocalized) {
		this.unlocalized = unlocalized;
	}

	public String getUnlocalized() {
		return unlocalized;
	}

	public static final EnumSlingshotModes values[] = values();

	public static String[] names() {
		EnumSlingshotModes[] states = values;
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

	public EnumSlingshotModes cycle() {
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

	public static EnumSlingshotModes getFromString(String s) {
		for (int i = 0; i < values.length; i++) {
			if (values[i].getUnlocalized().equals(s)) {
				return values[i];
			}
		}

		return EnumSlingshotModes.RANDOM;
	}
}
