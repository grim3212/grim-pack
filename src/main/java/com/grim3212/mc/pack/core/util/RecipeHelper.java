package com.grim3212.mc.pack.core.util;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.grim3212.mc.pack.GrimPack;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeHelper {

	public static class BlockStack {

		private Block block;
		private int meta;

		public BlockStack(Block block, int meta) {
			this.block = block;
			this.meta = meta;
		}

		public Block getBlock() {
			return block;
		}

		public int getMeta() {
			return meta;
		}
	}

	public static BlockStack getBlockStack(JsonObject json) {

		String blockName = GrimPack.modID + ":" + JsonUtils.getString(json, "item");

		Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName));

		if (block == null)
			throw new JsonSyntaxException("Unknown block '" + blockName + "'");

		return new BlockStack(block, JsonUtils.getInt(json, "data", 0));
	}

	public static boolean isBlock(ItemStack in) {
		if (in.getItem() instanceof ItemBlock) {
			return true;
		}
		return false;
	}

	/**
	 * Gets an ore dictionary name for a List of itemstacks
	 *
	 * @param ores A list of itemstacks representing the complete contents of a ore
	 *             dictionary entry
	 * @return A string that represents this ore dictionary list
	 */
	/*
	 * public static String getOreDict(ItemStack[] ores) { // List<Integer> oreIds =
	 * Lists.newArrayList(); // // for (ItemStack stack : ores) { // for (int id :
	 * OreDictionary.getOreIDs(stack)) { // oreIds.add(id); // } // }
	 * 
	 * int[] oreNums = OreDictionary.getOreIDs(ores[0]);
	 * 
	 * for (int i = 0; i < oreNums.length; i++) { if (containsMatch(false, ores,
	 * OreDictionary.getOres(OreDictionary.getOreName(oreNums[i])))) { return
	 * OreDictionary.getOreName(oreNums[i]); } }
	 * 
	 * return "No Ore Dict Found!"; }
	 */

	/*
	 * public static String getOreDict(NonNullList<ItemStack> ores) { int[] oreNums
	 * = OreDictionary.getOreIDs(ores.get(0));
	 * 
	 * for (int i = 0; i < oreNums.length; i++) { if (containsMatch(false, ores,
	 * OreDictionary.getOres(OreDictionary.getOreName(oreNums[i])))) { return
	 * OreDictionary.getOreName(oreNums[i]); } }
	 * 
	 * return "No Ore Dict Found!"; }
	 */

	/**
	 * Reversed order from OreDictionary so I don't have to convert arrays to lists
	 * and vice versa
	 * 
	 * @param strict
	 * @param inputs
	 * @param targets
	 * @return
	 */
	/*
	 * public static boolean containsMatch(boolean strict, ItemStack[] inputs,
	 * NonNullList<ItemStack> targets) { for (ItemStack input : inputs) { for
	 * (ItemStack target : targets) { if (OreDictionary.itemMatches(target, input,
	 * strict)) { return true; } } } return false; }
	 */

	/**
	 * Reversed order from OreDictionary so I don't have to convert arrays to lists
	 * and vice versa
	 * 
	 * @param strict
	 * @param inputs
	 * @param targets
	 * @return
	 */
	/*
	 * public static boolean containsMatch(boolean strict, NonNullList<ItemStack>
	 * inputs, NonNullList<ItemStack> targets) { for (ItemStack input : inputs) {
	 * for (ItemStack target : targets) { if (OreDictionary.itemMatches(target,
	 * input, strict)) { return true; } } } return false; }
	 */

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

		Iterator<IRecipe> recipes = Minecraft.getInstance().world.getRecipeManager().getRecipes().iterator();

		while (recipes.hasNext()) {
			IRecipe recipe = recipes.next();

			if (recipe.getId().getPath().startsWith(path)) {
				recipePaths.add(recipe.getId());
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

		Iterator<IRecipe> recipes = Minecraft.getInstance().world.getRecipeManager().getRecipes().iterator();

		while (recipes.hasNext()) {
			IRecipe recipe = recipes.next();

			if (recipe.getId().getPath().endsWith(path)) {
				recipePaths.add(recipe.getId());
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
	/*
	 * public static void replaceRecipes(ItemStack stackToFind, String tagName,
	 * NonNullList<ItemStack> recipeExclusions) { int replaced = 0; if (tagName ==
	 * null || tagName.isEmpty()) { GrimLog.error(GrimPack.modName,
	 * "Tag Name cannot be null!"); } else { // Search vanilla recipes for recipes
	 * to replace for (IRecipe obj :
	 * Minecraft.getInstance().world.getRecipeManager().getRecipes()) { if
	 * (obj.getClass() == ShapedRecipe.class || obj.getClass() ==
	 * ShapelessRecipe.class) { ItemStack output = obj.getRecipeOutput(); if
	 * (!output.isEmpty() && OreDictionary.containsMatch(false, recipeExclusions,
	 * output)) { continue; }
	 * 
	 * NonNullList<Ingredient> lst = obj.getIngredients(); for (int x = 0; x <
	 * lst.size(); x++) { Ingredient ing = lst.get(x);
	 * 
	 * if (ing != Ingredient.EMPTY) { ItemStack[] ingredients =
	 * ing.getMatchingStacks();
	 * 
	 * for (ItemStack stack : ingredients) { if
	 * (OreDictionary.itemMatches(stackToFind, stack, true)) { // Replace!
	 * lst.set(x, new OreIngredient(oreName)); replaced++; } } } } } } }
	 * GrimLog.info(GrimPack.modName, "Replaced " + replaced +
	 * " ingredients with oredict " + oreName); }
	 */
}
