package com.grim3212.mc.tools.util;

public enum EnumSpearType {

	STONE(0.5D), IRON(1.2D, 6.0D), DIAMOND(2.5D, 7.0D), EXPLOSIVE(1.2D), FIRE(0.5D), SLIME(1.2D), LIGHT(0.5D), LIGHTNING(1.2D);

	private double damage;
	private double itemDamage;

	EnumSpearType(double damage) {
		this(damage, 4D);
	}

	EnumSpearType(double damage, double itemDamage) {
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
