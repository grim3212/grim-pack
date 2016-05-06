package com.grim3212.mc.industry.client.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.industry.GrimIndustry;
import com.grim3212.mc.industry.client.model.CamoPlateModel.CamoPlateBuilder;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModelEvent {

	public static HashMap<Item, String[]> renderStates = Maps.newHashMap();

	@SubscribeEvent
	public void onModelBake(ModelBakeEvent event) {
		TextureAtlasSprite particle = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/portal");

		Iterator<Entry<Item, String[]>> itr = renderStates.entrySet().iterator();

		while (itr.hasNext()) {
			Entry<Item, String[]> entry = itr.next();

			for (String state : entry.getValue()) {
				String name = entry.getKey().getUnlocalizedName().substring(5);
				IFlexibleBakedModel model = (IFlexibleBakedModel) event.modelRegistry.getObject(new ModelResourceLocation(GrimIndustry.modID + ":" + name, state));

				if (model != null) {
					event.modelRegistry.putObject(new ModelResourceLocation(GrimIndustry.modID + ":" + name, state), new CamoPlateBuilder(model, particle).makeBakedModel());
				}
			}
		}
	}

	public static void renderCustomModel(Block block, String... states) {
		renderCustomModel(Item.getItemFromBlock(block), states);
	}

	public static void renderNormalModel(Block block) {
		renderCustomModel(Item.getItemFromBlock(block), new String[] { "inventory", "normal" });
	}

	public static void renderCustomModel(Item item, String... states) {
		RenderHelper.renderItem(item);
		renderStates.put(item, states);
	}
}
