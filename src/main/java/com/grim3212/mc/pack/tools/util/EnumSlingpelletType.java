package com.grim3212.mc.pack.tools.util;

public enum EnumSlingpelletType {

	STONE(0.5D), NETHERRACK(1.2D, 6.0D), IRON(1.2D, 6.0D), EXPLOSIVE(1.2D), FIRE(0.5D), SLIME(1.2D), LIGHT(0.5D);

	private double damage;
	private double itemDamage;

	EnumSlingpelletType(double damage) {
		this(damage, 4D);
	}

	EnumSlingpelletType(double damage, double itemDamage) {
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