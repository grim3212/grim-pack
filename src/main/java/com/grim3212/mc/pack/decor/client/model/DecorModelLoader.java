package com.grim3212.mc.pack.decor.client.model;

import java.io.IOException;

import com.google.common.collect.ImmutableList;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.decor.client.model.DecorModel.EnumDecorModelType;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public enum DecorModelLoader implements ICustomModelLoader {
	instance;

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getResourceDomain().equals(GrimPack.modID) && (modelLocation.getResourcePath().equals("models/block/decor_fireplace") || modelLocation.getResourcePath().equals("models/block/decor_colorizer"));
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws IOException {
		if (modelLocation.getResourcePath().equals("models/block/decor_colorizer")) {
			return new DecorModel(ImmutableList.<ResourceLocation> of(), new ResourceLocation("grimpack:blocks/colorizer"), EnumDecorModelType.Colorizer);
		} else if (modelLocation.getResourcePath().equals("models/block/decor_fireplace")) {
			return new DecorModel(ImmutableList.<ResourceLocation> of(), new ResourceLocation("grimpack:blocks/colorizer"), EnumDecorModelType.Fireplace);
		}

		return DecorModel.MODEL;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}
}
