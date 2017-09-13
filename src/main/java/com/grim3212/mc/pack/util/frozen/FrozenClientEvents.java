package com.grim3212.mc.pack.util.frozen;

import java.util.Map;

import com.google.common.collect.Maps;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.util.GrimUtil;
import com.grim3212.mc.pack.util.frozen.FrozenCapability.IFrozenCapability;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FrozenClientEvents {

	// Hopefully to be used in the future
	public static final ResourceLocation FROZEN = new ResourceLocation(GrimPack.modID, "textures/entities/frozen.png");

	private Map<Integer, RenderFrozenEntity<EntityLivingBase>> cache = Maps.newHashMap();

	@SubscribeEvent
	public void renderLivingEventPre(RenderLivingEvent.Pre<EntityLivingBase> event) {
		EntityLivingBase entity = event.getEntity();
		final IFrozenCapability frozen = entity.getCapability(GrimUtil.FROZEN_CAP, null);
		if (frozen != null) {
			if (frozen.isFrozen()) {

				float partialTicks = event.getPartialRenderTick();

				float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;

				GlStateManager.pushMatrix();

				if (cache.containsKey(entity.getEntityId())) {
					cache.get(entity.getEntityId()).doRender(entity, event.getX(), event.getY(), event.getZ(), yaw, partialTicks);
				} else {
					cache.put(entity.getEntityId(), new RenderFrozenEntity<>(event.getRenderer()));
					cache.get(entity.getEntityId()).doRender(entity, event.getX(), event.getY(), event.getZ(), yaw, partialTicks);
				}

				GlStateManager.popMatrix();
				event.setCanceled(true);
			} else {
				if (cache.containsKey(entity.getEntityId())) {
					cache.remove(entity.getEntityId());
				}
			}
		}
	}

	// @SubscribeEvent
	// public void
	// renderLivingEventSpecials(RenderLivingEvent.Post<EntityLivingBase> event)
	// {
	// EntityLivingBase living = event.getEntity();
	//
	// final IFrozenCapability frozen =
	// event.getEntity().getCapability(GrimUtil.FROZEN_CAP, null);
	// if (frozen != null) {
	// if (frozen.isFrozen()) {
	//
	// GlStateManager.disableLighting();
	// TextureMap texturemap =
	// Minecraft.getMinecraft().getTextureMapBlocks();
	// TextureAtlasSprite textureatlassprite =
	// texturemap.getAtlasSprite("minecraft:blocks/ice");
	// GlStateManager.pushMatrix();
	// GlStateManager.translate((float) event.getX(), (float) event.getY(),
	// (float) event.getZ());
	// float f = living.width * 1.4F;
	// GlStateManager.scale(f, f, f);
	// Tessellator tessellator = Tessellator.getInstance();
	// float f1 = 0.5F;
	// float f2 = 0.0F;
	// float f3 = living.height / f;
	// float f4 = (float) (living.posY -
	// living.getEntityBoundingBox().minY);
	// GlStateManager.rotate(-event.getRenderer().getRenderManager().playerViewY,
	// 0.0F, 1.0F, 0.0F);
	// GlStateManager.translate(0.0F, 0.0F, -0.3F + (int) f3 * 0.02F);
	// GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	// float f5 = 0.0F;
	// int i = 0;
	// tessellator.getBuffer().begin(GL11.GL_QUADS,
	// DefaultVertexFormats.POSITION_TEX);
	//
	// while (f3 > 0.0F) {
	// event.getRenderer().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	// float f6 = textureatlassprite.getMinU();
	// float f7 = textureatlassprite.getMinV();
	// float f8 = textureatlassprite.getMaxU();
	// float f9 = textureatlassprite.getMaxV();
	//
	// if (i / 2 % 2 == 0) {
	// float f10 = f8;
	// f8 = f6;
	// f6 = f10;
	// }
	// tessellator.getBuffer().pos(f1 - f2, 0.0F - f4, f5).tex(f8,
	// f9).endVertex();
	// tessellator.getBuffer().pos(-f1 - f2, 0.0F - f4, f5).tex(f6,
	// f9).endVertex();
	// tessellator.getBuffer().pos(-f1 - f2, 1.4F - f4, f5).tex(f6,
	// f7).endVertex();
	// tessellator.getBuffer().pos(f1 - f2, 1.4F - f4, f5).tex(f8,
	// f7).endVertex();
	// f3 -= 0.45F;
	// f4 -= 0.45F;
	// f1 *= 0.9F;
	// f5 += 0.03F;
	// ++i;
	// }
	// tessellator.draw();
	// GlStateManager.popMatrix();
	// GlStateManager.enableLighting();
	// }
	// }
	// }
}
