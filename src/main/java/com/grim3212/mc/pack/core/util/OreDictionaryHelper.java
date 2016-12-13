package com.grim3212.mc.pack.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class OreDictionaryHelper {

	public static void replaceRecipes(ItemStack stack, String name, ItemStack[] exclusions) {
		Map<ItemStack, String> replacements = Maps.newHashMap();
		replacements.put(stack, name);

		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		List<IRecipe> recipesToRemove = new ArrayList<IRecipe>();
		List<IRecipe> recipesToAdd = new ArrayList<IRecipe>();

		ItemStack[] replaceStacks = replacements.keySet().toArray(new ItemStack[replacements.keySet().size()]);

		for (Object obj : recipes) {
			if (obj instanceof ShapedRecipes) {
				ShapedRecipes recipe = (ShapedRecipes) obj;
				ItemStack output = recipe.getRecipeOutput();
				if (!output.isEmpty() && containsMatch(false, exclusions, output)) {
					continue;
				}

				if (containsMatch(true, recipe.recipeItems, replaceStacks)) {
					recipesToRemove.add(recipe);
					recipesToAdd.add(addShapedRecipe(replacements, recipe));
				}
			} else if (obj instanceof ShapelessRecipes) {
				ShapelessRecipes recipe = (ShapelessRecipes) obj;
				ItemStack output = recipe.getRecipeOutput();
				if (!output.isEmpty() && containsMatch(false, exclusions, output)) {
					continue;
				}

				if (containsMatch(true, (ItemStack[]) recipe.recipeItems.toArray(new ItemStack[recipe.recipeItems.size()]), replaceStacks)) {
					recipesToRemove.add((IRecipe) obj);
					recipesToAdd.add(addShapelessRecipe(replacements, recipe));
				}
			}

			if (obj instanceof ShapedOreRecipe) {
				ShapedOreRecipe recipe = (ShapedOreRecipe) obj;
				ItemStack output = recipe.getRecipeOutput();
				if (!output.isEmpty() && containsMatch(false, exclusions, output)) {
					continue;
				}

				ArrayList<ItemStack> check = new ArrayList<ItemStack>();
				for (Object object : recipe.getInput()) {
					ItemStack item = ItemStack.EMPTY;

					if (object instanceof ItemStack) {
						item = (ItemStack) object;
					} else if (object instanceof Item) {
						item = new ItemStack((Item) object);
					} else if (object instanceof Block) {
						item = new ItemStack((Block) object, 1, OreDictionary.WILDCARD_VALUE);
					}

					if (!item.isEmpty()) {
						check.add(item);
					}
				}

				if (!check.isEmpty() && containsMatch(true, check.toArray((new ItemStack[check.size()])), replaceStacks)) {
					recipesToRemove.add((IRecipe) obj);
					recipesToAdd.add(addShapedOreRecipe(replacements, recipe));
				}
			} else if (obj instanceof ShapelessOreRecipe) {
				ShapelessOreRecipe recipe = (ShapelessOreRecipe) obj;
				ItemStack output = recipe.getRecipeOutput();
				if (!output.isEmpty() && containsMatch(false, exclusions, output)) {
					continue;
				}

				ArrayList<ItemStack> check = new ArrayList<ItemStack>();
				for (Object object : recipe.getInput()) {
					ItemStack item = ItemStack.EMPTY;

					if (object instanceof ItemStack) {
						item = (ItemStack) object;
					} else if (object instanceof Item) {
						item = new ItemStack((Item) object);
					} else if (object instanceof Block) {
						item = new ItemStack((Block) object, 1, OreDictionary.WILDCARD_VALUE);
					}

					if (!item.isEmpty()) {
						check.add(item);
					}
				}

				if (!check.isEmpty() && containsMatch(true, check.toArray((new ItemStack[check.size()])), replaceStacks)) {
					recipesToRemove.add((IRecipe) obj);
					recipesToAdd.add(addShapelessOreRecipe(replacements, recipe));
				}
			}
		}

		recipes.removeAll(recipesToRemove);
		recipes.addAll(recipesToAdd);

		if (recipesToRemove.size() > 0) {
			GrimLog.info(Loader.instance().activeModContainer().getName(), "Replaced " + recipesToRemove.size() + " ore recipes for " + stack.getUnlocalizedName());
		}
	}

	private static boolean containsMatch(boolean strict, ItemStack[] inputs, ItemStack... targets) {
		if (targets != null && inputs != null)
			for (ItemStack input : inputs) {
				for (ItemStack target : targets) {
					if (OreDictionary.itemMatches(target, input, strict)) {
						return true;
					}
				}
			}
		return false;
	}

	private static ShapedOreRecipe addShapedRecipe(Map<ItemStack, String> toReplace, ShapedRecipes recipe) {
		ItemStack output = recipe.getRecipeOutput();
		int x = recipe.recipeWidth;
		int y = recipe.recipeHeight;
		ItemStack[] items = recipe.recipeItems;

		if (x * y > 9)
			return null;

		ArrayList<Object> inputArray = new ArrayList<Object>();

		String[] s = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
		Character[] c = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };

		String[] returnArray = new String[y];

		for (int i = 1; i < y + 1; i++) {
			String f = "";
			for (int k = 1; k < x + 1; k++) {
				f += items[k + ((i - 1) * x) - 1].isEmpty() ? " " : s[k + ((i - 1) * x) - 1];
			}
			returnArray[i - 1] = f;
		}

		for (int i = 0; i < returnArray.length; i++) {
			inputArray.add(returnArray[i]);
		}

		for (int i = 0; i < x * y; i++) {
			if (!items[i].isEmpty()) {
				ItemStack item = items[i];
				boolean b = false;

				for (Entry<ItemStack, String> entry : toReplace.entrySet()) {
					if (OreDictionary.itemMatches(entry.getKey(), item, true)) {
						inputArray.add(c[i]);
						inputArray.add(entry.getValue());
						b = true;
					}
				}

				if (!b) {
					inputArray.add(c[i]);
					inputArray.add(item);
				}
			}
		}

		Object[] newInputs = inputArray.toArray();
		return new ShapedOreRecipe(output, newInputs);
	}

	// Shapeless
	private static ShapelessOreRecipe addShapelessRecipe(Map<ItemStack, String> toReplace, ShapelessRecipes recipe) {
		ItemStack output = recipe.getRecipeOutput();
		List<ItemStack> items = (List<ItemStack>) recipe.recipeItems;
		ArrayList<Object> inputs = new ArrayList<Object>();

		for (ItemStack item : items) {
			boolean b = false;
			if (!item.isEmpty()) {
				for (Entry<ItemStack, String> entry : toReplace.entrySet()) {
					if (OreDictionary.itemMatches(entry.getKey(), item, true)) {
						String oreName = entry.getValue();
						inputs.add(oreName);
						b = true;
					}
				}

				if (!b) {
					inputs.add(item);
				}
			}
		}

		Object[] newInputs = inputs.toArray();
		return new ShapelessOreRecipe(output, newInputs);
	}

	@SuppressWarnings("unchecked")
	private static ShapedOreRecipe addShapedOreRecipe(Map<ItemStack, String> toReplace, ShapedOreRecipe recipe) {
		ItemStack output = recipe.getRecipeOutput();
		Object[] objects = recipe.getInput();
		ArrayList<Object> inputs = new ArrayList<Object>();

		int x = recipe.getWidth();
		int y = recipe.getHeight();

		if (x * y == 0 || x * y > 9)
			return null;

		String[] s = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
		Character[] c = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };

		String[] returnArray = new String[y];

		for (int i = 1; i < y + 1; i++) {
			String f = "";
			for (int k = 1; k < x + 1; k++) {
				f += objects[k + ((i - 1) * x) - 1] == null ? " " : s[k + ((i - 1) * x) - 1];
			}
			returnArray[i - 1] = f;
		}

		for (int i = 0; i < returnArray.length; i++) {
			inputs.add(returnArray[i]);
		}

		// item
		for (int i = 0; i < x * y; i++) {
			boolean b = false;
			ItemStack item = ItemStack.EMPTY;
			Object obj = objects[i];

			if (obj == null) {
				continue;
			}

			if (obj instanceof ItemStack) {
				item = (ItemStack) obj;
			} else if (obj instanceof Item) {
				item = new ItemStack((Item) obj);
			} else if (obj instanceof Block) {
				item = new ItemStack((Block) obj, 1, OreDictionary.WILDCARD_VALUE);
			} else if (obj instanceof List) {
				if ((List<ItemStack>) obj != null && !((List<ItemStack>) obj).isEmpty()) {
					List<ItemStack> list = (List<ItemStack>) obj;

					for (ItemStack oreItem : list) {
						if (oreItem.isEmpty() || OreDictionary.getOreIDs(oreItem).length == 0)
							continue;
						int[] id = OreDictionary.getOreIDs(oreItem);

						for (int j = 0; j < id.length; j++) {
							String str = OreDictionary.getOreName(OreDictionary.getOreIDs(oreItem)[j]);
							if (str != null) {
								inputs.add(c[i]);
								inputs.add(str);
								b = true;
								break;
							}
						}
						if (b)
							break;
					}
				}

				if (!b) {
					inputs.add(c[i]);
					inputs.add("Unknown");
					b = true;
				}
			}

			if (!item.isEmpty()) {
				for (Entry<ItemStack, String> entry : toReplace.entrySet()) {
					if (OreDictionary.itemMatches(entry.getKey(), item, true)) {
						String oreName = entry.getValue();
						inputs.add(c[i]);
						inputs.add(oreName);
						b = true;
					}
				}

				if (!b) {
					inputs.add(c[i]);
					inputs.add(obj);
				}
			}
		}

		Object[] newInputs = inputs.toArray();
		return new ShapedOreRecipe(output, newInputs);
	}

	// Shapeless-Ore
	@SuppressWarnings("unchecked")
	private static ShapelessOreRecipe addShapelessOreRecipe(Map<ItemStack, String> toReplace, ShapelessOreRecipe recipe) {
		ItemStack output = recipe.getRecipeOutput();
		NonNullList<Object> objects = recipe.getInput();
		ArrayList<Object> inputs = new ArrayList<Object>();

		for (Object obj : objects) {
			boolean b = false;
			ItemStack item = ItemStack.EMPTY;

			if (obj instanceof ItemStack) {
				item = (ItemStack) obj;
			} else if (obj instanceof Item) {
				item = new ItemStack((Item) obj);
			} else if (obj instanceof Block) {
				item = new ItemStack((Block) obj, 1, OreDictionary.WILDCARD_VALUE);
			} else if (obj instanceof List) {
				if ((List<ItemStack>) obj != null && !((List<ItemStack>) obj).isEmpty()) {
					List<ItemStack> list = (List<ItemStack>) obj;
					for (ItemStack oreItem : list) {
						if (oreItem.isEmpty() || OreDictionary.getOreIDs(oreItem).length == 0)
							continue;
						int[] id = OreDictionary.getOreIDs(oreItem);
						for (int j = 0; j < id.length; j++) {
							String str = OreDictionary.getOreName(OreDictionary.getOreIDs(oreItem)[j]);
							if (str != null) {
								inputs.add(str);
								b = true;
								break;
							}
						}
						if (b)
							break;
					}
				}

				if (!b) {
					inputs.add("Unknown");
					b = true;
				}
			}

			if (!item.isEmpty()) {
				for (Entry<ItemStack, String> entry : toReplace.entrySet()) {
					if (OreDictionary.itemMatches(entry.getKey(), item, true)) {
						String oreName = entry.getValue();
						inputs.add(oreName);
						b = true;
					}
				}
			}

			if (!b) {
				inputs.add(obj);
			}
		}

		Object[] newInputs = inputs.toArray();
		return new ShapelessOreRecipe(output, newInputs);
	}
}
