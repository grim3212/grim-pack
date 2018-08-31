
package com.grim3212.mc.pack.industry.client.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.google.common.collect.Maps;
import com.grim3212.mc.pack.industry.block.storage.BlockTank;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.client.resource.VanillaResourceType;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TankBaseModel implements IBakedModel, ISelectiveResourceReloadListener {

	private IBakedModel tankModel;
	private static final float X[] = new float[] { 0.15f, 0.15f, 0.85f, 0.85f };
	private static final float Z[] = new float[] { 0.15f, 0.85f, 0.85f, 0.15f };

	public TankBaseModel(IBakedModel baseModel) {
		this.tankModel = baseModel;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		List<BakedQuad> quads = new LinkedList<BakedQuad>();

		if (MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT_MIPPED) {
			// Tank
			quads.addAll(tankModel.getQuads(state, side, rand));
		} else if (MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.TRANSLUCENT) {
			// Fluid
			if (state instanceof IExtendedBlockState) {
				IExtendedBlockState exState = (IExtendedBlockState) state;
				if (exState.getValue(BlockTank.FLUID_STACK) != null) {
					FluidStack stack = exState.getValue(BlockTank.FLUID_STACK);

					if (stack != null)
						quads.addAll(this.getCachedModel(stack).getQuads(state, side, rand));
				}
			}
		}

		return quads;
	}

	public final Map<FluidStack, IBakedModel> cache = Maps.newHashMap();

	public IBakedModel getCachedModel(FluidStack stack) {
		if (!this.cache.containsKey(stack)) {
			TankFluidModel fluidModel = new TankFluidModel(stack.getFluid(), stack.amount / Fluid.BUCKET_VOLUME, X, Z);
			this.cache.put(stack, fluidModel);
		}

		return this.cache.get(stack);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return tankModel.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return tankModel.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return tankModel.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return tankModel.getParticleTexture();
	}

	@Override
	@SuppressWarnings("deprecation")
	public ItemCameraTransforms getItemCameraTransforms() {
		return tankModel.getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return tankModel.getOverrides();
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
		if(resourcePredicate.test(VanillaResourceType.MODELS)) {
			this.cache.clear();
		}
	}
}