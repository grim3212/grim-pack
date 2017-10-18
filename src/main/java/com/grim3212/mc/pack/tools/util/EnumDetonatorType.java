package com.grim3212.mc.pack.tools.util;

import net.minecraft.util.IStringSerializable;

public enum EnumDetonatorType implements IStringSerializable {
	THERMAL("thermal_detonator", 1.0f, 50, 2.5f, false, true), SOLAR("solar_detonator", 1.0f, 30, 5f, true, false), NUKEULATOR("nukeulator", 1.25f, 150, 10f, false, false);

	private final String unlocalized;
	private final float throwSpeed;
	private final int fuseDelay;
	private final float explosionSize;
	private final boolean entitiesOnly;
	private final boolean fire;

	private EnumDetonatorType(String unlocalized, float throwSpeed, int fuseDelay, float explosionSize, boolean entitiesOnly, boolean fire) {
		this.unlocalized = unlocalized;
		this.throwSpeed = throwSpeed;
		this.fuseDelay = fuseDelay;
		this.explosionSize = explosionSize;
		this.entitiesOnly = entitiesOnly;
		this.fire = fire;
	}

	public String getUnlocalized() {
		return unlocalized;
	}

	public float getThrowSpeed() {
		return throwSpeed;
	}

	public int getFuseDelay() {
		return fuseDelay;
	}

	public float getExplosionSize() {
		return explosionSize;
	}

	public boolean isEntitiesOnly() {
		return entitiesOnly;
	}

	public boolean isFire() {
		return fire;
	}

	public static EnumDetonatorType[] getValues() {
		return values;
	}

	public static final EnumDetonatorType values[] = values();

	public static String[] names() {
		EnumDetonatorType[] states = values;
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
