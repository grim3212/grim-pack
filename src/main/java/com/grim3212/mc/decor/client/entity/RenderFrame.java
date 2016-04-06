package com.grim3212.mc.decor.client.entity;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.decor.GrimDecor;
import com.grim3212.mc.decor.entity.EntityFrame;
import com.grim3212.mc.decor.util.EnumFrame;
import com.grim3212.mc.decor.util.EnumFrameRender;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFrame extends Render<EntityFrame> {

	private static final ResourceLocation framesTexture = new ResourceLocation(GrimDecor.modID, "textures/entities/framesTexture.png");

	public RenderFrame(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityFrame frame, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(entityYaw, 0f, 1f, 0f);
		GlStateManager.enableRescaleNormal();

		this.bindEntityTexture(frame);
		renderBeams(frame, frame.getCurrentFrame());

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	private void renderBeams(EntityFrame entity, EnumFrame frame) {
		GlStateManager.scale(frame.sizeX / 256.0F + 0.001F, frame.sizeY / 256.0F + 0.001F, 0.0625F);
		float xPos = -8.0F;
		float yPos = -8.0F;

		WorldRenderer renderer = Tessellator.getInstance().getWorldRenderer();
		Tessellator tess = Tessellator.getInstance();
		int[] planks = frame.planks;
		EnumFrameRender[] renderFrames = EnumFrameRender.values();
		BlockPos pos = new BlockPos(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ));
		int light = this.renderManager.worldObj.getCombinedLight(pos, 0);
		int j = light >> 16 & 65535;
		int k = light & 65535;

		for (int i = 0; i < planks.length; i++) {
			int currentPlank = planks[i];
			float zFront = renderFrames[currentPlank].zFront;
			float zBack = renderFrames[currentPlank].zBack;
			float u1 = 0.5F * entity.material;
			float u2 = 0.5F * (entity.material + renderFrames[currentPlank].texSize);
			float u3 = 0.5F * (entity.material + 0.5F);

			if (!frame.isCollidable) {
				u3 = 0.5F * (entity.material + 1.0F);
			}

			float sizeX = frame.sizeX / 16.0F;
			float sizeY = frame.sizeY / 16.0F;
			float f1 = (float) Math.sqrt(Math.pow((renderFrames[currentPlank].x2 - renderFrames[currentPlank].x1) * sizeX, 2.0D) + Math.pow((renderFrames[currentPlank].y2 - renderFrames[currentPlank].y1) * sizeY, 2.0D));
			float f2 = (float) Math.sqrt(Math.pow((renderFrames[currentPlank].x4 - renderFrames[currentPlank].x3) * sizeX, 2.0D) + Math.pow((renderFrames[currentPlank].y4 - renderFrames[currentPlank].y3) * sizeY, 2.0D));
			float f3 = (f2 - f1) / (f2 * 2.0F);
			float v1 = f2 / 32.0F;
			float v2 = f3 * v1;
			float v3 = (1.0F - f3) * v1;

			renderer.begin(GL11.GL_QUADS, RenderHelper.POSITION_TEX_COLOR_LIGHTMAP_NORMAL);
			renderer.pos(xPos + renderFrames[currentPlank].x1, yPos + renderFrames[currentPlank].y1, zFront).tex(u1, v2).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x2, yPos + renderFrames[currentPlank].y2, zFront).tex(u1, v3).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x3, yPos + renderFrames[currentPlank].y3, zFront).tex(u2, v1).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x4, yPos + renderFrames[currentPlank].y4, zFront).tex(u2, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();

			renderer.pos(xPos + renderFrames[currentPlank].x4, yPos + renderFrames[currentPlank].y4, zBack).tex(u2, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, 1.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x3, yPos + renderFrames[currentPlank].y3, zBack).tex(u2, v1).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, 1.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x2, yPos + renderFrames[currentPlank].y2, zBack).tex(u1, v3).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, 1.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x1, yPos + renderFrames[currentPlank].y1, zBack).tex(u1, v2).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, 0.0F, 1.0F).endVertex();

			renderer.pos(xPos + renderFrames[currentPlank].x2, yPos + renderFrames[currentPlank].y2, zFront).tex(u3, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x1, yPos + renderFrames[currentPlank].y1, zFront).tex(u3, v1).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x1, yPos + renderFrames[currentPlank].y1, zBack).tex(u1, v1).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x2, yPos + renderFrames[currentPlank].y2, zBack).tex(u1, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();

			renderer.pos(xPos + renderFrames[currentPlank].x3, yPos + renderFrames[currentPlank].y3, zFront).tex(u3, v1 / 3.0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x2, yPos + renderFrames[currentPlank].y2, zFront).tex(u3, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x2, yPos + renderFrames[currentPlank].y2, zBack).tex(u1, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x3, yPos + renderFrames[currentPlank].y3, zBack).tex(u1, v1 / 3.0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();

			renderer.pos(xPos + renderFrames[currentPlank].x4, yPos + renderFrames[currentPlank].y4, zFront).tex(u3, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x3, yPos + renderFrames[currentPlank].y3, zFront).tex(u3, v1).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x3, yPos + renderFrames[currentPlank].y3, zBack).tex(u1, v1).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x4, yPos + renderFrames[currentPlank].y4, zBack).tex(u1, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();

			renderer.pos(xPos + renderFrames[currentPlank].x1, yPos + renderFrames[currentPlank].y1, zFront).tex(u3, v1 / 3.0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x4, yPos + renderFrames[currentPlank].y4, zFront).tex(u3, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x4, yPos + renderFrames[currentPlank].y4, zBack).tex(u1, 0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();
			renderer.pos(xPos + renderFrames[currentPlank].x1, yPos + renderFrames[currentPlank].y1, zBack).tex(u1, v1 / 3.0F).color(entity.getFrameColor()[0] / 256.0F, entity.getFrameColor()[1] / 256.0F, entity.getFrameColor()[2] / 256.0F, 1.0f).lightmap(j, k).normal(0.0F, -1.0F, 0.0F).endVertex();

			tess.draw();
		}
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