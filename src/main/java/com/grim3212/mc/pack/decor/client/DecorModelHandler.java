package com.grim3212.mc.pack.decor.client;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.decor.client.model.DecorModelLoader;
import com.grim3212.mc.pack.decor.client.model.SlopedModelLoader;
import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DecorModelHandler {

	@SubscribeEvent
	public void registerModels(ModelRegistryEvent evt) {
		if (DecorConfig.subpartColorizer.get()) {

			ModelLoaderRegistry.registerLoader(DecorModelLoader.instance);

			if (DecorConfig.subpartSlopes.get()) {
				// Needed to be able to get the OBJ models and modify with a
				// different
				// model loader
				OBJLoader.INSTANCE.addDomain(GrimPack.modID);
				// Register all custom models for furniture, fireplaces, and
				// lamp posts
				ModelLoaderRegistry.registerLoader(SlopedModelLoader.instance);
			}
		}
	}
}
