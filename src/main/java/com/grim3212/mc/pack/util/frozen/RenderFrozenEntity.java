package com.grim3212.mc.pack.util.frozen;

import java.lang.reflect.Field;
import java.util.List;

import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.util.GrimUtil;
import com.grim3212.mc.pack.util.frozen.FrozenCapability.IFrozenCapability;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class RenderFrozenEntity<T extends EntityLivingBase> extends RenderLivingBase<T> {

	public RenderFrozenEntity(RenderLivingBase<T> living) {
		super(living.getRenderManager(), living.getMainModel(), 0.625f);

		try {
			// Get the layers using Reflection
			Field layers = ReflectionHelper.findField(RenderLivingBase.class, "layerRenderers", "field_177097_h");
			layers.setAccessible(true);
			Object o = layers.get(living);

			// Then try and add all the layers to this new Renderer
			if (o instanceof List<?>) {
				@SuppressWarnings("unchecked")
				List<LayerRenderer<T>> layerRenderers = (List<LayerRenderer<T>>) o;

				for (LayerRenderer<T> layer : layerRenderers) {
					this.addLayer(layer);
				}
			}

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
		boolean shouldSit = entity.isRiding() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
		this.mainModel.isRiding = shouldSit;
		this.mainModel.isChild = entity.isChild();

		try {
			float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
			float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
			float f2 = f1 - f;

			if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase) {
				EntityLivingBase entitylivingbase = (EntityLivingBase) entity.getRidingEntity();
				f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
				f2 = f1 - f;
				float f3 = MathHelper.wrapDegrees(f2);

				if (f3 < -85.0F) {
					f3 = -85.0F;
				}

				if (f3 >= 85.0F) {
					f3 = 85.0F;
				}

				f = f1 - f3;

				if (f3 * f3 > 2500.0F) {
					f += f3 * 0.2F;
				}

				f2 = f1 - f;
			}

			float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
			this.renderLivingAt(entity, x, y, z);
			float f8 = this.handleRotationFloat(entity, partialTicks);
			this.applyRotations(entity, f8, f, partialTicks);
			float f4 = this.prepareScale(entity, partialTicks);
			float f5 = 0.0F;
			float f6 = 0.0F;

			if (!entity.isRiding()) {
				f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
				f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

				if (entity.isChild()) {
					f6 *= 3.0F;
				}

				if (f5 > 1.0F) {
					f5 = 1.0F;
				}
				f2 = f1 - f; // Forge: Fix MC-1207
			}

			GlStateManager.enableAlpha();
			this.mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
			this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);

			if (this.renderOutlines) {
				boolean flag1 = this.setScoreTeamColor(entity);
				GlStateManager.enableColorMaterial();
				GlStateManager.enableOutlineMode(this.getTeamColor(entity));

				if (!this.renderMarker) {
					this.renderModel(entity, f6, f5, f8, f2, f7, f4);
				}

				if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isSpectator()) {
					this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
				}

				GlStateManager.disableOutlineMode();
				GlStateManager.disableColorMaterial();

				if (flag1) {
					this.unsetScoreTeamColor();
				}
			} else {
				boolean flag = this.setDoRenderBrightness(entity, partialTicks);
				this.renderModel(entity, f6, f5, f8, f2, f7, f4);

				if (flag) {
					this.unsetBrightness();
				}

				GlStateManager.depthMask(true);

				if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isSpectator()) {
					this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
				}
			}

			GlStateManager.disableRescaleNormal();
		} catch (Exception exception) {
			GrimLog.error("Couldn't render frozen entity", exception.getLocalizedMessage());
		}

		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.enableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GlStateManager.enableCull();
		GlStateManager.popMatrix();

		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<T>(entity, this, partialTicks, x, y, z));
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		final IFrozenCapability frozen = entity.getCapability(GrimUtil.FROZEN_CAP, null);
		if (frozen != null) {
			if (frozen.isFrozen()) {
				return FrozenClientEvents.FROZEN;
			}
		}
		return null;
	}

}
