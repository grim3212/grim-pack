package com.grim3212.mc.pack.core.config;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class BasicRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	protected ItemStack input;
	protected ItemStack output;
	protected float experience;

	public BasicRecipe() {
		this.input = ItemStack.EMPTY;
		this.output = ItemStack.EMPTY;
		this.experience = 0.0F;
	}

	public BasicRecipe(ItemStack input, ItemStack output, float experience) {
		this.input = input;
		this.output = output;
		this.experience = experience;
	}

	public ItemStack getInput() {
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
