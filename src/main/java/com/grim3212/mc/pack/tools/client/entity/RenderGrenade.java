package com.grim3212.mc.pack.tools.client.entity;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.tools.entity.EntityGrenade;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderGrenade extends Render<EntityGrenade> {

	private static final ResourceLocation resourceLocation = new ResourceLocation(GrimPack.modID, "textures/entities/grenade.png");

	protected RenderGrenade(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityGrenade entity, double x, double y, double z, float entityYaw, float partialTicks) {
		this.bindEntityTexture(entity);
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 100, 0.0f, 1.0f, 0.0f);
		Tessellator tesselator = Tessellator.getInstance();
		BufferBuilder buff = tesselator.getBuffer();

		byte var11 = 0;
		float var12 = 0.0F;
		float var13 = 0.5F;
		float var14 = (float) (0 + var11 * 10) / 32.0F;
		float var15 = (float) (5 + var11 * 10) / 32.0F;
		float var16 = 0.0F;
		float var17 = 0.15625F;
		float var18 = (float) (5 + var11 * 10) / 32.0F;
		float var19 = (float) (10 + var11 * 10) / 32.0F;
		float var20 = 0.05625F;
		GlStateManager.enableRescaleNormal();
		float var21 = (float) entity.projectileShake - partialTicks;

		if (var21 > 0.0F) {
			float var22 = -MathHelper.sin(var21 * 3.0F) * var21;
			GlStateManager.rotate(var22, 0.0F, 0.0F, 1.0F);
		}

		GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(var20, var20, var20);
		GlStateManager.translate(-4.0F, 0.0F, 0.0F);
		GlStateManager.glNormal3f(var20, 0.0F, 0.0F);

		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buff.pos(-7.0D, -2.0D, -2.0D).tex(var16, var18).endVertex();
		buff.pos(-7.0D, -2.0D, 2.0D).tex(var17, var18).endVertex();
		buff.pos(-7.0D, 2.0D, 2.0D).tex(var17, var19).endVertex();
		buff.pos(-7.0D, 2.0D, -2.0D).tex(var16, var19).endVertex();
		tesselator.draw();

		GlStateManager.glNormal3f(-var20, 0.0F, 0.0F);

		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buff.pos(-7.0D, 2.0D, -2.0D).tex(var16, var18).endVertex();
		buff.pos(-7.0D, 2.0D, 2.0D).tex(var17, var18).endVertex();
		buff.pos(-7.0D, -2.0D, 2.0D).tex(var17, var19).endVertex();
		buff.pos(-7.0D, -2.0D, -2.0D).tex(var16, var19).endVertex();
		tesselator.draw();

		for (int var23 = 0; var23 < 4; ++var23) {
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.glNormal3f(0.0F, 0.0F, var20);

			buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buff.pos(-8.0D, -2.0D, 0.0D).tex(var12, var14).endVertex();
			buff.pos(8.0D, -2.0D, 0.0D).tex(var13, var14).endVertex();
			buff.pos(8.0D, 2.0D, 0.0D).tex(var13, var15).endVertex();
			buff.pos(-8.0D, 2.0D, 0.0D).tex(var12, var15).endVertex();
			tesselator.draw();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGrenade entity) {
		return resourceLocation;
	}

	public static class RenderGrenadeFactory implements IRenderFactory<EntityGrenade> {

		@Override
		public Render<? super EntityGrenade> createRenderFor(RenderManager manager) {
			return new RenderGrenade(manager);
		}
	}
}