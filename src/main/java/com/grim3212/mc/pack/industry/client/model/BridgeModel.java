package com.grim3212.mc.pack.industry.client.model;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.model.CompositeModel;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.industry.block.BlockBridge;
import com.grim3212.mc.pack.industry.util.EnumBridgeType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt.DirtType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.client.resource.VanillaResourceType;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;

public class BridgeModel implements IModel {

	public static final BridgeModel MODEL = new BridgeModel(ImmutableList.<ResourceLocation>of(), new ResourceLocation("grimpack:blocks/bridge"));

	private final ImmutableList<ResourceLocation> modelLocation;
	private final ResourceLocation textureLocation;

	public BridgeModel(ImmutableList<ResourceLocation> modelLocation, ResourceLocation textureLocation) {
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
	public IBakedModel bake(IModelState state, VertexFormat format, java.util.function.Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		return new BakedBridgeModel(state, modelLocation, textureLocation, format, PerspectiveMapWrapper.getTransforms(state));
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

		return new BridgeModel(this.modelLocation, base);
	}

	@Override
	public IModel process(ImmutableMap<String, String> customData) {
		ImmutableList.Builder<ResourceLocation> modelLocations = ImmutableList.builder();

		if (!customData.containsKey("models"))
			throw new UnsupportedOperationException("Model location no found for a BridgeModel");

		String[] models = customData.get("models").replaceAll("\\[|\\]|\"", "").split(",");

		for (String model : models) {
			modelLocations.add(new ResourceLocation(model));
		}

		return new BridgeModel(modelLocations.build(), this.textureLocation);
	}

	@SuppressWarnings("deprecation")
	public class BakedBridgeModel implements IBakedModel, ISelectiveResourceReloadListener {

		protected final IModelState modelState;
		protected final ImmutableList<ResourceLocation> modelLocation;
		protected final ResourceLocation textureLocation;
		protected final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
		protected final VertexFormat format;
		protected final IModel baseModel;
		protected final ImmutableList<IModel> modelParts;

		public BakedBridgeModel(IModelState modelState, ImmutableList<ResourceLocation> modelLocation, ResourceLocation textureLocation, VertexFormat fmt, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
			this.modelState = modelState;
			this.modelLocation = modelLocation;
			this.textureLocation = textureLocation;
			this.format = fmt;
			this.transforms = transforms;

			this.baseModel = ModelLoaderRegistry.getModelOrLogError(this.modelLocation.get(0), "Base model not found " + this.modelLocation.get(0));

			ImmutableList.Builder<IModel> builder = ImmutableList.builder();
			for (int i = 1; i < modelLocation.size(); i++) {
				builder.add(ModelLoaderRegistry.getModelOrLogError(this.modelLocation.get(i), "Model part not found " + this.modelLocation.get(i)));
			}
			this.modelParts = builder.build();
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
			if (resourcePredicate.test(VanillaResourceType.MODELS)) {
				this.cache.clear();
			}
		}

		@Override
		public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
			if (state instanceof IExtendedBlockState) {
				IExtendedBlockState exState = (IExtendedBlockState) state;
				if (exState.getValue(BlockBridge.BLOCK_STATE) != null) {
					IBlockState blockState = exState.getValue(BlockBridge.BLOCK_STATE);
					return this.getCachedModel(blockState, state.getValue(BlockBridge.TYPE)).getQuads(state, side, rand);
				}
			}
			return ImmutableList.of();
		}

		private final Map<IBlockState, IBakedModel> cache = new HashMap<IBlockState, IBakedModel>();
		// Base Model if there isn't a Block State set
		private IBakedModel BASE = null;

		// Base Model if there isn't a Block State set for Grav Lift
		private IBakedModel BASE_GRAVITY = null;

		public IBakedModel getCachedModel(IBlockState blockState, EnumBridgeType type) {
			if (blockState == Blocks.AIR.getDefaultState()) {
				if (type == EnumBridgeType.GRAVITY) {
					if (BASE_GRAVITY == null) {
						ImmutableMap.Builder<String, String> newTexture = ImmutableMap.builder();
						newTexture.put("texture", "grimpack:blocks/bridge_gravity");
						BASE_GRAVITY = generateModel(newTexture.build());
					}
					return BASE_GRAVITY;
				} else {
					if (BASE == null) {
						ImmutableMap.Builder<String, String> newTexture = ImmutableMap.builder();
						newTexture.put("texture", "grimpack:blocks/bridge");
						BASE = generateModel(newTexture.build());
					}
					return BASE;
				}
			}

			if (!this.cache.containsKey(blockState) && blockState != Blocks.AIR.getDefaultState()) {
				ImmutableMap.Builder<String, String> newTexture = ImmutableMap.builder();

				if (blockState.getBlock() == Blocks.GRASS) {
					newTexture.put("texture", "minecraft:blocks/grass_top");
				} else if (blockState.getBlock() == Blocks.DIRT && blockState.getValue(BlockDirt.VARIANT) == DirtType.PODZOL) {
					newTexture.put("texture", "minecraft:blocks/dirt_podzol_top");
				} else if (blockState.getBlock() == Blocks.MYCELIUM) {
					newTexture.put("texture", "minecraft:blocks/mycelium_top");
				} else {
					BlockModelShapes blockModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
					TextureAtlasSprite blockTexture = blockModel.getTexture(blockState);

					if (blockTexture.getIconName().equals("missingno")) {
						newTexture.put("texture", type == EnumBridgeType.GRAVITY ? "grimpack:blocks/bridge_gravity" : "grimpack:blocks/bridge");
					} else {
						newTexture.put("texture", blockTexture.getIconName());
					}
				}

				this.cache.put(blockState, generateModel(newTexture.build()));
			}

			return this.cache.get(blockState);
		}

		/**
		 * Generate the model defined in json that is a combination of all models
		 * defined
		 * 
		 * @param state
		 * @param texture
		 * @return
		 */
		protected IBakedModel generateModel(ImmutableMap<String, String> texture) {
			ImmutableList.Builder<IBakedModel> builder = ImmutableList.builder();
			builder.add(this.baseModel.retexture(texture).bake(this.modelState, this.format, ModelLoader.defaultTextureGetter()));
			for (IModel model : this.modelParts)
				builder.add(model.bake(this.modelState, this.format, ModelLoader.defaultTextureGetter()));

			return new CompositeModel(builder.build());
		}

		/**
		 * Generates the model defined in the json and then also merges extra models to
		 * it
		 * 
		 * @param state
		 * @param texture
		 * @param models
		 * @return
		 */
		protected IBakedModel generateModel(ImmutableMap<String, String> texture, IModel... models) {
			ImmutableList.Builder<IBakedModel> builder = ImmutableList.builder();
			builder.add(this.generateModel(texture));
			for (IModel model : models)
				builder.add(model.bake(this.modelState, this.format, ModelLoader.defaultTextureGetter()));

			return new CompositeModel(builder.build());
		}

		@Override
		public boolean isAmbientOcclusion() {
			return true;
		}

		@Override
		public boolean isGui3d() {
			return true;
		}

		@Override
		public boolean isBuiltInRenderer() {
			return false;
		}

		@Override
		public TextureAtlasSprite getParticleTexture() {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(textureLocation.toString());
		}

		@Override
		public ItemCameraTransforms getItemCameraTransforms() {
			return baseModel.bake(this.modelState, this.format, ModelLoader.defaultTextureGetter()).getItemCameraTransforms();
		}

		@Override
		public ItemOverrideList getOverrides() {
			return itemHandler;
		}

		// Not really needed since there isn't even an item form of bridges
		private final ItemOverrideList itemHandler = new ItemOverrideList(Lists.<ItemOverride>newArrayList()) {
			@Override
			public IBakedModel handleItemState(IBakedModel model, ItemStack stack, World world, EntityLivingBase entity) {
				if (stack.hasTagCompound() && stack.getTagCompound().hasKey("registryName") && stack.getTagCompound().hasKey("meta")) {
					Block block = Block.REGISTRY.getObject(new ResourceLocation(NBTHelper.getString(stack, "registryName")));
					IBlockState state = block.getStateFromMeta(NBTHelper.getInt(stack, "meta"));
					return BakedBridgeModel.this.getCachedModel(state, EnumBridgeType.values[stack.getMetadata()]);
				}

				return BakedBridgeModel.this.getCachedModel(Blocks.AIR.getDefaultState(), EnumBridgeType.values[stack.getMetadata()]);
			}
		};

		@Override
		public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
			return PerspectiveMapWrapper.handlePerspective(baseModel.bake(this.modelState, this.format, ModelLoader.defaultTextureGetter()), transforms, cameraTransformType);
		}
	}

	public static enum BridgeModelLoader implements ICustomModelLoader {
		instance;

		@Override
		public boolean accepts(ResourceLocation modelLocation) {
			return modelLocation.getResourceDomain().equals(GrimPack.modID) && (modelLocation.getResourcePath().equals("models/block/dynamic_bridge"));
		}

		@Override
		public IModel loadModel(ResourceLocation modelLocation) throws IOException {
			if (modelLocation.getResourcePath().equals("models/block/dynamic_bridge")) {
				return new BridgeModel(ImmutableList.<ResourceLocation>of(), new ResourceLocation("grimpack:blocks/bridge"));
			}

			return BridgeModel.MODEL;
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {
		}
	}
}
