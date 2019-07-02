package com.grim3212.mc.pack.decor.client.model;

import java.io.IOException;

import com.google.common.collect.ImmutableList;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.decor.client.model.DecorModel.EnumDecorModelType;

import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;

public enum DecorModelLoader implements ICustomModelLoader {
	instance;

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getNamespace().equals(GrimPack.modID) && (modelLocation.getPath().equals("models/block/decor_fireplace") || modelLocation.getPath().equals("models/block/decor_colorizer"));
	}

	@Override
	public IUnbakedModel loadModel(ResourceLocation modelLocation) throws IOException {
		if (modelLocation.getPath().equals("models/block/decor_colorizer")) {
			return new DecorModel(ImmutableList.<ResourceLocation>of(), new ResourceLocation("grimpack:block/colorizer"), EnumDecorModelType.Colorizer);
		} else if (modelLocation.getPath().equals("models/block/decor_fireplace")) {
			return new DecorModel(ImmutableList.<ResourceLocation>of(), new ResourceLocation("grimpack:block/colorizer"), EnumDecorModelType.Fireplace);
		}

		return DecorModel.MODEL;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}
}
