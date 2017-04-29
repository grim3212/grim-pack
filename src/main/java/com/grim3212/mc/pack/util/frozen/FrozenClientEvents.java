package com.grim3212.mc.pack.util.frozen;

import com.grim3212.mc.pack.util.GrimUtil;
import com.grim3212.mc.pack.util.frozen.FrozenCapability.IFrozenCapability;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FrozenClientEvents {

	// Hopefully to be used in the future
	// public static final ResourceLocation FROZEN = new
	// ResourceLocation(GrimPack.modID, "textures/entities/frozen.png");

	@SubscribeEvent
	public void RenderLivingEvent(RenderLivingEvent.Pre<EntityLivingBase> event) {
		final IFrozenCapability frozen = event.getEntity().getCapability(GrimUtil.FROZEN_CAP, null);
		if (frozen != null) {
			if (frozen.isFrozen()) {
				GlStateManager.pushMatrix();
				GlStateManager.color(0.5f, 2.0f, 10.0f);
				GlStateManager.popMatrix();
			}
		}
	}
}
