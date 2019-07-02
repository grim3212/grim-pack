package com.grim3212.mc.pack.decor.client.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.grim3212.mc.pack.core.client.model.CompositeModel;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

@SuppressWarnings("deprecation")
public class BakedColorizerOBJModel implements IBakedModel, ISelectiveResourceReloadListener {

	protected final ISprite sprite;
	protected final ModelBakery bakery;
	protected final ImmutableList<ModelData> modelData;
	protected final ResourceLocation textureLocation;
	protected final VertexFormat format;
	protected final IUnbakedModel baseModel;
	protected final ImmutableList<IUnbakedModel> modelParts;

	public BakedColorizerOBJModel(ModelBakery bakery, ISprite sprite, ImmutableList<ModelData> modelData, ResourceLocation textureLocation, VertexFormat fmt) {
		this.bakery = bakery;
		this.sprite = sprite;
		this.modelData = modelData;
		this.textureLocation = textureLocation;
		this.format = fmt;

		this.baseModel = processModel(this.modelData.get(0));

		ImmutableList.Builder<IUnbakedModel> builder = ImmutableList.builder();
		for (int i = 1; i < modelData.size(); i++) {
			builder.add(processModel(this.modelData.get(i)));
		}
		this.modelParts = builder.build();
	}

	public IUnbakedModel processModel(ModelData modelData) {
		return ModelLoaderRegistry.getModelOrLogError(modelData.getModelLocation(), "Model not found for {" + modelData.getName() + "}").process(modelData.getCustomData());
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
		if (resourcePredicate.test(VanillaResourceType.MODELS) || resourcePredicate.test(VanillaResourceType.TEXTURES)) {
			this.cache.clear();
		}
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
		return getQuads(state, side, rand, EmptyModelData.INSTANCE);
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData extraData) {
		if (extraData.getData(TileEntityColorizer.BLOCK_STATE) != null) {
			BlockState blockState = extraData.getData(TileEntityColorizer.BLOCK_STATE);

			ImmutableList.Builder<BakedQuad> tintedQuads = ImmutableList.builder();

			// Make it so that hasTintIndex will return true so that tinting
			// the block can happen
			for (BakedQuad quad : this.getCachedModel(blockState).getQuads(state, side, rand))
				tintedQuads.add(new BakedQuad(quad.getVertexData(), 0xFFFFFF, quad.getFace(), quad.getSprite(), quad.shouldApplyDiffuseLighting(), quad.getFormat()));

			return tintedQuads.build();
		}

		return ImmutableList.of();
	}

	protected final Map<BlockState, IBakedModel> cache = new HashMap<BlockState, IBakedModel>();

	public IBakedModel getCachedModel(BlockState blockState) {

		if (!this.cache.containsKey(blockState)) {
			ImmutableMap.Builder<String, String> newTexture = ImmutableMap.builder();

			if (blockState == Blocks.AIR.getDefaultState()) {
				newTexture.put("#None", "grimpack:blocks/colorizer");
			} else if (blockState.getBlock() == Blocks.GRASS) {
				newTexture.put("#None", "minecraft:blocks/grass_top");
			} else if (blockState.getBlock() == Blocks.PODZOL) {
				newTexture.put("#None", "minecraft:blocks/dirt_podzol_top");
			} else if (blockState.getBlock() == Blocks.MYCELIUM) {
				newTexture.put("#None", "minecraft:blocks/mycelium_top");
			} else {
				BlockModelShapes blockModel = Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes();
				TextureAtlasSprite blockTexture = blockModel.getTexture(blockState);

				newTexture.put("#None", blockTexture.getName().toString());
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
		builder.add(this.baseModel.retexture(texture).bake(this.bakery, ModelLoader.defaultTextureGetter(), this.sprite, this.format));
		for (IUnbakedModel model : this.modelParts)
			builder.add(model.bake(this.bakery, ModelLoader.defaultTextureGetter(), this.sprite, this.format));

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
	protected IBakedModel generateModel(ImmutableMap<String, String> texture, IUnbakedModel... models) {
		ImmutableList.Builder<IBakedModel> builder = ImmutableList.builder();
		builder.add(this.generateModel(texture));
		for (IUnbakedModel model : models)
			builder.add(model.bake(this.bakery, ModelLoader.defaultTextureGetter(), this.sprite, this.format));

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
		return Minecraft.getInstance().getTextureMap().getAtlasSprite(textureLocation.toString());
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return baseModel.bake(this.bakery, ModelLoader.defaultTextureGetter(), this.sprite, this.format).getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ColorizerObjItemOverrideHandler.INSTANCE;
	}

	private static final class ColorizerObjItemOverrideHandler extends ItemOverrideList {
		public static final ColorizerObjItemOverrideHandler INSTANCE = new ColorizerObjItemOverrideHandler();

		private ColorizerObjItemOverrideHandler() {
		}

		@Override
		public IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, World worldIn, LivingEntity entityIn) {
			BakedColorizerOBJModel colorizerModel = (BakedColorizerOBJModel) model;

			if (stack.hasTag() && stack.getTag().contains("stored_state")) {
				return colorizerModel.getCachedModel(NBTUtil.readBlockState(NBTHelper.getTagCompound(stack, "stored_state")));
			}

			return colorizerModel.getCachedModel(Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		return PerspectiveMapWrapper.handlePerspective(baseModel.bake(this.bakery, ModelLoader.defaultTextureGetter(), this.sprite, this.format), PerspectiveMapWrapper.getTransforms(this.sprite.getState()), cameraTransformType);
	}
}
