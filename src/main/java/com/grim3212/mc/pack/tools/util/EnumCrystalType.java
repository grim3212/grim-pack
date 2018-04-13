package com.grim3212.mc.pack.tools.util;

import net.minecraft.util.IStringSerializable;

public enum EnumCrystalType implements IStringSerializable {
	RED(28, 12, 0xff0000, "ruby"), BLUE(25, 9, 255, "sapphire"), GREEN(26, 10, 65280, "emerald"), PURPLE(27, 11, 0xff00ff, "amethyst"), YELLOW(31, 15, 0xffff00, "citrine"), WHITE(29, 13, 0xffffff, "soulgem"), BLACK(30, 14, 0x604040, "necris");

	private final int shardIndex;
	private final int gemIndex;
	private final int crystalColor;
	private final String name;

	private EnumCrystalType(int shardIdx, int gemIdx, int color, String name) {
		this.shardIndex = shardIdx;
		this.gemIndex = gemIdx;
		this.crystalColor = color;
		this.name = name;
	}

	public static final EnumCrystalType values[] = values();

	public static String[] names() {
		EnumCrystalType[] states = values;
		String[] names = new String[states.length];

		for (int i = 0; i < states.length; i++) {
			names[i] = states[i].getUnlocalized();
		}

		return names;
	}

	public static EnumCrystalType fromString(String type) {
		for (EnumCrystalType crystalType : EnumCrystalType.values) {
			if (crystalType.getUnlocalized().equalsIgnoreCase(type)) {
				return crystalType;
			}
		}
		return null;
	}

	public String getUnlocalized() {
		return this.name;
	}

	@Override
	public String getName() {
		return this.getUnlocalized();
	}

	public int getCrystalColor() {
		return crystalColor;
	}

	public int getGemIndex() {
		return gemIndex;
	}

	public int getShardIndex() {
		return shardIndex;
	}
}
