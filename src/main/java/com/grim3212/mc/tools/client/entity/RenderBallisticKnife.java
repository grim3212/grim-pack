package com.grim3212.mc.tools.client.entity;

import com.grim3212.mc.core.client.entity.RenderProjectile;
import com.grim3212.mc.tools.GrimTools;
import com.grim3212.mc.tools.entity.EntityBallisticKnife;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBallisticKnife extends RenderProjectile<EntityBallisticKnife> {

	private static final ResourceLocation resourceLocation = new ResourceLocation(GrimTools.modID, "textures/entities/ballistic_knife.png");

	protected RenderBallisticKnife(RenderManager renderManager) {
		super(renderManager, resourceLocation);
	}

	public static class BallisticKnifeFactory implements IRenderFactory<EntityBallisticKnife> {
		@Override
		public Render<? super EntityBallisticKnife> createRenderFor(RenderManager manager) {
			return new RenderBallisticKnife(manager);
		}
	}
}
