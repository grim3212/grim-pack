package com.grim3212.mc.pack.core.util.generator.renderers;

import javax.annotation.Nullable;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@SuppressWarnings("deprecation")
public class RendererHelper {

	public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, double zLevel) {
		float f = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) (x + 0), (double) (y + height), zLevel).tex(((float) (textureX + 0) * f), ((float) (textureY + height) * f)).endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + height), zLevel).tex(((float) (textureX + width) * f), ((float) (textureY + height) * f)).endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + 0), zLevel).tex(((float) (textureX + width) * f), ((float) (textureY + 0) * f)).endVertex();
		bufferbuilder.pos((double) (x + 0), (double) (y + 0), zLevel).tex(((float) (textureX + 0) * f), ((float) (textureY + 0) * f)).endVertex();
		tessellator.draw();
	}

	public static void resizeWindow(int width, int height, boolean force) {
		Minecraft mc = Minecraft.getInstance();

		if (force || mc.getMainWindow().getWidth() != width || mc.getMainWindow().getHeight() != height) {
			try {
				mc.getMainWindow().toggleFullscreen();
				ObfuscationReflectionHelper.setPrivateValue(MainWindow.class, mc.getMainWindow(), width, "tempDisplayWidth");
				ObfuscationReflectionHelper.setPrivateValue(MainWindow.class, mc.getMainWindow(), height, "tempDisplayHeight");
				mc.getMainWindow().toggleFullscreen();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	public static void renderItemModel(ItemStack stack, int x, int y, IBakedModel bakedmodel, boolean blend) {
		Minecraft mc = Minecraft.getInstance();

		RenderSystem.pushMatrix();
		mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
		mc.getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmapDirect(false, false);
		RenderSystem.enableRescaleNormal();
		RenderSystem.enableAlphaTest();
		RenderSystem.alphaFunc(516, 0.1F);
		if (blend || stack.hasEffect())
			RenderSystem.enableBlend();
		else
			RenderSystem.disableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		setupGuiTransform(x, y, bakedmodel.isGui3d(), mc.getItemRenderer().zLevel);
		//bakedmodel = ForgeHooksClient.handleCameraTransforms(bakedmodel, ItemCameraTransforms.TransformType.GUI, false);
		//mc.getItemRenderer().renderItem(stack, bakedmodel);
		RenderSystem.disableAlphaTest();
		RenderSystem.disableRescaleNormal();
		RenderSystem.disableLighting();
		RenderSystem.popMatrix();
		mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
		mc.getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
	}

	private static void setupGuiTransform(int xPosition, int yPosition, boolean isGui3d, float zLevel) {
		RenderSystem.translatef((float) xPosition, (float) yPosition, 100.0F + zLevel);
		RenderSystem.translatef(8.0F, 8.0F, 0.0F);
		RenderSystem.scalef(1.0F, -1.0F, 1.0F);
		RenderSystem.scalef(16.0F, 16.0F, 16.0F);

		if (isGui3d) {
			RenderSystem.enableLighting();
		} else {
			RenderSystem.disableLighting();
		}
	}

	public static void renderItemAndEffectIntoGUI(ItemStack stack, int xPosition, int yPosition, boolean blend) {
		renderItemAndEffectIntoGUI(Minecraft.getInstance().player, stack, xPosition, yPosition, blend);
	}

	public static void renderItemAndEffectIntoGUI(@Nullable LivingEntity p_184391_1_, final ItemStack p_184391_2_, int p_184391_3_, int p_184391_4_, boolean blend) {
		ItemRenderer render = Minecraft.getInstance().getItemRenderer();

		if (!p_184391_2_.isEmpty()) {
			render.zLevel += 50.0F;

			try {
				renderItemModel(p_184391_2_, p_184391_3_, p_184391_4_, render.getItemModelWithOverrides(p_184391_2_, (World) null, p_184391_1_), blend);
			} catch (Throwable throwable) {
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering item");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being rendered");
				crashreportcategory.addDetail("Item Type", new ICrashReportDetail<String>() {
					public String call() throws Exception {
						return String.valueOf((Object) p_184391_2_.getItem());
					}
				});
				crashreportcategory.addDetail("Item NBT", new ICrashReportDetail<String>() {
					public String call() throws Exception {
						return String.valueOf((Object) p_184391_2_.getTag());
					}
				});
				crashreportcategory.addDetail("Item Foil", new ICrashReportDetail<String>() {
					public String call() throws Exception {
						return String.valueOf(p_184391_2_.hasEffect());
					}
				});
				throw new ReportedException(crashreport);
			}

			render.zLevel -= 50.0F;
		}
	}
}
