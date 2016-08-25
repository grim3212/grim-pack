package com.grim3212.mc.pack.industry.client.entity;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.industry.client.model.ModelExtruder;
import com.grim3212.mc.pack.industry.entity.EntityExtruder;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderExtruder extends Render<EntityExtruder> {

	private static final ResourceLocation extruderTexture = new ResourceLocation(GrimPack.modID, "textures/entities/extruder.png");
	private static final ModelExtruder extruderModel = new ModelExtruder();

	protected RenderExtruder(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityExtruder entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.rotate(180f + entityYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(entity.rotationPitch, 0.0F, 0.0F, 1.0F);

		float damageTaken = entity.getDamageTaken() - partialTicks;
		float timeSinceHit = entity.getTimeSinceHit() - partialTicks;

		if (damageTaken < 0.0F) {
			damageTaken = 0.0F;
		}
		if (timeSinceHit > 0.0F) {
			GlStateManager.rotate(((MathHelper.sin(timeSinceHit) * timeSinceHit * damageTaken) / 10F) * entity.getForwardDirection(), 1.0F, 0.0F, 0.0F);
		}

		float scale = 0.75F;
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.scale(1.0F / scale, 1.0F / scale, 1.0F / scale);
		this.bindEntityTexture(entity);
		GlStateManager.scale(-1F, -1F, 1.0F);
		extruderModel.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, entity.extrusionWave, 0.0625F);
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityExtruder entity) {
		return extruderTexture;
	}

	public static class ExtruderFactory implements IRenderFactory<EntityExtruder> {
		@Override
		public Render<? super EntityExtruder> createRenderFor(RenderManager manager) {
			return new RenderExtruder(manager);
		}
	}
}
