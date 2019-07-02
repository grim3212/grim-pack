package com.grim3212.mc.pack.core.util;

import java.util.Optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.grim3212.mc.pack.GrimPack;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.JSONUtils;
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

		String blockName = GrimPack.modID + ":" + JSONUtils.getString(json, "item");

		Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName));

		if (block == null)
			throw new JsonSyntaxException("Unknown block '" + blockName + "'");

		return new BlockStack(block, JSONUtils.getInt(json, "data", 0));
	}

	public static boolean isBlock(ItemStack in) {
		if (in.getItem() instanceof BlockItem) {
			return true;
		}
		return false;
	}

	public static Optional<ResourceLocation> getTag(Ingredient input) {
		JsonElement serialized = input.serialize();

		if (serialized != null && !serialized.isJsonNull()) {

			JsonObject json = serialized.getAsJsonObject();

			if (json.has("tag")) {
				ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(json, "tag"));
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
}
