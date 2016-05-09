package com.grim3212.mc.core.client.entity;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.core.entity.EntityProjectile;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderProjectile<T extends EntityProjectile> extends Render<T> {

	private final ResourceLocation textureLocation;
	private final boolean doFlip;
	private float pitch = 40.0F;

	public RenderProjectile(RenderManager renderManager, ResourceLocation textureLocation, boolean doFlip) {
		super(renderManager);
		this.textureLocation = textureLocation;
		this.doFlip = doFlip;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return textureLocation;
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (doFlip) {
			if (entity.prevRotationYaw != 0.0F || entity.prevRotationPitch != 0.0F) {
				this.bindEntityTexture(entity);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.pushMatrix();
				GlStateManager.translate(x, y, z);
				GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
				if (entity.rotationPitch != entity.prevRotationPitch) {
					GlStateManager.rotate(this.pitch, 0.0F, 0.0F, 1.0F);
					this.pitch -= 20.0F;
				} else {
					GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
				}
				renderAll(entity, x, y, z, entityYaw, partialTicks);
			}
		} else {
			this.bindEntityTexture(entity);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) x, (float) y, (float) z);
			GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
			renderAll(entity, x, y, z, entityYaw, partialTicks);
		}
	}

	private void renderAll(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		Tessellator tessellator = Tessellator.getInstance();
		int i = 0;
		float f = 0.0F;
		float f1 = 0.5F;
		float f2 = (float) (0 + i * 10) / 32.0F;
		float f3 = (float) (5 + i * 10) / 32.0F;
		float f4 = 0.0F;
		float f5 = 0.15625F;
		float f6 = (float) (5 + i * 10) / 32.0F;
		float f7 = (float) (10 + i * 10) / 32.0F;
		float f8 = 0.05625F;
		GlStateManager.enableRescaleNormal();
		float f9 = (float) entity.arrowShake - partialTicks;

		if (f9 > 0.0F) {
			float f10 = -MathHelper.sin(f9 * 3.0F) * f9;
			GlStateManager.rotate(f10, 0.0F, 0.0F, 1.0F);
		}

		GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(f8, f8, f8);
		GlStateManager.translate(-4.0F, 0.0F, 0.0F);
		GL11.glNormal3f(f8, 0.0F, 0.0F);
		tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
		tessellator.getBuffer().pos(-7.0D, -2.0D, -2.0D).tex((double) f4, (double) f6).endVertex();
		tessellator.getBuffer().pos(-7.0D, -2.0D, 2.0D).tex((double) f5, (double) f6).endVertex();
		tessellator.getBuffer().pos(-7.0D, 2.0D, 2.0D).tex((double) f5, (double) f7).endVertex();
		tessellator.getBuffer().pos(-7.0D, 2.0D, -2.0D).tex((double) f4, (double) f7).endVertex();
		tessellator.draw();
		GL11.glNormal3f(-f8, 0.0F, 0.0F);
		tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
		tessellator.getBuffer().pos(-7.0D, 2.0D, -2.0D).tex((double) f4, (double) f6).endVertex();
		tessellator.getBuffer().pos(-7.0D, 2.0D, 2.0D).tex((double) f5, (double) f6).endVertex();
		tessellator.getBuffer().pos(-7.0D, -2.0D, 2.0D).tex((double) f5, (double) f7).endVertex();
		tessellator.getBuffer().pos(-7.0D, -2.0D, -2.0D).tex((double) f4, (double) f7).endVertex();
		tessellator.draw();

		for (int j = 0; j < 4; ++j) {
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, f8);
			tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
			tessellator.getBuffer().pos(-8.0D, -2.0D, 0.0D).tex((double) f, (double) f2).endVertex();
			tessellator.getBuffer().pos(8.0D, -2.0D, 0.0D).tex((double) f1, (double) f2).endVertex();
			tessellator.getBuffer().pos(8.0D, 2.0D, 0.0D).tex((double) f1, (double) f3).endVertex();
			tessellator.getBuffer().pos(-8.0D, 2.0D, 0.0D).tex((double) f, (double) f3).endVertex();
			tessellator.draw();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	public static class RenderProjectileFactory implements IRenderFactory<EntityProjectile> {

		private ResourceLocation resource;
		private boolean useFlip;

		public RenderProjectileFactory(ResourceLocation resource) {
			this(resource, false);
		}

		public RenderProjectileFactory(ResourceLocation resource, boolean useFlip) {
			this.resource = resource;
			this.useFlip = useFlip;
		}

		@Override
		public Render<? super EntityProjectile> createRenderFor(RenderManager manager) {
			return new RenderProjectile<EntityProjectile>(manager, resource, useFlip);
		}

	}
}
