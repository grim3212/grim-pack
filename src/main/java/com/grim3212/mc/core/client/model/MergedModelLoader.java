package com.grim3212.mc.core.client.model;

import java.util.HashMap;

import com.google.common.collect.Maps;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class MergedModelLoader implements ICustomModelLoader {

	public final String MODEL_RESOURCE_LOCATION = "models/block/smartmodel/";

	private static HashMap<String, Class<? extends IModel>> models = Maps.newHashMap();

	public static void registerMergedModel(String modelName, Class<? extends IModel> unbakedModel) {
		models.put(modelName, unbakedModel);
	}

	@Override
	public boolean accepts(ResourceLocation resourceLocation) {
		return resourceLocation.getResourceDomain().startsWith("grim") && resourceLocation.getResourcePath().startsWith(MODEL_RESOURCE_LOCATION);
	}

	@Override
	public IModel loadModel(ResourceLocation resourceLocation) {
		String resourcePath = resourceLocation.getResourcePath();
		if (!resourcePath.startsWith(MODEL_RESOURCE_LOCATION)) {
			assert false : "loadModel expected " + MODEL_RESOURCE_LOCATION + " but found " + resourcePath;
		}
		String modelName = resourcePath.substring(MODEL_RESOURCE_LOCATION.length());

		if (models.containsKey(modelName)) {
			System.out.println("loading: " + modelName);

			try {
				return models.get(modelName).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				return ModelLoaderRegistry.getMissingModel();
			}
		} else {
			return ModelLoaderRegistry.getMissingModel();
		}
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}

}
