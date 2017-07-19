package com.grim3212.mc.pack.tools.util;

public enum EnumSpearType {

	STONE("spear", 0.5D), IRON("iron_spear", 1.2D, 6.0D), DIAMOND("diamond_spear", 2.5D, 7.0D), EXPLOSIVE("explosive_spear", 1.2D), FIRE("fire_spear", 0.5D), SLIME("slime_spear", 1.2D), LIGHT("light_spear", 0.5D), LIGHTNING("lightning_spear", 1.2D);

	private double damage;
	private double itemDamage;
	private String registryName;

	EnumSpearType(String registryName, double damage) {
		this(registryName, damage, 4D);
	}

	EnumSpearType(String registryName, double damage, double itemDamage) {
		this.damage = damage;
		this.itemDamage = itemDamage;
		this.registryName = registryName;
	}

	public double getDamage() {
		return damage;
	}

	public double getItemDamage() {
		return itemDamage;
	}

	public String getRegistryName() {
		return registryName;
	}
}
