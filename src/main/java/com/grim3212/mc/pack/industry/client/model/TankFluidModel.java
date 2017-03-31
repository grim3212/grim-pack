package com.grim3212.mc.pack.industry.client.model;

import java.util.EnumMap;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.fluids.Fluid;

public class TankFluidModel implements IBakedModel {

	/**
	 * Offset to stop z-fighting
	 */
	private static final float OFFSET = 0.00005f;

	/**
	 * The number of buckets that this tank can hold
	 */
	private static final float NUM_BUCKETS = 8f;

	/**
	 * Fluid currently represented
	 */
	private Fluid fluid;
	private VertexFormat format;
	private EnumMap<EnumFacing, List<BakedQuad>> faceQuads;

	/**
	 * Constructor for a TankFluidModel that fills a whole block
	 * 
	 * @param fluid
	 * @param level
	 */
	public TankFluidModel(Fluid fluid, int level) {
		this(fluid, level, new float[] { 0, 0, 1, 1 }, new float[] { 0, 1, 1, 0 });
	}

	/**
	 * Constructor for a TankFluidModel which can have custom bounds using x and
	 * z for vertexes
	 * 
	 * @param fluid
	 * @param level
	 * @param x
	 * @param z
	 */
	public TankFluidModel(Fluid fluid, int level, float x[], float z[]) {
		// ModelFluid.BakedFluid

		this.fluid = fluid;

		format = DefaultVertexFormats.ITEM;
		faceQuads = Maps.newEnumMap(EnumFacing.class);

		for (EnumFacing side : EnumFacing.values()) {
			faceQuads.put(side, ImmutableList.<BakedQuad>of());
		}

		float[] y = new float[4];

		for (int i = 0; i < 4; i++) {
			y[i] = Math.min(level / NUM_BUCKETS, 1f - OFFSET);
		}

		TextureAtlasSprite texture = getParticleTexture();

		// top

		UnpackedBakedQuad.Builder quadBuilder;
		EnumFacing side = EnumFacing.UP;

		quadBuilder = new UnpackedBakedQuad.Builder(format);
		quadBuilder.setQuadOrientation(side);
		quadBuilder.setTexture(texture);
		float X, Z;

		for (int i = 0; i < 4; i++) {
			X = Math.min(Math.max(x[i], OFFSET), 1f - OFFSET);
			Z = Math.min(Math.max(z[i], OFFSET), 1f - OFFSET);

			putVertex(quadBuilder, side, X, y[i] - OFFSET, Z, texture.getInterpolatedU(x[i] * 16), texture.getInterpolatedV(z[i] * 16));
		}

		faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));

		// bottom

		side = EnumFacing.DOWN;
		quadBuilder = new UnpackedBakedQuad.Builder(format);
		quadBuilder.setQuadOrientation(side);
		quadBuilder.setTexture(texture);

		for (int i = 0; i < 4; i++) {
			X = Math.min(Math.max(x[i], OFFSET), 1f - OFFSET);
			Z = Math.min(Math.max(z[i], OFFSET), 1f - OFFSET);

			putVertex(quadBuilder, side, Z, 0 + OFFSET, X, texture.getInterpolatedU(z[i] * 16), texture.getInterpolatedV(x[i] * 16));
		}

		faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));

		/// sides

		// east

		side = EnumFacing.EAST;

		quadBuilder = new UnpackedBakedQuad.Builder(format);
		quadBuilder.setQuadOrientation(side);
		quadBuilder.setTexture(texture);

		putVertex(quadBuilder, side, x[2] - OFFSET, y[2], z[2], texture.getInterpolatedU(0), texture.getInterpolatedV(16 - level));
		putVertex(quadBuilder, side, x[2] - OFFSET, 0 + OFFSET, z[2], texture.getInterpolatedU(0), texture.getInterpolatedV(16));
		putVertex(quadBuilder, side, x[3] - OFFSET, 0 + OFFSET, z[3], texture.getInterpolatedU(16), texture.getInterpolatedV(16));
		putVertex(quadBuilder, side, x[3] - OFFSET, y[3], z[3], texture.getInterpolatedU(16), texture.getInterpolatedV(16 - level));

		faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));

		// west

		side = EnumFacing.WEST;

		quadBuilder = new UnpackedBakedQuad.Builder(format);
		quadBuilder.setQuadOrientation(side);
		quadBuilder.setTexture(texture);

		putVertex(quadBuilder, side, x[0] + OFFSET, y[0], z[0], texture.getInterpolatedU(0), texture.getInterpolatedV(16 - level));
		putVertex(quadBuilder, side, x[0] + OFFSET, 0 + OFFSET, z[0], texture.getInterpolatedU(0), texture.getInterpolatedV(16));
		putVertex(quadBuilder, side, x[1] + OFFSET, 0 + OFFSET, z[1], texture.getInterpolatedU(16), texture.getInterpolatedV(16));
		putVertex(quadBuilder, side, x[1] + OFFSET, y[1], z[1], texture.getInterpolatedU(16), texture.getInterpolatedV(16 - level));

		faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));

		// south

		side = EnumFacing.SOUTH;

		quadBuilder = new UnpackedBakedQuad.Builder(format);
		quadBuilder.setQuadOrientation(side);
		quadBuilder.setTexture(texture);

		putVertex(quadBuilder, side, x[1], y[1], z[1] - OFFSET, texture.getInterpolatedU(0), texture.getInterpolatedV(16 - level));
		putVertex(quadBuilder, side, x[1], 0 + OFFSET, z[1] - OFFSET, texture.getInterpolatedU(0), texture.getInterpolatedV(16));
		putVertex(quadBuilder, side, x[2], 0 + OFFSET, z[2] - OFFSET, texture.getInterpolatedU(16), texture.getInterpolatedV(16));
		putVertex(quadBuilder, side, x[2], y[2], z[2] - OFFSET, texture.getInterpolatedU(16), texture.getInterpolatedV(16 - level));

		faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));

		// north

		side = EnumFacing.NORTH;

		quadBuilder = new UnpackedBakedQuad.Builder(format);
		quadBuilder.setQuadOrientation(side);
		quadBuilder.setTexture(texture);

		putVertex(quadBuilder, side, x[3], y[3], z[3] + OFFSET, texture.getInterpolatedU(0), texture.getInterpolatedV(16 - level));
		putVertex(quadBuilder, side, x[3], 0 + OFFSET, z[3] + OFFSET, texture.getInterpolatedU(0), texture.getInterpolatedV(16));
		putVertex(quadBuilder, side, x[0], 0 + OFFSET, z[0] + OFFSET, texture.getInterpolatedU(16), texture.getInterpolatedV(16));
		putVertex(quadBuilder, side, x[0], y[0], z[0] + OFFSET, texture.getInterpolatedU(16), texture.getInterpolatedV(16 - level));

		faceQuads.put(side, ImmutableList.<BakedQuad>of(quadBuilder.build()));
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if (side != null)
			return faceQuads.get(side);

		return faceQuads.get(EnumFacing.UP);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getStill().toString());
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}

	private void putVertex(UnpackedBakedQuad.Builder builder, EnumFacing side, float x, float y, float z, float u, float v) {
		for (int e = 0; e < format.getElementCount(); e++) {
			switch (format.getElement(e).getUsage()) {
			case POSITION:
				float[] data = new float[] { x, y, z };
				builder.put(e, data);
				break;

			case COLOR:
				builder.put(e, ((fluid.getColor() >> 16) & 0xFF) / 255f, ((fluid.getColor() >> 8) & 0xFF) / 255f, (fluid.getColor() & 0xFF) / 255f, ((fluid.getColor() >> 24) & 0xFF) / 255f);
				break;

			case UV:
				if (format.getElement(e).getIndex() == 0) {
					builder.put(e, u, v);
					break;
				}

			case NORMAL:
				builder.put(e, (float) side.getFrontOffsetX(), (float) side.getFrontOffsetY(), (float) side.getFrontOffsetZ(), 0f);
				break;

			default:
				builder.put(e);
				break;
			}
		}
	}
}
