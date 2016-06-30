package com.grim3212.mc.pack.decor.client.model;

import java.io.IOException;

import com.grim3212.mc.pack.GrimPack;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public enum DecorModelLoader implements ICustomModelLoader {
	instance;

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getResourceDomain().equals(GrimPack.modID) && modelLocation.getResourcePath().equals("models/block/decor_custom");
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws IOException {
		return FurnitureModel.MODEL;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}
}
