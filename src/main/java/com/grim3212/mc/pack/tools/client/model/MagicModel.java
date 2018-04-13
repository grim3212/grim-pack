package com.grim3212.mc.pack.tools.client.model;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.tools.items.ItemMagicStone.StoneType;
import com.grim3212.mc.pack.tools.util.EnumCrystalType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class MagicModel implements IModel {

	public static final IModel MODEL = new MagicModel();

	public final EnumCrystalType mainType;
	public final EnumCrystalType subType;
	public final MagicItemType magicType;

	public MagicModel() {
		this(null, null, null);
	}

	public MagicModel(EnumCrystalType mainType, EnumCrystalType subType, MagicItemType magicType) {
		this.mainType = mainType;
		this.subType = subType;
		this.magicType = magicType;
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		ImmutableMap<TransformType, TRSRTransformation> transformMap = PerspectiveMapWrapper.getTransforms(state);
		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

		TextureAtlasSprite mainSprite = null;

		if (this.mainType != null && this.magicType != null) {
			ImmutableList.Builder<ResourceLocation> textureBuilder = ImmutableList.builder();

			if (this.magicType == MagicItemType.WAND) {
				ResourceLocation mainLocation = new ResourceLocation(GrimPack.modID, "items/magic/wand_" + this.mainType.getUnlocalized());

				// Set the sprite
				mainSprite = bakedTextureGetter.apply(mainLocation);

				textureBuilder.add(mainLocation);

				if (this.subType != null) {
					textureBuilder.add(new ResourceLocation(GrimPack.modID, "items/magic/wand_core_" + this.subType.getUnlocalized()));
				}
			} else if (this.magicType == MagicItemType.STONE) {
				ResourceLocation mainLocation = new ResourceLocation(GrimPack.modID, "items/magic/main_" + this.mainType.getUnlocalized());

				// Set the sprite
				mainSprite = bakedTextureGetter.apply(mainLocation);

				textureBuilder.add(mainLocation);

				if (this.subType != null) {
					textureBuilder.add(new ResourceLocation(GrimPack.modID, "items/magic/sub_" + this.subType.getUnlocalized()));
				}
			}

			IBakedModel model = (new ItemLayerModel(textureBuilder.build()).bake(state, format, bakedTextureGetter));
			builder.addAll(model.getQuads(null, null, 0));
		}

		return new BakedMagicModel(this, builder.build(), mainSprite, format, Maps.immutableEnumMap(transformMap), Maps.<StoneType, IBakedModel>newHashMap());
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableList.of();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();

		if (this.magicType != null) {
			if (this.magicType == MagicItemType.WAND) {
				if (this.mainType != null)
					builder.add(new ResourceLocation(GrimPack.modID, "items/magic/wand_" + this.mainType.getUnlocalized()));
				if (this.subType != null)
					builder.add(new ResourceLocation(GrimPack.modID, "items/magic/wand_core_" + this.subType.getUnlocalized()));
			} else if (this.magicType == MagicItemType.STONE) {
				if (this.mainType != null)
					builder.add(new ResourceLocation(GrimPack.modID, "items/magic/main_" + this.mainType.getUnlocalized()));
				if (this.subType != null)
					builder.add(new ResourceLocation(GrimPack.modID, "items/magic/sub_" + this.subType.getUnlocalized()));
			}
		}

		return builder.build();
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

	@Override
	public IModel process(ImmutableMap<String, String> customData) {
		EnumCrystalType mainType = EnumCrystalType.fromString(customData.get("main"));
		EnumCrystalType subType = EnumCrystalType.fromString(customData.get("sub"));

		MagicItemType type = magicType;
		if (customData.containsKey("type")) {
			String typeStr = customData.get("type").replaceAll("^\"|\"$", "");
			if (typeStr.equals("wand"))
				type = MagicItemType.WAND;
			else if (typeStr.equals("stone"))
				type = MagicItemType.STONE;
			else
				throw new IllegalArgumentException(String.format("MagicModel custom data \"type\" must have value \'wand\' or \'stone\' (was \'%s\')", typeStr));
		}

		return new MagicModel(mainType, subType, type);
	}

	public enum LoaderMagic implements ICustomModelLoader {
		instance;

		@Override
		public boolean accepts(ResourceLocation modelLocation) {
			return modelLocation.getResourceDomain().equals(GrimPack.modID) && modelLocation.getResourcePath().contains("magic_item");
		}

		@Override
		public IModel loadModel(ResourceLocation modelLocation) throws IOException {
			return MODEL;
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {
			// no need to clear cache since we create a new model instance
		}
	}

	private static final class BakedMagicStoneOverrideHandler extends ItemOverrideList {
		public static final BakedMagicStoneOverrideHandler INSTANCE = new BakedMagicStoneOverrideHandler();

		private BakedMagicStoneOverrideHandler() {
			super(ImmutableList.<ItemOverride>of());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
			BakedMagicModel stoneModel = (BakedMagicModel) originalModel;

			StoneType stoneType = StoneType.getStoneFromMeta(stack.getMetadata());

			if (stoneType != null) {
				if (!stoneModel.cache.containsKey(stoneType)) {
					IModel model = stoneModel.parent.process(ImmutableMap.of("main", stoneType.mainType.getUnlocalized(), "sub", stoneType.subType.getUnlocalized()));
					Function<ResourceLocation, TextureAtlasSprite> textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
						public TextureAtlasSprite apply(ResourceLocation location) {
							return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
						}
					};

					IBakedModel bakedModel = model.bake(new SimpleModelState(stoneModel.transforms), stoneModel.format, textureGetter);
					stoneModel.cache.put(stoneType, bakedModel);
					return bakedModel;
				}

				return stoneModel.cache.get(stoneType);
			} else {
				return stoneModel;
			}
		}
	}

	// the dynamic bucket is based on the empty bucket
	protected static class BakedMagicModel implements IBakedModel {

		private final MagicModel parent;
		private final Map<StoneType, IBakedModel> cache;
		private final ImmutableMap<TransformType, TRSRTransformation> transforms;
		private final ImmutableList<BakedQuad> quads;
		private final TextureAtlasSprite particle;
		private final VertexFormat format;

		public BakedMagicModel(MagicModel parent, ImmutableList<BakedQuad> quads, TextureAtlasSprite particle, VertexFormat format, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms, Map<StoneType, IBakedModel> cache) {
			this.quads = quads;
			this.particle = particle;
			this.format = format;
			this.parent = parent;
			this.transforms = transforms;
			this.cache = cache;
		}

		@Override
		public ItemOverrideList getOverrides() {
			return BakedMagicStoneOverrideHandler.INSTANCE;
		}

		@Override
		public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
			return PerspectiveMapWrapper.handlePerspective(this, transforms, cameraTransformType);
		}

		@Override
		public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
			if (side == null)
				return quads;
			return ImmutableList.of();
		}

		public boolean isAmbientOcclusion() {
			return true;
		}

		public boolean isGui3d() {
			return false;
		}

		public boolean isBuiltInRenderer() {
			return false;
		}

		public TextureAtlasSprite getParticleTexture() {
			return particle;
		}

		public ItemCameraTransforms getItemCameraTransforms() {
			return ItemCameraTransforms.DEFAULT;
		}
	}

	public enum MagicItemType {
		STONE, WAND;
	}
}
