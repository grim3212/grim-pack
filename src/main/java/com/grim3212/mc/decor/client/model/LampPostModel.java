package com.grim3212.mc.decor.client.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.decor.block.BlockLampPost;
import com.grim3212.mc.decor.block.DecorBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.model.ISmartBlockModel;
import net.minecraftforge.common.property.IExtendedBlockState;

@SuppressWarnings("deprecation")
public class LampPostModel extends SimpleBakedModel implements ISmartBlockModel {

	public LampPostModel(List<BakedQuad> l1, List<List<BakedQuad>> l2, boolean b1, boolean b2, TextureAtlasSprite sprite, ItemCameraTransforms cam) {
		super(l1, l2, b1, b2, sprite, cam);
	}

	@Override
	public IBakedModel handleBlockState(IBlockState state) {
		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState exState = (IExtendedBlockState) state;
			if (exState.getValue(BlockLampPost.BLOCKID) != null && exState.getValue(BlockLampPost.BLOCKMETA) != null) {
				int blockID = (Integer) exState.getValue(BlockLampPost.BLOCKID);
				int blockMeta = (Integer) exState.getValue(BlockLampPost.BLOCKMETA);
				return this.getCachedModel(state.getBlock(), blockID, blockMeta);
			} else {
				return this.getCachedModel(state.getBlock(), 0, 0);
			}
		}

		return this;
	}

	private final Map<List<Integer>, IBakedModel> cache = new HashMap<List<Integer>, IBakedModel>();

	public IBakedModel getCachedModel(Block block, int blockID, int blockMeta) {
		List<Integer> key = Arrays.asList(blockID, blockMeta);

		if (!this.cache.containsKey(key)) {
			BlockModelShapes blockModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
			IBlockState blockState = Block.getBlockById(blockID).getStateFromMeta(blockMeta);
			TextureAtlasSprite blockTexture = blockModel.getTexture(blockState);

			if (Block.getBlockById(blockID) == Blocks.grass) {
				if (block == DecorBlocks.lamp_post_top) {
					this.cache.put(key, RenderHelper.mergeModels(blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:calendar", "inventory")), new TexturedBuilder(this, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/grass_top")).makeBakedModel()));
				} else
					this.cache.put(key, new TexturedBuilder(this, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/grass_top")).makeBakedModel());
			} else if (Block.getBlockById(blockID) == Blocks.dirt && blockMeta == 2) {
				if (block == DecorBlocks.lamp_post_top)
					this.cache.put(key, RenderHelper.mergeModels(blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:calendar", "inventory")), new TexturedBuilder(this, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/dirt_podzol_top")).makeBakedModel()));
				else
					this.cache.put(key, new TexturedBuilder(this, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/dirt_podzol_top")).makeBakedModel());
			} else if (Block.getBlockById(blockID) == Blocks.mycelium) {
				if (block == DecorBlocks.lamp_post_top)
					this.cache.put(key, RenderHelper.mergeModels(blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:calendar", "inventory")), new TexturedBuilder(this, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/mycelium_top")).makeBakedModel()));
				else
					this.cache.put(key, new TexturedBuilder(this, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/mycelium_top")).makeBakedModel());
			} else {
				if (block == DecorBlocks.lamp_post_top) {
					this.cache.put(key, RenderHelper.mergeModels(blockModel.getModelManager().getModel(new ModelResourceLocation("grimdecor:calendar", "inventory")), new TexturedBuilder(this, blockTexture).makeBakedModel()));
				} else {
					this.cache.put(key, new TexturedBuilder(this, blockTexture).makeBakedModel());

				}
			}
		}

		return this.cache.get(key);
	}
}
