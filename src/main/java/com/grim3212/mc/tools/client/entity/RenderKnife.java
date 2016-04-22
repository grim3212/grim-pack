package com.grim3212.mc.tools.client.entity;

import com.grim3212.mc.core.client.entity.RenderProjectile;
import com.grim3212.mc.tools.GrimTools;
import com.grim3212.mc.tools.entity.EntityKnife;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderKnife extends RenderProjectile<EntityKnife> {

	private static final ResourceLocation resourceLocation = new ResourceLocation(GrimTools.modID, "textures/entities/throwing_knife.png");

	protected RenderKnife(RenderManager renderManager) {
		super(renderManager, resourceLocation, true);
	}

	public static class KnifeFactory implements IRenderFactory<EntityKnife> {
		@Override
		public Render<? super EntityKnife> createRenderFor(RenderManager manager) {
			return new RenderKnife(manager);
		}
	}
}
