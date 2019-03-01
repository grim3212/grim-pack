package com.grim3212.mc.pack.world.client.entity;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.world.client.entity.model.ModelParaBuzzy;
import com.grim3212.mc.pack.world.entity.EntityParaBuzzy;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@OnlyIn(Dist.CLIENT)
public class RenderParaBuzzy extends RenderLiving<EntityParaBuzzy> {

	public static final ResourceLocation BLUE = new ResourceLocation(GrimPack.modID, "textures/entities/parabuzzy.png");
	public static final ResourceLocation BLUE_ANGRY = new ResourceLocation(GrimPack.modID, "textures/entities/parabuzzy_mad.png");

	public static final ResourceLocation RED = new ResourceLocation(GrimPack.modID, "textures/entities/redparabuzzy.png");
	public static final ResourceLocation RED_ANGRY = new ResourceLocation(GrimPack.modID, "textures/entities/redparabuzzy_mad.png");

	public static final ResourceLocation BLUE_SPIKED = new ResourceLocation(GrimPack.modID, "textures/entities/spikeparabuzzy.png");
	public static final ResourceLocation BLUE_SPIKED_ANGRY = new ResourceLocation(GrimPack.modID, "textures/entities/spikeparabuzzy_mad.png");

	public static final ResourceLocation RED_SPIKED = new ResourceLocation(GrimPack.modID, "textures/entities/redspikeparabuzzy.png");
	public static final ResourceLocation RED_SPIKED_ANGRY = new ResourceLocation(GrimPack.modID, "textures/entities/redspikeparabuzzy_mad.png");

	public RenderParaBuzzy(RenderManager renderManager, ModelBase modelbase, float shadowSize) {
		super(renderManager, modelbase, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityParaBuzzy entity) {
		if (entity.isAngry()) {
			switch (entity.getType()) {
			case BLUE:
				return BLUE_ANGRY;
			case BLUE_SPIKED:
				return BLUE_SPIKED_ANGRY;
			case RED:
				return RED_ANGRY;
			case RED_SPIKED:
				return RED_SPIKED_ANGRY;
			}
		}

		switch (entity.getType()) {
		case BLUE_SPIKED:
			return BLUE_SPIKED;
		case RED:
			return RED;
		case RED_SPIKED:
			return RED_SPIKED;
		default:
			return BLUE;
		}
	}

	public static class RenderParaBuzzyFactory implements IRenderFactory<EntityParaBuzzy> {

		@Override
		public Render<? super EntityParaBuzzy> createRenderFor(RenderManager manager) {
			return new RenderParaBuzzy(manager, new ModelParaBuzzy(), 0.4F);
		}

	}
}
