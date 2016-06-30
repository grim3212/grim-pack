package com.grim3212.mc.pack.decor.client.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.BlockTextured;

import net.minecraft.block.Block;
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

public class BakedFurnitureModel implements IPerspectiveAwareModel, IResourceManagerReloadListener {

	private final IModelState state;
	private final ResourceLocation modelLocation;
	private final ResourceLocation textureLocation;
	private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
	private final VertexFormat format;

	public BakedFurnitureModel(IModelState state, ResourceLocation modelLocation, ResourceLocation textureLocation, VertexFormat fmt, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
		this.state = state;
		this.modelLocation = modelLocation;
		this.textureLocation = textureLocation;
		this.format = fmt;
		this.transforms = transforms;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		this.cache.clear();
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState exState = (IExtendedBlockState) state;
			if (exState.getValue(BlockTextured.BLOCKID) != null && exState.getValue(BlockTextured.BLOCKMETA) != null) {
				int blockID = exState.getValue(BlockTextured.BLOCKID);
				int blockMeta = exState.getValue(BlockTextured.BLOCKMETA);
				return this.getCachedModel(state, blockID, blockMeta).getQuads(state, side, rand);
			}
		}
		return ImmutableList.of();
	}

	private final Map<List<Integer>, IBakedModel> cache = new HashMap<List<Integer>, IBakedModel>();

	@SuppressWarnings("deprecation")
	public IBakedModel getCachedModel(IBlockState state, int blockID, int blockMeta) {
		List<Integer> key = Arrays.asList(blockID, blockMeta);

		if (!this.cache.containsKey(key)) {
			ImmutableMap.Builder<String, String> newTexture = ImmutableMap.builder();

			if (Block.getBlockById(blockID) == Blocks.GRASS) {
				newTexture.put("texture", "minecraft:blocks/grass_top");
			} else if (Block.getBlockById(blockID) == Blocks.DIRT && blockMeta == 2) {
				newTexture.put("texture", "minecraft:blocks/dirt_podzol_top");
			} else if (Block.getBlockById(blockID) == Blocks.MYCELIUM) {
				newTexture.put("texture", "minecraft:blocks/mycelium_top");
			} else {
				BlockModelShapes blockModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
				IBlockState blockState = Block.getBlockById(blockID).getStateFromMeta(blockMeta);
				TextureAtlasSprite blockTexture = blockModel.getTexture(blockState);

				newTexture.put("texture", blockTexture.getIconName());
			}

			IModel model = ModelLoaderRegistry.getModelOrLogError(this.modelLocation, "Model not found " + this.modelLocation);
			this.cache.put(key, ModelProcessingHelper.retexture(model, newTexture.build()).bake(this.state, this.format, ModelLoader.defaultTextureGetter()));
		}

		return this.cache.get(key);
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
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return itemHandler;
	}

	private final ItemOverrideList itemHandler = new ItemOverrideList(Lists.<ItemOverride> newArrayList()) {
		@Override
		public IBakedModel handleItemState(IBakedModel model, ItemStack stack, World world, EntityLivingBase entity) {
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("blockID") && stack.getTagCompound().hasKey("blockMeta")) {
				int blockID = NBTHelper.getInt(stack, "blockID");
				int blockMeta = NBTHelper.getInt(stack, "blockMeta");
				return BakedFurnitureModel.this.getCachedModel(Block.getBlockFromItem(stack.getItem()).getDefaultState(), blockID, blockMeta);
			}
			return BakedFurnitureModel.this;
		}
	};

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		return IPerspectiveAwareModel.MapWrapper.handlePerspective(this, transforms, cameraTransformType);
	}
}
