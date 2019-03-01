package com.grim3212.mc.pack.world.client.entity;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.world.client.entity.model.ModelBobomb;
import com.grim3212.mc.pack.world.entity.EntityBobomb;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@OnlyIn(Dist.CLIENT)
public class RenderBobomb extends RenderLiving<EntityBobomb> {

	public static final ResourceLocation BOBOMB_NORMAL = new ResourceLocation(GrimPack.modID, "textures/entities/bobomb_normal.png");
	public static final ResourceLocation BOBOMB_RED = new ResourceLocation(GrimPack.modID, "textures/entities/bobomb_red.png");

	public RenderBobomb(RenderManager renderManager, ModelBase modelbase, float shadowSize) {
		super(renderManager, modelbase, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBobomb entity) {
		if (entity.isActivated()) {
			return BOBOMB_RED;
		}

		return BOBOMB_NORMAL;
	}

	public static class RenderBobombFactory implements IRenderFactory<EntityBobomb> {

		@Override
		public Render<? super EntityBobomb> createRenderFor(RenderManager manager) {
			return new RenderBobomb(manager, new ModelBobomb(), 0.3F);
		}

	}
}
