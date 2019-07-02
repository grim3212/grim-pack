package com.grim3212.mc.pack.decor.client.model;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grim3212.mc.pack.GrimPack;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public enum SlopedModelLoader implements ICustomModelLoader {
	instance;

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getNamespace().equals(GrimPack.modID) && modelLocation.getPath().equals("models/block/decor_slope");
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}

	@Override
	public IUnbakedModel loadModel(ResourceLocation modelLocation) throws Exception {
		if (modelLocation.getPath().equals("models/block/decor_slope")) {
			return new SlopedModel(ImmutableList.<ModelData>of(), new ResourceLocation("grimpack:blocks/colorizer"));
		}

		return SlopedModel.MODEL;
	}

	public static class SlopedModel implements IUnbakedModel {

		public static final SlopedModel MODEL = new SlopedModel(ImmutableList.<ModelData>of(), new ResourceLocation("grimpack:blocks/colorizer"));

		private final ImmutableList<ModelData> modelData;
		private final ResourceLocation textureLocation;

		public SlopedModel(ImmutableList<ModelData> modelData, ResourceLocation textureLocation) {
			this.modelData = modelData;
			this.textureLocation = textureLocation;
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

			return new SlopedModel(this.modelData, base);
		}

		@Override
		public IUnbakedModel process(ImmutableMap<String, String> customData) {
			ImmutableList.Builder<ModelData> models = ImmutableList.builder();

			if (!customData.containsKey("models"))
				throw new UnsupportedOperationException("Model location no found for a DecorModel");

			JsonParser parser = new JsonParser();
			JsonObject json = parser.parse(customData.get("models")).getAsJsonObject();

			for (Map.Entry<String, JsonElement> entry : json.entrySet()) {

				JsonObject obj = entry.getValue().getAsJsonObject();

				if (obj.has("model")) {
					if (obj.has("custom")) {
						ImmutableMap.Builder<String, String> modelCustomData = ImmutableMap.builder();
						for (Entry<String, JsonElement> e : obj.get("custom").getAsJsonObject().entrySet()) {
							if (e.getValue().isJsonNull())
								modelCustomData.put(e.getKey(), null);
							else
								modelCustomData.put(e.getKey(), e.getValue().toString());
						}

						models.add(new ModelData(entry.getKey(), new ResourceLocation(obj.get("model").getAsString()), modelCustomData.build()));
					} else {
						models.add(new ModelData(entry.getKey(), new ResourceLocation(obj.get("model").getAsString()), ImmutableMap.<String, String>of()));
					}
				}
			}

			return new SlopedModel(models.build(), this.textureLocation);
		}

		@Override
		public IBakedModel bake(ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format) {
			return new BakedColorizerOBJModel(bakery, sprite, modelData, textureLocation, format);
		}
	}
}
