package com.grim3212.mc.pack.core.config;

import net.minecraft.item.ItemStack;

public class Recipe {

	protected ItemStack input;
	protected ItemStack output;
	protected float experience;

	public Recipe() {
		this.input = ItemStack.EMPTY;
		this.output = ItemStack.EMPTY;
		this.experience = 0.0F;
	}

	public Recipe(ItemStack input, ItemStack output, float experience) {
		this.input = input;
		this.output = output;
		this.experience = experience;
	}

	public ItemStack getInput() {
		return input;
	}

	public ItemStack getOutput() {
		return output;
	}

	public float getExperience() {
		return experience;
	}

	@Override
	public String toString() {
		return input.getUnlocalizedName() + ">" + output.getUnlocalizedName() + ">" + experience;
	}
}
