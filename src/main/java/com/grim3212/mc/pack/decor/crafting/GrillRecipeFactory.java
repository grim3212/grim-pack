package com.grim3212.mc.pack.decor.crafting;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.grim3212.mc.pack.core.config.BaseRecipe;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.decor.GrimDecor;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class GrillRecipeFactory implements IRecipeFactory {

	public static Map<Ingredient, Pair<ItemStack, Float>> grillRecipes = Maps.newHashMap();

	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		Ingredient in = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, "input"), context);
		ItemStack out = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "output"), context);
		float exp = JsonUtils.getFloat(json, "experience", 0.0f);

		return new GrillRecipe(in, out, exp);
	}

	private class GrillRecipe extends BaseRecipe {

		public GrillRecipe(Ingredient input, ItemStack output, float experience) {
			super(input, output, experience);
			grillRecipes.put(input, Pair.of(output, experience));

			GrimLog.debugInfo(GrimDecor.partName, "Registered grill recipe " + input.getMatchingStacks()[0].toString() + " -> " + output + " : " + experience);
		}
	}

	public static boolean grillRecipesContain(ItemStack stack) {
		NonNullList<Ingredient> required = NonNullList.create();
		required.addAll(grillRecipes.keySet());

		Iterator<Ingredient> req = required.iterator();

		while (req.hasNext()) {
			if (req.next().apply(stack)) {
				return true;
			}
		}

		return false;
	}

	public static ItemStack getOutput(ItemStack stack) {
		NonNullList<Ingredient> required = NonNullList.create();
		required.addAll(grillRecipes.keySet());
		Iterator<Ingredient> req = required.iterator();

		while (req.hasNext()) {
			Ingredient in = req.next();

			if (in.apply(stack)) {
				return grillRecipes.get(in).getLeft().copy();
			}
		}

		return ItemStack.EMPTY;
	}

	public static float getExperience(ItemStack output) {
		Collection<Pair<ItemStack, Float>> values = grillRecipes.values();
		Iterator<Pair<ItemStack, Float>> itr = values.iterator();

		while (itr.hasNext()) {
			Pair<ItemStack, Float> outputs = itr.next();

			if (!outputs.getLeft().isEmpty()) {
				if (outputs.getLeft().isItemEqual(output)) {
					return outputs.getRight();
				}
			}
		}

		return 0.0f;
	}
}
