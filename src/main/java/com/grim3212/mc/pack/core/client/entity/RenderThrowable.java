package com.grim3212.mc.pack.core.client.entity;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("deprecation")
public class RenderThrowable<T extends Entity> extends EntityRenderer<T> {

	protected final ItemStack item;
	private final ItemRenderer itemRenderer;

	public RenderThrowable(EntityRendererManager renderManagerIn, ItemStack itemIn, ItemRenderer itemRendererIn) {
		super(renderManagerIn);
		this.item = itemIn;
		this.itemRenderer = itemRendererIn;
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) x, (float) y, (float) z);
		GlStateManager.enableRescaleNormal();
		GlStateManager.rotatef(-this.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef((float) (this.getRenderManager().options.thirdPersonView == 2 ? -1 : 1) * this.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
		this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.setupSolidRenderingTextureCombine(this.getTeamColor(entity));
		}

		this.itemRenderer.renderItem(this.item, TransformType.GROUND);

		if (this.renderOutlines) {
			GlStateManager.tearDownSolidRenderingTextureCombine();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}

	public static class RenderThrowableFactory implements IRenderFactory<ThrowableEntity> {

		private final ItemStack item;

		public RenderThrowableFactory(ItemStack item) {
			this.item = item;
		}

		@Override
		public EntityRenderer<? super ThrowableEntity> createRenderFor(EntityRendererManager manager) {
			return new RenderThrowable<ThrowableEntity>(manager, item, Minecraft.getInstance().getItemRenderer());
		}
	}
}
