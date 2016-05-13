package com.grim3212.mc.pack.core.client.model;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;

public abstract class TexturedBuilder extends SimpleBakedModel.Builder {

	public TexturedBuilder(ModelBlock modelBlock, ItemOverrideList itemOverrides) {
		super(modelBlock, itemOverrides);
	}

	public TexturedBuilder(IBlockState state, IBakedModel model, TextureAtlasSprite texture, BlockPos blockPos) {
		super(state, model, texture, blockPos);
	}

	@Override
	public abstract IBakedModel makeBakedModel();
}
