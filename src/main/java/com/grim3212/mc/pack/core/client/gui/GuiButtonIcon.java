package com.grim3212.mc.pack.core.client.gui;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.TooltipHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonIcon extends GuiButton {

	private final ResourceLocation texture = new ResourceLocation(GrimPack.modID + ":" + "textures/gui/icons.png");

	private int iconX;
	private int iconY;
	private String tooltip = "";

	public GuiButtonIcon(int id, int x, int y, int iconX, int iconY) {
		super(id, x, y, 20, 20, "");
		this.iconX = iconX;
		this.iconY = iconY;
	}

	public GuiButtonIcon(int id, int x, int y, int iconX, int iconY, String tooltip) {
		this(id, x, y, iconX, iconY);
		this.tooltip = tooltip;
	}

	@Override
	public void drawButton(Minecraft mc, int x, int y) {
		super.drawButton(mc, x, y);

		if (this.visible) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(texture);

			this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, iconX, iconY, 16, 16);
		}
	}

	@Override
	public void drawButtonForegroundLayer(int mouseX, int mouseY) {
		if (!tooltip.isEmpty())
			TooltipHelper.drawHoveringText(tooltip, mouseX, mouseY, 120, Minecraft.getMinecraft().fontRendererObj);
	}

}
