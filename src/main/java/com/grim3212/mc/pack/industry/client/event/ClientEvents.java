package com.grim3212.mc.pack.industry.client.event;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.industry.client.model.TankBaseModel;
import com.grim3212.mc.pack.industry.config.IndustryConfig;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEvents {

	@SubscribeEvent
	public void textureStitch(TextureStitchEvent event) {
		if (IndustryConfig.subpartFans) {
			event.getMap().registerSprite(new ResourceLocation(GrimPack.modID, "entities/whiteAir"));
			event.getMap().registerSprite(new ResourceLocation(GrimPack.modID, "blocks/bridge_gravity"));
		}
	}

	@SubscribeEvent
	public void modelBake(ModelBakeEvent event) {
		if (IndustryConfig.subpartStorage) {
			// Replace tank model with a model that uses fluids
			ModelResourceLocation modelLocation = new ModelResourceLocation(new ResourceLocation(GrimPack.modID, "tank"), "normal");
			event.getModelRegistry().putObject(modelLocation, new TankBaseModel(event.getModelRegistry().getObject(modelLocation)));
		}
	}

}
