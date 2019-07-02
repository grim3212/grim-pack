package com.grim3212.mc.pack.core.config;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.World;

public abstract class BasicRecipe implements IRecipe<IInventory> {

	protected IRecipeType<?> typeIn;
	protected Ingredient input;
	protected ItemStack output;
	protected float experience;

	public BasicRecipe() {
		this.input = Ingredient.EMPTY;
		this.output = ItemStack.EMPTY;
		this.experience = 0.0F;
	}

	public BasicRecipe(IRecipeType<?> typeIn, Ingredient input, ItemStack output, float experience) {
		this.input = input;
		this.output = output;
		this.experience = experience;
	}

	public Ingredient getInput() {
		return input;
	}

	public float getExperience() {
		return experience;
	}

	@Override
	public String toString() {
		return input + ">" + output + ">" + experience;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(IInventory inv, World worldIn) {
		return false;
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canFit(int width, int height) {
		return false;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

	@Override
	public IRecipeType<?> getType() {
		return this.typeIn;
	}
}
