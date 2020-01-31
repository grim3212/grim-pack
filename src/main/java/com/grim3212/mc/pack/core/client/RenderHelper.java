package com.grim3212.mc.pack.core.client;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderHelper {

	public static final VertexFormat POSITION_TEX_COLOR_LIGHTMAP_NORMAL = (new VertexFormat(ImmutableList.<VertexFormatElement>builder().add(DefaultVertexFormats.POSITION_3F).add(DefaultVertexFormats.TEX_2F).add(DefaultVertexFormats.COLOR_4UB).add(DefaultVertexFormats.TEX_2S).add(DefaultVertexFormats.NORMAL_3B).add(DefaultVertexFormats.PADDING_1B).build()));

	/**public static void renderBlockWithMetaInInventory(Block block, int meta) {
		Item item = Item.getItemFromBlock(block);
		for (int i = 0; i < meta; i++)
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	public static void renderBlockNormal(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "normal"));
	}

	public static void renderBlock(Block block) {
		renderItem(Item.getItemFromBlock(block));
	}

	/**
	 * Render an item without any metadata
	 * 
	 * @param item to render
	 *
	public static void renderItem(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	/**
	 * Render an item without meta that all share the same model
	 * 
	 * @param item to render
	 * @param meta number of meta values
	 *
	public static void renderItem(Item item, int meta) {
		for (int i = 0; i < meta; i++)
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	/**
	 * Uses the forge single blockstate for variants
	 * 
	 * @param item     With variants
	 * @param variants The different variant names
	 *
	public static void renderVariantForgeMeta(Item item, int meta, String... variants) {
		for (int i = 0; i < variants.length; i++) {
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), variants[i]));
		}
	}

	/**
	 * Uses the forge single blockstate for variants
	 * 
	 * @param item     With variants
	 * @param variants The different variant names
	 *
	public static void renderVariantForge(Item item, String... variants) {
		for (int i = 0; i < variants.length; i++) {
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), variants[i]));
		}
	}

	/**
	 * Uses the forge single blockstate for variants
	 * 
	 * @param item     With variants
	 * @param variants The different variant names
	 *
	public static void renderVariantForgeMeta(Block item, int meta) {
		for (int i = 0; i < meta; i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(item), i, new ModelResourceLocation(item.getRegistryName(), "inventory_" + i));
		}
	}

	/**
	 * Uses the forge single blockstate for variants
	 * 
	 * @param item     With variants
	 * @param variants The different variant names
	 *
	public static void renderVariantForge(Block item, String... variants) {
		for (int i = 0; i < variants.length; i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(item), i, new ModelResourceLocation(item.getRegistryName(), variants[i]));
		}
	}

	/**
	 * Uses the vanilla item states with one for each for each variant
	 * 
	 * @param item     With variants
	 * @param variants The different variant names
	 *
	public static void renderVariantVanilla(Item item, String... variants) {
		for (int i = 0; i < variants.length; i++) {
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName() + "_" + variants[i], "inventory"));
		}
	}

	public static void registerExtraModel(Block item, String model) {
		registerExtraModel(Item.getItemFromBlock(item), model);
	}

	public static void registerExtraModel(Item item, String model) {
		ModelBakery.registerItemVariants(item, new ResourceLocation(GrimPack.modID, model));
	}

	public static void registerExtraModels(Block item, String... models) {
		registerExtraModels(Item.getItemFromBlock(item), models);
	}

	public static void registerExtraModels(Item item, String... models) {
		for (String model : models)
			ModelBakery.registerItemVariants(item, new ResourceLocation(GrimPack.modID, model));
	}*/
}
