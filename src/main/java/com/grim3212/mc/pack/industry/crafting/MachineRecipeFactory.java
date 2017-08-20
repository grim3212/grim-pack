package com.grim3212.mc.pack.industry.crafting;

import com.google.gson.JsonObject;
import com.grim3212.mc.pack.core.config.BaseRecipe;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.util.MachineRecipes;
import com.grim3212.mc.pack.industry.util.MachineRecipes.MachineType;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class MachineRecipeFactory implements IRecipeFactory {

	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		Ingredient in = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, "input"), context);
		ItemStack out = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "output"), context);
		float exp = JsonUtils.getFloat(json, "experience", 0.0f);
		MachineType type = MachineType.fromJson(json);

		return new MachineRecipe(type, in, out, exp);
	}

	private class MachineRecipe extends BaseRecipe {

		public MachineRecipe(MachineType type, Ingredient in, ItemStack out, float exp) {
			super(in, out, exp);
			MachineRecipes.INSTANCE.addRecipe(input, out, exp, type);

			GrimLog.debugInfo(GrimIndustry.partName, "Registered '" + type.getName() + "' recipe " + input.getMatchingStacks()[0].toString() + " -> " + output + ": " + experience);

		}

	}
}
