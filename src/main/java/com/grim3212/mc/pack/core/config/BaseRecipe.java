package com.grim3212.mc.pack.core.config;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class BaseRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	protected Ingredient input;
	protected ItemStack output;
	protected float experience;

	public BaseRecipe() {
		this.input = Ingredient.EMPTY;
		this.output = ItemStack.EMPTY;
		this.experience = 0.0F;
	}

	public BaseRecipe(Ingredient input, ItemStack output, float experience) {
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
		return input.getMatchingStacks()[0] + ">" + output + ">" + experience;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
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
}
