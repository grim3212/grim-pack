package com.grim3212.mc.pack.tools.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.core.util.RecipeHelper.BlockStack;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class ChiselRecipeFactory implements IRecipeFactory {

	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		try {
			ItemStack inBlock = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "inBlock"), context);
			if (!RecipeHelper.isBlock(inBlock))
				throw new JsonSyntaxException("'inBlock' must be a block");

			ItemStack outBlock = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "outBlock"), context);
			if (!RecipeHelper.isBlock(outBlock))
				throw new JsonSyntaxException("'outBlock' must be a block");

			if (JsonUtils.getBoolean(JsonUtils.getJsonObject(json, "outItem"), "defaultDrops", false)) {
				return new ChiselRecipe(inBlock, outBlock, ItemStack.EMPTY);
			} else {
				ItemStack outItem = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "outItem"), context);
				return new ChiselRecipe(inBlock, outBlock, outItem);
			}
		} catch (JsonSyntaxException e) {
			BlockStack block = RecipeHelper.getBlockStack(JsonUtils.getJsonObject(json, "inBlock"), context);
			BlockStack rBlock = RecipeHelper.getBlockStack(JsonUtils.getJsonObject(json, "outBlock"), context);

			if (JsonUtils.getBoolean(JsonUtils.getJsonObject(json, "outItem"), "defaultDrops", false)) {
				return new ChiselRecipe(block, rBlock, ItemStack.EMPTY);
			} else {
				ItemStack outItem = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "outItem"), context);
				return new ChiselRecipe(block, rBlock, outItem);
			}
		}
	}
}