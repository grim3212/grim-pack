package com.grim3212.mc.pack.core.util.generator.renderers;

import java.awt.Color;
import java.io.File;
import java.nio.FloatBuffer;
import java.util.Collection;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.core.util.generator.Generator;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class IconRenderer {

	private static final int[] SCALES = { 2, 4, 8, 10, 16 };

	public static void renderItems(Collection<ItemStack> items) {
		GlStateManager.pushMatrix();

		for (int scale : SCALES) {
			GrimLog.info(Generator.GENERATOR_NAME, "Starting image scale at " + 16 * scale);

			GlStateManager.pushMatrix();
			GlStateManager.scalef(scale, scale, 1);

			for (ItemStack item : items) {
				// Don't regenerate already done items
				if (!new File(Generator.getIconDir() + "/" + item.getItem().getRegistryName().getNamespace() + "/" + item.getItem().getRegistryName().getPath()).exists()) {
					try {
						IconRenderer.renderItem(item, scale);
					} catch (Exception e) {
						GrimLog.error(Generator.GENERATOR_NAME, "Couldn't render " + item.getTranslationKey() + " at " + 16 * scale + " scale!");
						e.printStackTrace();
					}
				}
			}
			GlStateManager.popMatrix();
		}

		GlStateManager.popMatrix();
	}

	private static final float COLOR_R = 1f / 255;
	private static final float COLOR_G = 1f;
	private static final float COLOR_B = 1f / 255;

	private static void renderItem(ItemStack stack, int scale) {
		boolean isDrawing = ObfuscationReflectionHelper.getPrivateValue(BufferBuilder.class, Tessellator.getInstance().getBuffer(), "isDrawing");

		if (isDrawing) {
			Tessellator.getInstance().draw();
		}

		GlStateManager.clearColor(COLOR_R, COLOR_G, COLOR_B, 1);
		GlStateManager.clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GlStateManager.enableRescaleNormal();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawItemStack(stack, 0, 0);

		// Dir path in the form of
		// '.../icons/domain_name/path_name/meta/'
		// Ex: '.../icons/grimpack/steel_ingot/0/'
		File dir = new File(Generator.getIconDir() + "/" + stack.getItem().getRegistryName().getNamespace() + "/" + stack.getItem().getRegistryName().getPath());

		// Try and mkdirs
		dir.mkdirs();

		if (dir.isDirectory()) {
			// Directory exists was a success then continue
			ScreenshotRenderer.saveTrimmedScreenshot(new File(dir, "/" + 16 * scale + ".png"), (16 * scale), (16 * scale), new Color(COLOR_R, COLOR_G, COLOR_B));
		} else {
			GrimLog.error(Generator.GENERATOR_NAME, "Directory could not be found " + dir);
		}

	}

	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);

	private static FloatBuffer setColorBuffer(float r, float g, float b, float a) {
		colorBuffer.clear();
		colorBuffer.put(r).put(g).put(b).put(a);
		colorBuffer.flip();
		return colorBuffer;
	}

	private static void drawItemStack(ItemStack stack, int x, int y) {
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.lightfv(GL11.GL_LIGHT1, GL11.GL_AMBIENT, setColorBuffer(.1f, .1f, .1f, 1.0F));
		GlStateManager.lightModelfv(GL11.GL_LIGHT_MODEL_AMBIENT, setColorBuffer(.5f, .5f, .5f, 1.0F));
		OpenGlHelper.glMultiTexCoord2f(OpenGlHelper.GL_TEXTURE1, (float) 240 / 1.0F, (float) 240 / 1.0F);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		// Don't blend so that we forget alpha
		RendererHelper.renderItemAndEffectIntoGUI(stack, x, y, false);
		RenderHelper.disableStandardItemLighting();
	}

}
