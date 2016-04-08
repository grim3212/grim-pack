package com.grim3212.mc.core.client.model;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.ISmartBlockModel;
import net.minecraftforge.common.property.IExtendedBlockState;

@SuppressWarnings("deprecation")
public class MergedModel implements IFlexibleBakedModel, ISmartBlockModel {

	private List<IBakedModel> models;

	public MergedModel(IBakedModel... models) {
		for (IBakedModel model : models)
			this.models.add(model);
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
		return false;
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
	public List<BakedQuad> getFaceQuads(EnumFacing side) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<BakedQuad> getGeneralQuads() {
		throw new UnsupportedOperationException();
	}

	@Override
	public VertexFormat getFormat() {
		return Attributes.DEFAULT_BAKED_FORMAT;
	}

	@Override
	public IBakedModel handleBlockState(IBlockState iBlockState) {
		if (iBlockState instanceof IExtendedBlockState) {
			// IExtendedBlockState iExtendedBlockState = (IExtendedBlockState)
			// iBlockState;
			return new AssembledBakedModel();
		}
		return new AssembledBakedModel();
	}

	public class AssembledBakedModel implements IBakedModel {

		public AssembledBakedModel() {
		}

		@Override
		public List<BakedQuad> getFaceQuads(EnumFacing side) {
			List<BakedQuad> allFaceQuads = new LinkedList<BakedQuad>();
			for (int i = 0; i < models.size(); i++)
				allFaceQuads.addAll(models.get(i).getFaceQuads(side));

			return allFaceQuads;
		}

		// general quads that can face in any direction
		@Override
		public List<BakedQuad> getGeneralQuads() {
			List<BakedQuad> allGeneralQuads = new LinkedList<BakedQuad>();

			for (int i = 0; i < models.size(); i++)
				allGeneralQuads.addAll(models.get(i).getGeneralQuads());

			return allGeneralQuads;
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
			return false;
		}

		@Override
		public TextureAtlasSprite getParticleTexture() {
			return models.get(0).getParticleTexture();
		}

		@Override
		public ItemCameraTransforms getItemCameraTransforms() {
			return models.get(0).getItemCameraTransforms();
		}

	}
}
