package com.grim3212.mc.world.client.entity;

import com.grim3212.mc.world.GrimWorld;
import com.grim3212.mc.world.entity.EntityIceCube;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderIceCube extends Render<EntityIceCube> {

	private static final ResourceLocation resourceLocation = new ResourceLocation(GrimWorld.modID, "textures/entities/ice.png");

	public RenderIceCube(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityIceCube entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.rotate((entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks) - 90F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
		GlStateManager.enableRescaleNormal();
		GlStateManager.rotate(45F, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(1F, 1F, 1F);

		EntityItem ice = new EntityItem(renderManager.worldObj, renderManager.viewerPosX, renderManager.viewerPosY, renderManager.viewerPosZ, new ItemStack(Blocks.ice));
		ice.hoverStart = 0.0F;
		this.renderManager.doRenderEntity(ice, 0, 0, 0, 0, 0, false);
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityIceCube entity) {
		return resourceLocation;
	}

	public static class RenderIceCubeFactory implements IRenderFactory<EntityIceCube> {

		@Override
		public Render<? super EntityIceCube> createRenderFor(RenderManager manager) {
			return new RenderIceCube(manager);
		}

	}
}
