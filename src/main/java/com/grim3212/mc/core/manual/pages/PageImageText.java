package com.grim3212.mc.core.manual.pages;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.core.manual.gui.GuiSubSectionPage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class PageImageText extends Page {

	private ResourceLocation imageLocation;
	private String location;

	public PageImageText(String pageName, String location) {
		super(pageName, true);
		this.location = location;
	}

	@Override
	public void setup() {
		this.imageLocation = new ResourceLocation(getModid() + ":textures/gui/manual/" + location);
	}

	@Override
	public void drawScreen(GuiSubSectionPage gui, int mouseX, int mouseY) {
		super.drawScreen(gui, mouseX, mouseY);

		int x = gui.getX() + 15;
		int y = gui.getY() + 28;
		PageInfo.drawText(x, y, this.getPageName());

		TextureManager render = Minecraft.getMinecraft().renderEngine;
		render.bindTexture(imageLocation);

		GL11.glColor4f(1F, 1F, 1F, 1F);
		((GuiScreen) gui).drawTexturedModalRect(gui.getX(), gui.getY(), 0, 0, gui.getManualWidth(), gui.getManualHeight());
	}
}