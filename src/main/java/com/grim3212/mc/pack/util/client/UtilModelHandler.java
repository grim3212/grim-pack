package com.grim3212.mc.pack.util.client;

import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.util.init.UtilBlocks;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UtilModelHandler {

	@SubscribeEvent
	public void registerModels(ModelRegistryEvent evt) {
		RenderHelper.renderVariantForge(UtilBlocks.grave, "facing=north", "facing=south", "facing=west", "facing=east");
	}

}
