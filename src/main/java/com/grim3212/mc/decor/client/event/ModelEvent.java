package com.grim3212.mc.decor.client.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.decor.GrimDecor;
import com.grim3212.mc.decor.client.model.TexturedBuilder;

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
				IFlexibleBakedModel model = (IFlexibleBakedModel) event.modelRegistry.getObject(new ModelResourceLocation(GrimDecor.modID + ":" + entry.getKey().getUnlocalizedName().substring(5), state));
				event.modelRegistry.putObject(new ModelResourceLocation(GrimDecor.modID + ":" + entry.getKey().getUnlocalizedName().substring(5), state), new TexturedBuilder(model, particle).makeBakedModel());
			}
		}
	}

	public static void renderCustomModel(Block block, String... states) {
		renderCustomModel(Item.getItemFromBlock(block), states);
	}

	public static void renderCustomModel(Item item, String... states) {
		RenderHelper.renderItem(item);
		renderStates.put(item, states);
	}
}
