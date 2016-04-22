package com.grim3212.mc.tools.client.entity;

import com.grim3212.mc.core.client.entity.RenderProjectile;
import com.grim3212.mc.tools.GrimTools;
import com.grim3212.mc.tools.entity.EntityTomahawk;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTomahawk extends RenderProjectile<EntityTomahawk> {

	private final static ResourceLocation resourceLocation = new ResourceLocation(GrimTools.modID, "textures/entities/tomahawk.png");

	protected RenderTomahawk(RenderManager renderManager) {
		super(renderManager, resourceLocation, true);
	}

	public static class TomahawkFactory implements IRenderFactory<EntityTomahawk> {
		@Override
		public Render<? super EntityTomahawk> createRenderFor(RenderManager manager) {
			return new RenderTomahawk(manager);
		}
	}
}
