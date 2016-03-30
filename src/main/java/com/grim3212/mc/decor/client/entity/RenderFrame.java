package com.grim3212.mc.decor.client.entity;

import com.grim3212.mc.decor.GrimDecor;
import com.grim3212.mc.decor.entity.EntityFrame;
import com.grim3212.mc.decor.util.EnumFrame;
import com.grim3212.mc.decor.util.EnumFrameRender;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFrame extends Render<EntityFrame> {

	private static final ResourceLocation framesTexture = new ResourceLocation(GrimDecor.modID, "textures/entities/framesTexture.png");

	public RenderFrame(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityFrame entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(entityYaw, 0f, 1f, 0f);
		GlStateManager.enableRescaleNormal();
		this.bindEntityTexture(entity);
		EnumFrame var10 = entity.getCurrentFrame();
		renderBeams(entity, var10);
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	private void renderBeams(EntityFrame entity, EnumFrame frame) {
		GlStateManager.scale(frame.sizeX / 256.0F + 0.001F, frame.sizeY / 256.0F + 0.001F, 0.0625F);
		float var3 = -8.0F;
		float var4 = -8.0F;
		EnumFrameRender[] var5 = EnumFrameRender.values();

		WorldRenderer renderer = Tessellator.getInstance().getWorldRenderer();
		Tessellator tess = Tessellator.getInstance();
		int[] var7 = frame.planks;
		int var8 = var7.length;

		for (int var9 = 0; var9 < var8; var9++) {
			int var10 = var7[var9];
			float var11 = var5[var10].zFront;
			float var12 = var5[var10].zBack;
			float var13 = 0.5F * entity.material;
			float var14 = 0.5F * (entity.material + var5[var10].texSize);
			float var15 = 0.5F * (entity.material + 0.5F);

			if (!frame.isCollidable) {
				var15 = 0.5F * (entity.material + 1.0F);
			}

			float var16 = frame.sizeX / 16.0F;
			float var17 = frame.sizeY / 16.0F;
			float var18 = (float) Math.sqrt(Math.pow((var5[var10].x2 - var5[var10].x1) * var16, 2.0D) + Math.pow((var5[var10].y2 - var5[var10].y1) * var17, 2.0D));
			float var19 = (float) Math.sqrt(Math.pow((var5[var10].x4 - var5[var10].x3) * var16, 2.0D) + Math.pow((var5[var10].y4 - var5[var10].y3) * var17, 2.0D));
			float var20 = (var19 - var18) / (var19 * 2.0F);
			float var21 = var19 / 32.0F;
			float var22 = var20 * var21;
			float var23 = (1.0F - var20) * var21;
			float var25 = 0.0F;

			// TODO: Is this even doing anything?
			this.setLightmap(entity, frame.sizeX, frame.sizeY);

			renderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
			renderer.pos(var3 + var5[var10].x1, var4 + var5[var10].y1, var11).tex(var13, var22).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, 0.0F, -1.0F).endVertex();
			renderer.pos(var3 + var5[var10].x2, var4 + var5[var10].y2, var11).tex(var13, var23).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, 0.0F, -1.0F).endVertex();
			renderer.pos(var3 + var5[var10].x3, var4 + var5[var10].y3, var11).tex(var14, var21).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, 0.0F, -1.0F).endVertex();
			renderer.pos(var3 + var5[var10].x4, var4 + var5[var10].y4, var11).tex(var14, var25).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, 0.0F, -1.0F).endVertex();

			renderer.pos(var3 + var5[var10].x4, var4 + var5[var10].y4, var12).tex(var14, var25).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, 0.0F, 1.0F).endVertex();
			renderer.pos(var3 + var5[var10].x3, var4 + var5[var10].y3, var12).tex(var14, var21).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, 0.0F, 1.0F).endVertex();
			renderer.pos(var3 + var5[var10].x2, var4 + var5[var10].y2, var12).tex(var13, var23).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, 0.0F, 1.0F).endVertex();
			renderer.pos(var3 + var5[var10].x1, var4 + var5[var10].y1, var12).tex(var13, var22).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, 0.0F, 1.0F).endVertex();

			renderer.pos(var3 + var5[var10].x2, var4 + var5[var10].y2, var11).tex(var15, var25).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(var3 + var5[var10].x1, var4 + var5[var10].y1, var11).tex(var15, var21).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(var3 + var5[var10].x1, var4 + var5[var10].y1, var12).tex(var13, var21).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(var3 + var5[var10].x2, var4 + var5[var10].y2, var12).tex(var13, var25).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();

			renderer.pos(var3 + var5[var10].x3, var4 + var5[var10].y3, var11).tex(var15, var21 / 3.0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(var3 + var5[var10].x2, var4 + var5[var10].y2, var11).tex(var15, var25).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(var3 + var5[var10].x2, var4 + var5[var10].y2, var12).tex(var13, var25).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(var3 + var5[var10].x3, var4 + var5[var10].y3, var12).tex(var13, var21 / 3.0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();

			renderer.pos(var3 + var5[var10].x4, var4 + var5[var10].y4, var11).tex(var15, var25).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(var3 + var5[var10].x3, var4 + var5[var10].y3, var11).tex(var15, var21).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(var3 + var5[var10].x3, var4 + var5[var10].y3, var12).tex(var13, var21).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(var3 + var5[var10].x4, var4 + var5[var10].y4, var12).tex(var13, var25).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();

			renderer.pos(var3 + var5[var10].x1, var4 + var5[var10].y1, var11).tex(var15, var21 / 3.0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(var3 + var5[var10].x4, var4 + var5[var10].y4, var11).tex(var15, var25).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(var3 + var5[var10].x4, var4 + var5[var10].y4, var12).tex(var13, var25).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(var3 + var5[var10].x1, var4 + var5[var10].y1, var12).tex(var13, var21 / 3.0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).normal(0.0F, -1.0F, 0.0F).endVertex();

			tess.draw();
		}
	}

	private void setLightmap(EntityFrame frame, float f1, float f2) {
		int i = MathHelper.floor_double(frame.posX);
		int j = MathHelper.floor_double(frame.posY + (double) (f2 / 16.0F));
		int k = MathHelper.floor_double(frame.posZ);
		EnumFacing enumfacing = frame.facingDirection;

		if (enumfacing == EnumFacing.NORTH) {
			i = MathHelper.floor_double(frame.posX + (double) (f1 / 16.0F));
		}

		if (enumfacing == EnumFacing.WEST) {
			k = MathHelper.floor_double(frame.posZ - (double) (f1 / 16.0F));
		}

		if (enumfacing == EnumFacing.SOUTH) {
			i = MathHelper.floor_double(frame.posX - (double) (f1 / 16.0F));
		}

		if (enumfacing == EnumFacing.EAST) {
			k = MathHelper.floor_double(frame.posZ + (double) (f1 / 16.0F));
		}

		int l = this.renderManager.worldObj.getCombinedLight(new BlockPos(i, j, k), 0);
		int i1 = l % 65536;
		int j1 = l / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) i1, (float) j1);
		GlStateManager.color(1.0F, 1.0F, 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFrame entity) {
		return framesTexture;
	}

	public static class FrameFactory implements IRenderFactory<EntityFrame> {
		@Override
		public Render<? super EntityFrame> createRenderFor(RenderManager manager) {
			return new RenderFrame(manager);
		}
	}

}