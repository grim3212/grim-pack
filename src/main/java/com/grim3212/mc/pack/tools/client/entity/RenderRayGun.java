package com.grim3212.mc.pack.tools.client.entity;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.tools.entity.EntityRayw;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderRayGun extends Render<EntityRayw> {

	private ResourceLocation resourceLocation;

	public RenderRayGun(RenderManager manager, ResourceLocation tex) {
		super(manager);
		resourceLocation = tex;
	}

	@Override
	public void doRender(EntityRayw entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(770, 771);
		GlStateManager.scale(1.0F, 1.0F, 1.0F);
		this.bindEntityTexture(entity);
		Tessellator tesselator = Tessellator.getInstance();
		float minU = 0.0F;
		float maxU = 1.0F;
		float minV = 0.0F;
		float maxV = 1.0F;
		GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tesselator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		tesselator.getBuffer().pos((double) (0.0F - 0.5F), (double) (0.0F - 0.25F), 0.0D).tex((double) minU, (double) maxV).normal(1.0f, 1.0f, 1.0f).endVertex();
		tesselator.getBuffer().pos((double) (1.0F - 0.5F), (double) (0.0F - 0.25F), 0.0D).tex((double) maxU, (double) maxV).normal(1.0f, 1.0f, 1.0f).endVertex();
		tesselator.getBuffer().pos((double) (1.0F - 0.5F), (double) (1.0F - 0.25F), 0.0D).tex((double) maxU, (double) minV).normal(1.0f, 1.0f, 1.0f).endVertex();
		tesselator.getBuffer().pos((double) (0.0F - 0.5F), (double) (1.0F - 0.25F), 0.0D).tex((double) minU, (double) minV).normal(1.0f, 1.0f, 1.0f).endVertex();
		tesselator.draw();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRayw entity) {
		return resourceLocation;
	}

	public static class RenderRayGunFactory implements IRenderFactory<EntityRayw> {

		private ResourceLocation location;

		public RenderRayGunFactory(ResourceLocation location) {
			this.location = location;
		}

		@Override
		public Render<? super EntityRayw> createRenderFor(RenderManager manager) {
			return new RenderRayGun(manager, this.location);
		}

	}
}
