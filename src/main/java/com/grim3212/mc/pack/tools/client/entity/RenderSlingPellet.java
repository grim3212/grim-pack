package com.grim3212.mc.pack.tools.client.entity;

import com.grim3212.mc.pack.tools.entity.EntitySlingPellet;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSlingPellet extends Render<EntitySlingPellet> {

	private final RenderItem itemRenderer;

	public RenderSlingPellet(RenderManager renderManagerIn, RenderItem itemRendererIn) {
		super(renderManagerIn);
		this.itemRenderer = itemRendererIn;
	}

	@Override
	public void doRender(EntitySlingPellet entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.enableRescaleNormal();
		GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((float) (this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
		this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		this.itemRenderer.renderItem(new ItemStack(ToolsItems.sling_pellet, 1, entity.getType().ordinal()), ItemCameraTransforms.TransformType.GROUND);

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySlingPellet entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

	public static class RenderSlingPelletFactory implements IRenderFactory<EntitySlingPellet> {

		@Override
		public Render<? super EntitySlingPellet> createRenderFor(RenderManager manager) {
			return new RenderSlingPellet(manager, Minecraft.getMinecraft().getRenderItem());
		}

	}
}
