package com.grim3212.mc.pack.tools.client.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderThrowableFactory implements IRenderFactory<EntityThrowable> {

	private Item item;

	public RenderThrowableFactory(Item item) {
		this.item = item;
	}

	@Override
	public Render<? super EntityThrowable> createRenderFor(RenderManager manager) {
		return new RenderSnowball<EntityThrowable>(manager, item, Minecraft.getMinecraft().getRenderItem());
	}

}
