package com.grim3212.mc.decor.client.entity;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.decor.GrimDecor;
import com.grim3212.mc.decor.config.DecorConfig;
import com.grim3212.mc.decor.entity.EntityWallpaper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWallpaper extends Render<EntityWallpaper> {

	private static final ResourceLocation wallpaperTexture = new ResourceLocation(GrimDecor.modID, "textures/entities/wallpapers.png");

	public float colorRedTopLeft;
	public float colorRedBottomLeft;
	public float colorRedBottomRight;
	public float colorRedTopRight;
	public float colorGreenTopLeft;
	public float colorGreenBottomLeft;
	public float colorGreenBottomRight;
	public float colorGreenTopRight;
	public float colorBlueTopLeft;
	public float colorBlueBottomLeft;
	public float colorBlueBottomRight;
	public float colorBlueTopRight;
	public int brightnessTopLeft;
	public int brightnessTopRight;
	public int brightnessBottomLeft;
	public int brightnessBottomRight;

	public static class WallpaperFactory implements IRenderFactory<EntityWallpaper> {
		@Override
		public Render<? super EntityWallpaper> createRenderFor(RenderManager manager) {
			return new RenderWallpaper(manager);
		}
	}

	protected RenderWallpaper(RenderManager renderManager) {
		super(renderManager);
	}

	public void initWallpaper(EntityWallpaper entitywallpaper, double x, double y, double z, float angle) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.rotate(angle, 0.0F, 1.0F, 0.0F);
		GlStateManager.enableRescaleNormal();
		GlStateManager.shadeModel(7425);
		GlStateManager.disableLighting();
		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

		this.bindEntityTexture(entitywallpaper);
		renderWallpaper(entitywallpaper);

		GlStateManager.shadeModel(7424);
		GlStateManager.enableLighting();
		net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	private void renderWallpaper(EntityWallpaper entitywallpaper) {
		WorldRenderer renderer = Tessellator.getInstance().getWorldRenderer();
		Tessellator tessellator = Tessellator.getInstance();

		GlStateManager.scale(0.03125F, 0.03125F, 0.03125F);

		int x = MathHelper.floor_double(entitywallpaper.posX);
		int y = MathHelper.floor_double(entitywallpaper.posY);
		int z = MathHelper.floor_double(entitywallpaper.posZ);

		float minX = -16.0F;
		float minY = -16.0F;
		float minZ = 0.0F;

		float maxX = 16.0F;
		float maxY = 16.0F;
		float maxZ = DecorConfig.widthWallpaper;

		float minU = entitywallpaper.getFrameID() / 16 / 16.0F;
		float minV = entitywallpaper.getFrameID() % 16 / 16.0F;

		float maxUV = 0.0625F;
		float sideUV = 0.00195313F;

		float red = entitywallpaper.getFrameColor()[0] / 256.0F;
		float green = entitywallpaper.getFrameColor()[1] / 256.0F;
		float blue = entitywallpaper.getFrameColor()[2] / 256.0F;

		renderer.begin(GL11.GL_QUADS, RenderHelper.POSITION_TEX_COLOR_LIGHTMAP_NORMAL);
		if (Minecraft.isAmbientOcclusionEnabled()) {
			renderWithAmbientOcclusion(new BlockPos(x, y, z), red, green, blue, entitywallpaper.direction);

			if (entitywallpaper.direction == 0) {
				renderer.pos(minX, minY, minZ).tex(minU + maxUV, minV + maxUV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(0.0F, 0.0F, 1.0F).endVertex();
				renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(0.0F, 0.0F, 1.0F).endVertex();
				renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(0.0F, 0.0F, 1.0F).endVertex();
				renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(0.0F, 0.0F, 1.0F).endVertex();
			} else if (entitywallpaper.direction == 2) {
				renderer.pos(minX, minY, minZ).tex(minU + maxUV, minV + maxUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(0.0F, 0.0F, 1.0F).endVertex();
				renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(0.0F, 0.0F, 1.0F).endVertex();
				renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(0.0F, 0.0F, 1.0F).endVertex();
				renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(0.0F, 0.0F, 1.0F).endVertex();
			} else if (entitywallpaper.direction == 1) {
				renderer.pos(minX, minY, minZ).tex(minU + maxUV, minV + maxUV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(0.0F, 0.0F, 1.0F).endVertex();
				renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(0.0F, 0.0F, 1.0F).endVertex();
				renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(0.0F, 0.0F, 1.0F).endVertex();
				renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(0.0F, 0.0F, 1.0F).endVertex();
			} else if (entitywallpaper.direction == 3) {
				renderer.pos(minX, minY, minZ).tex(minU + maxUV, minV + maxUV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(0.0F, 0.0F, 1.0F).endVertex();
				renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(0.0F, 0.0F, 1.0F).endVertex();
				renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).normal(0.0F, 0.0F, 1.0F).lightmap(0, this.brightnessTopRight).endVertex();
				renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).normal(0.0F, 0.0F, 1.0F).lightmap(0, this.brightnessTopLeft).endVertex();
			}

			if (!entitywallpaper.isBlockLeft) {
				if (entitywallpaper.direction == 0) {
					renderer.pos(minX, maxY, maxZ).tex(minU + maxUV, minV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(-1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(-1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(minX, minY, minZ).tex(minU + maxUV - sideUV, minV + maxUV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(-1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(minX, minY, maxZ).tex(minU + maxUV - sideUV, minV + maxUV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(-1.0F, 0.0F, 0.0F).endVertex();
				} else if (entitywallpaper.direction == 2) {
					renderer.pos(minX, maxY, maxZ).tex(minU + maxUV, minV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(-1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(-1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(minX, minY, minZ).tex(minU + maxUV - sideUV, minV + maxUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(-1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(minX, minY, maxZ).tex(minU + maxUV - sideUV, minV + maxUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(-1.0F, 0.0F, 0.0F).endVertex();
				} else if (entitywallpaper.direction == 1) {
					renderer.pos(minX, maxY, maxZ).tex(minU + maxUV, minV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(-1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(-1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(minX, minY, minZ).tex(minU + maxUV - sideUV, minV + maxUV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(-1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(minX, minY, maxZ).tex(minU + maxUV - sideUV, minV + maxUV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(-1.0F, 0.0F, 0.0F).endVertex();
				} else if (entitywallpaper.direction == 3) {
					renderer.pos(minX, maxY, maxZ).tex(minU + maxUV, minV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(-1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(-1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(minX, minY, minZ).tex(minU + maxUV - sideUV, minV + maxUV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(-1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(minX, minY, maxZ).tex(minU + maxUV - sideUV, minV + maxUV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(-1.0F, 0.0F, 0.0F).endVertex();
				}
			}
			if (!entitywallpaper.isBlockUp) {
				if (entitywallpaper.direction == 0) {
					renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(0.0F, 1.0F, 0.0F).endVertex();
					renderer.pos(minX, maxY, maxZ).tex(minU + maxUV, minV + sideUV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(0.0F, 1.0F, 0.0F).endVertex();
					renderer.pos(maxX, maxY, maxZ).tex(minU, minV + sideUV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(0.0F, 1.0F, 0.0F).endVertex();
					renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(0.0F, 1.0F, 0.0F).endVertex();
				} else if (entitywallpaper.direction == 2) {
					renderer.pos(maxX, maxY, maxZ).tex(minU, minV + sideUV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(0.0F, 1.0F, 0.0F).endVertex();
					renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(0.0F, 1.0F, 0.0F).endVertex();
					renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(0.0F, 1.0F, 0.0F).endVertex();
					renderer.pos(minX, maxY, maxZ).tex(minU + maxUV, minV + sideUV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(0.0F, 1.0F, 0.0F).endVertex();
				} else if (entitywallpaper.direction == 1) {
					renderer.pos(maxX, maxY, maxZ).tex(minU, minV + sideUV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(0.0F, 1.0F, 0.0F).endVertex();
					renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(0.0F, 1.0F, 0.0F).endVertex();
					renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(0.0F, 1.0F, 0.0F).endVertex();
					renderer.pos(minX, maxY, maxZ).tex(minU + maxUV, minV + sideUV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(0.0F, 1.0F, 0.0F).endVertex();
				} else if (entitywallpaper.direction == 3) {
					renderer.pos(maxX, maxY, maxZ).tex(minU, minV + sideUV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(0.0F, 1.0F, 0.0F).endVertex();
					renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(0.0F, 1.0F, 0.0F).endVertex();
					renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(0.0F, 1.0F, 0.0F).endVertex();
					renderer.pos(minX, maxY, maxZ).tex(minU + maxUV, minV + sideUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(0.0F, 1.0F, 0.0F).endVertex();
				}
			}
			if (!entitywallpaper.isBlockRight) {
				if (entitywallpaper.direction == 0) {
					renderer.pos(maxX, minY, maxZ).tex(minU + sideUV, minV + maxUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(maxX, maxY, maxZ).tex(minU + sideUV, minV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(1.0F, 0.0F, 0.0F).endVertex();
				} else if (entitywallpaper.direction == 2) {
					renderer.pos(maxX, minY, maxZ).tex(minU + sideUV, minV + maxUV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(maxX, maxY, maxZ).tex(minU + sideUV, minV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(1.0F, 0.0F, 0.0F).endVertex();
				} else if (entitywallpaper.direction == 1) {
					renderer.pos(maxX, minY, maxZ).tex(minU + sideUV, minV + maxUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(maxX, maxY, maxZ).tex(minU + sideUV, minV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(1.0F, 0.0F, 0.0F).endVertex();
				} else if (entitywallpaper.direction == 3) {
					renderer.pos(maxX, minY, maxZ).tex(minU + sideUV, minV + maxUV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(1.0F, 0.0F, 0.0F).endVertex();
					renderer.pos(maxX, maxY, maxZ).tex(minU + sideUV, minV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(1.0F, 0.0F, 0.0F).endVertex();
				}
			}
			if (!entitywallpaper.isBlockDown) {
				if (entitywallpaper.direction == 0) {
					renderer.pos(minX, minY, maxZ).tex(minU + maxUV, minV + maxUV - sideUV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(0.0F, -1.0F, 0.0F).endVertex();
					renderer.pos(minX, minY, minZ).tex(minU + maxUV, minV + maxUV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(0.0F, -1.0F, 0.0F).endVertex();
					renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(0.0F, -1.0F, 0.0F).endVertex();
					renderer.pos(maxX, minY, maxZ).tex(minU, minV + maxUV - sideUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(0.0F, -1.0F, 0.0F).endVertex();
				} else if (entitywallpaper.direction == 2) {
					renderer.pos(minX, minY, maxZ).tex(minU + maxUV, minV + maxUV - sideUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(0.0F, -1.0F, 0.0F).endVertex();
					renderer.pos(minX, minY, minZ).tex(minU + maxUV, minV + maxUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(0.0F, -1.0F, 0.0F).endVertex();
					renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(0.0F, -1.0F, 0.0F).endVertex();
					renderer.pos(maxX, minY, maxZ).tex(minU, minV + maxUV - sideUV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(0.0F, -1.0F, 0.0F).endVertex();
				} else if (entitywallpaper.direction == 1) {
					renderer.pos(minX, minY, maxZ).tex(minU + maxUV, minV + maxUV - sideUV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(0.0F, -1.0F, 0.0F).endVertex();
					renderer.pos(minX, minY, minZ).tex(minU + maxUV, minV + maxUV).color(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, 1.0f).lightmap(0, this.brightnessTopRight).normal(0.0F, -1.0F, 0.0F).endVertex();
					renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(0.0F, -1.0F, 0.0F).endVertex();
					renderer.pos(maxX, minY, maxZ).tex(minU, minV + maxUV - sideUV).color(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, 1.0f).lightmap(0, this.brightnessBottomRight).normal(0.0F, -1.0F, 0.0F).endVertex();
				} else if (entitywallpaper.direction == 3) {
					renderer.pos(minX, minY, maxZ).tex(minU + maxUV, minV + maxUV - sideUV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(0.0F, -1.0F, 0.0F).endVertex();
					renderer.pos(minX, minY, minZ).tex(minU + maxUV, minV + maxUV).color(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, 1.0f).lightmap(0, this.brightnessBottomLeft).normal(0.0F, -1.0F, 0.0F).endVertex();
					renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(0.0F, -1.0F, 0.0F).endVertex();
					renderer.pos(maxX, minY, maxZ).tex(minU, minV + maxUV - sideUV).color(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, 1.0f).lightmap(0, this.brightnessTopLeft).normal(0.0F, -1.0F, 0.0F).endVertex();
				}
			}
		} else {
			BlockPos pos = new BlockPos(x, y, z);
			float blockLight = this.renderManager.worldObj.getLightBrightness(pos);

			renderer.pos(minX, minY, minZ).tex(minU + maxUV, minV + maxUV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(0.0F, 0.0F, -1.0F).endVertex();
			renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(0.0F, 0.0F, -1.0F).endVertex();
			renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(0.0F, 0.0F, -1.0F).endVertex();
			renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(0.0F, 0.0F, -1.0F).endVertex();

			if (!entitywallpaper.isBlockLeft) {
				renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(-1.0F, 0.0F, 0.0F).endVertex();
				renderer.pos(minX, minY, minZ).tex(minU + maxUV - sideUV, minV + maxUV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(-1.0F, 0.0F, 0.0F).endVertex();
				renderer.pos(minX, minY, maxZ).tex(minU + maxUV - sideUV, minV + maxUV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(-1.0F, 0.0F, 0.0F).endVertex();
				renderer.pos(minX, maxY, maxZ).tex(minU + maxUV, minV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(-1.0F, 0.0F, 0.0F).endVertex();
			}
			if (!entitywallpaper.isBlockUp) {
				renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(0.0F, 1.0F, 0.0F).endVertex();
				renderer.pos(minX, maxY, minZ).tex(minU + maxUV, minV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(0.0F, 1.0F, 0.0F).endVertex();
				renderer.pos(minX, maxY, maxZ).tex(minU + maxUV, minV + sideUV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(0.0F, 1.0F, 0.0F).endVertex();
				renderer.pos(maxX, maxY, maxZ).tex(minU, minV + sideUV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(0.0F, 1.0F, 0.0F).endVertex();
			}
			if (!entitywallpaper.isBlockRight) {
				renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(1.0F, 0.0F, 0.0F).endVertex();
				renderer.pos(maxX, maxY, minZ).tex(minU, minV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(1.0F, 0.0F, 0.0F).endVertex();
				renderer.pos(maxX, maxY, maxZ).tex(minU + sideUV, minV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(1.0F, 0.0F, 0.0F).endVertex();
				renderer.pos(maxX, minY, maxZ).tex(minU + sideUV, minV + maxUV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(1.0F, 0.0F, 0.0F).endVertex();
			}
			if (!entitywallpaper.isBlockDown) {
				renderer.pos(minX, minY, minZ).tex(minU + maxUV, minV + maxUV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(0.0F, -1.0F, 0.0F).endVertex();
				renderer.pos(maxX, minY, minZ).tex(minU, minV + maxUV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(0.0F, -1.0F, 0.0F).endVertex();
				renderer.pos(maxX, minY, maxZ).tex(minU, minV + maxUV - sideUV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(0.0F, -1.0F, 0.0F).endVertex();
				renderer.pos(minX, minY, maxZ).tex(minU + maxUV, minV + maxUV - sideUV).color(blockLight * red, blockLight * green, blockLight * blue, 1.0f).lightmap(0, getMixedBrightnessForBlock(pos)).normal(0.0F, -1.0F, 0.0F).endVertex();
			}
		}
		tessellator.draw();
	}

	public boolean renderWithAmbientOcclusion(BlockPos pos, float red, float green, float blue, int direction) {
		float lightSelf = getAmbientOcclusionLightValue(pos);
		int brightnessSelf = getMixedBrightnessForBlock(pos);

		if (direction == 0) {
			float lightValueXZNN = getAmbientOcclusionLightValue(pos.west());
			float lightValueYZNN = getAmbientOcclusionLightValue(pos.down());
			float lightValueYZPN = getAmbientOcclusionLightValue(pos.up());
			float lightValueXZPN = getAmbientOcclusionLightValue(pos.east());

			float lightValueXYZNNN = getAmbientOcclusionLightValue(pos.west().down());
			float lightValueXYZNPN = getAmbientOcclusionLightValue(pos.west().up());
			float lightValueXYZPNN = getAmbientOcclusionLightValue(pos.east().down());
			float lightValueXYZPPN = getAmbientOcclusionLightValue(pos.east().up());

			int brightnessXZNN = getMixedBrightnessForBlock(pos.west());
			int brightnessYZNN = getMixedBrightnessForBlock(pos.down());
			int brightnessYZPN = getMixedBrightnessForBlock(pos.up());
			int brightnessXZPN = getMixedBrightnessForBlock(pos.east());

			int brightnessXYZNNN = getMixedBrightnessForBlock(pos.west().down());
			int brightnessXYZNPN = getMixedBrightnessForBlock(pos.west().up());
			int brightnessXYZPNN = getMixedBrightnessForBlock(pos.east().down());
			int brightnessXYZPPN = getMixedBrightnessForBlock(pos.east().up());

			float lightTopLeft = (lightSelf + lightValueXZNN + lightValueXYZNPN + lightValueYZPN) / 4.0F;
			float lightBottomLeft = (lightSelf + lightValueYZPN + lightValueXZPN + lightValueXYZPPN) / 4.0F;
			float lightBottomRight = (lightSelf + lightValueYZNN + lightValueXYZPNN + lightValueXZPN) / 4.0F;
			float lightTopRight = (lightSelf + lightValueXZNN + lightValueXYZNNN + lightValueYZNN) / 4.0F;

			this.brightnessTopLeft = getAoBrightness(brightnessXZNN, brightnessXYZNPN, brightnessYZPN, brightnessSelf);
			this.brightnessBottomLeft = getAoBrightness(brightnessYZPN, brightnessXZPN, brightnessXYZPPN, brightnessSelf);
			this.brightnessBottomRight = getAoBrightness(brightnessYZNN, brightnessXYZPNN, brightnessXZPN, brightnessSelf);
			this.brightnessTopRight = getAoBrightness(brightnessXZNN, brightnessXYZNNN, brightnessYZNN, brightnessSelf);

			this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red * 0.8F);
			this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green * 0.8F);
			this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue * 0.8F);

			this.colorRedTopLeft *= lightTopLeft;
			this.colorGreenTopLeft *= lightTopLeft;
			this.colorBlueTopLeft *= lightTopLeft;

			this.colorRedBottomLeft *= lightBottomLeft;
			this.colorGreenBottomLeft *= lightBottomLeft;
			this.colorBlueBottomLeft *= lightBottomLeft;

			this.colorRedBottomRight *= lightBottomRight;
			this.colorGreenBottomRight *= lightBottomRight;
			this.colorBlueBottomRight *= lightBottomRight;

			this.colorRedTopRight *= lightTopRight;
			this.colorGreenTopRight *= lightTopRight;
			this.colorBlueTopRight *= lightTopRight;

			return true;
		}

		if (direction == 2) {
			float lightValueXZNP = getAmbientOcclusionLightValue(pos.west());
			float lightValueXZPP = getAmbientOcclusionLightValue(pos.east());
			float lightValueYZNP = getAmbientOcclusionLightValue(pos.down());
			float lightValueYZPP = getAmbientOcclusionLightValue(pos.up());

			float lightValueXYZNNP = getAmbientOcclusionLightValue(pos.west().down());
			float lightValueXYZNPP = getAmbientOcclusionLightValue(pos.west().up());
			float lightValueXYZPNP = getAmbientOcclusionLightValue(pos.east().down());
			float lightValueXYZPPP = getAmbientOcclusionLightValue(pos.east().up());

			int brightnessXZNP = getMixedBrightnessForBlock(pos.west());
			int brightnessXZPP = getMixedBrightnessForBlock(pos.east());
			int brightnessYZNP = getMixedBrightnessForBlock(pos.down());
			int brightnessYZPP = getMixedBrightnessForBlock(pos.up());

			int brightnessXYZNNP = getMixedBrightnessForBlock(pos.west().down());
			int brightnessXYZNPP = getMixedBrightnessForBlock(pos.west().up());
			int brightnessXYZPNP = getMixedBrightnessForBlock(pos.east().down());
			int brightnessXYZPPP = getMixedBrightnessForBlock(pos.east().up());

			float lightTopLeft = (lightValueXZNP + lightValueXYZNPP + lightSelf + lightValueYZPP) / 4.0F;
			float lightTopRight = (lightSelf + lightValueYZPP + lightValueXZPP + lightValueXYZPPP) / 4.0F;
			float lightBottomRight = (lightValueYZNP + lightSelf + lightValueXYZPNP + lightValueXZPP) / 4.0F;
			float lightBottomLeft = (lightValueXYZNNP + lightValueXZNP + lightValueYZNP + lightSelf) / 4.0F;

			this.brightnessTopLeft = getAoBrightness(brightnessXZNP, brightnessXYZNPP, brightnessYZPP, brightnessSelf);
			this.brightnessTopRight = getAoBrightness(brightnessYZPP, brightnessXZPP, brightnessXYZPPP, brightnessSelf);
			this.brightnessBottomRight = getAoBrightness(brightnessYZNP, brightnessXYZPNP, brightnessXZPP, brightnessSelf);
			this.brightnessBottomLeft = getAoBrightness(brightnessXYZNNP, brightnessXZNP, brightnessYZNP, brightnessSelf);

			this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red * 0.8F);
			this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green * 0.8F);
			this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue * 0.8F);

			this.colorRedTopLeft *= lightTopLeft;
			this.colorGreenTopLeft *= lightTopLeft;
			this.colorBlueTopLeft *= lightTopLeft;

			this.colorRedBottomLeft *= lightBottomLeft;
			this.colorGreenBottomLeft *= lightBottomLeft;
			this.colorBlueBottomLeft *= lightBottomLeft;

			this.colorRedBottomRight *= lightBottomRight;
			this.colorGreenBottomRight *= lightBottomRight;
			this.colorBlueBottomRight *= lightBottomRight;

			this.colorRedTopRight *= lightTopRight;
			this.colorGreenTopRight *= lightTopRight;
			this.colorBlueTopRight *= lightTopRight;

			return true;
		}

		if (direction == 1) {
			float lightValueXYNN = getAmbientOcclusionLightValue(pos.down());
			float lightValueXZNN = getAmbientOcclusionLightValue(pos.north());
			float lightValueXZNP = getAmbientOcclusionLightValue(pos.south());
			float lightValueXYNP = getAmbientOcclusionLightValue(pos.up());

			float lightValueXYZNNN = getAmbientOcclusionLightValue(pos.down().north());
			float lightValueXYZNNP = getAmbientOcclusionLightValue(pos.down().south());
			float lightValueXYZNPN = getAmbientOcclusionLightValue(pos.up().north());
			float lightValueXYZNPP = getAmbientOcclusionLightValue(pos.up().south());

			int brightnessXYNN = getMixedBrightnessForBlock(pos.down());
			int brightnessXZNN = getMixedBrightnessForBlock(pos.north());
			int brightnessXZNP = getMixedBrightnessForBlock(pos.south());
			int brightnessXYNP = getMixedBrightnessForBlock(pos.up());

			int brightnessXYZNNN = getMixedBrightnessForBlock(pos.down().north());
			int brightnessXYZNNP = getMixedBrightnessForBlock(pos.down().south());
			int brightnessXYZNPN = getMixedBrightnessForBlock(pos.up().north());
			int brightnessXYZNPP = getMixedBrightnessForBlock(pos.up().south());

			float lightTopRight = (lightValueXYNN + lightValueXYZNNP + lightSelf + lightValueXZNP) / 4.0F;
			float lightTopLeft = (lightSelf + lightValueXZNP + lightValueXYNP + lightValueXYZNPP) / 4.0F;
			float lightBottomLeft = (lightValueXZNN + lightSelf + lightValueXYZNPN + lightValueXYNP) / 4.0F;
			float lightBottomRight = (lightValueXYZNNN + lightValueXYNN + lightValueXZNN + lightSelf) / 4.0F;

			this.brightnessTopRight = getAoBrightness(brightnessXYNN, brightnessXYZNNP, brightnessXZNP, brightnessSelf);
			this.brightnessTopLeft = getAoBrightness(brightnessXZNP, brightnessXYNP, brightnessXYZNPP, brightnessSelf);
			this.brightnessBottomLeft = getAoBrightness(brightnessXZNN, brightnessXYZNPN, brightnessXYNP, brightnessSelf);
			this.brightnessBottomRight = getAoBrightness(brightnessXYZNNN, brightnessXYNN, brightnessXZNN, brightnessSelf);

			this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red * 0.6F);
			this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green * 0.6F);
			this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue * 0.6F);

			this.colorRedTopLeft *= lightTopLeft;
			this.colorGreenTopLeft *= lightTopLeft;
			this.colorBlueTopLeft *= lightTopLeft;

			this.colorRedBottomLeft *= lightBottomLeft;
			this.colorGreenBottomLeft *= lightBottomLeft;
			this.colorBlueBottomLeft *= lightBottomLeft;

			this.colorRedBottomRight *= lightBottomRight;
			this.colorGreenBottomRight *= lightBottomRight;
			this.colorBlueBottomRight *= lightBottomRight;

			this.colorRedTopRight *= lightTopRight;
			this.colorGreenTopRight *= lightTopRight;
			this.colorBlueTopRight *= lightTopRight;

			return true;
		}

		if (direction == 3) {
			float lightValueXYPN = getAmbientOcclusionLightValue(pos.down());
			float lightValueXZPN = getAmbientOcclusionLightValue(pos.north());
			float lightValueXZPP = getAmbientOcclusionLightValue(pos.south());
			float lightValueXYPP = getAmbientOcclusionLightValue(pos.up());

			float lightValueXYZPNN = getAmbientOcclusionLightValue(pos.down().north());
			float lightValueXYZPNP = getAmbientOcclusionLightValue(pos.down().south());
			float lightValueXYZPPN = getAmbientOcclusionLightValue(pos.up().north());
			float lightValueXYZPPP = getAmbientOcclusionLightValue(pos.up().south());

			int brightnessXYPN = getMixedBrightnessForBlock(pos.down());
			int brightnessXZPN = getMixedBrightnessForBlock(pos.north());
			int brightnessXZPP = getMixedBrightnessForBlock(pos.south());
			int brightnessXYPP = getMixedBrightnessForBlock(pos.up());

			int brightnessXYZPNN = getMixedBrightnessForBlock(pos.down().north());
			int brightnessXYZPNP = getMixedBrightnessForBlock(pos.down().south());
			int brightnessXYZPPN = getMixedBrightnessForBlock(pos.up().north());
			int brightnessXYZPPP = getMixedBrightnessForBlock(pos.up().south());

			float lightTopLeft = (lightValueXYPN + lightValueXYZPNP + lightSelf + lightValueXZPP) / 4.0F;
			float lightTopRight = (lightSelf + lightValueXZPP + lightValueXYPP + lightValueXYZPPP) / 4.0F;
			float lightBottomRight = (lightValueXZPN + lightSelf + lightValueXYZPPN + lightValueXYPP) / 4.0F;
			float lightBottomLeft = (lightValueXYZPNN + lightValueXYPN + lightValueXZPN + lightSelf) / 4.0F;

			this.brightnessTopLeft = getAoBrightness(brightnessXYPN, brightnessXYZPNP, brightnessXZPP, brightnessSelf);
			this.brightnessTopRight = getAoBrightness(brightnessXZPP, brightnessXYPP, brightnessXYZPPP, brightnessSelf);
			this.brightnessBottomRight = getAoBrightness(brightnessXZPN, brightnessXYZPPN, brightnessXYPP, brightnessSelf);
			this.brightnessBottomLeft = getAoBrightness(brightnessXYZPNN, brightnessXYPN, brightnessXZPN, brightnessSelf);

			this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red * 0.6F);
			this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green * 0.6F);
			this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue * 0.6F);

			this.colorRedTopLeft *= lightTopLeft;
			this.colorGreenTopLeft *= lightTopLeft;
			this.colorBlueTopLeft *= lightTopLeft;

			this.colorRedBottomLeft *= lightBottomLeft;
			this.colorGreenBottomLeft *= lightBottomLeft;
			this.colorBlueBottomLeft *= lightBottomLeft;

			this.colorRedBottomRight *= lightBottomRight;
			this.colorGreenBottomRight *= lightBottomRight;
			this.colorBlueBottomRight *= lightBottomRight;

			this.colorRedTopRight *= lightTopRight;
			this.colorGreenTopRight *= lightTopRight;
			this.colorBlueTopRight *= lightTopRight;

			return true;
		}

		return false;
	}

	public int getAoBrightness(int brightness1, int brightness2, int brightness3, int brightnessSelf) {
		int divisor = 4;

		int light1 = brightness1 % 65536;
		int light2 = brightness2 % 65536;
		int light3 = brightness3 % 65536;
		int light4 = brightnessSelf % 65536;

		if (light1 == 0) {
			divisor--;
		}

		if (light2 == 0) {
			divisor--;
		}

		if (light3 == 0) {
			divisor--;
		}

		int light = (light1 + light2 + light3 + light4) / divisor;

		divisor = 4;

		int shade1 = brightness1 / 65536;
		int shade2 = brightness2 / 65536;
		int shade3 = brightness3 / 65536;
		int shade4 = brightnessSelf / 65536;

		if (shade1 == 0) {
			divisor--;
		}

		if (shade2 == 0) {
			divisor--;
		}

		if (shade3 == 0) {
			divisor--;
		}

		int shade = (shade1 + shade2 + shade3 + shade4) / divisor * 65536;

		return light + shade;
	}

	public float getAmbientOcclusionLightValue(BlockPos pos) {
		return Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock().isNormalCube() ? 0.2F : 1.0F;
	}

	public int getMixedBrightnessForBlock(BlockPos pos) {
		return this.renderManager.worldObj.getCombinedLight(pos, 0);
	}

	public void doRender(EntityWallpaper wallpaper, double x, double y, double z, float yaw, float f1) {
		initWallpaper(wallpaper, x, y, z, yaw);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWallpaper wallpaper) {
		return wallpaperTexture;
	}
}