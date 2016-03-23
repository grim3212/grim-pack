package com.grim3212.mc.core.manual.pages;

import com.grim3212.mc.core.manual.gui.GuiSubSectionPage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

public class PageInfo extends Page {

	public PageInfo(String pageName) {
		super(pageName, false);
	}

	@Override
	public void drawScreen(GuiSubSectionPage gui, int mouseX, int mouseY) {
		super.drawScreen(gui, mouseX, mouseY);
		drawText(gui.getX() + 15, gui.getY() + 28, this.getPageName());
	}

	public static void drawText(int x, int y, String pageInfo) {
		String pageText = StatCollector.translateToLocal(pageInfo).replaceAll("<f>", "\u00a7");
		String[] paragraphs = pageText.split("<br>");

		FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;
		boolean unicode = renderer.getUnicodeFlag();
		renderer.setUnicodeFlag(true);

		for (int i = 0; i < paragraphs.length; i++) {

			int length = renderer.splitStringWidth(paragraphs[i], 162);
			renderer.drawSplitString(paragraphs[i], x, y, 162, 0);

			y += length + 10;
		}

		renderer.setUnicodeFlag(unicode);
	}
}