package com.grim3212.mc.pack.core.util;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.GrimPack;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

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

	public static ResourceLocation getRecipePath(String fullPath) {
		return new ResourceLocation(fullPath);
	}

	public static ResourceLocation createPath(String path) {
		return new ResourceLocation(GrimPack.modID, path);
	}

	/**
	 * Get what the path should start with and it will find all others
	 * 
	 * @param path
	 * @return
	 */
	public static List<ResourceLocation> getAllPaths(String path) {
		List<ResourceLocation> recipePaths = Lists.newArrayList();

		Iterator<IRecipe> recipes = ForgeRegistries.RECIPES.iterator();

		while (recipes.hasNext()) {
			IRecipe recipe = recipes.next();

			if (recipe.getRegistryName().getResourcePath().startsWith(path)) {
				recipePaths.add(recipe.getRegistryName());
			}
		}

		return recipePaths;
	}

	/**
	 * Get what the path should end with and it will find all others
	 * 
	 * @param path
	 * @return
	 */
	public static List<ResourceLocation> getAllPathsEnd(String path) {
		List<ResourceLocation> recipePaths = Lists.newArrayList();

		Iterator<IRecipe> recipes = ForgeRegistries.RECIPES.iterator();

		while (recipes.hasNext()) {
			IRecipe recipe = recipes.next();

			if (recipe.getRegistryName().getResourcePath().endsWith(path)) {
				recipePaths.add(recipe.getRegistryName());
			}
		}

		return recipePaths;
	}

	/**
	 * Should be called in PostInit to not cause broken recipes
	 * 
	 * @param stackToFind
	 * @param oreName
	 * @param recipeExclusions
	 */
	public static void replaceRecipes(ItemStack stackToFind, String oreName, NonNullList<ItemStack> recipeExclusions) {
		int replaced = 0;
		if (oreName == null || oreName.isEmpty()) {
			GrimLog.error(GrimPack.modName, "Ore Name cannot be null!");
		} else {
			// Search vanilla recipes for recipes to replace
			for (IRecipe obj : ForgeRegistries.RECIPES) {
				if (obj.getClass() == ShapedRecipes.class || obj.getClass() == ShapelessRecipes.class) {
					ItemStack output = obj.getRecipeOutput();
					if (!output.isEmpty() && OreDictionary.containsMatch(false, recipeExclusions, output)) {
						continue;
					}

					NonNullList<Ingredient> lst = obj.getIngredients();
					for (int x = 0; x < lst.size(); x++) {
						Ingredient ing = lst.get(x);

						if (ing != Ingredient.EMPTY) {
							ItemStack[] ingredients = ing.getMatchingStacks();

							for (ItemStack stack : ingredients) {
								if (OreDictionary.itemMatches(stackToFind, stack, true)) {
									// Replace!
									lst.set(x, new OreIngredient(oreName));
									replaced++;
								}
							}
						}
					}
				}
			}
		}
		GrimLog.info(GrimPack.modName, "Replaced " + replaced + " ingredients with oredict " + oreName);
	}
}
