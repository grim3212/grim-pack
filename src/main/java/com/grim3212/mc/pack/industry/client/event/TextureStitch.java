package com.grim3212.mc.pack.industry.client.event;

import com.grim3212.mc.pack.GrimPack;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TextureStitch {

	@SubscribeEvent
	public void textureStitch(TextureStitchEvent event) {
		event.getMap().registerSprite(new ResourceLocation(GrimPack.modID, "entities/whiteAir"));
	}

}
