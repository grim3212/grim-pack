package com.grim3212.mc.pack.util.client;

import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.util.init.UtilBlocks;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class UtilModelHandler {

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent evt) {
		RenderHelper.renderBlock(UtilBlocks.grave);
	}

}
