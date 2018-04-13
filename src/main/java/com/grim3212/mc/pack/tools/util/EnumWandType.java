package com.grim3212.mc.pack.tools.util;

import com.grim3212.mc.pack.tools.items.ItemMagicStone.StoneType;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public enum EnumWandType implements IStringSerializable {
	BOOM("dynomite", 100, new StoneType(EnumCrystalType.RED, EnumCrystalType.BLACK), 5f), 
	DEATH("death", 100, new StoneType(EnumCrystalType.YELLOW, EnumCrystalType.BLACK), 10f), 
	FIRE("fire", 100, new StoneType(EnumCrystalType.RED, EnumCrystalType.YELLOW), 0f), 
	POLYMORPH("polymorph", 100, new StoneType(EnumCrystalType.PURPLE, EnumCrystalType.BLACK), 20f),
	HEAL("heal", 100, new StoneType(EnumCrystalType.BLUE, EnumCrystalType.YELLOW), 10f),
	HUNGER("hunger", 100, new StoneType(EnumCrystalType.BLUE, EnumCrystalType.WHITE), 10f),
	ITEM("item", 100, new StoneType(EnumCrystalType.PURPLE, EnumCrystalType.GREEN), 10f),
	ARMOR("armor", 100, new StoneType(EnumCrystalType.PURPLE, EnumCrystalType.RED), 0f),
	HOME("home", 100, new StoneType(EnumCrystalType.GREEN, EnumCrystalType.RED), 0f);

	private final String unlocalized;
	private final int maxUses;
	private final StoneType stoneType;
	private float range;

	private EnumWandType(String name, int maxUses, StoneType stoneType, float range) {
		this.unlocalized = name;
		this.maxUses = maxUses;
		this.stoneType = stoneType;

		this.range = range;
	}

	public static final EnumWandType values[] = values();

	public static String[] names() {
		EnumWandType[] states = values;
		String[] names = new String[states.length];

		for (int i = 0; i < states.length; i++) {
			names[i] = states[i].getUnlocalized();
		}

		return names;
	}

	public static EnumWandType getWandType(ItemStack stack) {
		int meta = stack.getMetadata();

		for (int i = 0; i < values.length; i++) {
			EnumWandType type = values[i];

			if (type.getStoneType().getMeta() == meta) {
				return type;
			}
		}

		return null;
	}

	@Override
	public String getName() {
		return this.getUnlocalized();
	}

	public String getUnlocalized() {
		return unlocalized;
	}

	public int getMaxUses() {
		return maxUses;
	}

	public StoneType getStoneType() {
		return stoneType;
	}

	public float getRange() {
		return range;
	}
}
