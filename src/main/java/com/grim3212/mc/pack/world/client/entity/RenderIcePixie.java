package com.grim3212.mc.pack.world.client.entity;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.world.client.entity.model.ModelIcePixie;
import com.grim3212.mc.pack.world.entity.EntityIcePixie;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderIcePixie extends RenderBiped<EntityIcePixie> {

	private static final ResourceLocation resourceLocation = new ResourceLocation(GrimPack.modID, "textures/entities/icepixie.png");

	public RenderIcePixie(RenderManager renderManager, ModelBiped model, float shadowSize) {
		super(renderManager, model, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityIcePixie entity) {
		return resourceLocation;
	}

	public static class RenderIcePixieFactory implements IRenderFactory<EntityIcePixie> {

		@Override
		public Render<? super EntityIcePixie> createRenderFor(RenderManager manager) {
			return new RenderIcePixie(manager, new ModelIcePixie(0.0F, 0.0F), 0.2F);
		}

	}
}
