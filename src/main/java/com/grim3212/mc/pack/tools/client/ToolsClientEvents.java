package com.grim3212.mc.pack.tools.client;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.tools.util.EnumCrystalType;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ToolsClientEvents {

	@SubscribeEvent
	public void textureStitch(TextureStitchEvent.Pre event) {

		for (int i = 0; i < EnumCrystalType.values.length; i++) {
			String type = EnumCrystalType.values[i].getUnlocalized();

			event.getMap().registerSprite(new ResourceLocation(GrimPack.modID, "items/magic/main_" + type));
			event.getMap().registerSprite(new ResourceLocation(GrimPack.modID, "items/magic/sub_" + type));
			event.getMap().registerSprite(new ResourceLocation(GrimPack.modID, "items/magic/wand_" + type));
			event.getMap().registerSprite(new ResourceLocation(GrimPack.modID, "items/magic/wand_core_" + type));
		}
	}

}
