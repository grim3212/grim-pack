package com.grim3212.mc.pack.world.client.entity;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.world.client.entity.model.ModelTreasureMob;
import com.grim3212.mc.pack.world.entity.EntityTreasureMob;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTreasureMob extends RenderLiving<EntityTreasureMob> {

	private static final ResourceLocation resourceLocation = new ResourceLocation(GrimPack.modID, "textures/entities/treasure_mob.png");

	public RenderTreasureMob(RenderManager manager, ModelBase modelbase, float shadowSize) {
		super(manager, modelbase, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTreasureMob entity) {
		return resourceLocation;
	}

	public static class RenderTreasureMobFactory implements IRenderFactory<EntityTreasureMob> {

		@Override
		public Render<? super EntityTreasureMob> createRenderFor(RenderManager manager) {
			return new RenderTreasureMob(manager, new ModelTreasureMob(), 0.5F);
		}

	}
}
