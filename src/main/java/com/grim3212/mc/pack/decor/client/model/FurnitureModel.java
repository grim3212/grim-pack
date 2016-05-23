package com.grim3212.mc.pack.decor.client.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
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
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

@SuppressWarnings("deprecation")
public class FurnitureModel implements IBakedModel, IResourceManagerReloadListener {

	private final TextureAtlasSprite particle;
	private final IBakedModel model;

	public FurnitureModel(IBakedModel model, TextureAtlasSprite particle) {
		this.model = model;
		this.particle = particle;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState exState = (IExtendedBlockState) state;
			if (exState.getValue(BlockTextured.BLOCKID) != null && exState.getValue(BlockTextured.BLOCKMETA) != null) {
				int blockID = (Integer) exState.getValue(BlockTextured.BLOCKID);
				int blockMeta = (Integer) exState.getValue(BlockTextured.BLOCKMETA);
				return this.getCachedModel(blockID, blockMeta).getQuads(exState, side, rand);
			} else {
				return this.getCachedModel(0, 0).getQuads(exState, side, rand);
			}
		}

		return ImmutableList.of();
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
				return FurnitureModel.this.getCachedModel(blockID, blockMeta);
			}
			return FurnitureModel.this;
		}
	};

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

			if (Block.getBlockById(blockID) == Blocks.GRASS) {
				this.cache.put(key, new BakedModel(model, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/grass_top")));
			} else if (Block.getBlockById(blockID) == Blocks.DIRT && blockMeta == 2) {
				this.cache.put(key, new BakedModel(model, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/dirt_podzol_top")));
			} else if (Block.getBlockById(blockID) == Blocks.MYCELIUM) {
				this.cache.put(key, new BakedModel(model, Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/mycelium_top")));
			} else {
				this.cache.put(key, new BakedModel(model, blockTexture));
			}
		}

		System.out.println(key);
		
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
		return this.particle;
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}
}