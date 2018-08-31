package com.grim3212.mc.pack.tools.client.model;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.items.ItemBetterBucket;
import com.grim3212.mc.pack.tools.items.ItemBetterMilkBucket;

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
import net.minecraftforge.client.model.ItemTextureQuadConverter;
import net.minecraftforge.client.model.ModelStateComposition;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class BetterBucketModel implements IModel {

	// minimal Z offset to prevent depth-fighting
	private static final float NORTH_Z_BASE = 7.496f / 16f;
	private static final float SOUTH_Z_BASE = 8.504f / 16f;
	private static final float NORTH_Z_FLUID = 7.498f / 16f;
	private static final float SOUTH_Z_FLUID = 8.502f / 16f;

	public static final IModel MODEL = new BetterBucketModel();

	protected final ResourceLocation baseLocation;
	protected final ResourceLocation liquidLocation;
	protected final ResourceLocation coverLocation;
	protected HashMap<String, ResourceLocation> overlays = Maps.newHashMap();

	protected final Fluid fluid;
	protected final boolean flipGas;
	protected final String customName;

	public BetterBucketModel() {
		this(null, null, null, null, false, "");
	}

	public BetterBucketModel(ResourceLocation baseLocation, ResourceLocation liquidLocation, ResourceLocation coverLocation, Fluid fluid, boolean flipGas, String customName) {
		this.baseLocation = baseLocation;
		this.liquidLocation = liquidLocation;
		this.coverLocation = coverLocation;
		this.fluid = fluid;
		this.flipGas = flipGas;
		this.customName = customName;

		for (int i = 0; i < ItemBetterBucket.extraPickups.size(); i++)
			overlays.put(ItemBetterBucket.extraPickups.get(i), new ResourceLocation(GrimPack.modID, "items/overlay_" + ItemBetterBucket.extraPickups.get(i)));
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableList.of();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();
		if (baseLocation != null)
			builder.add(baseLocation);
		if (liquidLocation != null)
			builder.add(liquidLocation);
		if (coverLocation != null)
			builder.add(coverLocation);

		builder.addAll(overlays.values());

		return builder.build();
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		ImmutableMap<TransformType, TRSRTransformation> transformMap = PerspectiveMapWrapper.getTransforms(state);

		// if the fluid is a gas wi manipulate the initial state to be rotated
		// 180? to turn it upside down
		if (flipGas && fluid != null && fluid.isGaseous()) {
			state = new ModelStateComposition(state, TRSRTransformation.blockCenterToCorner(new TRSRTransformation(null, new Quat4f(0, 0, 1, 0), null, null)));
		}

		TRSRTransformation transform = state.apply(Optional.empty()).orElse(TRSRTransformation.identity());
		TextureAtlasSprite fluidSprite = null;
		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

		String extraName = this.customName;

		if (fluid != null) {
			fluidSprite = bakedTextureGetter.apply(fluid.getStill());

			if (fluidSprite == Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite() && fluid.getUnlocalizedName().equals("fluid.milk")) {
				extraName = "milk";
			}
		}

		if (baseLocation != null) {
			// build base (insidest)
			IBakedModel model = (new ItemLayerModel(ImmutableList.of(baseLocation))).bake(state, format, bakedTextureGetter);
			builder.addAll(model.getQuads(null, null, 0));
		}

		if (extraName.isEmpty()) {
			if (liquidLocation != null && fluidSprite != null) {
				TextureAtlasSprite liquid = bakedTextureGetter.apply(liquidLocation);
				// build liquid layer (inside)
				builder.addAll(ItemTextureQuadConverter.convertTexture(format, transform, liquid, fluidSprite, NORTH_Z_FLUID, EnumFacing.NORTH, fluid.getColor(), -1));
				builder.addAll(ItemTextureQuadConverter.convertTexture(format, transform, liquid, fluidSprite, SOUTH_Z_FLUID, EnumFacing.SOUTH, fluid.getColor(), -1));
			}

			if (coverLocation != null) {
				// cover (the actual item around the other two)
				TextureAtlasSprite base = bakedTextureGetter.apply(coverLocation);
				builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, NORTH_Z_BASE, base, EnumFacing.NORTH, 0xffffffff, -1));
				builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, SOUTH_Z_BASE, base, EnumFacing.SOUTH, 0xffffffff, -1));
			}
		} else {
			if (ItemBetterBucket.extraPickups.contains(extraName)) {
				TextureAtlasSprite base = bakedTextureGetter.apply(overlays.get(extraName));
				builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, NORTH_Z_BASE, base, EnumFacing.NORTH, 0xffffffff, -1));
				builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, SOUTH_Z_BASE, base, EnumFacing.SOUTH, 0xffffffff, -1));
			}
		}

		return new BakedBetterBucket(this, builder.build(), fluidSprite, format, Maps.immutableEnumMap(transformMap), Maps.<String, IBakedModel>newHashMap());
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

	/**
	 * Sets the liquid in the model. fluid - Name of the fluid in the
	 * FluidRegistry flipGas - If "true" the model will be flipped upside down
	 * if the liquid is a gas. If "false" it wont
	 * <p/>
	 * If the fluid can't be found, water is used
	 */
	@Override
	public IModel process(ImmutableMap<String, String> customData) {
		String fluidName = customData.get("fluid");
		String custom = "";
		Fluid fluid = FluidRegistry.getFluid(fluidName);

		if (fluid == null) {
			fluid = this.fluid;

			if (ItemBetterBucket.extraPickups.contains(fluidName))
				custom = fluidName;
		}

		boolean flip = flipGas;
		if (customData.containsKey("flipGas")) {
			String flipStr = customData.get("flipGas");
			if (flipStr.equals("true"))
				flip = true;
			else if (flipStr.equals("false"))
				flip = false;
			else
				throw new IllegalArgumentException(String.format("BetterBucket custom data \"flipGas\" must have value \'true\' or \'false\' (was \'%s\')", flipStr));
		}

		// create new model with correct liquid
		return new BetterBucketModel(baseLocation, liquidLocation, coverLocation, fluid, flip, custom);
	}

	/**
	 * Allows to use different textures for the model. There are 3 layers: base
	 * - The empty bucket/container fluid - A texture representing the liquid
	 * portion. Non-transparent = liquid cover - An overlay that's put over the
	 * liquid (optional)
	 * <p/>
	 * If no liquid is given a hardcoded variant for the bucket is used.
	 */
	@Override
	public IModel retexture(ImmutableMap<String, String> textures) {
		ResourceLocation base = baseLocation;
		ResourceLocation liquid = liquidLocation;
		ResourceLocation cover = coverLocation;

		if (textures.containsKey("base"))
			base = new ResourceLocation(textures.get("base"));
		if (textures.containsKey("fluid"))
			liquid = new ResourceLocation(textures.get("fluid"));
		if (textures.containsKey("cover"))
			cover = new ResourceLocation(textures.get("cover"));

		return new BetterBucketModel(base, liquid, cover, fluid, flipGas, customName);
	}

	public enum LoaderDynBucket implements ICustomModelLoader {
		instance;

		@Override
		public boolean accepts(ResourceLocation modelLocation) {
			return modelLocation.getResourceDomain().equals(GrimPack.modID) && modelLocation.getResourcePath().contains("better_bucket");
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

	private static final class BakedBetterBucketOverrideHandler extends ItemOverrideList {
		public static final BakedBetterBucketOverrideHandler INSTANCE = new BakedBetterBucketOverrideHandler();

		private BakedBetterBucketOverrideHandler() {
			super(ImmutableList.<ItemOverride>of());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
			FluidStack fluidStack = FluidUtil.getFluidContained(stack);
			if (fluidStack == null) {
				if (Utils.hasFluidHandler(stack)) {
					fluidStack = (Utils.getFluidHandler(stack)).drain(Fluid.BUCKET_VOLUME, false);
				}
			}

			BakedBetterBucket bucketModel = (BakedBetterBucket) originalModel;

			// not a fluid item apparently
			if (fluidStack == null) {
				if (ItemBetterBucket.extraPickups.contains(ItemBetterBucket.getFluid(stack)) || stack.getItem() instanceof ItemBetterMilkBucket) {
					String name = ItemBetterBucket.getFluid(stack);
					if (name.isEmpty() && stack.getItem() instanceof ItemBetterMilkBucket)
						name = "milk";

					if (!bucketModel.cache.containsKey(name)) {
						IModel model = bucketModel.parent.process(ImmutableMap.of("fluid", name));
						Function<ResourceLocation, TextureAtlasSprite> textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
							public TextureAtlasSprite apply(ResourceLocation location) {
								return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
							}
						};

						IBakedModel bakedModel = model.bake(new SimpleModelState(bucketModel.transforms), bucketModel.format, textureGetter);
						bucketModel.cache.put(name, bakedModel);
						return bakedModel;
					}
				} else {
					// empty bucket
					return bucketModel;
				}
			} else {

				Fluid fluid = fluidStack.getFluid();
				String name = fluid.getName();

				if (!bucketModel.cache.containsKey(name)) {
					IModel model = bucketModel.parent.process(ImmutableMap.of("fluid", name));
					Function<ResourceLocation, TextureAtlasSprite> textureGetter;
					textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
						public TextureAtlasSprite apply(ResourceLocation location) {
							return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
						}
					};

					IBakedModel bakedModel = model.bake(new SimpleModelState(bucketModel.transforms), bucketModel.format, textureGetter);
					bucketModel.cache.put(name, bakedModel);
					return bakedModel;
				}
			}

			return stack.getItem() instanceof ItemBetterMilkBucket ? bucketModel.cache.get("milk") : bucketModel.cache.get(ItemBetterBucket.getFluid(stack));
		}
	}

	// the dynamic bucket is based on the empty bucket
	protected static class BakedBetterBucket implements IBakedModel {

		private final BetterBucketModel parent;
		private final Map<String, IBakedModel> cache;
		private final ImmutableMap<TransformType, TRSRTransformation> transforms;
		private final ImmutableList<BakedQuad> quads;
		private final TextureAtlasSprite particle;
		private final VertexFormat format;

		public BakedBetterBucket(BetterBucketModel parent, ImmutableList<BakedQuad> quads, TextureAtlasSprite particle, VertexFormat format, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms, Map<String, IBakedModel> cache) {
			this.quads = quads;
			this.particle = particle;
			this.format = format;
			this.parent = parent;
			this.transforms = transforms;
			this.cache = cache;
		}

		@Override
		public ItemOverrideList getOverrides() {
			return BakedBetterBucketOverrideHandler.INSTANCE;
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
}