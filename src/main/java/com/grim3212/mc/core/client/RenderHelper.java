package com.grim3212.mc.core.client;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RenderHelper {

	@SideOnly(Side.CLIENT)
	public static void renderBlockWithMetaInInventory(Block block, int meta) {
		String modid = Loader.instance().activeModContainer().getModId();

		Item item = Item.getItemFromBlock(block);
		for (int i = 0; i < meta; i++)
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void renderBlock(Block block) {
		renderItem(Item.getItemFromBlock(block));
	}

	/**
	 * Render an item without any metadata
	 * 
	 * @param item
	 *            to render
	 */
	@SideOnly(Side.CLIENT)
	public static void renderItem(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Loader.instance().activeModContainer().getModId() + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void renderVariant(Item item, String[] variants) {
		String modID = Loader.instance().activeModContainer().getModId();

		for (int i = 0; i < variants.length; i++) {
			ModelResourceLocation modelLoc = new ModelResourceLocation(modID + ":" + item.getUnlocalizedName().substring(5) + "_" + variants[i], "inventory");
			ModelLoader.setCustomModelResourceLocation(item, i, modelLoc);
		}
	}
}
