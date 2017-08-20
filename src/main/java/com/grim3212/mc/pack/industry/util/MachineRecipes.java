package com.grim3212.mc.pack.industry.util;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;

public class MachineRecipes {

	public static final MachineRecipes INSTANCE = new MachineRecipes();
	private Map<Ingredient, Pair<ItemStack, Float>> modernFurnaceRecipes = Maps.newHashMap();
	private Map<Ingredient, Pair<ItemStack, Float>> derrickRecipes = Maps.newHashMap();
	private Map<Ingredient, Pair<ItemStack, Float>> refineryRecipes = Maps.newHashMap();

	public NonNullList<ItemStack> getInputs(MachineType type) {
		Map<Ingredient, Pair<ItemStack, Float>> recipes = this.getRecipeList(type);

		NonNullList<ItemStack> inputs = NonNullList.create();
		NonNullList<Ingredient> required = NonNullList.create();
		required.addAll(recipes.keySet());
		Iterator<Ingredient> req = required.iterator();

		while (req.hasNext()) {
			Ingredient in = req.next();

			if (in.getMatchingStacks().length > 0) {
				inputs.add(in.getMatchingStacks()[0]);
			}
		}

		return inputs;
	}

	/**
	 * 
	 * @param input
	 *            Recipe in
	 * @param output
	 *            Recipe out
	 * @param experience
	 *            The experience when finished
	 * @param recipeList
	 *            0 = modern furnace, 1 = derrick, 2 = refinery
	 */
	public void addRecipe(Ingredient input, ItemStack output, float experience, MachineType type) {
		this.getRecipeList(type).put(input, Pair.of(output, experience));
	}

	public ItemStack getResult(ItemStack stack, MachineType type) {
		Map<Ingredient, Pair<ItemStack, Float>> recipes = this.getRecipeList(type);

		NonNullList<Ingredient> required = NonNullList.create();
		required.addAll(recipes.keySet());
		Iterator<Ingredient> req = required.iterator();

		while (req.hasNext()) {
			Ingredient in = req.next();

			if (in.apply(stack)) {
				return recipes.get(in).getLeft().copy();
			}
		}

		return ItemStack.EMPTY;
	}

	public float getSmeltingExperience(ItemStack stack, MachineType type) {
		float ret = stack.getItem().getSmeltingExperience(stack);
		if (ret != -1)
			return ret;

		if (type == MachineType.MODERN_FURNACE) {
			NonNullList<Ingredient> required = NonNullList.create();
			required.addAll(this.getRecipeList(type).keySet());
			Iterator<Ingredient> req = required.iterator();

			while (req.hasNext()) {
				Ingredient in = req.next();

				if (in.apply(stack)) {
					ret = this.getRecipeList(type).get(in).getRight();
					break;
				}
			}

			if (ret != -1)
				return ret;
			else
				return FurnaceRecipes.instance().getSmeltingExperience(stack);
		} else {
			NonNullList<Ingredient> required = NonNullList.create();
			required.addAll(this.getRecipeList(type).keySet());
			Iterator<Ingredient> req = required.iterator();

			while (req.hasNext()) {
				Ingredient in = req.next();

				if (in.apply(stack)) {
					return this.getRecipeList(type).get(in).getRight();
				}
			}
		}

		return 0.0F;
	}

	public Map<Ingredient, Pair<ItemStack, Float>> getModernFurnaceList() {
		return this.modernFurnaceRecipes;
	}

	public Map<Ingredient, Pair<ItemStack, Float>> getDerrickList() {
		return this.derrickRecipes;
	}

	public Map<Ingredient, Pair<ItemStack, Float>> getRefineryList() {
		return this.refineryRecipes;
	}

	public Map<Ingredient, Pair<ItemStack, Float>> getRecipeList(MachineType type) {
		switch (type) {
		case DERRICK:
			return this.getDerrickList();
		case MODERN_FURNACE:
			return this.getModernFurnaceList();
		case REFINERY:
			return this.getRefineryList();
		default:
			return null;
		}
	}

	public enum MachineType implements IStringSerializable {
		MODERN_FURNACE, DERRICK, REFINERY;

		public static final MachineType values[] = MachineType.values();

		@Override
		public String getName() {
			return this.name();
		}

		public static MachineType fromJson(JsonObject json) {
			String s = JsonUtils.getString(json, "machine");

			switch (s) {
			case "derrick":
				return MachineType.DERRICK;
			case "modern_furnace":
				return MachineType.MODERN_FURNACE;
			case "refinery":
				return MachineType.REFINERY;
			default:
				throw new JsonSyntaxException("Machine type '" + s + "' does not exist!");
			}
		}
	}
}
