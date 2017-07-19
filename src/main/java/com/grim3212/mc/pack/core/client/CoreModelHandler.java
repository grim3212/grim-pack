package com.grim3212.mc.pack.core.client;

import com.grim3212.mc.pack.core.item.CoreItems;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CoreModelHandler {

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent evt) {
		RenderHelper.renderItem(CoreItems.instruction_manual);
	}

}
