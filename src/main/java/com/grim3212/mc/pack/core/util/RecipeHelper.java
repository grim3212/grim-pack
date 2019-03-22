package com.grim3212.mc.pack.core.util;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.grim3212.mc.pack.GrimPack;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeHelper {

	public static class BlockStack {

		private Block block;
		private int meta;

		public BlockStack(Block block, int meta) {
			this.block = block;
			this.meta = meta;
		}

		public Block getBlock() {
			return block;
		}

		public int getMeta() {
			return meta;
		}
	}

	public static BlockStack getBlockStack(JsonObject json) {

		String blockName = GrimPack.modID + ":" + JsonUtils.getString(json, "item");

		Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName));

		if (block == null)
			throw new JsonSyntaxException("Unknown block '" + blockName + "'");

		return new BlockStack(block, JsonUtils.getInt(json, "data", 0));
	}

	public static boolean isBlock(ItemStack in) {
		if (in.getItem() instanceof ItemBlock) {
			return true;
		}
		return false;
	}

	public static Optional<ResourceLocation> getTag(Ingredient input) {
		JsonElement serialized = input.serialize();

		if (serialized != null && !serialized.isJsonNull()) {

			JsonObject json = serialized.getAsJsonObject();

			if (json.has("tag")) {
				ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(json, "tag"));
				Tag<Item> tag = ItemTags.getCollection().get(resourcelocation);
				if (tag == null) {
					throw new JsonSyntaxException("Unknown item tag '" + resourcelocation + "'");
				} else {
					return Optional.of(tag.getId());
				}
			} else {
				return Optional.empty();
			}
		}
		return Optional.empty();
	}

	public static ResourceLocation getRecipePath(String fullPath) {
		return new ResourceLocation(fullPath);
	}

	public static ResourceLocation getRecipePath(String part, String fullPath) {
		return new ResourceLocation(GrimPack.modID, part + '/' + fullPath);
	}

	/**
	 * Get what the path should start with and it will find all others
	 * 
	 * @param path
	 * @return
	 */
	public static List<ResourceLocation> getAllPaths(String part, String path) {
		List<ResourceLocation> recipePaths = Lists.newArrayList();

		Iterator<IRecipe> recipes = Minecraft.getInstance().world.getRecipeManager().getRecipes().iterator();

		while (recipes.hasNext()) {
			IRecipe recipe = recipes.next();

			if (recipe.getId().getPath().startsWith(part + "/" + path)) {
				recipePaths.add(recipe.getId());
			}
		}

		return recipePaths;
	}

	/**
	 * Get what the path should end with and it will find all others
	 * 
	 * @param path
	 * @return
	 */
	public static List<ResourceLocation> getAllPathsEnd(String path) {
		List<ResourceLocation> recipePaths = Lists.newArrayList();

		Iterator<IRecipe> recipes = Minecraft.getInstance().world.getRecipeManager().getRecipes().iterator();

		while (recipes.hasNext()) {
			IRecipe recipe = recipes.next();

			if (recipe.getId().getPath().endsWith(path)) {
				recipePaths.add(recipe.getId());
			}
		}

		return recipePaths;
	}
}
