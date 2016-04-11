package com.grim3212.mc.core.client.model;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BreakingFour;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.EnumFacing;

@SuppressWarnings("deprecation")
public abstract class TexturedBuilder {

	private final List<BakedQuad> builderGeneralQuads;
	private final List<List<BakedQuad>> builderFaceQuads;
	private final boolean builderAmbientOcclusion;
	private TextureAtlasSprite builderTexture;
	private boolean builderGui3d;
	private ItemCameraTransforms builderCameraTransforms;

	public TexturedBuilder(IBakedModel model, TextureAtlasSprite blockTexture) {
		this(model.isAmbientOcclusion(), model.isGui3d(), model.getItemCameraTransforms());
		this.builderTexture = blockTexture;
		EnumFacing[] aenumfacing = EnumFacing.values();
		int i = aenumfacing.length;

		for (int j = 0; j < i; ++j) {
			EnumFacing enumfacing = aenumfacing[j];
			this.addFaceBreakingFours(model, blockTexture, enumfacing);
		}

		this.addGeneralBreakingFours(model, blockTexture);
	}

	private void addFaceBreakingFours(IBakedModel model, TextureAtlasSprite blockTexture, EnumFacing facing) {
		Iterator<BakedQuad> iterator = model.getFaceQuads(facing).iterator();

		while (iterator.hasNext()) {
			BakedQuad bakedquad = (BakedQuad) iterator.next();
			TextureAtlasSprite sprite = blockTexture;
			this.addFaceQuad(facing, new BreakingFour(bakedquad, sprite));
		}
	}

	private void addGeneralBreakingFours(IBakedModel model, TextureAtlasSprite blockTexture) {
		Iterator<BakedQuad> iterator = model.getGeneralQuads().iterator();

		while (iterator.hasNext()) {
			BakedQuad bakedquad = (BakedQuad) iterator.next();

			TextureAtlasSprite sprite = blockTexture;
			this.addGeneralQuad(new BreakingFour(bakedquad, sprite));
		}
	}

	private TexturedBuilder(boolean ao, boolean gui3d, ItemCameraTransforms cam) {
		this.builderGeneralQuads = Lists.newArrayList();
		this.builderFaceQuads = Lists.newArrayListWithCapacity(6);
		EnumFacing[] aenumfacing = EnumFacing.values();
		int i = aenumfacing.length;

		for (int j = 0; j < i; ++j) {
			this.builderFaceQuads.add(Lists.<BakedQuad> newArrayList());
		}

		this.builderAmbientOcclusion = ao;
		this.builderGui3d = gui3d;
		this.builderCameraTransforms = cam;
	}

	public TexturedBuilder addFaceQuad(EnumFacing facing, BakedQuad bakedQuad) {
		((List<BakedQuad>) this.builderFaceQuads.get(facing.ordinal())).add(bakedQuad);
		return this;
	}

	public TexturedBuilder addGeneralQuad(BakedQuad bakedQuad) {
		this.builderGeneralQuads.add(bakedQuad);
		return this;
	}

	public TexturedBuilder setTexture(TextureAtlasSprite sprite) {
		this.builderTexture = sprite;
		return this;
	}

	public abstract IBakedModel makeBakedModel();

	public boolean isBuilderAmbientOcclusion() {
		return builderAmbientOcclusion;
	}

	public TextureAtlasSprite getBuilderTexture() {
		return builderTexture;
	}

	public boolean isBuilderGui3d() {
		return builderGui3d;
	}

	public ItemCameraTransforms getBuilderCameraTransforms() {
		return builderCameraTransforms;
	}

	public List<BakedQuad> getBuilderGeneralQuads() {
		return builderGeneralQuads;
	}

	public List<List<BakedQuad>> getBuilderFaceQuads() {
		return builderFaceQuads;
	}
}
