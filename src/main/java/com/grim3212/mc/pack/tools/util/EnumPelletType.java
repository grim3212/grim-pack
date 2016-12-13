package com.grim3212.mc.pack.tools.util;

import net.minecraft.util.IStringSerializable;

public enum EnumPelletType implements IStringSerializable {
	STONE(0.5F, "stone"), IRON(1.5F, "iron"), NETHERRACK(1.0F, "netherrack"), LIGHT(1.0F, "light"), FIRE(1.5F, "fire"), EXPLOSION(1.5F, "explosion");

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
