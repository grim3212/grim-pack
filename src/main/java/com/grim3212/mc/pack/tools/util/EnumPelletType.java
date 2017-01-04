package com.grim3212.mc.pack.tools.util;

import com.grim3212.mc.pack.tools.config.ToolsConfig;

import net.minecraft.util.IStringSerializable;

public enum EnumPelletType implements IStringSerializable {
	STONE(ToolsConfig.stonePelletDamage, "stone"), IRON(ToolsConfig.ironPelletDamage, "iron"), NETHERRACK(ToolsConfig.netherrackPelletDamage, "netherrack"), LIGHT(ToolsConfig.lightPelletDamage, "light"), FIRE(ToolsConfig.firePelletDamage, "fire"), EXPLOSION(ToolsConfig.explosivePelletDamage, "explosion");

	private float damage;
	private String unlocalized;

	private EnumPelletType(float damage, String unlocalized) {
		this.damage = damage;
		this.unlocalized = unlocalized;
	}

	public float getDamage() {
		return damage;
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
