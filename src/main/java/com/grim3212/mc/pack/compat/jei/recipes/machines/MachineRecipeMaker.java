package com.grim3212.mc.pack.compat.jei.recipes.machines;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.industry.util.MachineRecipes;
import com.grim3212.mc.pack.industry.util.MachineRecipes.MachineType;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public final class MachineRecipeMaker {

	public static final ResourceLocation MACHINE_LOCATION = new ResourceLocation(GrimPack.modID, "textures/gui/jei_machine.png");

	private MachineRecipeMaker() {
	}

	public static List<MachineRecipeWrapper> getMachineRecipes(IJeiHelpers helpers, MachineType type) {
		Map<Ingredient, Pair<ItemStack, Float>> recipesMap = MachineRecipes.INSTANCE.getRecipeList(type);

		List<MachineRecipeWrapper> recipes = Lists.newArrayList();

		for (Entry<Ingredient, Pair<ItemStack, Float>> entry : recipesMap.entrySet()) {
			Ingredient input = entry.getKey();
			ItemStack output = entry.getValue().getLeft();

			List<ItemStack> inputs = Lists.newArrayList(input.getMatchingStacks());
			MachineRecipeWrapper recipe = new MachineRecipeWrapper(inputs, output, type);
			recipes.add(recipe);
		}

		if (type == MachineType.MODERN_FURNACE) {
			IStackHelper stackHelper = helpers.getStackHelper();
			FurnaceRecipes furnaceRecipes = FurnaceRecipes.instance();
			Map<ItemStack, ItemStack> smeltingMap = furnaceRecipes.getSmeltingList();

			for (Map.Entry<ItemStack, ItemStack> entry : smeltingMap.entrySet()) {
				ItemStack input = entry.getKey();
				ItemStack output = entry.getValue();

				List<ItemStack> inputs = stackHelper.getSubtypes(input);
				MachineRecipeWrapper recipe = new MachineRecipeWrapper(inputs, output, type);
				recipes.add(recipe);
			}
		}

		return recipes;
	}
}
