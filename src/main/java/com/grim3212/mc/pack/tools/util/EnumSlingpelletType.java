package com.grim3212.mc.pack.tools.util;

public enum EnumSlingpelletType {

	STONE(2.0F), NETHERRACK(16.0F), IRON(8.0F), EXPLOSIVE(12.0F), FIRE(12.0F), SLIME(12.0F), LIGHT(8.0F);

	private float damage;

	EnumSlingpelletType(float damage) {
		this.damage = damage;
	}

	public float getDamage() {
		return damage;
	}
}