package com.grim3212.mc.pack.industry.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class MachineRecipes {

	public static final MachineRecipes INSTANCE = new MachineRecipes();
	private Map<Ingredient, Pair<ItemStack, Float>> modernFurnaceRecipes = Maps.newHashMap();
	private Map<Ingredient, Pair<ItemStack, Float>> derrickRecipes = Maps.newHashMap();
	private Map<Ingredient, Pair<ItemStack, Float>> refineryRecipes = Maps.newHashMap();

	private List<Pair<ItemStack, Integer>> modernFurnaceFuel = Lists.newArrayList();

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

	public ItemStack getResult(Object in, MachineType type) {
		if (in instanceof String) {
			return getResult((String) in, type);
		} else if (in instanceof ItemStack) {
			return getResult((ItemStack) in, type);
		}
		return ItemStack.EMPTY;
	}

	public ItemStack getResult(String oreDict, MachineType type) {
		Map<Ingredient, Pair<ItemStack, Float>> recipes = this.getRecipeList(type);

		NonNullList<Ingredient> required = NonNullList.create();
		required.addAll(recipes.keySet());
		Iterator<Ingredient> req = required.iterator();

		while (req.hasNext()) {
			Ingredient in = req.next();

			for (ItemStack stack : OreDictionary.getOres(oreDict)) {
				if (in.apply(stack)) {
					return recipes.get(in).getLeft().copy();
				}
			}
		}

		return ItemStack.EMPTY;
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

	public float getSmeltingExperience(ItemStack output, MachineType type) {
		if (type == MachineType.MODERN_FURNACE) {
			float ret = output.getItem().getSmeltingExperience(output);

			Collection<Pair<ItemStack, Float>> values = this.getRecipeList(type).values();
			Iterator<Pair<ItemStack, Float>> itr = values.iterator();

			while (itr.hasNext()) {
				Pair<ItemStack, Float> outputs = itr.next();

				if (!outputs.getLeft().isEmpty()) {
					if (outputs.getLeft().isItemEqual(output)) {
						ret = outputs.getRight();
					}
				}
			}

			if (ret != -1)
				return ret;
			else
				return FurnaceRecipes.instance().getSmeltingExperience(output);
		} else {
			Collection<Pair<ItemStack, Float>> values = this.getRecipeList(type).values();
			Iterator<Pair<ItemStack, Float>> itr = values.iterator();

			while (itr.hasNext()) {
				Pair<ItemStack, Float> outputs = itr.next();

				if (!outputs.getLeft().isEmpty()) {
					if (outputs.getLeft().isItemEqual(output)) {
						return outputs.getRight();
					}
				}
			}
		}

		return 0.0F;
	}

	public void addModernFurnaceFuel(ItemStack stack, int burnTime) {
		modernFurnaceFuel.add(Pair.of(stack, burnTime));
	}

	public List<Pair<ItemStack, Integer>> getModernFurnaceFuel() {
		return modernFurnaceFuel;
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
