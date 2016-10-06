package com.grim3212.mc.pack.decor.client.model;

import com.google.common.collect.ImmutableMap;

import net.minecraft.util.ResourceLocation;

public class ModelData {

	private String name;
	private ResourceLocation modelLocation;
	private ImmutableMap<String, String> customData;

	public ModelData(String name, ResourceLocation modelLocation, ImmutableMap<String, String> customData) {
		this.name = name;
		this.modelLocation = modelLocation;
		this.customData = customData;
	}

	public String getName() {
		return name;
	}

	public ResourceLocation getModelLocation() {
		return modelLocation;
	}

	public ImmutableMap<String, String> getCustomData() {
		return customData;
	}

}
