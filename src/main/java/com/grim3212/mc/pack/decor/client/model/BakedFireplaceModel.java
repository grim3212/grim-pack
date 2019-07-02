package com.grim3212.mc.pack.decor.client.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class BakedFireplaceModel extends BakedColorizerModel {

	public BakedFireplaceModel(ModelBakery bakery, ISprite sprite, ImmutableList<ResourceLocation> modelLocation, ResourceLocation textureLocation, VertexFormat fmt) {
		super(bakery, sprite, modelLocation, textureLocation, fmt);
	}

	@Override
	public ItemOverrideList getOverrides() {
		return FireplaceItemOverrideHandler.INSTANCE;
	}

	private static final class FireplaceItemOverrideHandler extends ItemOverrideList {
		public static final FireplaceItemOverrideHandler INSTANCE = new FireplaceItemOverrideHandler();

		private FireplaceItemOverrideHandler() {
		}

		@Override
		public IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, World worldIn, LivingEntity entityIn) {
			BakedFireplaceModel fireplaceModel = (BakedFireplaceModel) model;

			if (stack.hasTag() && stack.getTag().contains("stored_state")) {
				return fireplaceModel.getCachedModel(Block.getBlockFromItem(stack.getItem()).getDefaultState(), NBTUtil.readBlockState(NBTHelper.getTagCompound(stack, "stored_state")));
			}

			return fireplaceModel.getCachedModel(Block.getBlockFromItem(stack.getItem()).getDefaultState(), Blocks.AIR.getDefaultState());
		}
	}

	private final Map<BlockState, IBakedModel> cache = new HashMap<BlockState, IBakedModel>();
	private final Map<List<Object>, IBakedModel> firepitCache = new HashMap<List<Object>, IBakedModel>();
	private static final ResourceLocation FIREPIT_COVERED = new ResourceLocation("grimpack:block/firepit_covered");

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		cache.clear();
		firepitCache.clear();
	}

	public IBakedModel getCachedModel(BlockState state, BlockState blockState) {
		List<Object> firepitKey = Arrays.asList(blockState, DecorConfig.enableFirepitNet.get() ? 1 : 0);
		ImmutableMap.Builder<String, String> newTexture = ImmutableMap.builder();

		if (state.getBlock() == DecorBlocks.firepit) {
			if (!this.firepitCache.containsKey(firepitKey)) {
				if (blockState == Blocks.AIR.getDefaultState()) {
					newTexture.put("texture", "grimpack:blocks/colorizer");
				} else if (blockState.getBlock() == Blocks.GRASS) {
					newTexture.put("texture", "minecraft:blocks/grass_top");
				} else if (blockState.getBlock() == Blocks.PODZOL) {
					newTexture.put("texture", "minecraft:blocks/dirt_podzol_top");
				} else if (blockState.getBlock() == Blocks.MYCELIUM) {
					newTexture.put("texture", "minecraft:blocks/mycelium_top");
				} else {
					BlockModelShapes blockModel = Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes();
					TextureAtlasSprite blockTexture = blockModel.getTexture(blockState);

					newTexture.put("texture", blockTexture.getName().toString());
				}

				if (DecorConfig.enableFirepitNet.get()) {
					this.firepitCache.put(firepitKey, generateModel(newTexture.build(), ModelLoaderRegistry.getModelOrLogError(FIREPIT_COVERED, "Model part not found " + FIREPIT_COVERED)));
				} else {
					this.firepitCache.put(firepitKey, generateModel(newTexture.build()));
				}
			}

			return this.firepitCache.get(firepitKey);
		}

		if (!this.cache.containsKey(blockState)) {
			if (blockState == Blocks.AIR.getDefaultState()) {
				newTexture.put("texture", "grimpack:blocks/colorizer");
			} else if (blockState.getBlock() == Blocks.GRASS) {
				newTexture.put("texture", "minecraft:blocks/grass_top");
			} else if (blockState.getBlock() == Blocks.PODZOL) {
				newTexture.put("texture", "minecraft:blocks/dirt_podzol_top");
			} else if (blockState.getBlock() == Blocks.MYCELIUM) {
				newTexture.put("texture", "minecraft:blocks/mycelium_top");
			} else {
				BlockModelShapes blockModel = Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes();
				TextureAtlasSprite blockTexture = blockModel.getTexture(blockState);

				newTexture.put("texture", blockTexture.getName().toString());
			}

			this.cache.put(blockState, generateModel(newTexture.build()));
		}

		return this.cache.get(blockState);
	}
}