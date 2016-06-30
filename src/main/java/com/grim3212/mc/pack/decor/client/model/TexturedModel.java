package com.grim3212.mc.pack.decor.client.model;

import java.util.List;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class TexturedModel extends SimpleBakedModel {

	public TexturedModel(List<BakedQuad> generalQuadsIn, Map<EnumFacing, List<BakedQuad>> faceQuadsIn, boolean ambientOcclusionIn, boolean gui3dIn, TextureAtlasSprite textureIn, ItemCameraTransforms cameraTransformsIn, ItemOverrideList itemOverrideListIn) {
		super(generalQuadsIn, faceQuadsIn, ambientOcclusionIn, gui3dIn, textureIn, cameraTransformsIn, itemOverrideListIn);
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return super.getItemCameraTransforms();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return super.getParticleTexture();
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		return super.getQuads(state, side, rand);
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return super.isAmbientOcclusion();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return super.isBuiltInRenderer();
	}

	@Override
	public boolean isGui3d() {
		return super.isGui3d();
	}
}
