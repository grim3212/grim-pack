package com.grim3212.mc.pack.decor.client.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.client.model.TexturedBuilder;
import com.grim3212.mc.pack.core.util.NBTHelper;

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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class LampPostItemModel extends SimpleBakedModel implements IBakedModel {

	public LampPostItemModel(List<BakedQuad> l1, Map<EnumFacing, List<BakedQuad>> l2, boolean b1, boolean b2, TextureAtlasSprite sprite, ItemCameraTransforms cam, ItemOverrideList itemOverrideListIn) {
		super(l1, l2, b1, b2, sprite, cam, itemOverrideListIn);
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
				return LampPostItemModel.this.getCachedModel(blockID, blockMeta);
			}
			return super.handleItemState(model, stack, world, entity);
		}
	};

	private final Map<List<Integer>, IBakedModel> cache = new HashMap<List<Integer>, IBakedModel>();

	public IBakedModel getCachedModel(int blockID, int blockMeta) {
		List<Integer> key = Arrays.asList(blockID, blockMeta);

		if (!this.cache.containsKey(key)) {
			BlockModelShapes blockModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
			IBlockState blockState = Block.getBlockById(blockID).getStateFromMeta(blockMeta);
			TextureAtlasSprite blockTexture = blockModel.getTexture(blockState);

			if (Block.getBlockById(blockID) == Blocks.GRASS) {
				this.cache.put(key, new LampPostItemBuilder(this, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/grass_top")).makeBakedModel());
			} else if (Block.getBlockById(blockID) == Blocks.DIRT && blockMeta == 2) {
				this.cache.put(key, new LampPostItemBuilder(this, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/dirt_podzol_top")).makeBakedModel());
			} else if (Block.getBlockById(blockID) == Blocks.MYCELIUM) {
				this.cache.put(key, new LampPostItemBuilder(this, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/mycelium_top")).makeBakedModel());
			} else {
				this.cache.put(key, new LampPostItemBuilder(this, blockTexture).makeBakedModel());
			}
		}

		return this.cache.get(key);
	}

	public static class LampPostItemBuilder extends TexturedBuilder {

		public LampPostItemBuilder(IBakedModel model, TextureAtlasSprite blockTexture) {
			super(model, blockTexture);
		}

		public IBakedModel makeBakedModel() {
			if (this.getBuilderTexture() == null) {
				throw new RuntimeException("Missing particle!");
			} else {
				return new LampPostItemModel(this.getBuilderGeneralQuads(), this.getBuilderFaceQuads(), this.isBuilderAmbientOcclusion(), this.isBuilderGui3d(), this.getBuilderTexture(), this.getBuilderCameraTransforms(), this.getBuilderItemOverrideList());
			}
		}
	}
}
