package com.grim3212.mc.pack.core.manual.pages;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.manual.gui.GuiManualPage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
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
	 * @param x        X position
	 * @param y        Y position
	 * @param pageInfo Where to find the info to localize and break up into colors
	 *                 and line breaks
	 * 
	 *                 Modified version of Botania PageText by Vazkii
	 * 
	 *                 https://github.com/Vazkii/Botania/blob/master/src/main/java/vazkii/botania/common/lexicon/page/PageText.java
	 */
	public static void drawText(int x, int y, String pageInfo) {
		GlStateManager.pushMatrix();
		GlStateManager.scalef(0.8f, 0.8f, 1.0f);
		GlStateManager.translatef(34f, 14f, 0f);

		int width = 210;

		String pageText = I18n.format(pageInfo).replaceAll("<f>", "\u00a7");
		String[] paragraphs = pageText.split("<br>");

		FontRenderer renderer = Minecraft.getInstance().fontRenderer;

		List<List<String>> lines = new ArrayList<>();

		String controlCodes;
		for (String s : paragraphs) {
			List<String> words = new ArrayList<>();
			String lineStr = "";
			String[] tokens = s.split(" ");
			for (String token : tokens) {
				String prev = lineStr;
				String spaced = token + " ";
				lineStr += spaced;

				controlCodes = toControlCodes(getControlCodes(prev));
				if (renderer.getStringWidth(lineStr) > width) {
					lines.add(words);
					lineStr = controlCodes + spaced;
					words = new ArrayList<>();
				}

				words.add(controlCodes + token);
			}

			if (!lineStr.isEmpty())
				lines.add(words);
			lines.add(new ArrayList<>());
		}

		for (List<String> words : lines) {
			int xPos = x;

			for (String s : words) {
				renderer.drawString(s, xPos, y, 0);
				xPos += renderer.getStringWidth(s) + 4;
			}

			y += 10;
		}

		GlStateManager.popMatrix();
	}

	private static String getControlCodes(String s) {
		String controls = s.replaceAll("(?<!\u00a7)(.)", "");
		return controls.replaceAll(".*r", "r");
	}

	private static String toControlCodes(String s) {
		return s.replaceAll(".", "\u00a7$0");
	}
}