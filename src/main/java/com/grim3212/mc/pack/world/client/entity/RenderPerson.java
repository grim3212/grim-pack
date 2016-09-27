package com.grim3212.mc.pack.world.client.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderPerson extends RenderBiped<EntityCreature> {

	private ResourceLocation RESOURCE_LOCATION;

	public RenderPerson(RenderManager renderManager, ModelBiped model, float shadowSize, ResourceLocation loc) {
		this(renderManager, model, shadowSize, 1.0F, loc);
	}

	public RenderPerson(RenderManager renderManager, ModelBiped model, float shadowSize, float scale, ResourceLocation loc) {
		super(renderManager, model, shadowSize);
		this.modelBipedMain = model;
		this.scale = scale;
		this.RESOURCE_LOCATION = loc;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCreature entity) {
		return RESOURCE_LOCATION;
	}

	public static class RenderPersonFactory implements IRenderFactory<EntityCreature> {

		private final ResourceLocation RESOURCE_LOCATION;

		public RenderPersonFactory(ResourceLocation resource) {
			this.RESOURCE_LOCATION = resource;
		}

		@Override
		public Render<? super EntityCreature> createRenderFor(RenderManager manager) {
			return new RenderPerson(manager, new ModelBiped(), 0.5F, this.RESOURCE_LOCATION);
		}

	}
}
