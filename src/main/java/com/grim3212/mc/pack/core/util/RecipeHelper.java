package com.grim3212.mc.pack.core.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.GrimPack;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeHelper {

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

	public static ItemStack getItemStackFromString(String s) {
		try {
			if (s.contains(":")) {
				String[] split = s.split(":");

				if (split.length == 2) {
					return new ItemStack((Item) Item.REGISTRY.getObject(new ResourceLocation(s)));
				} else if (split.length == 3) {
					return new ItemStack((Item) Item.REGISTRY.getObject(new ResourceLocation(split[0], split[1])), 1, Integer.parseInt(split[2]));
				}
			} else {
				return new ItemStack((Item) Item.REGISTRY.getObject(new ResourceLocation(s)));
			}
		} catch (Exception e) {
			GrimLog.error(GrimPack.modName, e.getMessage());
			return ItemStack.EMPTY;
		}

		return ItemStack.EMPTY;
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
