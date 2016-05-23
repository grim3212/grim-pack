package com.grim3212.mc.pack.core.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHelper {

	public static final VertexFormat POSITION_TEX_COLOR_LIGHTMAP_NORMAL = (new VertexFormat()).addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);

	public static void renderBlockWithMetaInInventory(Block block, int meta) {
		Item item = Item.getItemFromBlock(block);
		for (int i = 0; i < meta; i++)
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	public static void renderBlock(Block block) {
		renderItem(Item.getItemFromBlock(block));
	}

	/**
	 * Render an item without any metadata
	 * 
	 * @param item
	 *            to render
	 */
	public static void renderItem(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	/**
	 * Uses the forge single blockstate for variants
	 * 
	 * @param item
	 *            With variants
	 * @param variants
	 *            The different variant names
	 */
	public static void renderVariantForge(Item item, String... variants) {
		for (int i = 0; i < variants.length; i++) {
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), variants[i]));
		}
	}

	/**
	 * Uses the forge single blockstate for variants
	 * 
	 * @param item
	 *            With variants
	 * @param variants
	 *            The different variant names
	 */
	public static void renderVariantForge(Block item, String... variants) {
		for (int i = 0; i < variants.length; i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(item), i, new ModelResourceLocation(item.getRegistryName(), variants[i]));
		}
	}

	/**
	 * Uses the vanilla item states with one for each for each variant
	 * 
	 * @param item
	 *            With variants
	 * @param variants
	 *            The different variant names
	 */
	public static void renderVariantVanilla(Item item, String... variants) {
		for (int i = 0; i < variants.length; i++) {
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName() + "_" + variants[i], "inventory"));
		}
	}

	public static void registerExtraModel(Block item, String model) {
		registerExtraModel(Item.getItemFromBlock(item), model);
	}

	public static void registerExtraModel(Item item, String model) {
		ModelBakery.registerItemVariants(item, new ResourceLocation(Loader.instance().activeModContainer().getModId() + ":" + model));
	}

	public static void registerExtraModels(Block item, String... models) {
		registerExtraModels(Item.getItemFromBlock(item), models);
	}

	public static void registerExtraModels(Item item, String... models) {
		for (String model : models)
			ModelBakery.registerItemVariants(item, new ResourceLocation(Loader.instance().activeModContainer().getModId() + ":" + model));
	}
}
