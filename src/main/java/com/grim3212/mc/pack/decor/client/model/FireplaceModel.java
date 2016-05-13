package com.grim3212.mc.pack.decor.client.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.core.client.model.TexturedBuilder;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.BlockFireplaceBase;
import com.grim3212.mc.pack.decor.block.BlockGrill;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.property.IExtendedBlockState;

@SuppressWarnings("deprecation")
public class FireplaceModel extends SimpleBakedModel implements ISmartBlockModel, ISmartItemModel, IResourceManagerReloadListener {

	public FireplaceModel(List<BakedQuad> generalQuads, List<List<BakedQuad>> faceQuads, boolean ambientOcclusion, boolean gui3d, TextureAtlasSprite texture, ItemCameraTransforms cameraTransforms) {
		super(generalQuads, faceQuads, ambientOcclusion, gui3d, texture, cameraTransforms);
	}

	@Override
	public IBakedModel handleItemState(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("blockID") && stack.getTagCompound().hasKey("blockMeta")) {
			int blockID = NBTHelper.getInt(stack, "blockID");
			int blockMeta = NBTHelper.getInt(stack, "blockMeta");
			return this.getCachedModel(Block.getBlockFromItem(stack.getItem()).getDefaultState(), blockID, blockMeta, false);
		}

		return null;
	}

	@Override
	public IBakedModel handleBlockState(IBlockState state) {
		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState exState = (IExtendedBlockState) state;
			if (exState.getBlock() instanceof BlockFireplaceBase) {
				if (exState.getValue(BlockFireplaceBase.BLOCKID) != null && exState.getValue(BlockFireplaceBase.BLOCKMETA) != null) {
					int blockID = exState.getValue(BlockFireplaceBase.BLOCKID);
					int blockMeta = exState.getValue(BlockFireplaceBase.BLOCKMETA);
					return this.getCachedModel(state, blockID, blockMeta, exState.getValue(BlockFireplaceBase.ACTIVE));
				} else {
					return this.getCachedModel(state, 0, 0, false);
				}
			} else {
				if (exState.getValue(BlockGrill.BLOCKID) != null && exState.getValue(BlockGrill.BLOCKMETA) != null) {
					int blockID = exState.getValue(BlockGrill.BLOCKID);
					int blockMeta = exState.getValue(BlockGrill.BLOCKMETA);
					return this.getCachedModel(state, blockID, blockMeta, exState.getValue(BlockGrill.ACTIVE));
				} else {
					return this.getCachedModel(state, 0, 0, false);
				}
			}
		}

		return null;
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
						this.firepitCache.put(firepitKey, RenderHelper.mergeModels(new FireplaceBuilder(this, blockTexture).makeBakedModel(), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:firepit_wood", "inventory")), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:firepit_covered", "inventory")), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:fire_high", "inventory"))));
					} else {
						this.firepitCache.put(firepitKey, RenderHelper.mergeModels(new FireplaceBuilder(this, blockTexture).makeBakedModel(), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:firepit_wood", "inventory")), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:fire_high", "inventory"))));
					}
				} else {
					if (DecorConfig.enableFirepitNet) {
						this.firepitCache.put(firepitKey, RenderHelper.mergeModels(new FireplaceBuilder(this, blockTexture).makeBakedModel(), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:firepit_wood", "inventory")), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:firepit_covered", "inventory"))));
					} else {
						this.firepitCache.put(firepitKey, RenderHelper.mergeModels(new FireplaceBuilder(this, blockTexture).makeBakedModel(), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:firepit_wood", "inventory"))));
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
				this.cache.put(key, RenderHelper.mergeModels(new FireplaceBuilder(this, blockTexture).makeBakedModel(), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:chimney_top", "inventory"))));
			} else if (state.getBlock() == DecorBlocks.fireplace) {
				if (isActive && !DecorConfig.isFireParticles) {
					this.cache.put(key, RenderHelper.mergeModels(new FireplaceBuilder(this, blockTexture).makeBakedModel(), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:fireplace_wood", "inventory")), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:fire", "inventory"))));
				} else {
					this.cache.put(key, RenderHelper.mergeModels(new FireplaceBuilder(this, blockTexture).makeBakedModel(), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:fireplace_wood", "inventory"))));
				}
			} else if (state.getBlock() == DecorBlocks.firering) {
				if (isActive && !DecorConfig.isFireParticles) {
					this.cache.put(key, RenderHelper.mergeModels(new FireplaceBuilder(this, blockTexture).makeBakedModel(), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:firering_wood", "inventory")), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:fire", "inventory"))));
				} else {
					this.cache.put(key, RenderHelper.mergeModels(new FireplaceBuilder(this, blockTexture).makeBakedModel(), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:firering_wood", "inventory"))));
				}
			} else if (state.getBlock() == DecorBlocks.stove) {
				if (isActive && !DecorConfig.isFireParticles) {
					this.cache.put(key, RenderHelper.mergeModels(new FireplaceBuilder(this, blockTexture).makeBakedModel(), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:stove_wood", "inventory")), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:stove_covered", "inventory")), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:fire_high", "inventory"))));
				} else {
					this.cache.put(key, RenderHelper.mergeModels(new FireplaceBuilder(this, blockTexture).makeBakedModel(), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:stove_wood", "inventory")), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:stove_covered", "inventory"))));
				}
			} else if (state.getBlock() == DecorBlocks.grill) {
				if (isActive && !DecorConfig.isFireParticles) {
					this.cache.put(key, RenderHelper.mergeModels(new FireplaceBuilder(this, blockTexture).makeBakedModel(), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:grill_coal", "inventory")), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:grill_fire", "inventory"))));
				} else {
					this.cache.put(key, RenderHelper.mergeModels(new FireplaceBuilder(this, blockTexture).makeBakedModel(), blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:grill_coal", "inventory"))));
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
				return new FireplaceModel(this.getBuilderGeneralQuads(), this.getBuilderFaceQuads(), this.isBuilderAmbientOcclusion(), this.isBuilderGui3d(), this.getBuilderTexture(), this.getBuilderCameraTransforms());
			}
		}
	}
}