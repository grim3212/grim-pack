package com.grim3212.mc.pack.decor.client.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.client.model.CompositeModel;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizer;

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
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.ModelProcessingHelper;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;

@SuppressWarnings("deprecation")
public class BakedColorizerModel implements IPerspectiveAwareModel, IResourceManagerReloadListener {

	protected final IModelState modelState;
	protected final ImmutableList<ResourceLocation> modelLocation;
	protected final ResourceLocation textureLocation;
	protected final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
	protected final VertexFormat format;
	protected final IModel baseModel;
	protected final ImmutableList<IModel> modelParts;

	public BakedColorizerModel(IModelState modelState, ImmutableList<ResourceLocation> modelLocation, ResourceLocation textureLocation, VertexFormat fmt, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
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
	public void onResourceManagerReload(IResourceManager resourceManager) {
		this.cache.clear();
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState exState = (IExtendedBlockState) state;
			if (exState.getValue(BlockColorizer.BLOCK_STATE) != null) {
				IBlockState blockState = exState.getValue(BlockColorizer.BLOCK_STATE);
				return this.getCachedModel(blockState).getQuads(state, side, rand);
			}
		}
		return ImmutableList.of();
	}

	protected final Map<IBlockState, IBakedModel> cache = new HashMap<IBlockState, IBakedModel>();

	public IBakedModel getCachedModel(IBlockState blockState) {

		if (!this.cache.containsKey(blockState)) {
			ImmutableMap.Builder<String, String> newTexture = ImmutableMap.builder();

			if (blockState == Blocks.AIR.getDefaultState()) {
				newTexture.put("texture", "grimpack:blocks/colorizer");
			} else if (blockState.getBlock() == Blocks.GRASS) {
				newTexture.put("texture", "minecraft:blocks/grass_top");
			} else if (blockState.getBlock() == Blocks.DIRT && blockState.getValue(BlockDirt.VARIANT) == DirtType.PODZOL) {
				newTexture.put("texture", "minecraft:blocks/dirt_podzol_top");
			} else if (blockState.getBlock() == Blocks.MYCELIUM) {
				newTexture.put("texture", "minecraft:blocks/mycelium_top");
			} else {
				BlockModelShapes blockModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
				TextureAtlasSprite blockTexture = blockModel.getTexture(blockState);

				newTexture.put("texture", blockTexture.getIconName());
			}

			this.cache.put(blockState, generateModel(newTexture.build()));
		}

		return this.cache.get(blockState);
	}

	
	//TODO: Add support for IModelCustomData
	
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
		builder.add(ModelProcessingHelper.retexture(this.baseModel, texture).bake(this.modelState, this.format, ModelLoader.defaultTextureGetter()));
		for (IModel model : this.modelParts)
			builder.add(model.bake(this.modelState, this.format, ModelLoader.defaultTextureGetter()));

		return new CompositeModel(builder.build());
	}

	/**
	 * Generates the model defined in the json and then also merges extra models
	 * to it
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

	private final ItemOverrideList itemHandler = new ItemOverrideList(Lists.<ItemOverride> newArrayList()) {
		@Override
		public IBakedModel handleItemState(IBakedModel model, ItemStack stack, World world, EntityLivingBase entity) {
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("registryName") && stack.getTagCompound().hasKey("meta")) {
				Block block = Block.REGISTRY.getObject(new ResourceLocation(NBTHelper.getString(stack, "registryName")));
				IBlockState state = block.getStateFromMeta(NBTHelper.getInt(stack, "meta"));
				return BakedColorizerModel.this.getCachedModel(state);
			}

			return BakedColorizerModel.this.getCachedModel(Blocks.AIR.getDefaultState());
		}
	};

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		return IPerspectiveAwareModel.MapWrapper.handlePerspective(baseModel.bake(this.modelState, this.format, ModelLoader.defaultTextureGetter()), transforms, cameraTransformType);
	}
}
