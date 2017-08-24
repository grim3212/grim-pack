package com.grim3212.mc.pack.core.client;

import com.grim3212.mc.pack.core.common.CommonItems;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.item.CoreItems;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CoreModelHandler {

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent evt) {
		RenderHelper.renderItem(CoreItems.instruction_manual);

		if (CoreConfig.subpartIron)
			RenderHelper.renderItem(CommonItems.iron_stick);

		if (CoreConfig.subpartRubber)
			RenderHelper.renderItem(CommonItems.rubber);

		if (CoreConfig.subpartCoal)
			RenderHelper.renderItem(CommonItems.coal_dust);

		if (CoreConfig.subpartAluminum) {
			RenderHelper.renderItem(CommonItems.aluminum_ingot);
			RenderHelper.renderItem(CommonItems.aluminum_shaft);
			RenderHelper.renderItem(CommonItems.aluminum_can);
			RenderHelper.renderBlock(CommonItems.aluminum_ore);
		}

		if (CoreConfig.subpartGraphite) {
			RenderHelper.renderItem(CommonItems.graphite);
			RenderHelper.renderItem(CommonItems.graphite_rod);
		}

		if (CoreConfig.subpartSteel) {
			RenderHelper.renderBlock(CommonItems.steel_block);
			RenderHelper.renderItem(CommonItems.steel_ingot);
			RenderHelper.renderItem(CommonItems.steel_shaft);
		}
	}

}
