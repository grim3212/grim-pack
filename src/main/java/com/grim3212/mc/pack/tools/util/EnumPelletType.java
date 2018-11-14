package com.grim3212.mc.pack.tools.util;

import com.grim3212.mc.pack.tools.config.ToolsConfig;

import net.minecraft.util.IStringSerializable;

public enum EnumPelletType implements IStringSerializable {
	STONE("stone"), IRON("iron"), NETHERRACK("netherrack"), LIGHT("light"), FIRE("fire"), EXPLOSION("explosion");

	private String unlocalized;

	private EnumPelletType(String unlocalized) {
		this.unlocalized = unlocalized;
	}

	public float getDamage() {
		switch (this) {
		case EXPLOSION:
			return ToolsConfig.explosivePelletDamage;
		case FIRE:
			return ToolsConfig.firePelletDamage;
		case IRON:
			return ToolsConfig.ironPelletDamage;
		case LIGHT:
			return ToolsConfig.lightPelletDamage;
		case NETHERRACK:
			return ToolsConfig.netherrackPelletDamage;
		case STONE:
			return ToolsConfig.stonePelletDamage;
		default:
			return 0;
		}
	}

	public String getUnlocalized() {
		return unlocalized;
	}

	public static final EnumPelletType values[] = values();

	public static String[] names() {
		EnumPelletType[] states = values;
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
}
