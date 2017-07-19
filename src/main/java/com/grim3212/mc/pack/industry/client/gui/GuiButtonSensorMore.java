package com.grim3212.mc.pack.industry.client.gui;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.TooltipHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiButtonSensorMore extends GuiButton {

	private final ResourceLocation texture = new ResourceLocation(GrimPack.modID + ":" + "textures/gui/icons.png");

	public GuiButtonSensorMore(int id, int x, int y) {
		super(id, x, y, 20, 20, "");
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		super.drawButton(mc, mouseX, mouseY, partialTicks);

		if (this.visible) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(texture);
			int xDraw = 48;
			int yDraw = 0;

			this.drawTexturedModalRect(this.x + 2, this.y + 2, xDraw, yDraw, 16, 16);
		}
	}

	@Override
	public void drawButtonForegroundLayer(int mouseX, int mouseY) {
		TooltipHelper.drawHoveringText(I18n.format("grimpack.industry.sensor.moreOptions"), mouseX, mouseY, 120, Minecraft.getMinecraft().fontRenderer);
	}

}
