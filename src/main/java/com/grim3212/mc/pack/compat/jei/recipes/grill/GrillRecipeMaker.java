package com.grim3212.mc.pack.compat.jei.recipes.grill;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.decor.crafting.GrillRecipeFactory;

import mezz.jei.api.IJeiHelpers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public final class GrillRecipeMaker {

	private GrillRecipeMaker() {
	}

	public static List<GrillRecipeWrapper> getGrillRecipes(IJeiHelpers helpers) {
		Map<Ingredient, Pair<ItemStack, Float>> grillMap = GrillRecipeFactory.grillRecipes;

		List<GrillRecipeWrapper> recipes = Lists.newArrayList();

		for (Entry<Ingredient, Pair<ItemStack, Float>> entry : grillMap.entrySet()) {
			Ingredient input = entry.getKey();
			ItemStack output = entry.getValue().getLeft();

			List<ItemStack> inputs = Lists.newArrayList(input.getMatchingStacks());
			GrillRecipeWrapper recipe = new GrillRecipeWrapper(inputs, output);
			recipes.add(recipe);
		}

		return recipes;
	}
}
