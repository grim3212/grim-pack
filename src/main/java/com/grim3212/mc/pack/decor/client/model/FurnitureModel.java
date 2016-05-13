package com.grim3212.mc.pack.decor.client.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grim3212.mc.pack.core.client.model.TexturedBuilder;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.BlockTextured;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ISmartBlockModel;
import net.minecraftforge.client.model.ISmartItemModel;
import net.minecraftforge.common.property.IExtendedBlockState;

@SuppressWarnings("deprecation")
public class FurnitureModel extends SimpleBakedModel implements ISmartBlockModel, ISmartItemModel, IResourceManagerReloadListener {

	public FurnitureModel(List<BakedQuad> generalQuads, List<List<BakedQuad>> faceQuads, boolean ambientOcclusion, boolean gui3d, TextureAtlasSprite texture, ItemCameraTransforms cameraTransforms) {
		super(generalQuads, faceQuads, ambientOcclusion, gui3d, texture, cameraTransforms);
	}

	@Override
	public IBakedModel handleBlockState(IBlockState state) {
		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState exState = (IExtendedBlockState) state;
			if (exState.getValue(BlockTextured.BLOCKID) != null && exState.getValue(BlockTextured.BLOCKMETA) != null) {
				int blockID = (Integer) exState.getValue(BlockTextured.BLOCKID);
				int blockMeta = (Integer) exState.getValue(BlockTextured.BLOCKMETA);
				return this.getCachedModel(blockID, blockMeta);
			} else {
				return this.getCachedModel(0, 0);
			}
		}

		return this;
	}

	@Override
	public IBakedModel handleItemState(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("blockID") && stack.getTagCompound().hasKey("blockMeta")) {
			int blockID = NBTHelper.getInt(stack, "blockID");
			int blockMeta = NBTHelper.getInt(stack, "blockMeta");
			return this.getCachedModel(blockID, blockMeta);
		}
		return this;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		this.cache.clear();
	}

	private final Map<List<Integer>, IBakedModel> cache = new HashMap<List<Integer>, IBakedModel>();

	public IBakedModel getCachedModel(int blockID, int blockMeta) {
		List<Integer> key = Arrays.asList(blockID, blockMeta);

		if (!this.cache.containsKey(key)) {
			BlockModelShapes blockModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
			IBlockState blockState = Block.getBlockById(blockID).getStateFromMeta(blockMeta);
			TextureAtlasSprite blockTexture = blockModel.getTexture(blockState);

			if (Block.getBlockById(blockID) == Blocks.grass) {
				this.cache.put(key, new FurnitureBuilder(this, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/grass_top")).makeBakedModel());
			} else if (Block.getBlockById(blockID) == Blocks.dirt && blockMeta == 2) {
				this.cache.put(key, new FurnitureBuilder(this, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/dirt_podzol_top")).makeBakedModel());
			} else if (Block.getBlockById(blockID) == Blocks.mycelium) {
				this.cache.put(key, new FurnitureBuilder(this, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/mycelium_top")).makeBakedModel());
			} else {
				this.cache.put(key, new FurnitureBuilder(this, blockTexture).makeBakedModel());
			}
		}

		return this.cache.get(key);
	}

	public static class FurnitureBuilder extends TexturedBuilder {

		public FurnitureBuilder(IBakedModel model, TextureAtlasSprite blockTexture) {
			super(model, blockTexture);
		}

		public IBakedModel makeBakedModel() {
			if (this.getBuilderTexture() == null) {
				throw new RuntimeException("Missing particle!");
			} else {
				return new FurnitureModel(this.getBuilderGeneralQuads(), this.getBuilderFaceQuads(), this.isBuilderAmbientOcclusion(), this.isBuilderGui3d(), this.getBuilderTexture(), this.getBuilderCameraTransforms());
			}
		}
	}
}