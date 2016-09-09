package com.grim3212.mc.pack.tools.client.entity;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.tools.entity.EntityBoomerang;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBoomerang extends Render<EntityBoomerang> {

	private final ResourceLocation resourceLocation;
	private final String itemLocation;

	protected RenderBoomerang(RenderManager renderManager, ResourceLocation entityLocation, String itemLocation) {
		super(renderManager);
		this.resourceLocation = entityLocation;
		this.itemLocation = itemLocation;
		this.shadowSize = 0.15F;
		this.shadowOpaque = 0.75F;
	}

	@Override
	public void doRender(EntityBoomerang entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);

		float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(-entityYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(pitch, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(entity.getBoomerangRotation(), 0.0F, 1.0F, 0.0F);
		this.bindEntityTexture(entity);

		Tessellator tessellator = Tessellator.getInstance();
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(itemLocation);
		int i = 0;
		float f4 = ((float) ((i % 16) * 16) + 0.0F) / sprite.getIconHeight();
		float f5 = ((float) ((i % 16) * 16) + 15.99F) / sprite.getIconHeight();
		float f6 = ((float) ((i / 16) * 16) + 0.0F) / sprite.getIconHeight();
		float f7 = ((float) ((i / 16) * 16) + 15.99F) / sprite.getIconHeight();
		float f8 = 1.0F;
		GlStateManager.enableRescaleNormal();
		float scale = 0.0625F;
		GlStateManager.translate(-0.5F, 0.0F, -0.5F);

		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		tessellator.getBuffer().pos(0.0D, 0.0D, 0.0D).tex(f5, f7).normal(0.0F, 0.0F, 1.0F).endVertex();
		tessellator.getBuffer().pos(f8, 0.0D, 0.0D).tex(f4, f7).normal(0.0F, 0.0F, 1.0F).endVertex();
		tessellator.getBuffer().pos(f8, 0.0D, 1.0D).tex(f4, f6).normal(0.0F, 0.0F, 1.0F).endVertex();
		tessellator.getBuffer().pos(0.0D, 0.0D, 1.0D).tex(f5, f6).normal(0.0F, 0.0F, 1.0F).endVertex();
		tessellator.draw();

		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		tessellator.getBuffer().pos(0.0D, 0.0F - scale, 1.0D).tex(f5, f6).normal(0.0F, 0.0F, -1.0F).endVertex();
		tessellator.getBuffer().pos(f8, 0.0F - scale, 1.0D).tex(f4, f6).normal(0.0F, 0.0F, -1.0F).endVertex();
		tessellator.getBuffer().pos(f8, 0.0F - scale, 0.0D).tex(f4, f7).normal(0.0F, 0.0F, -1.0F).endVertex();
		tessellator.getBuffer().pos(0.0D, 0.0F - scale, 0.0D).tex(f5, f7).normal(0.0F, 0.0F, -1.0F).endVertex();
		tessellator.draw();

		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		for (int j = 0; j < 16; j++) {
			float f10 = (float) j / 16F;
			float f14 = (f5 + (f4 - f5) * f10) - 0.001953125F;
			float f18 = f8 * f10;
			tessellator.getBuffer().pos(f18, 0.0F - scale, 0.0D).tex(f14, f7).normal(-1.0F, 0.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f18, 0.0D, 0.0D).tex(f14, f7).normal(-1.0F, 0.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f18, 0.0D, 1.0D).tex(f14, f6).normal(-1.0F, 0.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f18, 0.0F - scale, 1.0D).tex(f14, f6).normal(-1.0F, 0.0F, 0.0F).endVertex();
		}
		tessellator.draw();

		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		for (int k = 0; k < 16; k++) {
			float f11 = (float) k / 16F;
			float f15 = (f5 + (f4 - f5) * f11) - 0.001953125F;
			float f19 = f8 * f11 + 0.0625F;
			tessellator.getBuffer().pos(f19, 0.0F - scale, 1.0D).tex(f15, f6).normal(1.0F, 0.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f19, 0.0D, 1.0D).tex(f15, f6).normal(1.0F, 0.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f19, 0.0D, 0.0D).tex(f15, f7).normal(1.0F, 0.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f19, 0.0F - scale, 0.0D).tex(f15, f7).normal(1.0F, 0.0F, 0.0F).endVertex();
		}
		tessellator.draw();

		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		for (int l = 0; l < 16; l++) {
			float f12 = (float) l / 16F;
			float f16 = (f7 + (f6 - f7) * f12) - 0.001953125F;
			float f20 = f8 * f12 + 0.0625F;
			tessellator.getBuffer().pos(0.0D, 0.0D, f20).tex(f5, f16).normal(0.0F, 1.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f8, 0.0D, f20).tex(f4, f16).normal(0.0F, 1.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f8, 0.0F - scale, f20).tex(f4, f16).normal(0.0F, 1.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(0.0D, 0.0F - scale, f20).tex(f5, f16).normal(0.0F, 1.0F, 0.0F).endVertex();
		}
		tessellator.draw();

		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		for (int i1 = 0; i1 < 16; i1++) {
			float f13 = (float) i1 / 16F;
			float f17 = (f7 + (f6 - f7) * f13) - 0.001953125F;
			float f21 = f8 * f13;
			tessellator.getBuffer().pos(f8, 0.0D, f21).tex(f4, f17).normal(0.0F, -1.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(0.0D, 0.0D, f21).tex(f5, f17).normal(0.0F, -1.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(0.0D, 0.0F - scale, f21).tex(f5, f17).normal(0.0F, -1.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f8, 0.0F - scale, f21).tex(f4, f17).normal(0.0F, -1.0F, 0.0F).endVertex();
		}
		tessellator.draw();

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBoomerang entity) {
		return this.resourceLocation;
	}

	public static class RenderBoomerangFactory implements IRenderFactory<EntityBoomerang> {

		private final ResourceLocation location;
		private final String itemLocation;

		public RenderBoomerangFactory(ResourceLocation location, String itemLocation) {
			this.location = location;
			this.itemLocation = itemLocation;
		}

		@Override
		public Render<? super EntityBoomerang> createRenderFor(RenderManager manager) {
			return new RenderBoomerang(manager, this.location, this.itemLocation);
		}
	}
}
