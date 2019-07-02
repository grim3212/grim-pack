package com.grim3212.mc.pack.industry.client.entity;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.industry.client.model.ModelExtruder;
import com.grim3212.mc.pack.industry.entity.EntityExtruder;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class RenderExtruder extends EntityRenderer<EntityExtruder> {

	private static final ResourceLocation extruderTexture = new ResourceLocation(GrimPack.modID, "textures/entities/extruder.png");
	private static final ModelExtruder extruderModel = new ModelExtruder();

	protected RenderExtruder(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityExtruder entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);

		GlStateManager.pushMatrix();
		GlStateManager.translated(x, y + entity.getHeight() / 2, z);
		GlStateManager.rotatef(180f + entityYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(entity.rotationPitch, 0.0F, 0.0F, 1.0F);

		float damageTaken = entity.getDamageTaken() - partialTicks;
		float timeSinceHit = entity.getTimeSinceHit() - partialTicks;

		if (damageTaken < 0.0F) {
			damageTaken = 0.0F;
		}
		if (timeSinceHit > 0.0F) {
			GlStateManager.rotatef(((MathHelper.sin(timeSinceHit) * timeSinceHit * damageTaken) / 10F) * entity.getForwardDirection(), 1.0F, 0.0F, 0.0F);
		}

		float scale = 0.75F;
		GlStateManager.scalef(scale, scale, scale);
		GlStateManager.scalef(1.0F / scale, 1.0F / scale, 1.0F / scale);
		this.bindEntityTexture(entity);
		GlStateManager.scalef(-1F, -1F, 1.0F);
		extruderModel.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, entity.getExtrusionWave(), 0.0625F);
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityExtruder entity) {
		return extruderTexture;
	}

	public static class ExtruderFactory implements IRenderFactory<EntityExtruder> {
		@Override
		public EntityRenderer<? super EntityExtruder> createRenderFor(EntityRendererManager manager) {
			return new RenderExtruder(manager);
		}
	}
}
