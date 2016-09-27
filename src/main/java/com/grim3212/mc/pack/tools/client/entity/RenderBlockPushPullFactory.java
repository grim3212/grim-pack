package com.grim3212.mc.pack.tools.client.entity;

import com.grim3212.mc.pack.tools.entity.EntityBlockPushPull;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBlockPushPullFactory implements IRenderFactory<EntityBlockPushPull> {

	@Override
	public Render<? super EntityBlockPushPull> createRenderFor(RenderManager manager) {

		return new RenderBlockPushPull(manager);
	}

	private class RenderBlockPushPull extends RenderFallingBlock {

		public RenderBlockPushPull(RenderManager renderManagerIn) {
			super(renderManagerIn);
			shadowSize = 0f;
		}

	}
}
