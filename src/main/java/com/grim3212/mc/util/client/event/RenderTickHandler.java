package com.grim3212.mc.util.client.event;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.util.GrimUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class RenderTickHandler {

	public static float zLevel = 0.0F;
	private int timeWidth;
	public static boolean enabled = false;
	public int scrollMax = -200;
	public int scrollHeight = scrollMax;
	public int scrollMin = -177;
	public static boolean retracting = false;
	public Minecraft game = Minecraft.getMinecraft();

	private static final ResourceLocation resourceLocation = new ResourceLocation(GrimUtil.modID, "textures/gui/carcass.png");

	@SubscribeEvent
	public void tick(RenderTickEvent event) {
		if (!enabled && !retracting)
			return;

		timeWidth = game.displayWidth;
		ScaledResolution scaledresolution = new ScaledResolution(game);
		timeWidth = scaledresolution.getScaledWidth();
		FontRenderer fontrenderer = game.fontRendererObj;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		game.renderEngine.bindTexture(resourceLocation);
		drawTexturedModalRect(timeWidth - 245, scrollHeight, 0, 0, 176 - 43, 200);
		drawTexturedModalRect(timeWidth - 325, scrollHeight, 0, 0, 176 - 50, 200);
		Date today;
		String result;
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("MMMM d - HH:mm:ss - z");
		today = new Date();
		result = formatter.format(today);
		fontrenderer.drawStringWithShadow((new StringBuilder()).append("").append(result).toString(), timeWidth - 277, scrollHeight + 180, 0xc6c6c6);
		if (enabled) {
			if (scrollHeight < scrollMin)
				scrollHeight++;
		} else {
			if (scrollHeight > scrollMax)
				scrollHeight--;
			if (scrollHeight == scrollMax)
				retracting = false;
		}
		return;
	}

	public void drawTexturedModalRect(int i, int j, int k, int l, int i1, int j1) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer renderer = tessellator.getWorldRenderer();
		renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		renderer.pos(i + 0, j + j1, zLevel).tex((float) (k + 0) * f, (float) (l + j1) * f1).endVertex();
		renderer.pos(i + i1, j + j1, zLevel).tex((float) (k + i1) * f, (float) (l + j1) * f1).endVertex();
		renderer.pos(i + i1, j + 0, zLevel).tex((float) (k + i1) * f, (float) (l + 0) * f1).endVertex();
		renderer.pos(i + 0, j + 0, zLevel).tex((float) (k + 0) * f, (float) (l + 0) * f1).endVertex();
		tessellator.draw();
	}
}
