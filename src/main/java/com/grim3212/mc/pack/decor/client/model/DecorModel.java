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
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class DecorModel implements IRetexturableModel, IModelCustomData {

	public static final DecorModel MODEL = new DecorModel(ImmutableList.<ResourceLocation>of(), new ResourceLocation("grimpack:blocks/colorizer"), EnumDecorModelType.Unknown);

	private final ImmutableList<ResourceLocation> modelLocation;
	private final ResourceLocation textureLocation;
	private final EnumDecorModelType modelType;

	public DecorModel(ImmutableList<ResourceLocation> modelLocation, ResourceLocation textureLocation, EnumDecorModelType modelType) {
		this.modelLocation = modelLocation;
		this.textureLocation = textureLocation;
		this.modelType = modelType;
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
		if (this.modelType == EnumDecorModelType.Colorizer) {
			return new BakedColorizerModel(state, modelLocation, textureLocation, format, IPerspectiveAwareModel.MapWrapper.getTransforms(state));
		} else if (this.modelType == EnumDecorModelType.Fireplace) {
			return new BakedFireplaceModel(state, modelLocation, textureLocation, format, IPerspectiveAwareModel.MapWrapper.getTransforms(state));
		}

		return new BakedColorizerModel(state, modelLocation, textureLocation, format, IPerspectiveAwareModel.MapWrapper.getTransforms(state));
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

		return new DecorModel(this.modelLocation, base, this.modelType);
	}

	@Override
	public IModel process(ImmutableMap<String, String> customData) {
		ImmutableList.Builder<ResourceLocation> modelLocations = ImmutableList.builder();

		if (!customData.containsKey("models"))
			throw new UnsupportedOperationException("Model location no found for a DecorModel");

		String[] models = customData.get("models").replaceAll("\\[|\\]|\"", "").split(",");

		for (String model : models) {
			modelLocations.add(new ResourceLocation(model));
		}

		ImmutableList<ResourceLocation> immutableModels = modelLocations.build();
		for (int i = 1; i < immutableModels.size(); i++) {
			//Load the extra models and this should load the sub-model textures
			ModelLoaderRegistry.getModelOrLogError(immutableModels.get(i), "Model couldn't be found " + immutableModels.get(i));
		}

		return new DecorModel(immutableModels, this.textureLocation, this.modelType);
	}

	public static enum EnumDecorModelType {
		Fireplace, Colorizer, Slope, Unknown
	}
}
