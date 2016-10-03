package com.grim3212.mc.pack.tools.util;

public enum EnumSlingshotType {

	STONE(0.5D), IRON(1.2D, 6.0D), BLACK_DIAMOND(2.5D, 7.0D);

	private double damage;
	private double itemDamage;

	EnumSlingshotType(double damage) {
		this(damage, 4D);
	}

	EnumSlingshotType(double damage, double itemDamage) {
		this.damage = damage;
		this.itemDamage = itemDamage;
	}

	public double getDamage() {
		return damage;
	}

	public double getItemDamage() {
		return itemDamage;
	}
}