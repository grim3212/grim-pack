package com.grim3212.mc.pack.tools.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.core.util.RecipeHelper.BlockStack;
import com.grim3212.mc.pack.tools.util.ChiselRegistry;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.registries.IForgeRegistryEntry;

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

			JsonObject outObject = JsonUtils.getJsonObject(json, "outItems");

			if (JsonUtils.getBoolean(outObject, "defaultDrops", false)) {
				return new ChiselRecipe(inBlock, outBlock, NonNullList.create());
			} else {
				NonNullList<ItemStack> outItems = NonNullList.create();
				for (JsonElement ele : JsonUtils.getJsonArray(outObject, "items")) {
					if (ele.isJsonObject())
						outItems.add(CraftingHelper.getItemStack((JsonObject) ele, context));
				}

				return new ChiselRecipe(inBlock, outBlock, outItems);
			}
		} catch (JsonSyntaxException e) {
			BlockStack block = RecipeHelper.getBlockStack(JsonUtils.getJsonObject(json, "inBlock"), context);
			BlockStack rBlock = RecipeHelper.getBlockStack(JsonUtils.getJsonObject(json, "outBlock"), context);

			JsonObject outObject = JsonUtils.getJsonObject(json, "outItems");

			if (JsonUtils.getBoolean(outObject, "defaultDrops", false)) {
				return new ChiselRecipe(block, rBlock, NonNullList.create());
			} else {
				NonNullList<ItemStack> outItems = NonNullList.create();
				for (JsonElement ele : JsonUtils.getJsonArray(outObject, "items")) {
					if (ele.isJsonObject())
						outItems.add(CraftingHelper.getItemStack((JsonObject) ele, context));
				}

				return new ChiselRecipe(block, rBlock, outItems);
			}
		}
	}

	private class ChiselRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

		public ChiselRecipe(ItemStack inBlock, ItemStack outBlock, NonNullList<ItemStack> outItems) {
			ChiselRegistry.registerBlock(inBlock, outBlock, outItems);
		}

		public ChiselRecipe(BlockStack inBlock, BlockStack outBlock, NonNullList<ItemStack> outItems) {
			this(inBlock.getBlock(), inBlock.getMeta(), outBlock.getBlock(), outBlock.getMeta(), outItems);
		}

		public ChiselRecipe(Block inBlock, int bMeta, Block outBlock, int oMeta, NonNullList<ItemStack> outItems) {
			ChiselRegistry.registerBlock(inBlock, bMeta, outBlock, oMeta, outItems);
		}

		@Override
		public boolean isDynamic() {
			return true;
		}

		@Override
		public boolean matches(InventoryCrafting inv, World worldIn) {
			return false;
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv) {
			return ItemStack.EMPTY;
		}

		@Override
		public boolean canFit(int width, int height) {
			return false;
		}

		@Override
		public ItemStack getRecipeOutput() {
			return ItemStack.EMPTY;
		}
	}
}