package com.grim3212.mc.pack.tools.client.entity;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.tools.entity.EntityDetonator;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderDetonator extends Render<EntityDetonator> {

	public static final ResourceLocation[] SMALL = new ResourceLocation[] { new ResourceLocation(GrimPack.modID, "textures/entities/detonators/thermal_detonator_small.png"), new ResourceLocation(GrimPack.modID, "textures/entities/detonators/solar_detonator_small.png"), new ResourceLocation(GrimPack.modID, "textures/entities/detonators/nukeulator_small.png") };
	public static final ResourceLocation[] MEDIUM = new ResourceLocation[] { new ResourceLocation(GrimPack.modID, "textures/entities/detonators/thermal_detonator_med.png"), new ResourceLocation(GrimPack.modID, "textures/entities/detonators/solar_detonator_med.png"), new ResourceLocation(GrimPack.modID, "textures/entities/detonators/nukeulator_med.png") };;
	public static final ResourceLocation[] LARGE = new ResourceLocation[] { new ResourceLocation(GrimPack.modID, "textures/entities/detonators/thermal_detonator_large.png"), new ResourceLocation(GrimPack.modID, "textures/entities/detonators/solar_detonator_large.png"), new ResourceLocation(GrimPack.modID, "textures/entities/detonators/nukeulator_large.png") };;

	protected RenderDetonator(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.15F;
		this.shadowOpaque = 0.75F;
	}

	@Override
	public void doRender(EntityDetonator entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		this.bindEntityTexture(entity);

		Tessellator tess = Tessellator.getInstance();
		float f3 = 0.0F;
		float f4 = 1.0F;
		float f5 = 0.0F;
		float f6 = 1.0F;
		float f7 = entity.width;
		float f8 = entity.width / 2.0F;
		float f9 = entity.height;
		float f10 = entity.height / 2.0F;
		float f11 = f10;
		GlStateManager.rotate(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		float scale = 0.3F;

		GlStateManager.scale(scale, scale, scale);
		tess.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
		tess.getBuffer().pos(0.0F - f8, (0.0F - f10) + f11, 0.0D).tex(f3, f6).color(1.0F, 1.0F, 1.0F, 1.0F).normal(0.0F, 1.0F, 0.0F).endVertex();
		tess.getBuffer().pos(f7 - f8, (0.0F - f10) + f11, 0.0D).tex(f4, f6).color(1.0F, 1.0F, 1.0F, 1.0F).normal(0.0F, 1.0F, 0.0F).endVertex();
		tess.getBuffer().pos(f7 - f8, (f9 - f10) + f11, 0.0D).tex(f4, f5).color(1.0F, 1.0F, 1.0F, 1.0F).normal(0.0F, 1.0F, 0.0F).endVertex();
		tess.getBuffer().pos(0.0F - f8, (f9 - f10) + f11, 0.0D).tex(f3, f5).color(1.0F, 1.0F, 1.0F, 1.0F).normal(0.0F, 1.0F, 0.0F).endVertex();
		tess.draw();

		GlStateManager.disableBlend();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDetonator entity) {
		float fuseDelay = entity.getType().getFuseDelay();
		float curTime = (float) entity.getFuseTimer() / fuseDelay;

		if (curTime >= 0.75f) {
			return LARGE[entity.getType().ordinal()];
		} else if (curTime >= 0.5f) {
			return MEDIUM[entity.getType().ordinal()];
		} else {
			return SMALL[entity.getType().ordinal()];
		}
	}

	public static class RenderDetonatorFactory implements IRenderFactory<EntityDetonator> {

		@Override
		public Render<? super EntityDetonator> createRenderFor(RenderManager manager) {
			return new RenderDetonator(manager);
		}
	}
}
