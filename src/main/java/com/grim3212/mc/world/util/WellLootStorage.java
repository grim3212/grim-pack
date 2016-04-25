package com.grim3212.mc.world.util;

import net.minecraft.item.ItemStack;

public class WellLootStorage {

	private ItemStack stack;
	private int minAmount;
	private int maxAmount;

	public WellLootStorage(ItemStack stack, int minAmount, int maxAmount) {
		if ((maxAmount >= minAmount) && (maxAmount != 0)) {
			this.stack = stack;
			this.minAmount = minAmount;
			this.maxAmount = maxAmount;
		} else {
			throw new RuntimeException("Minimum amount of a well loot can't be 0 and can't be higher than the maximum amount.");
		}
	}

	public boolean isOneItem() {
		return this.minAmount == this.maxAmount;
	}

	public ItemStack getStack() {
		return stack;
	}

	public int getMinAmount() {
		return minAmount;
	}

	public int getMaxAmount() {
		return maxAmount;
	}
}