package com.grim3212.mc.pack.core.util;

import java.util.Iterator;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class OreDictionaryHelper {

	/**
	 * Should be called in PostInit after all recipes are loaded
	 * 
	 * @param stackToFind
	 * @param oreName
	 * @param recipeExclusions
	 */
	public static void replaceRecipes(ItemStack stackToFind, String oreName, ItemStack[] recipeExclusions) {
		Iterator<IRecipe> itr = ForgeRegistries.RECIPES.iterator();

		while (itr.hasNext()) {
			IRecipe recipe = itr.next();

			for (Ingredient ingredient : recipe.getIngredients()) {
				// Found an appropriate ingredient
				if (ingredient.apply(stackToFind)) {
					if (!recipe.getRecipeOutput().isEmpty()) {
						boolean replace = true;
						// Check exclusions
						for (ItemStack stack : recipeExclusions) {
							if (!stack.isEmpty() && ItemStack.areItemStacksEqual(recipe.getRecipeOutput(), stack)) {
								// Don't replace
								replace = false;
								break;
							}
						}

						// Try and replace the recipe
						if (replace) {
							// TODO: Implement replacing code
						}
					}
				}
			}
		}
	}
}
