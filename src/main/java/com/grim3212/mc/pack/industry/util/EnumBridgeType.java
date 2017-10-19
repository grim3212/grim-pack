package com.grim3212.mc.pack.industry.util;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.util.IStringSerializable;

public enum EnumBridgeType implements IStringSerializable {
	LASER("laser", 65280, true), ACCEL("accel", 30975, true), TRICK("trick", 0xffff00, false), DEATH("death", 0xff0000, false), GRAVITY("gravity", 0xffffff, false);

	private final String unlocalized;
	private final int renderColor;
	private final boolean isSolid;

	private EnumBridgeType(String unlocalized, int renderColor, boolean isSolid) {
		this.unlocalized = unlocalized;
		this.renderColor = renderColor;
		this.isSolid = isSolid;
	}

	public int getRenderColor() {
		return renderColor;
	}

	public String getUnlocalized() {
		return unlocalized;
	}

	public boolean isSolid() {
		return isSolid;
	}

	public static EnumBridgeType[] getValues() {
		return values;
	}

	public static final EnumBridgeType values[] = values();

	public static String[] names() {
		EnumBridgeType[] states = values;
		String[] names = new String[states.length];

		for (int i = 0; i < states.length; i++) {
			names[i] = states[i].getUnlocalized();
		}

		return names;
	}

	public Page getPage() {
		switch (this) {
		case ACCEL:
			return ManualIndustry.bridgeAccel_page;
		case DEATH:
			return ManualIndustry.bridgeDeath_page;
		case GRAVITY:
			return ManualIndustry.gravLift_page;
		case TRICK:
			return ManualIndustry.bridgeTrick_page;
		default:
			return ManualIndustry.bridgeLaser_page;
		}
	}

	@Override
	public String getName() {
		return this.getUnlocalized();
	}
}
