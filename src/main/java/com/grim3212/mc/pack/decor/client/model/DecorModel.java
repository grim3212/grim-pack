package com.grim3212.mc.pack.decor.client.model;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class DecorModel implements IUnbakedModel {

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
	public Collection<ResourceLocation> getTextures(Function<ResourceLocation, IUnbakedModel> modelGetter, Set<String> missingTextureErrors) {
		ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();
		if (textureLocation != null)
			builder.add(textureLocation);

		return builder.build();
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

	@Override
	public IUnbakedModel retexture(ImmutableMap<String, String> textures) {
		ResourceLocation base = textureLocation;

		if (textures.containsKey("texture"))
			base = new ResourceLocation(textures.get("texture"));

		return new DecorModel(this.modelLocation, base, this.modelType);
	}

	@Override
	public IUnbakedModel process(ImmutableMap<String, String> customData) {
		ImmutableList.Builder<ResourceLocation> modelLocations = ImmutableList.builder();

		if (!customData.containsKey("models"))
			throw new UnsupportedOperationException("Model location no found for a DecorModel");

		String[] models = customData.get("models").replaceAll("\\[|\\]|\"", "").split(",");

		for (String model : models) {
			modelLocations.add(new ResourceLocation(model));
		}

		ImmutableList<ResourceLocation> immutableModels = modelLocations.build();
		for (int i = 1; i < immutableModels.size(); i++) {
			// Load the extra models and this should load the sub-model textures
			ModelLoaderRegistry.getModelOrLogError(immutableModels.get(i), "Model couldn't be found " + immutableModels.get(i));
		}

		return new DecorModel(immutableModels, this.textureLocation, this.modelType);
	}

	public static enum EnumDecorModelType {
		Fireplace, Colorizer, Slope, Unknown
	}

	@Override
	public IBakedModel bake(ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format) {
		if (this.modelType == EnumDecorModelType.Colorizer) {
			return new BakedColorizerModel(bakery, sprite, modelLocation, textureLocation, format);
		} else if (this.modelType == EnumDecorModelType.Fireplace) {
			return new BakedFireplaceModel(bakery, sprite, modelLocation, textureLocation, format);
		}

		return new BakedColorizerModel(bakery, sprite, modelLocation, textureLocation, format);
	}
}
