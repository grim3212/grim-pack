package com.grim3212.mc.pack.core.manual.pages;

import com.grim3212.mc.pack.core.manual.gui.GuiManualPage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;

public class PageInfo extends Page {

	public PageInfo(String pageName) {
		super(pageName, false);
	}

	@Override
	public void drawScreen(GuiManualPage gui, int mouseX, int mouseY) {
		super.drawScreen(gui, mouseX, mouseY);
		drawText(gui.getX() + 15, gui.getY() + 28, this.getInfo());
	}

	/**
	 * Draws text to the screen formatted and with color codes and line breaks
	 * removed and changed
	 * 
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 * @param pageInfo
	 *            Where to find the info to localize and break up into colors
	 *            and line breaks
	 */
	public static void drawText(int x, int y, String pageInfo) {
		String pageText = I18n.format(pageInfo).replaceAll("<f>", "\u00a7");
		String[] paragraphs = pageText.split("<br>");

		FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
		boolean unicode = renderer.getUnicodeFlag();
		renderer.setUnicodeFlag(true);

		for (int i = 0; i < paragraphs.length; i++) {
			int length = renderer.getWordWrappedHeight(paragraphs[i], 162);
			renderer.drawSplitString(paragraphs[i], x, y, 162, 0);

			y += length + 10;
		}

		renderer.setUnicodeFlag(unicode);
	}
}