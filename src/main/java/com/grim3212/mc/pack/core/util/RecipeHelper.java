package com.grim3212.mc.pack.core.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeHelper {

	/**
	 * Compare item stack based on damage. Takes into account
	 * OreDictionary.WILDCARD_VALUE
	 * 
	 * @param stack1
	 * @param stack2
	 * @return
	 */
	public static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == OreDictionary.WILDCARD_VALUE || stack2.getMetadata() == stack1.getMetadata());
	}

	public static IRecipe getLatestIRecipe() {
		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		return recipeList.get(recipeList.size() - 1);
	}

	/**
	 * Gets an ore dictionary name for a List of itemstacks
	 *
	 * @param ores
	 *            A list of itemstacks representing the complete contents of a
	 *            ore dictionary entry
	 * @return A string that represents this ore dictionary list
	 */
	public static String getOreDict(List<ItemStack> ores) {
		int[] oreNums = OreDictionary.getOreIDs(ores.get(0));

		for (int i = 0; i < oreNums.length; i++) {
			if (ores.equals(OreDictionary.getOres(OreDictionary.getOreName(oreNums[i])))) {
				return OreDictionary.getOreName(oreNums[i]);
			}
		}

		return "No Ore Dict Found!";
	}

	public static List<IRecipe> getLatestIRecipes(int numRecipes) {
		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		List<IRecipe> recipes = new ArrayList<IRecipe>();

		for (int i = 1; i <= numRecipes; i++) {
			recipes.add(recipeList.get(recipeList.size() - (1 * i)));
		}

		return Lists.reverse(recipes);
	}

	public static IRecipe getQuickIRecipeForItemStack(ItemStack resultingItem) {
		IRecipe returnRecipe = null;
		for (int i = 0; i < CraftingManager.getInstance().getRecipeList().size(); i++) {
			IRecipe recipe = (IRecipe) CraftingManager.getInstance().getRecipeList().get(i);
			if (recipe == null)
				continue;

			ItemStack result = recipe.getRecipeOutput();
			if (result != null && result.isItemEqual(resultingItem)) {
				returnRecipe = recipe;
				break;
			}
		}

		return returnRecipe;
	}

	public static List<IRecipe> getAllIRecipesForItem(ItemStack resultingItem) {
		List<IRecipe> returnRecipe = new ArrayList<IRecipe>();
		for (int i = 0; i < CraftingManager.getInstance().getRecipeList().size(); i++) {
			IRecipe recipe = (IRecipe) CraftingManager.getInstance().getRecipeList().get(i);
			if (recipe == null)
				continue;

			ItemStack result = recipe.getRecipeOutput();
			if (result != null && result.getItem() == resultingItem.getItem())
				returnRecipe.add(recipe);
		}

		return returnRecipe;
	}

	public static List<IRecipe> getAllIRecipesForItemWithMetadata(ItemStack resultingItem) {
		List<IRecipe> returnRecipe = new ArrayList<IRecipe>();
		for (int i = 0; i < CraftingManager.getInstance().getRecipeList().size(); i++) {
			IRecipe recipe = (IRecipe) CraftingManager.getInstance().getRecipeList().get(i);
			if (recipe == null)
				continue;

			ItemStack result = recipe.getRecipeOutput();
			if (result != null && result.isItemEqual(resultingItem))
				returnRecipe.add(recipe);
		}

		return returnRecipe;
	}
}
