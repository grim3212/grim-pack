package com.grim3212.mc.pack.decor.client.entity;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.decor.entity.EntityFrame;
import com.grim3212.mc.pack.decor.item.ItemFrame.EnumFrameType;
import com.grim3212.mc.pack.decor.util.EnumFrame;
import com.grim3212.mc.pack.decor.util.EnumFrameRender;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderFrame extends EntityRenderer<EntityFrame> {

	private static final ResourceLocation framesTexture = new ResourceLocation(GrimPack.modID, "textures/entity/decor/frames_texture.png");

	public RenderFrame(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityFrame frame, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translated(x, y, z);
		GlStateManager.rotatef(entityYaw, 0f, 1f, 0f);
		GlStateManager.enableRescaleNormal();

		this.bindEntityTexture(frame);
		renderBeams(frame, frame.getCurrentFrame());

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	private void renderBeams(EntityFrame entity, EnumFrame frame) {
		GlStateManager.scalef(frame.sizeX / 256.0F + 0.001F, frame.sizeY / 256.0F + 0.001F, 0.0625F);
		float xPos = -8.0F;
		float yPos = -8.0F;

		Tessellator tess = Tessellator.getInstance();
		int[] planks = frame.planks;
		EnumFrameRender[] renderFrames = EnumFrameRender.values();
		int light = this.renderManager.world.getCombinedLight(entity.getHangingPosition(), 0);
		int j = light >> 16 & 65535;
		int k = light & 65535;

		for (int i = 0; i < planks.length; i++) {
			int currentPlank = planks[i];
			float zFront = renderFrames[currentPlank].zFront;
			float zBack = renderFrames[currentPlank].zBack;

			int mod = entity.material == EnumFrameType.WOOD ? 0 : 1;

			float u1 = 0.5F * mod;
			float u2 = 0.5F * (mod + renderFrames[currentPlank].texSize);
			float u3 = 0.5F * (mod + 0.5F);

			if (!frame.isCollidable) {
				u3 = 0.5F * (mod + 1.0F);
			}

			float sizeX = frame.sizeX / 16.0F;
			float sizeY = frame.sizeY / 16.0F;
			float f1 = (float) Math.sqrt(Math.pow((renderFrames[currentPlank].x2 - renderFrames[currentPlank].x1) * sizeX, 2.0D) + Math.pow((renderFrames[currentPlank].y2 - renderFrames[currentPlank].y1) * sizeY, 2.0D));
			float f2 = (float) Math.sqrt(Math.pow((renderFrames[currentPlank].x4 - renderFrames[currentPlank].x3) * sizeX, 2.0D) + Math.pow((renderFrames[currentPlank].y4 - renderFrames[currentPlank].y3) * sizeY, 2.0D));
			float f3 = (f2 - f1) / (f2 * 2.0F);
			float v1 = f2 / 32.0F;
			float v2 = f3 * v1;
			float v3 = (1.0F - f3) * v1;

			tess.getBuffer().begin(GL11.GL_QUADS, RenderHelper.POSITION_TEX_COLOR_LIGHTMAP_NORMAL);
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x1, yPos + renderFrames[currentPlank].y1, zFront).tex(u1, v2).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x2, yPos + renderFrames[currentPlank].y2, zFront).tex(u1, v3).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x3, yPos + renderFrames[currentPlank].y3, zFront).tex(u2, v1).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x4, yPos + renderFrames[currentPlank].y4, zFront).tex(u2, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();

			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x4, yPos + renderFrames[currentPlank].y4, zBack).tex(u2, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, 1.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x3, yPos + renderFrames[currentPlank].y3, zBack).tex(u2, v1).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, 1.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x2, yPos + renderFrames[currentPlank].y2, zBack).tex(u1, v3).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, 1.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x1, yPos + renderFrames[currentPlank].y1, zBack).tex(u1, v2).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, 1.0F).endVertex();

			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x2, yPos + renderFrames[currentPlank].y2, zFront).tex(u3, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x1, yPos + renderFrames[currentPlank].y1, zFront).tex(u3, v1).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x1, yPos + renderFrames[currentPlank].y1, zBack).tex(u1, v1).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x2, yPos + renderFrames[currentPlank].y2, zBack).tex(u1, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();

			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x3, yPos + renderFrames[currentPlank].y3, zFront).tex(u3, v1 / 3.0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x2, yPos + renderFrames[currentPlank].y2, zFront).tex(u3, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x2, yPos + renderFrames[currentPlank].y2, zBack).tex(u1, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x3, yPos + renderFrames[currentPlank].y3, zBack).tex(u1, v1 / 3.0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();

			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x4, yPos + renderFrames[currentPlank].y4, zFront).tex(u3, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x3, yPos + renderFrames[currentPlank].y3, zFront).tex(u3, v1).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x3, yPos + renderFrames[currentPlank].y3, zBack).tex(u1, v1).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x4, yPos + renderFrames[currentPlank].y4, zBack).tex(u1, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();

			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x1, yPos + renderFrames[currentPlank].y1, zFront).tex(u3, v1 / 3.0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x4, yPos + renderFrames[currentPlank].y4, zFront).tex(u3, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x4, yPos + renderFrames[currentPlank].y4, zBack).tex(u1, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			tess.getBuffer().pos(xPos + renderFrames[currentPlank].x1, yPos + renderFrames[currentPlank].y1, zBack).tex(u1, v1 / 3.0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();

			tess.draw();
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFrame entity) {
		return framesTexture;
	}
}