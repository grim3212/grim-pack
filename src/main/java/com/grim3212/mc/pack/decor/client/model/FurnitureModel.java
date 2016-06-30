package com.grim3212.mc.pack.decor.client.model;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IModelCustomData;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class FurnitureModel implements IRetexturableModel, IModelCustomData {
	
	public static final FurnitureModel MODEL = new FurnitureModel(ImmutableList.<ResourceLocation>of(), new ResourceLocation("minecraft:blocks/portal"));
	
	private final ImmutableList<ResourceLocation> modelLocation;
	private final ResourceLocation textureLocation;

	public FurnitureModel(ImmutableList<ResourceLocation> modelLocation, ResourceLocation textureLocation) {
		this.modelLocation = modelLocation;
		this.textureLocation = textureLocation;
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableList.of();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();
		if (textureLocation != null)
			builder.add(textureLocation);

		return builder.build();
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		return new BakedFurnitureModel(state, modelLocation.get(0), textureLocation, format, IPerspectiveAwareModel.MapWrapper.getTransforms(state));
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

	@Override
	public IModel retexture(ImmutableMap<String, String> textures) {
		ResourceLocation base = textureLocation;

		if (textures.containsKey("texture"))
			base = new ResourceLocation(textures.get("texture"));

		return new FurnitureModel(this.modelLocation, base);
	}
	
	@Override
	public IModel process(ImmutableMap<String, String> customData) {
		ImmutableList.Builder<ResourceLocation> modelLocations = ImmutableList.builder();
		
		if(!customData.containsKey("models"))
			throw new UnsupportedOperationException("Model location no found for a DecorModel");
		
		String[] models = customData.get("models").replaceAll("\\[|\\]|\"", "").split(",");
		
		for(String model : models){
			modelLocations.add(new ResourceLocation(model));
		}
		
		return new FurnitureModel(modelLocations.build(), this.textureLocation);
	}
}
