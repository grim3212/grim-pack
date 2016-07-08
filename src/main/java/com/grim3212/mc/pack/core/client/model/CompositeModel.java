package com.grim3212.mc.pack.core.client.model;

import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;

public class CompositeModel implements IPerspectiveAwareModel {

	private final ImmutableList<IBakedModel> models;

	public CompositeModel(ImmutableList<IBakedModel> models) {
		this.models = models;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
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
	@SuppressWarnings("deprecation")
	public ItemCameraTransforms getItemCameraTransforms() {
		return models.get(0).getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		if (models.get(0) instanceof IPerspectiveAwareModel) {
			Pair<? extends IBakedModel, Matrix4f> pair = ((IPerspectiveAwareModel) models.get(0)).handlePerspective(cameraTransformType);
			if (pair != null && pair.getRight() != null)
				return Pair.of(this, pair.getRight());
		}
		return Pair.of(this, TRSRTransformation.identity().getMatrix());
	}

}
