package com.grim3212.mc.decor.entity;

import com.grim3212.mc.core.part.IPartEntities;
import com.grim3212.mc.decor.GrimDecor;
import com.grim3212.mc.decor.client.entity.RenderFrame.FrameFactory;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class DecorEntities implements IPartEntities {

	@Override
	public void initEntities() {
		EntityRegistry.registerModEntity(EntityFrame.class, "Frames", 1, GrimDecor.INSTANCE, 250, 2147483647, false);
	}

	@Override
	public void renderEntities() {
		RenderingRegistry.registerEntityRenderingHandler(EntityFrame.class, new FrameFactory());
	}

}
