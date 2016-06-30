package com.grim3212.mc.pack.core.client.model;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BakedQuadRetextured;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("deprecation")
public abstract class TexturedBuilder {

	private final List<BakedQuad> builderGeneralQuads;
	private final Map<EnumFacing, List<BakedQuad>> builderFaceQuads;
	private final ItemOverrideList builderItemOverrideList;
	private final boolean builderAmbientOcclusion;
	private TextureAtlasSprite builderTexture;
	private boolean builderGui3d;
	private ItemCameraTransforms builderCameraTransforms;

	public TexturedBuilder(IBakedModel model, TextureAtlasSprite texture) {
		this(model.isAmbientOcclusion(), model.isGui3d(), model.getItemCameraTransforms(), model.getOverrides());
		this.builderTexture = texture;
	}

	public TexturedBuilder(IBlockState state, IBakedModel model, TextureAtlasSprite texture, BlockPos pos) {
		this(model.isAmbientOcclusion(), model.isGui3d(), model.getItemCameraTransforms(), model.getOverrides());
		this.builderTexture = model.getParticleTexture();
		long i = MathHelper.getPositionRandom(pos);

		for (EnumFacing enumfacing : EnumFacing.values()) {
			this.addFaceQuads(state, model, texture, enumfacing, i);
		}

		this.addGeneralQuads(state, model, texture, i);
	}

	private TexturedBuilder(boolean ambientOcclusion, boolean gui3d, ItemCameraTransforms transforms, ItemOverrideList overrides) {
		this.builderGeneralQuads = Lists.<BakedQuad> newArrayList();
		this.builderFaceQuads = Maps.newEnumMap(EnumFacing.class);

		for (EnumFacing enumfacing : EnumFacing.values()) {
			this.builderFaceQuads.put(enumfacing, Lists.<BakedQuad> newArrayList());
		}

		this.builderItemOverrideList = overrides;
		this.builderAmbientOcclusion = ambientOcclusion;
		this.builderGui3d = gui3d;
		this.builderCameraTransforms = transforms;
	}

	private void addFaceQuads(IBlockState state, IBakedModel model, TextureAtlasSprite texture, EnumFacing facing, long rand) {
		for (BakedQuad bakedquad : model.getQuads(state, facing, rand)) {
			this.addFaceQuad(facing, new BakedQuadRetextured(bakedquad, texture));
		}
	}

	private void addGeneralQuads(IBlockState state, IBakedModel model, TextureAtlasSprite texture, long rand) {
		for (BakedQuad bakedquad : model.getQuads(state, (EnumFacing) null, rand)) {
			this.addGeneralQuad(new BakedQuadRetextured(bakedquad, texture));
		}
	}

	public TexturedBuilder addFaceQuad(EnumFacing facing, BakedQuad quad) {
		((List<BakedQuad>) this.builderFaceQuads.get(facing)).add(quad);
		return this;
	}

	public TexturedBuilder addGeneralQuad(BakedQuad quad) {
		this.builderGeneralQuads.add(quad);
		return this;
	}

	public TexturedBuilder setTexture(TextureAtlasSprite texture) {
		this.builderTexture = texture;
		return this;
	}

	public TextureAtlasSprite getBuilderTexture() {
		return builderTexture;
	}

	public ItemOverrideList getBuilderItemOverrideList() {
		return builderItemOverrideList;
	}

	public boolean isBuilderAmbientOcclusion() {
		return builderAmbientOcclusion;
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

	public Map<EnumFacing, List<BakedQuad>> getBuilderFaceQuads() {
		return builderFaceQuads;
	}

	public abstract IBakedModel makeBakedModel();
}
