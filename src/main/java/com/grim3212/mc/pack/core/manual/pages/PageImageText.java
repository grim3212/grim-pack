package com.grim3212.mc.pack.core.manual.pages;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.gui.GuiManualPage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class PageImageText extends Page {

	private ResourceLocation imageLocation;
	private String location;
	private float alpha;

	public PageImageText(String pageName, String location) {
		this(pageName, location, 1.0F);
	}

	public PageImageText(String pageName, String location, float alpha) {
		super(pageName, true);
		this.location = location;
		this.alpha = alpha;
	}

	@Override
	public void setup() {
		this.imageLocation = new ResourceLocation(GrimPack.modID + ":textures/gui/manual/" + location);

		// Only add if there was no original images
		if (this.getImageUrls().isEmpty()) {
			this.addImageUrl("assets/grimpack/" + this.imageLocation.getPath().substring(13));
		}
	}

	@Override
	public void drawScreen(GuiManualPage gui, int mouseX, int mouseY) {
		super.drawScreen(gui, mouseX, mouseY);

		Minecraft.getInstance().textureManager.bindTexture(imageLocation);

		GL11.glColor4f(1F, 1F, 1F, alpha);
		((GuiScreen) gui).drawTexturedModalRect(gui.getX(), gui.getY(), 0, 0, gui.getManualWidth(), gui.getManualHeight());

		GL11.glColor4f(0F, 0F, 0F, 1F);

		int x = gui.getX() + 15;
		int y = gui.getY() + 28;
		PageInfo.drawText(x, y, this.getInfo());
	}
}