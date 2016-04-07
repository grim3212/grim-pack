package com.grim3212.mc.core.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ModelLoader;
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
	public static void renderVariantForge(Item item, String[] variants) {
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
	public static void renderVariantForge(Block item, String[] variants) {
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
	public static void renderVariantVanilla(Item item, String[] variants) {
		for (int i = 0; i < variants.length; i++) {
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName() + "_" + variants[i], "inventory"));
		}
	}

	/**
	 * This is a way to merge models together to create a sort of overlay for
	 * multiple IBakedModels if you can't use the layering system with JSON.
	 * This automatically sets the priority index to be 0.
	 * 
	 * @param models
	 *            These are the models that will be overlayed on top of each
	 *            other with the first one being the bottom most model
	 * @return A new SimpleBakedModel with the IBakedModels overlayed on top of
	 *         eachother
	 */
	public static SimpleBakedModel mergeModels(IBakedModel... models) {
		return mergeModels(0, models);
	}

	/**
	 * This is a way to merge models together to create a sort of overlay for
	 * multiple IBakedModels if you can't use the layering system with JSON
	 * 
	 * @param priorityIndex
	 *            This is the model that sets gui3d, ambient occlusion, etc...
	 * @param models
	 *            These are the models that will be overlayed on top of each
	 *            other with the first one being the bottom most model
	 * @return A new SimpleBakedModel with the IBakedModels overlayed on top of
	 *         eachother
	 */
	@SuppressWarnings("deprecation")
	public static SimpleBakedModel mergeModels(int priorityIndex, IBakedModel... models) {
		IBakedModel priorityModel;
		if (models[priorityIndex] != null) {
			priorityModel = models[priorityIndex];
		} else {
			priorityModel = models[0];
		}

		SimpleBakedModel mergedModel = new SimpleBakedModel(new ArrayList<BakedQuad>(), setupFaceQuads(), priorityModel.isGui3d(), priorityModel.isAmbientOcclusion(), priorityModel.getParticleTexture(), priorityModel.getItemCameraTransforms());

		// Overlay the models on top of each other one after the other
		for (int i = 0; i < models.length; i++) {
			mergedModel.getGeneralQuads().addAll(models[i].getGeneralQuads());
			for (int j = 0; j < EnumFacing.VALUES.length; j++) {
				mergedModel.getFaceQuads(EnumFacing.VALUES[j]).addAll(models[i].getFaceQuads(EnumFacing.VALUES[j]));
			}
		}

		return mergedModel;
	}

	/**
	 * Creates a new List for faceQuads based on EnumFacing
	 * 
	 * @return The new faceQuad list
	 */
	public static List<List<BakedQuad>> setupFaceQuads() {
		// Setup up an empty faceQuad list
		List<List<BakedQuad>> faceQuads = new ArrayList<List<BakedQuad>>();
		for (int i = 0; i < EnumFacing.VALUES.length; i++) {
			faceQuads.add(Lists.<BakedQuad> newArrayList());
		}
		return faceQuads;
	}
}
