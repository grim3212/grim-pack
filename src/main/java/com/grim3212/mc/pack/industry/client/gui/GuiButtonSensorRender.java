package com.grim3212.mc.pack.industry.client.gui;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.TooltipHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiButtonSensorRender extends GuiButton {

	private final ResourceLocation texture = new ResourceLocation(GrimPack.modID + ":" + "textures/gui/icons.png");
	private boolean shouldRender = false;

	public GuiButtonSensorRender(int id, int x, int y, boolean shouldRender) {
		super(id, x, y, 20, 20, "");
		this.shouldRender = shouldRender;
	}

	@Override
	public void drawButton(Minecraft mc, int x, int y) {
		super.drawButton(mc, x, y);

		if (this.visible) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(texture);
			int xDraw = 16;
			int yDraw = 0;

			if (!shouldRender) {
				xDraw += 16;
			}
			this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, xDraw, yDraw, 16, 16);
		}
	}

	@Override
	public void drawButtonForegroundLayer(int mouseX, int mouseY) {
		TooltipHelper.drawHoveringText(I18n.format("grimpack.industry.sensor.shouldRender"), mouseX, mouseY, 120, Minecraft.getMinecraft().fontRendererObj);
	}

	public void setShouldRender(boolean shouldRender) {
		this.shouldRender = shouldRender;
	}
}
