package com.grim3212.mc.pack.decor.client.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.client.model.TexturedBuilder;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.BlockFireplaceBase;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

@SuppressWarnings("deprecation")
public class FireplaceModel extends SimpleBakedModel implements IBakedModel, IResourceManagerReloadListener {

	public FireplaceModel(List<BakedQuad> generalQuads, Map<EnumFacing, List<BakedQuad>> faceQuads, boolean ambientOcclusion, boolean gui3d, TextureAtlasSprite texture, ItemCameraTransforms cameraTransforms, ItemOverrideList itemOverrideListIn) {
		super(generalQuads, faceQuads, ambientOcclusion, gui3d, texture, cameraTransforms, itemOverrideListIn);
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
				return FireplaceModel.this.getCachedModel(Block.getBlockFromItem(stack.getItem()).getDefaultState(), blockID, blockMeta, false);
			}

			return super.handleItemState(model, stack, world, entity);
		}
	};

	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState exState = (IExtendedBlockState) state;
			if (exState.getBlock() instanceof BlockFireplaceBase) {
				if (exState.getValue(BlockFireplaceBase.BLOCKID) != null && exState.getValue(BlockFireplaceBase.BLOCKMETA) != null) {
					int blockID = exState.getValue(BlockFireplaceBase.BLOCKID);
					int blockMeta = exState.getValue(BlockFireplaceBase.BLOCKMETA);
					return this.getCachedModel(state, blockID, blockMeta, exState.getValue(BlockFireplaceBase.ACTIVE)).getQuads(state, side, rand);
				} else {
					return this.getCachedModel(state, 0, 0, false).getQuads(state, side, rand);
				}
			}
		}

		return super.getQuads(state, side, rand);
	}

	private final Map<List<Integer>, IBakedModel> cache = new HashMap<List<Integer>, IBakedModel>();
	private final Map<List<Integer>, IBakedModel> firepitCache = new HashMap<List<Integer>, IBakedModel>();

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		cache.clear();
		firepitCache.clear();
	}

	public IBakedModel getCachedModel(IBlockState state, int blockID, int blockMeta, boolean isActive) {
		List<Integer> key = Arrays.asList(blockID, blockMeta, isActive ? 1 : 0, DecorConfig.isFireParticles ? 1 : 0);
		List<Integer> firepitKey = Arrays.asList(blockID, blockMeta, isActive ? 1 : 0, DecorConfig.isFireParticles ? 1 : 0, DecorConfig.enableFirepitNet ? 1 : 0);

		if (state.getBlock() == DecorBlocks.firepit) {
			if (!this.firepitCache.containsKey(firepitKey)) {
				BlockModelShapes blockModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
				IBlockState blockState = Block.getBlockById(blockID).getStateFromMeta(blockMeta);
				TextureAtlasSprite blockTexture = blockModel.getTexture(blockState);
				if (isActive && !DecorConfig.isFireParticles) {
					if (DecorConfig.enableFirepitNet) {
						this.firepitCache.put(firepitKey, new FireplaceBuilder(this, blockTexture).makeBakedModel());
					} else {
						this.firepitCache.put(firepitKey, new FireplaceBuilder(this, blockTexture).makeBakedModel());
					}
				} else {
					if (DecorConfig.enableFirepitNet) {
						this.firepitCache.put(firepitKey, new FireplaceBuilder(this, blockTexture).makeBakedModel());
					} else {
						this.firepitCache.put(firepitKey, new FireplaceBuilder(this, blockTexture).makeBakedModel());
					}
				}
			}

			return this.firepitCache.get(firepitKey);
		}

		if (!this.cache.containsKey(key)) {
			BlockModelShapes blockModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
			IBlockState blockState = Block.getBlockById(blockID).getStateFromMeta(blockMeta);
			TextureAtlasSprite blockTexture = blockModel.getTexture(blockState);

			if (state.getBlock() == DecorBlocks.chimney) {
				this.cache.put(key, new FireplaceBuilder(this, blockTexture).makeBakedModel());
			} else if (state.getBlock() == DecorBlocks.fireplace) {
				if (isActive && !DecorConfig.isFireParticles) {
					this.cache.put(key, new FireplaceBuilder(this, blockTexture).makeBakedModel());
				} else {
					this.cache.put(key, new FireplaceBuilder(this, blockTexture).makeBakedModel());
				}
			} else if (state.getBlock() == DecorBlocks.firering) {
				if (isActive && !DecorConfig.isFireParticles) {
					this.cache.put(key, new FireplaceBuilder(this, blockTexture).makeBakedModel());
				} else {
					this.cache.put(key, new FireplaceBuilder(this, blockTexture).makeBakedModel());
				}
			} else if (state.getBlock() == DecorBlocks.stove) {
				if (isActive && !DecorConfig.isFireParticles) {
					this.cache.put(key, new FireplaceBuilder(this, blockTexture).makeBakedModel());
				} else {
					this.cache.put(key, new FireplaceBuilder(this, blockTexture).makeBakedModel());
				}
			} else if (state.getBlock() == DecorBlocks.grill) {
				if (isActive && !DecorConfig.isFireParticles) {
					this.cache.put(key, new FireplaceBuilder(this, blockTexture).makeBakedModel());
				} else {
					this.cache.put(key, new FireplaceBuilder(this, blockTexture).makeBakedModel());
				}
			}
		}

		return this.cache.get(key);
	}

	public static class FireplaceBuilder extends TexturedBuilder {

		public FireplaceBuilder(IBakedModel model, TextureAtlasSprite blockTexture) {
			super(model, blockTexture);
		}

		public IBakedModel makeBakedModel() {
			if (this.getBuilderTexture() == null) {
				throw new RuntimeException("Missing particle!");
			} else {
				return new FireplaceModel(this.getBuilderGeneralQuads(), this.getBuilderFaceQuads(), this.isBuilderAmbientOcclusion(), this.isBuilderGui3d(), this.getBuilderTexture(), this.getBuilderCameraTransforms(), this.getBuilderItemOverrideList());
			}
		}
	}
}