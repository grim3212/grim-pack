package com.grim3212.mc.pack.core.client.model;

import java.util.List;
import java.util.Random;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.TRSRTransformation;

@SuppressWarnings("deprecation")
public class CompositeModel implements IBakedModel {

	private final ImmutableList<IBakedModel> models;

	public CompositeModel(ImmutableList<IBakedModel> models) {
		this.models = models;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, Random rand) {
		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

		for (IBakedModel model : models) {
			builder.addAll(model.getQuads(state, side, rand));
		}

		return builder.build();
	}

	@Override
	public boolean isAmbientOcclusion() {
		return models.get(0).isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return models.get(0).isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return models.get(0).isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return models.get(0).getParticleTexture();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return models.get(0).getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.EMPTY;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		Pair<? extends IBakedModel, Matrix4f> pair = models.get(0).handlePerspective(cameraTransformType);
		if (pair != null && pair.getRight() != null)
			return Pair.of(this, pair.getRight());
		return Pair.of(this, TRSRTransformation.identity().getMatrixVec());
	}

}
