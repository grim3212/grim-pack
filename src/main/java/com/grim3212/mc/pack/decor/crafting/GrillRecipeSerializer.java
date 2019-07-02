package com.grim3212.mc.pack.decor.crafting;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.grim3212.mc.pack.core.config.BasicRecipe;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.crafting.GrillRecipeSerializer.GrillRecipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class GrillRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<GrillRecipe> {

	@Override
	public GrillRecipe read(ResourceLocation recipeId, JsonObject json) {
		Ingredient in = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));
		ItemStack out = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
		float exp = JSONUtils.getFloat(json, "experience", 0.0f);
		return new GrillRecipe(in, out, exp);
	}

	@Override
	public GrillRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		Ingredient in = Ingredient.read(buffer);
		ItemStack out = buffer.readItemStack();
		float exp = buffer.readFloat();
		return new GrillRecipe(in, out, exp);
	}

	@Override
	public void write(PacketBuffer buffer, GrillRecipe recipe) {
		recipe.getInput().write(buffer);
		buffer.writeItemStack(recipe.getRecipeOutput());
		buffer.writeFloat(recipe.getExperience());
	}

	public static Map<Ingredient, Pair<ItemStack, Float>> grillRecipes = Maps.newHashMap();

	public static boolean grillRecipesContain(ItemStack stack) {
		NonNullList<Ingredient> required = NonNullList.create();
		required.addAll(grillRecipes.keySet());

		Iterator<Ingredient> req = required.iterator();

		while (req.hasNext()) {
			if (req.next().test(stack)) {
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

			if (in.test(stack)) {
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

	protected class GrillRecipe extends BasicRecipe {

		public GrillRecipe(Ingredient input, ItemStack output, float experience) {
			super(DecorRecipes.GRILL_TYPE, input, output, experience);
			grillRecipes.put(input, Pair.of(output, experience));

			GrimLog.debugInfo(GrimDecor.partName, "Registered grill recipe " + input.getMatchingStacks()[0].getDisplayName() + " -> " + output.getDisplayName() + " : " + experience);
		}

		@Override
		public ResourceLocation getId() {
			return null;
		}

		@Override
		public IRecipeSerializer<?> getSerializer() {
			return null;
		}
	}
}