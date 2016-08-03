package com.grim3212.mc.pack.decor.client.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
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
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;

@SuppressWarnings("deprecation")
public class BakedFireplaceModel extends BakedTexturedModel {

	public BakedFireplaceModel(IModelState modelState, ImmutableList<ResourceLocation> modelLocation, ResourceLocation textureLocation, VertexFormat fmt, ImmutableMap<TransformType, TRSRTransformation> transforms) {
		super(modelState, modelLocation, textureLocation, fmt, transforms);
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
				return BakedFireplaceModel.this.getCachedModel(Block.getBlockFromItem(stack.getItem()).getDefaultState(), blockID, blockMeta);
			}

			return BakedFireplaceModel.this.getCachedModel(Block.getBlockFromItem(stack.getItem()).getDefaultState(), 0, 0);
		}
	};

	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState exState = (IExtendedBlockState) state;
			if (exState.getBlock() instanceof BlockFireplaceBase) {
				if (exState.getValue(BlockFireplaceBase.BLOCKID) != null && exState.getValue(BlockFireplaceBase.BLOCKMETA) != null) {
					int blockID = exState.getValue(BlockFireplaceBase.BLOCKID);
					int blockMeta = exState.getValue(BlockFireplaceBase.BLOCKMETA);
					return this.getCachedModel(state, blockID, blockMeta).getQuads(state, side, rand);
				}
			}
		}

		return ImmutableList.of();
	}

	private final Map<List<Integer>, IBakedModel> cache = new HashMap<List<Integer>, IBakedModel>();
	private final Map<List<Integer>, IBakedModel> firepitCache = new HashMap<List<Integer>, IBakedModel>();
	private static final ResourceLocation FIREPIT_COVERED = new ResourceLocation("grimpack:block/firepit_covered");

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		cache.clear();
		firepitCache.clear();
	}

	public IBakedModel getCachedModel(IBlockState state, int blockID, int blockMeta) {
		List<Integer> key = Arrays.asList(blockID, blockMeta);
		List<Integer> firepitKey = Arrays.asList(blockID, blockMeta, DecorConfig.enableFirepitNet ? 1 : 0);
		ImmutableMap.Builder<String, String> newTexture = ImmutableMap.builder();

		if (state.getBlock() == DecorBlocks.firepit) {
			if (!this.firepitCache.containsKey(firepitKey)) {
				if (blockID == 0 && blockMeta == 0) {
					newTexture.put("texture", "grimpack:blocks/colorizer");
				} else if (Block.getBlockById(blockID) == Blocks.GRASS) {
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

				if (DecorConfig.enableFirepitNet) {
					this.firepitCache.put(firepitKey, generateModel(newTexture.build(), ModelLoaderRegistry.getModelOrLogError(FIREPIT_COVERED, "Model part not found " + FIREPIT_COVERED)));
				} else {
					this.firepitCache.put(firepitKey, generateModel(newTexture.build()));
				}
			}

			return this.firepitCache.get(firepitKey);
		}

		if (!this.cache.containsKey(key)) {
			if (blockID == 0 && blockMeta == 0) {
				newTexture.put("texture", "grimpack:blocks/colorizer");
			} else if (Block.getBlockById(blockID) == Blocks.GRASS) {
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

			this.cache.put(key, generateModel(newTexture.build()));
		}

		return this.cache.get(key);
	}
}